package com.poshist.soa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poshist.car.entity.CarControl;
import com.poshist.car.service.CarService;
import com.poshist.common.Constant;
import com.poshist.common.utils.HttpUtils;
import com.poshist.soa.entity.Receive;
import com.poshist.soa.entity.Send;
import com.poshist.soa.entity.Via;
import com.poshist.soa.repository.ReceiveDao;
import com.poshist.soa.repository.SendDao;
import com.poshist.soa.repository.ViaDao;
import com.poshist.soa.vo.*;
import com.poshist.student.entity.Leave;
import com.poshist.student.entity.Student;
import com.poshist.student.service.StudentService;
import com.poshist.sys.entity.Dictionary;
import com.poshist.sys.repository.DictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class GateService {
    @Autowired
    private ReceiveDao receiveDao;
    @Autowired
    private ViaDao viaDao;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CarService carService;
    @Value("${sj.server}")
    private String sjServer;
    @Value("${sj.code}")
    private String sjCode;
    @Autowired
    private SendDao sendDao;
    @Autowired
    private DictionaryDao dictionaryDao;
    public BackVO receivePersonVia(ReceiveVO receiveVO) {
        BackVO backVO = receiveViaInfo(receiveVO, 0);
        return backVO;
    }

    public BackVO receiveCarVia(ReceiveVO receiveVO) {
        BackVO backVO = new BackVO();
        backVO.setMsgCode("0000");
        backVO.setMsg(ExceptionEnum.getExceptionEnum("0000").getMsg());
        return backVO;
    }

    /**
     * 处理门禁信息
     *
     * @param receiveVO
     * @param type
     * @return
     */
    private BackVO receiveViaInfo(ReceiveVO receiveVO, Integer type) {
        BackVO backVO = new BackVO();
        String msgCode = verifyBaseInfo(receiveVO);
        Date now = new Date();
        backVO.setReceiveTime(now);
        backVO.setClientId(receiveVO.getClientId());
        Receive receive = new Receive();
        receive = receiveVO.toDTO(receive);
        receive.setStatus(0);
        receive.setReceiveTime(now);
        receiveDao.save(receive);
        backVO.setId(receive.getId());
        if (!"0000".equals(msgCode)) {
            backVO.setMsg(ExceptionEnum.getExceptionEnum(msgCode).getMsg());
            backVO.setMsgCode(msgCode);
            return backVO;
        }
        ObjectMapper mapper = new ObjectMapper();
        String data = receiveVO.getData();
        try {
         //   data = AESUtils.decrypt(data);
            receive.setDecryptData(mapper.writeValueAsString(receiveVO));
            receiveDao.save(receive);
        } catch (Exception e) {
            e.printStackTrace();
            backVO.setMsgCode("0001");
            backVO.setMsg(ExceptionEnum.getExceptionEnum("0001").getMsg());
        }

        Via via = new Via();
        try {
            via = mapper.readValue(data, via.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            backVO.setMsgCode("1001");
            backVO.setMsg(ExceptionEnum.getExceptionEnum("1001").getMsg());
            return backVO;
        }
        via.setCardType(type);
        via.setReceive(receive);
        via.setStatus(0);
        viaDao.save(via);
        if (0 == via.getCardType()) {
            studentService.studentVia(via);
            via.setStatus(1);
            viaDao.save(via);
        }else{
            carService.carVia(via);
            via.setStatus(1);
            viaDao.save(via);
        }
        receive.setStatus(1);
        receiveDao.save(receive);
        backVO.setMsgCode("0000");
        backVO.setMsg(ExceptionEnum.getExceptionEnum("0000").getMsg());
        return backVO;
    }

    private String verifyBaseInfo(ReceiveVO receiveVO) {
        String result = "0000";
//        if (!"000001".equals(receiveVO.getClientCode())) {
//            return "0002";
//        }
        if (null == receiveVO.getSendTime()) {
            return "0003";
        }
        return result;
    }
    public void sendDoor(Leave leave)throws IOException {
          DoorcredentialVO doorcredentialVO=new DoorcredentialVO(leave);
          if(null==doorcredentialVO.getEquipmentIdList()){
              List<EquipmentVO> equipmentVOS=new ArrayList<>();
              List<Dictionary> dictionaries=dictionaryDao.getAllByStatusAndTypeOrderById(Constant.VALID,"1201");
              for(Dictionary dictionary:dictionaries){
                  EquipmentVO equipmentVO=new EquipmentVO();
                  equipmentVO.setEquipmentId(dictionary.getName());
                  equipmentVO.setIsDeleted("false");
                  equipmentVOS.add(equipmentVO);
              }
              doorcredentialVO.setEquipmentIdList(equipmentVOS);
          }
        ObjectMapper mapper = new ObjectMapper();
        String data=mapper.writeValueAsString(doorcredentialVO);
        String url=sjServer+"mj/doorcredentialserviceinfo";
        String backData = HttpUtils.httpPostRequest(url,data);
        Send send=new Send();
        send.setBackDate(backData);
        send.setSendData(data);
        send.setBussId(leave.getId());
        send.setSendTime(new Date());
        send.setThirdCode(sjCode);
        sendDao.save(send);
    }
    /**
     *
     * @param student
     * @param type 0 新建  1 修改
     * @return
     */
    public String sendPerson(Student student,int type) throws IOException {
        PersonVO personVO=new PersonVO(student);
        ObjectMapper mapper = new ObjectMapper();
        String data=mapper.writeValueAsString(personVO);
        String url="";
        if(type==0){
            url=sjServer+"base/addperson";
        }else{
            url=sjServer+"base/updateperson";
        }
        String backData = HttpUtils.httpPostRequest(url,data);
        Send send=new Send();
        send.setBackDate(backData);
        send.setSendData(data);
        send.setBussId(student.getId());
        send.setSendTime(new Date());
        send.setThirdCode(sjCode);
        sendDao.save(send);
        Map<String, Object> dataNode = mapper.readValue(backData, Map.class);
        Map backObject= (Map) dataNode.get("data");
        if(type==0){
        return backObject.get("personId").toString();}
        else{
            return null;
            }
    }
    /**
     *
     * @param carControl
     * @param type 0 新建  1 修改
     * @return
     * @throws JsonProcessingException
     */
    public String sendSJBlackWhiteList(CarControl carControl,int type) throws IOException {
        SJBlackWhiteListVO sjBlackWhiteListVO=new SJBlackWhiteListVO(carControl);
        ObjectMapper mapper = new ObjectMapper();
        String data=mapper.writeValueAsString(sjBlackWhiteListVO);
        String url="";
        if(type==0){
            url=sjServer+"park/addblackwhitelist";
        }else{
            url=sjServer+"park/updateblackwhitelist";
        }
        String backData = HttpUtils.httpPostRequest(url,data);
        Send send=new Send();
        send.setBackDate(backData);
        send.setSendData(data);
        send.setBussId(carControl.getId());
        send.setSendTime(new Date());
        send.setThirdCode(sjCode);
        sendDao.save(send);
        Map<String, Object> dataNode = mapper.readValue(backData, Map.class);
        Map backObject= (Map) dataNode.get("data");

        return backObject.get("blackGuid").toString();
    }

}
