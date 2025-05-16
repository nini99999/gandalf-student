package com.poshist.soa.service;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * @author: tank
 * @Date: 2025/5/7
 */
@Service
@Slf4j
public class HikVisionService {
    private ArtemisConfig config;
    @Value("${hikvision.baseUrl}")
    private String baseUrl;
    @Value("${hikvision.appKey}")
    private String appKey;
    @Value("${hikvision.appSecret}")
    private String appSecret;
    @Value("${hikvision.doors}")
    private String[] doors;
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
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @PostConstruct
    public void init() {
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

    public HikVisionService() {

    }

    @Scheduled(cron = "0 0/10 * * * ?")
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
        String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(body), null, null, "application/json");
        log.info("event :{}", rs);
        HikBaseVO<HikViaVO> baseVO = MAPPER.readValue(rs, new TypeReference<HikBaseVO<HikViaVO>>() {
        });
        for (HikViaVO.Via viaVo : baseVO.getData().getList()) {
            if (StringUtils.startsWith(viaVo.getPersonId(), "909000")) {
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
    @Async
    public void sendDoor(Leave leave) throws Exception {
        Map<String, String> path = new HashMap<>(1);
        Map<String, Object> param = new HashMap<>(1);
        HikLeaveVO hikLeave = new HikLeaveVO();
        HikLeaveVO.PersonData personData = new HikLeaveVO.PersonData();
        personData.setIndexCodes(new String[]{"909" + StringUtils.leftPad(leave.getStudent().getId() + "", 10, '0')});
        personData.setPersonDataType("person");
        List<HikLeaveVO.PersonData> personDataList = new ArrayList<>();
        personDataList.add(personData);
        hikLeave.setPersonDatas(personDataList);
        List<HikLeaveVO.ResourceInfo> resourceInfoList = new ArrayList<>();
        for (String door : doors) {
            HikLeaveVO.ResourceInfo resourceInfo = new HikLeaveVO.ResourceInfo();
            resourceInfo.setResourceIndexCode(door);
            resourceInfo.setResourceType("door");
            resourceInfoList.add(resourceInfo);
        }
        hikLeave.setResourceInfos(resourceInfoList);
        hikLeave.setStartTime(DateUtil.formatDate(leave.getEstimateStartTime()) + "T00:00:01.000+08:00");
        hikLeave.setEndTime(DateUtil.formatDate(leave.getEstimateEndTime()) + "T23:59:59.000+08:00");
        String url = ARTEMIS_PATH + "/api/acps/v1/auth_config/add";
        path.put("https://", url);
        log.info("leave req:{}", MAPPER.writeValueAsString(hikLeave));
        String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(hikLeave), null, null, "application/json");
        log.info("leave resp:{}", rs);
        extracted(  hikLeave, 1);
        sleep(300000);
        extracted( hikLeave, 4);
//        url = ARTEMIS_PATH + "/api/acps/v1/authDownload/configuration/shortcut";
//        path.put("https://", url);
//        HikDownloadVO download = new HikDownloadVO();
//        download.setTaskType(4);
//        download.setResourceInfos(hikLeave.getResourceInfos());
//        rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(download), null, null, "application/json");
//        log.info("download:{}", rs);

    }

    private void extracted( HikLeaveVO hikLeave, Integer type) throws Exception {
        String url;
        String rs;
        Map<String, String> path = new HashMap<>(1);
        Map<String, Object> param = new HashMap<>(1);
        url = ARTEMIS_PATH + "/api/acps/v1/download/configuration/task/add";
        path.put("https://", url);
        param.put("taskType", type);
        log.info("task add req:{}", MAPPER.writeValueAsString(param));
        rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(param), null, null, "application/json");
        log.info("task add resp:{}", rs);
        url = ARTEMIS_PATH + "/api/acps/v1/download/configuration/data/add";
        path.put("https://", url);
        param = new HashMap<>(2);
        Map rsMap = MAPPER.readValue(rs, Map.class);
        String taskId = ((Map) rsMap.get("data")).get("taskId").toString();
        param.put("taskId", taskId);
        param.put("resourceInfos", hikLeave.getResourceInfos());
        log.info("data add req:{}", MAPPER.writeValueAsString(param));
        rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(param), null, null, "application/json");
        log.info("data add resp:{}", rs);
        url = ARTEMIS_PATH + "/api/acps/v1/authDownload/task/start";
        path.put("https://", url);
        param = new HashMap<>(1);
        param.put("taskId", taskId);
        log.info("task start req:{}", MAPPER.writeValueAsString(param));
        rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(param), null, null, "application/json");
        log.info("task start resp:{}", rs);
    }

    public void sendPerson(Student student, String face) throws Exception {
        Map<String, String> path = new HashMap<>(1);
        if (StringUtils.isNotBlank(student.getFaceId())) {
            Map<String, String> body = new HashMap<>(2);
            body.put("faceId", student.getFaceId());
            body.put("faceData", "data:image/png;base64," + face);
            String url = ARTEMIS_PATH + "/api/resource/v1/face/single/update";
            path.put("https://", url);
            String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(body), null, null, "application/json");
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
            person.setJobNo(person.getPersonId());
            String url = ARTEMIS_PATH + "/api/resource/v2/person/single/add";
            path.put("https://", url);
            String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(person), null, null, "application/json");
            log.info("addPerson :{}", rs);
            Map rsMap = MAPPER.readValue(rs, Map.class);
            String faceId = ((Map) rsMap.get("data")).get("faceId").toString();
            student.setFaceId(faceId);
            HikCardVO card = new HikCardVO();
            card.setStartDate("2025-01-01");
            card.setEndDate("2037-12-01");
            HikCardVO.Card cardInfo = new HikCardVO.Card();
            cardInfo.setCardType(1);
            cardInfo.setPersonId(person.getPersonId());
            cardInfo.setOrgIndexCode(orgCode);
            cardInfo.setCardNo("1" + StringUtils.leftPad(student.getId() + "", 8, '0'));
            List<HikCardVO.Card> cardList = new ArrayList<>(1);
            cardList.add(cardInfo);
            card.setCardList(cardList);
            url = ARTEMIS_PATH + "/api/cis/v1/card/bindings";
            path.put("https://", url);
            rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(card), null, null, "application/json");
            log.info("addCard :{}", rs);
        }

    }

    public void addOrg() throws Exception {
        Map<String, Object> body = new HashMap<>(1);
        Map<String, String> path = new HashMap<>();
        body.put("orgIndexCodes", new String[]{orgCode});
        String url = ARTEMIS_PATH + "/api/resource/v1/org/orgIndexCodes/orgInfo";
        path.put("https://", url);
        String rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(body), null, null, "application/json");
        log.info("orgInfo :{}", rs);
        Map rsMap = MAPPER.readValue(rs, Map.class);
        if (null != ((Map) rsMap.get("data")).get("total")) {
            int total = Integer.parseInt(((Map) rsMap.get("data")).get("total").toString());
            if (total == 0) {
                url = ARTEMIS_PATH + "/api/resource/v1/org/rootOrg";
                path.put("https://", url);
                rs = ArtemisHttpUtil.doPostStringArtemis(config, path, null, null, null, "application/json");
                log.info("rootOrg:{}", rs);
                HikBaseVO<HikOrgVO> baseVO = MAPPER.readValue(rs, new TypeReference<HikBaseVO<HikOrgVO>>() {
                });
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
                log.info("orgAdd req:{}", MAPPER.writeValueAsString(orgList));
                rs = ArtemisHttpUtil.doPostStringArtemis(config, path, MAPPER.writeValueAsString(orgList), null, null, "application/json");
                log.info("orgAdd resp:{}", rs);
            }
        }

    }

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println( (StringUtils.startsWith("6d6d351d441868145280ab73959f5", "909")));
        Leave leave = new Leave();
        leave.setStartDate(new Date());
        leave.setEndDate(new Date());
        Student student = new Student();
        student.setId(4715L);
        leave.setStudent(student);
        HikLeaveVO hikLeave = new HikLeaveVO();
        HikLeaveVO.PersonData personData = new HikLeaveVO.PersonData();
        personData.setIndexCodes(new String[]{"909" + StringUtils.leftPad(leave.getStudent().getId() + "", 10, '0')});
        personData.setPersonDataType("person");
        List<HikLeaveVO.PersonData> personDataList = new ArrayList<>();
        personDataList.add(personData);
        hikLeave.setPersonDatas(personDataList);
        List<HikLeaveVO.ResourceInfo> resourceInfoList = new ArrayList<>();
        String[] doors = new String[]{"52c6abdda4f840c49a1b438a8f181dd5", "891e682b898f40de8d525d3f472d6659", "dbe988731975499face3dcfe60b88335",
                "4ca4b23f6e714941be6d108fff746483",
                "ab618fa518094b0b8b8421ea7ad9384e", "d0a10bb08f3b49d2847094998686969f", "c0eaece27672419d87d646aec21646e3", "1281843d25544e379ce6a49a244a4b8e",
                "7963d2d68a834c9687c26df0615b3e17", "289917ad5e014c8a8f262982c0dab69e", "a4f13385405f48b6ac148194b2144a36", "01388d3b48394f109ac33638b3d8c5ef"};
        for (String door : doors) {
            HikLeaveVO.ResourceInfo resourceInfo = new HikLeaveVO.ResourceInfo();
            resourceInfo.setResourceIndexCode(door);
            resourceInfo.setResourceType("door");
            resourceInfoList.add(resourceInfo);
        }
        hikLeave.setResourceInfos(resourceInfoList);
        hikLeave.setStartTime(DateUtil.formatDate(leave.getEstimateStartTime()) + "T00:00:01.000+08:00");
        hikLeave.setEndTime(DateUtil.formatDate(leave.getEstimateEndTime()) + "T23:59:59.000+08:00");
        System.out.println(MAPPER.writeValueAsString(hikLeave));
    }

}
