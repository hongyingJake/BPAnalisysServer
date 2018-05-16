<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>注册埋点</title>
    <link href="${pageContext.request.contextPath}/css/PageJsonShare.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
    <script src="${pageContext.request.contextPath}/js/PageJsonShare.js"></script>
    <script src="${pageContext.request.contextPath}/js/RegisterBP.js"></script>
    <script src="${pageContext.request.contextPath}/js/BPCommon.js"></script>
    <style type="text/css">
        .KeyFlg {
            width: 100%;
            border-collapse: collapse;
        }

        .KeyTitle {
            text-align: center;
            vertical-align: middle;
        }

        .KeyInput {
            width: 98%;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $('#BPFlg').blur(function () {
                checkBPFlg();
            });
            $('#btnSubmit').click(function () {
                if (!checkBPFlg()) {
                    return false;
                }
            });
            $('#formRegist').submit(function () {
                if (!checkBPFlg()) {
                    return false;
                }
            });
        });
        //埋点标识符格式验证
        function checkBPFlg()
        {
            var BPFlg = $('#BPFlg').val();
            if (!CheckInfoISCharsAndNum(BPFlg)) {
                alert('埋点标识符必须由字符、数字或下划线组成！');
                $('#BPFlg').val('');
                return false;
            }
            else {
                return true;
            }
        }
    </script>
</head>
<body>
    <div>
    <FORM id="formRegist" name="formRegist"  action="${pageContext.request.contextPath}/BPPoint/RegisterBP"  method="post">
        <table align="center" border="0" width="80%">
                <tr>
                    <td width="60px">埋点名称：</td>
                    <td><input type="text" id="BPName" name="BPName" value="${BPName }" style="width:96%;height:100%" /></td>
                    <td width="120px">埋点标识符：</td>
                    <td><input type="text" id="BPFlg" name="BPFlg" value="${BPFlg }" style="width:96%;height:100%" /></td>
                </tr>
                <tr>
                    <td>埋点备注信息：</td>
                    <td colspan="2">
                    <textarea style="width:96%;height:100%" id="BPDemo" name="BPDemo">${BPDemo }</textarea>
                    <input type="hidden"  id="BPId" name="BPId"  value="${BPId }"/>
                    <input type="hidden" id="ContextPath" name="ContextPath" value="${ContextPath }" />
                    </td>
                    <td>
                        <input type="submit" value="保存" id="btnSubmit" />
                    </td>
                </tr>
        </table>
        </FORM>
        <hr />
        <!--键值对集合添加-->
        <table border="1" width="100%">
            <tr>
                <td style="border:0px;border-right:1px solid;vertical-align:top;">
                    <table border="1" width="100%" class="KeyFlg" id="tbKeys">
                        <thead>
                            <tr style="background-color: #008000;height:38px;font-size:18px;color:white;">
                                <td class="KeyTitle">键标识</td>
                                <td class="KeyTitle">键说明</td>
                                <td class="KeyTitle" style="width:40px;">操作</td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" class="KeyInput" id="txtKeyFlg" name="txtKeyFlg"  />
                                </td>
                                <td>
                                    <input type="text" class="KeyInput" id="txtKeyInfo" name="txtKeyInfo" />
                                </td>
                                <td>
                                    <input type="button" id="btnKeySave" value="保存" />
                                </td>
                            </tr>
                        </thead>
                        <tbody id="tbBodyKeys"></tbody>
                    </table>
                </td>
                <td style="border:0px;width:20%">
                    <pre id="preJson" style="vertical-align:top;">
                    </pre>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
