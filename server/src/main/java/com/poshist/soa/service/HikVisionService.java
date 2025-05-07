package com.poshist.soa.service;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.poshist.soa.vo.HikBaseVO;
import com.poshist.soa.vo.HikOrgVO;
import com.poshist.soa.vo.HikPersonVO;
import com.poshist.student.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: tank
 * @Date: 2025/5/7
 */
@Service
@Slf4j
public class HikVisionService {
    private final ArtemisConfig config;
    @Value("${hikvision.baseUrl}")
    private String baseUrl;
    @Value("${hikvision.appKey}")
    private String appKey;
    @Value("${hikvision.appSecret}")
    private String appSecret;
    private static final String ARTEMIS_PATH = "/artemis";
    private final String orgCode = "pEIVnpxkeK2x1du8cz3ximz3iZnkrtgX";

    public HikVisionService() {
        config = new ArtemisConfig();
        // 代理API网关nginx服务器ip端口
        config.setHost(baseUrl);
        // 秘钥appkey
        config.setAppKey(appKey);
        // 秘钥appSecret
        config.setAppSecret(appSecret);
        try {
            addOrg();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void addPerson(Student student, String face) throws Exception {
        Map<String, String> path = new HashMap<>(1);
        if (StringUtils.isNotBlank(student.getFaceId())) {
            Map<String, String> body = new HashMap<>(2);
            body.put("faceId", student.getFaceId());
            body.put("faceData", face);
            String url = ARTEMIS_PATH + "/api/resource/v1/face/single/update";
            path.put("https://", url);
            String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(body), null, null, "application/json");
            log.info("updateFace :{}", rs);
        } else {
            HikPersonVO person = new HikPersonVO();
            person.setPersonName(student.getName());
            person.setPersonId("90" + StringUtils.leftPad(student.getId() + "", 10, '0'));
            person.setGender("1");
            HikPersonVO.FaceData faceData = new HikPersonVO.FaceData();
            faceData.setFaceData(face);
            List<HikPersonVO.FaceData> faces = new ArrayList<>(1);
            faces.add(faceData);
            person.setFaces(faces);
            person.setOrgIndexCode(orgCode);
            String url = ARTEMIS_PATH + "/api/resource/v2/person/single/add";
            path.put("https://", url);
            String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(person), null, null, "application/json");
            log.info("addPerson :{}", rs);
            Map rsMap = JSONUtil.toBean(rs, Map.class);
            String faceId = ((Map) rsMap.get("data")).get("faceId").toString();
            student.setFaceId(faceId);
        }

    }

    public void addOrg() throws Exception {
        Map<String, Object> body = new HashMap<>(1);
        Map<String, String> path = new HashMap<>();
        body.put("orgIndexCodes", new String[]{orgCode});
        String url = ARTEMIS_PATH + "/api/resource/v1/org/orgIndexCodes/orgInfo";
        path.put("https://", url);
        String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(body), null, null, "application/json");
        log.info("orgInfo :{}", rs);
        Map rsMap = JSONUtil.toBean(rs, Map.class);
        if (null != ((Map) rsMap.get("data")).get("total")) {
            int total = Integer.parseInt(((Map) rsMap.get("data")).get("total").toString());
            if (total == 0) {
                url = ARTEMIS_PATH + "/api/resource/v1/org/rootOrg";
                path.put("https://", url);
                rs = ArtemisHttpUtil.doPostStringArtemis(config, path, null, null, null, "application/json");
                log.info("rootOrg:{}", rs);
                HikBaseVO<HikOrgVO> baseVO = JSONUtil.toBean(rs, new TypeReference<HikBaseVO<HikOrgVO>>() {
                }, true);
                String rootIndexCode = baseVO.getData().getOrgIndexCode();
                HikOrgVO orgVO = new HikOrgVO();
                orgVO.setOrgName("请销假组织");
                orgVO.setOrgIndexCode(orgCode);
                orgVO.setClientId(1L);
                orgVO.setParentIndexCode(rootIndexCode);
                List<HikOrgVO> orgList = new ArrayList<>(1);
                orgList.add(orgVO);
                url = ARTEMIS_PATH + "/api/resource/v1/org/batch/add";
                path.put("https://", url);
                rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(body), null, null, "application/json");
                log.info("orgAdd:{}", rs);
            }
        }

    }

    public static void main(String[] args) {
        String str = "{\n" + "    \"code\": \"0\",\n" + "    \"msg\": \"SUCCESS\",\n" + "    \"data\": {\n" + "        \"total\": 1,\n" + "        \"list\": [\n" + "            "
                + "{\n" + "                \"orgIndexCode\": \"10935e1d-9a84-46ff-aa17-cdd239ad7e0b\",\n" + "                \"organizationCode\": \"as8d70890102001du21\",\n" +
                " " + "               \"orgName\": \"默认部门\",\n" + "                \"orgPath\": \"@10935e1d-9a84-46ff-aa17-cdd239ad7e0b@\",\n" + "                " +
                "\"parentOrgIndexCode\": \"0\",\n" + "                \"sort\": 1,\n" + "                \"available\": true,\n" + "                \"leaf\": true,\n" + "       "
                + "         \"createTime\": \"2019-08-06T14:01:17.839+0800\",\n" + "                \"updateTime\": \"2019-08-07T14:01:17.839+0800\",\n" + "                " +
                "\"status\": 0\n" + "            }\n" + "        ]\n" + "    }\n" + "}\n";
        Map rs = JSONUtil.toBean(str, Map.class);
        System.out.println(rs.get("data"));
    }

}
