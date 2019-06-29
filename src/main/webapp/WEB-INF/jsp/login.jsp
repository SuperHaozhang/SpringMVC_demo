<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        /** {*/
        /*    margin: 0;*/
        /*    padding: 0;*/
        /*}*/

        #form {
            margin: 0 auto;
            position: relative;
            top: 250px;
            width: 500px;
            height: 300px;
        }

        label {
            display: inline-block;
            width: 100px;
            height: 34px;
        }

        input {
            width: 250px;
            height: 34px;
            box-sizing: border-box;
        }

        button {
            width: 100px;
            height: 34px;
            border-radius: 12px;
            border-width: 0;
        }

        #login {
            margin-left: 100px;
        }

        #register {
            margin-left: 20px;
        }

        #form div + div {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div id="form">
<%--    当表单要提交的地址和当前浏览器的地址一样时，action可以省略配置--%>
    <form method="post">
        <div class="row">
            <label for="username">Username</label>
            <input type="text" name="username" id="username">
        </div>
        <div class="row">
            <label for="password">Password</label>
            <input type="password" name="password" id="password">
        </div>
        <div class="row">
            <button id="login" type="submit">Login</button> <button id="register">Register</button>
        </div>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
    $(function () {
        $("#register").click(function () {
            // 跳转
            location.href = 'register';
            return false;
        });
    });
</script>
</body>
</html>
