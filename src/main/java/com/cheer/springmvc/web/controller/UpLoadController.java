package com.cheer.springmvc.web.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UpLoadController {
    @RequestMapping("upload")
    public String upload(MultipartFile file, String name) throws IOException {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //判断上传文件类型
        if(suffix.equalsIgnoreCase(".png")){
            String uuid = UUID.randomUUID().toString();
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File("E:/"+uuid+suffix));
            return "/index.jsp";
        }else{
            return "error.jsp";
        }
    }
}
