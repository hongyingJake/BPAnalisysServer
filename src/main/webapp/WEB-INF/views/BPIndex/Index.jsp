<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>麦田埋点服务管理系统</title>
<link href="${pageContext.request.contextPath}/images/Favicon.ico.ico" type="image/x-icon" rel="icon">
<link href="${pageContext.request.contextPath}/images/Favicon.ico.ico" type="image/x-icon" rel="shortcut icon">
</head>
<frameset rows="55,304,15" cols="*" framespacing="1" border="1" bordercolor="#66FFFF">
    <frame src='Top' name="top" />
    <frameset id="attachucp" framespacing="0" border="0" frameborder="no" cols="220,10,*" rows="*">
        <frame scrolling="auto" noresize="" frameborder="no" name="leftFrame" src='Left'>
        </frame>
        <frame id="leftbar" scrolling="no" noresize="" name="switchFrame" src='Switch'>
        </frame>
        <frame scrolling="auto" noresize="" border="0" name="mainFrame" src='MainPage'>
        </frame>
    </frameset>
    <frame src='Bottom' name="Bottom" />
</frameset>
<noframes>
    <body>
        你的浏览器不支持框架，请使用其它浏览器！
    </body>
</noframes>
</html>