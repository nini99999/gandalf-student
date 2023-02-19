package com.poshist.sys.controller;

import com.poshist.common.Constant;
import com.poshist.common.vo.BaseVO;
import com.poshist.sys.service.UserService;
import com.poshist.sys.vo.PicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private UserService userService;

    /**
     * 上传学员照片
     * @param pic
     * @param picVO
     * @return
     * @throws IOException
     */
    @RequestMapping ("/upLoadStudentPic")
    @ResponseBody
    public BaseVO upLoadStudentPic (@RequestParam("pic") MultipartFile pic, PicVO picVO) throws IOException {
        BaseVO baseVO = new BaseVO();
        picVO.setData(pic.getBytes());
        picVO.setType(Constant.PIC_TYPE_STUDENT);
        picVO =userService.uploadPic(picVO);
        baseVO.setData(picVO);
        return baseVO;
    }

    /**
     * 获取学员照片
     * @param response
     * @param objectId
     */
    @GetMapping("/getStudentPic")
    public void getStudentPic(HttpServletResponse response,   Long objectId ){
        PicVO picVO=new PicVO();
        picVO.setObjectId(objectId);
        picVO.setType(Constant.PIC_TYPE_STUDENT);
        responsePic(response, picVO);

    }

    private void responsePic(HttpServletResponse response, @RequestBody PicVO picVO) {
        byte[] data = userService.getPic(picVO);

        response.setContentType("img/jpeg");
        response.setCharacterEncoding("utf-8");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            InputStream in = new ByteArrayInputStream(data);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传汽车图片
     * @param pic
     * @param picVO
     * @return
     * @throws IOException
     */
    @PostMapping("/upLoadCarPic")
    @ResponseBody
    public BaseVO upLoadCarPic(@RequestParam("pic") MultipartFile pic, PicVO picVO) throws IOException {
        BaseVO baseVO = new BaseVO();
        picVO.setData(pic.getBytes());
        picVO.setType(Constant.PIC_TYPE_CAR);
        picVO =userService.uploadPic(picVO);
        baseVO.setData(picVO);
        return baseVO;
    }

    /**
     * 获取汽车图片
     * @param response
     * @param objectId
     */

    @GetMapping("/getCarPic")
    public void getCarPic(HttpServletResponse response,   Long objectId ){
        PicVO picVO=new PicVO();
        picVO.setObjectId(objectId);
        picVO.setType(Constant.PIC_TYPE_CAR);
        responsePic(response, picVO);

    }

}
