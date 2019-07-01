package com.cheer.springmvc.web.controller;

import com.cheer.spring.mybatis.pojo.User;
import com.cheer.spring.mybatis.service.UserService;
import com.cheer.spring.mybatis.service.impl.UserServiceImpl;
import com.cheer.spring.mybatis.util.IOUtils;
import com.cheer.spring.mybatis.util.StringUtils;
import org.apache.commons.io.FileUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/system", "/"})
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // http://localhost:8080/springmvc/login
    @GetMapping("login")
    // @RequestMapping(path = "login", method = RequestMethod.GET)
    public String login() {
        LOGGER.debug("login()-------------------->");
        return "login"; // login是逻辑视图名称，待会儿视图解析器把会把它转换成一个实际存在物理文件login.jsp
    }

    @PostMapping("login")
    public String login(@RequestParam("username") String username, @RequestParam String password,HttpServletRequest request) {
        LOGGER.debug("username={}, password{}", username, password);
        if (userService.checkLogin(username, password)) {
            User user = userService.find(username);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:/getEmpList";
        } else {
            return "redirect:/login";
        }
    }


    @RequestMapping("register")
    public String register() {
        return "register";
    }

    @RequestMapping(value="/register", method= RequestMethod.POST)
    public String register( MultipartFile file,HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        LOGGER.debug(username+":::::"+password);
        User user = new User();
        int insert = 0;
        try {
            user.setUsername(username); //获取注册用户名
            user.setPassword(StringUtils.encrypt(password));//获取用户注册密码
            String fileName = file.getOriginalFilename();//获取上传文件名称
            String suffix = fileName.substring(fileName.lastIndexOf("."));//分解上传文件后缀名
            String avatar = username;
            String path = System.getProperty("user.home")+"/avatar/"+avatar+suffix;//设置上传文件对应用户名
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));//写入到头像文件夹中
            user.setAvatar(avatar + suffix);
            LOGGER.debug(avatar+suffix);
            insert = userService.insert(user);
            if(insert>0){
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("avatar")
    @ResponseBody
    public User avatar(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user.getAvatar() == null) {
            user.setAvatar("default.jpeg");
        } else {
            String uploadPath = request.getServletContext().getRealPath("/upload/avatar");
            String dest = uploadPath + "/" + user.getAvatar();
            File avatar = new File(dest);
            if (!avatar.exists()) {
                String src = System.getProperty("user.home") + "/avatar/" + user.getAvatar();
                IOUtils.copy(src, dest);
            }
        }
        return user;
    }


    @RequestMapping("out")
    public String out(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }

    @RequestMapping(value = "a",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,String> a(String username){
        Map<String,String> messgie = new HashMap<>();
        User user = userService.find(username);
        if(user!=null){
            messgie.put("code","-1");
            messgie.put("message","用户名已经存在");
        }
        return messgie;
    }

}
