package com.cheer.springmvc.web.controller;

import com.cheer.spring.mybatis.dao.EmpMapper;
import com.cheer.spring.mybatis.pojo.Emp;
import com.cheer.spring.mybatis.service.EmpService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;


@Controller  // 标识当前类是一个rest风格的控制器
public class EmpController {
    @Resource
    private EmpService empService;



    @RequestMapping("/getEmpList") // 标识当前方法可以接受一个http的get方法请求
    //@ResponseBody
    public String getEmpList(Model model) {
        List<Emp> list =this.empService.getList();
        model.addAttribute("list",list);
        return "empList2";
    }
    @RequestMapping("/delete")
    public String delete(String empno){

        int delete = empService.delete(Integer.parseInt(empno));
        System.out.println(delete);
        return "redirect:/getEmpList";
    }

    @RequestMapping("inseret")
    public String insert(){
        return "inseret";
    }

    @PostMapping("inseret")
    public String insert2(Emp emp){

        int insert = empService.insert(emp);

        return "redirect:/getEmpList";
    }

    @RequestMapping("update")
    public String getEmp(String empno,Model model){

        Emp emp = empService.getEmp(Integer.parseInt(empno));
        Model emp1 = model.addAttribute("emp", emp);
        return "update";
    }

    @PostMapping("update")
    public String getEmp1(Emp emp){
        int update = empService.update(emp);
        return "redirect:/getEmpList";
    }
}
