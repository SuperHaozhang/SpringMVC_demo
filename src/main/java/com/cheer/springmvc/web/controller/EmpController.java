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

    /**
     * 删除
     * @param empno 根据empno找到对应对象进行删除
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String empno){

        int delete = empService.delete(Integer.parseInt(empno));
        System.out.println(delete);
        return "redirect:/getEmpList";
    }

    /**
     * 转发到inseret方法
     * @return
     */
    @RequestMapping("inseret")
    public String insert(){
        return "inseret";
    }

    /**
     * 添加功能
     * @param emp 传入对象插入数据库中
     * @return
     */
    @PostMapping("inseret")
    public String insert2(Emp emp){

        int insert = empService.insert(emp);

        return "redirect:/getEmpList";
    }

    /**
     *
     * @param empno 根据empno找到对应emp对象
     * @param model 把emp对象放入请求域中
     * @return 转发至update
     */
    @RequestMapping("update")
    public String getEmp(String empno,Model model){
        Emp emp = empService.getEmp(Integer.parseInt(empno));
        Model emp1 = model.addAttribute("emp", emp);
        return "update";
    }

    /**
     * 更新对应的对象数据
     * @param emp 传入的对象进行更新
     * @return
     */
    @PostMapping("update")
    public String getEmp1(Emp emp){
        int update = empService.update(emp);
        return "redirect:/getEmpList";
    }
}
