<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>Top</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
    <style type="text/css">
        .systemTitle {
            font-size: 36px;
            font-weight: 200;
            width: 80%;
            text-align: center;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $('#aClose').click(function () {
                if(confirm("您确定要退出吗？")) {
                    window.parent.close();
                }
            });
        });
    </script>
</head>
<body>
    <div>
        <table style="border:0px solid; width:100%;">
            <tr>
                <td class="systemTitle">麦田埋点服务系统</td>
                <td>
                    	欢迎
                    ${UserName}
                    &nbsp;&nbsp;&nbsp;
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/BPRegist/Login" target="_parent">退出</a>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>