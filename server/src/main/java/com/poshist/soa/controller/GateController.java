package com.poshist.soa.controller;

import com.poshist.common.utils.CommonUtils;
import com.poshist.soa.service.GateService;
import com.poshist.soa.vo.BackVO;
import com.poshist.soa.vo.ReceiveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/soa")
public class GateController {
    @Autowired
    private GateService gateService;
    @RequestMapping("/personViaInfo")
    public BackVO personViaInfo(@RequestBody ReceiveVO receiveVO, HttpServletRequest request) throws Exception {
        receiveVO.setIp(CommonUtils.getIpAddr(request));
        BackVO backVO=gateService.receivePersonVia(receiveVO);

        return backVO;
    }
    @RequestMapping("/carViaInfo")
    public BackVO carViaInfo(@RequestBody ReceiveVO receiveVO, HttpServletRequest request) throws Exception {
        receiveVO.setIp(CommonUtils.getIpAddr(request));
        BackVO backVO=gateService.receiveCarVia(receiveVO);
        return backVO;
    }
}
