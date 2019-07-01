<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        table {
            margin: 50px auto 0 auto;
            border-collapse: collapse;
            text-align: center;
            vertical-align: center;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            width: 100px;
            height: 30px;
        }
        #user,#user1{
            margin-right: 20px;
        }
        #avatar {
            width: 45px;
            height: 45px;
            margin-right: 20px;
        }
    </style>
</head>
<body>
<div id="header">
    <img id="avatar" src=""><span id="user"></span><a href="out">点击退出</a>
    <span id="user1"></span><a href="inseret">点击添加</a>
    <span id=""></span><h4>欢迎您${sessionScope.user.username}，登录成功！</h4>
</div>
<table>
    <tr>
        <th name="empNo">empNo</th>
        <th name="eName">eName</th>
        <th name="job">job</th>
        <th name="mgr">mgr</th>
        <th name="hireDate">hireDate</th>
        <th name="sal">sal</th>
        <th name="com">com</th>
        <th name="deptNo">deptNo</th>
        <th name="opera">opera</th>
    </tr>
    <c:forEach var="emp" items="${requestScope.list}">
    <tr>
        <td>${emp.empno}</td>
        <td>${emp.ename}</td>
        <td>${emp.job}</td>
        <td>${emp.mgr}</td>
        <td>${emp.hiredate}</td>
        <td>${emp.sal}</td>
        <td>${emp.com}</td>
        <td>${emp.deptno}</td>
        <td><a href="delete?empno=${emp.empno}">删除</a><a href="update?empno=${emp.empno}">修改</a></td>

    </tr>
    </c:forEach>
</table>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script>


    $(function () {
        $.ajax({
            url: "avatar",
            dataType: "json",
            method: "GET",
            success: function (response) {
                let avatar = response.avatar;
                $("#avatar").attr("src", "upload/avatar/" + avatar)
            }
        });
    });

</script>


</body>
</html>