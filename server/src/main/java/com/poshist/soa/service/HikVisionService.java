package com.poshist.soa.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.poshist.soa.entity.Receive;
import com.poshist.soa.entity.Via;
import com.poshist.soa.repository.ReceiveDao;
import com.poshist.soa.repository.ViaDao;
import com.poshist.soa.vo.*;
import com.poshist.student.entity.Leave;
import com.poshist.student.entity.Student;
import com.poshist.student.repository.StudentDao;
import com.poshist.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private ViaDao viaDao;
    @Autowired
    private ReceiveDao receiveDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StudentService studentService;
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

    public void event() throws Exception {
        Map<String, String> path = new HashMap<>(1);
        Map<String, Object> body = new HashMap<>(5);
        body.put("pageNo", 1);
        body.put("pageSize", 1000);
        Date date = new Date();
        body.put("startTime", DateUtil.format(DateUtil.offsetMinute(date, -10), "yyyy-MM-dd'T'HH:mm:ss") + "+08:00");
        body.put("endTime", DateUtil.format(date, "yyyy-MM-dd'T'HH:mm:ss") + "+08:00");
        body.put("eventTypes", new Integer[]{196893});
        String url = ARTEMIS_PATH + "/api/acs/v2/door/events";
        path.put("https://", url);
        String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(body), null, null, "application/json");
        log.info("event :{}", rs);
        HikBaseVO<HikViaVO> baseVO = JSONUtil.toBean(rs, new TypeReference<HikBaseVO<HikViaVO>>() {
        }, true);
        for (HikViaVO.Via viaVo : baseVO.getData().getList()) {
            if (StringUtils.startsWith(viaVo.getPersonId(), "909")) {
                Long id = Long.valueOf(viaVo.getPersonId().substring(3));
                Optional<Student> optionalStudent = studentDao.findById(id);
                if (optionalStudent.isPresent()) {
                    Receive receive = new Receive();
                    receive.setReceiveTime(new Date());
                    receive.setStatus(1);
                    receiveDao.save(receive);
                    Via via = new Via();
                    via.setStudentId(id);
                    via.setCardCode(optionalStudent.get().getCardCode());
                    via.setViaTime(DateUtil.parseUTC(viaVo.getReceiveTime()));
                    via.setCardType(0);
                    via.setGateId(viaVo.getDoorIndexCode());
                    via.setGateInfo(viaVo.getDoorName());
                    via.setReceive(receive);
                    via.setStatus(1);
                    via.setViaResult(0);
                    via.setViaType(viaVo.getInAndOutType());
                    studentService.studentVia(via);
                    viaDao.save(via);
                }
            }

        }
    }

    public void sendDoor(Leave leave) throws Exception {
        Map<String, String> path = new HashMap<>(1);
        Map<String, Object> body = new HashMap<>(2);
        body.put("pageNo", 1);
        body.put("pageSize", 500);
        String url = ARTEMIS_PATH + "/api/resource/v2/door/search";
        path.put("https://", url);
        String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(body), null, null, "application/json");
        log.info("getDoor :{}", rs);
        HikBaseVO<HikDoorVO> baseVO = JSONUtil.toBean(rs, new TypeReference<HikBaseVO<HikDoorVO>>() {
        }, true);
        HikLeaveVO hikLeave = new HikLeaveVO();
        HikLeaveVO.PersonData personData = new HikLeaveVO.PersonData();
        personData.setIndexCodes(new String[]{"909" + StringUtils.leftPad(leave.getStudent().getId() + "", 10, '0')});
        personData.setPersonDataType("person");
        List<HikLeaveVO.PersonData> personDataList = new ArrayList<>();
        personDataList.add(personData);
        hikLeave.setPersonDatas(personDataList);
        List<HikLeaveVO.ResourceInfo> resourceInfoList = new ArrayList<>();
        for (HikDoorVO.Door door : baseVO.getData().getList()) {
            HikLeaveVO.ResourceInfo resourceInfo = new HikLeaveVO.ResourceInfo();
            resourceInfo.setResourceIndexCode(door.getIndexCode());
            resourceInfo.setResourceType("door");
            resourceInfoList.add(resourceInfo);
        }
        hikLeave.setResourceInfos(resourceInfoList);
        hikLeave.setStartTime(DateUtil.formatDate(leave.getStartDate()) + "T00:00:00+08:00");
        hikLeave.setEndTime(DateUtil.formatDate(leave.getEndDate()) + "T23:59:59+08:00");
        url = ARTEMIS_PATH + "/api/acps/v1/auth_config/add";
        path.put("https://", url);
        rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(hikLeave), null, null, "application/json");
        log.info("leave:{}", rs);
        url = ARTEMIS_PATH + "/api/acps/v1/authDownload/configuration/shortcut";
        path.put("https://", url);
        HikDownloadVO download = new HikDownloadVO();
        download.setTaskType(4);
        download.setResourceInfos(hikLeave.getResourceInfos());
        rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(download), null, null, "application/json");
        log.info("download:{}", rs);

    }

    public void sendPerson(Student student, String face) throws Exception {
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
            person.setPersonId("909" + StringUtils.leftPad(student.getId() + "", 10, '0'));
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
            HikCardVO card = new HikCardVO();
            card.setStartDate("2025-01-01");
            card.setEndDate("2037-21-01");
            HikCardVO.Card cardInfo = new HikCardVO.Card();
            cardInfo.setCardType(1);
            cardInfo.setPersonId(person.getPersonId());
            cardInfo.setOrgIndexCode(orgCode);
            cardInfo.setCardNo("80" + StringUtils.leftPad(student.getId() + "", 10, '0'));
            List<HikCardVO.Card> cardList = new ArrayList<>(1);
            cardList.add(cardInfo);
            card.setCardList(cardList);
            url = ARTEMIS_PATH + "/api/cis/v1/card/bindings";
            path.put("https://", url);
            rs = ArtemisHttpUtil.doPostStringArtemis(config, path, JSONUtil.toJsonStr(card), null, null, "application/json");
            log.info("addCard :{}", rs);
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
        System.out.println(DateUtil.parseUTC("2019-11-16T15:44:33+08:00"));
    }

}
