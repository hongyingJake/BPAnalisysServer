<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>登录麦田埋点服务管理系统</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
 <script type="text/javascript">
        $(function () {
            //提交
            $('#login').click(function () {
                if ($('#UserLoginName').val().trim() == ''||$('#UserLoginPwd').val().trim() == '') {
                    alert('登录名称或密码不可为空!');
                    return false;
                }
                else {
                    return true;
                }
            });
        });
    </script>
</head>
<body>
    <div> 
    <FORM name="formUser"  action="Login"  method="post">
        <table border="0" align="center" width="50%">
        		<tr><td colspan="2" style="color:red;">
        		${loginInfo}
        		</td></tr>
                <tr>
                    <td>登录名称：</td>
                    <td><input type="text" id="UserLoginName" name="UserLoginName" value="${UserLoginName}"/></td>
                </tr>
                <tr>
                    <td>密&nbsp;&nbsp;码：</td>
                    <td><input type="password" id="UserLoginPwd" name="UserLoginPwd" /></td>
                </tr>
                <tr>
                    <td>记住我：</td>
                    <td><input type="checkbox" checked="checked" name="IsRememberMe" /></td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" id="login" value="登录" />
                    </td>
                    <td>
                        <a href="Register" target="_parent">注册</a>
                    </td>
                </tr>
        </table>
        </FORM>
    </div>
</body>
</html>