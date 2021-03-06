<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
 <title>注册麦田埋点服务管理系统</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
</head>
    <script type="text/javascript">
        $(function () {
            //密码确认
            $('#UserLoginPwd2').blur(function () {
                CheckPwd();
            });
            //手机号码
            $('#UserPhone').blur(function () {
                CheckPhone();
            });
            //提交
            $('#btnSubmit').click(function () {
                if ($('#UserLoginName').val().trim() == '') {
                    alert('用户登录名称不可为空!');
                    return false;
                }
                else if (!CheckPwd()) {
                    return false;
                }
                else if (!CheckPhone()) {
                    return false;
                }
                else {
                    return true;
                }
            });
        });
        function CheckPhone()
        {
            var UserPhone = $('#UserPhone').val();
            var regex = /^\d+$/g;
            if (!regex.test(UserPhone)) {
                alert('手机号码格式不正确！');
                return false;
            }
            else {
                return true;
            }
        }
        function CheckPwd()
        {
            var UserLoginPwd = $('#UserLoginPwd').val();
            var UserLoginPwd2 = $('#UserLoginPwd2').val();
            if (UserLoginPwd == '') {
                alert('密码不可为空，请重新输入!');
                $('#UserLoginPwd').focus();
                return false;
            }
            else {
                if (UserLoginPwd != UserLoginPwd2) {
                    alert('两次输入的密码不一致，请重新输入!');
                    return false;
                }
                else {
                    return true;
                }
            }
        }
    </script>
</head>
<body>
    <div>
        <FORM name="formUser"  action="Successed"  method="post">
            <table align="center" width="60%">
            	<tr>
            		<td colspan="2" style="color:red;">${errMsg}</td>
            	</tr>
                <tr>
                    <td>用户名称：</td>
                    <td><input type="text" id="UserName" name="UserName" placeholder="用户名称"/></td>
                </tr>
                <tr>
                    <td>用户登录名称：</td>
                    <td><input type="text" id="UserLoginName" name="UserLoginName" placeholder="登录名称"/></td>
                </tr>
                <tr>
                    <td>用户登录密码：</td>
                    <td><input type="password" id="UserLoginPwd" name="UserLoginPwd" /></td>
                </tr>
                <tr>
                    <td>再次确认密码：</td>
                    <td><input type="password" id="UserLoginPwd2" name="UserLoginPwd2" /></td>
                </tr>
                <tr>
                    <td>请输入手机号码：</td>
                    <td><input type="text" id="UserPhone" name="UserPhone" /></td>
                </tr>
                <tr>
                    <td>请输入公司名称：</td>
                    <td><input type="text" id="UserCompanyName" name="UserCompanyName" placeholder="公司名称"/></td>
                </tr>
                <tr>
                    <td>备注信息：</td>
                        <td><textarea id="Demo" name="Demo" ></textarea></td>
                </tr>
                <tr>
                    <td style="float:right;">
                        <input type="submit" value="注册信息" id="btnSubmit" />
                    </td>
                    <td>
                        <a href="Login" target="_parent">取消</a>
                    </td>
                </tr>
            </table>
        </FORM>
    </div>
</body>
</html>