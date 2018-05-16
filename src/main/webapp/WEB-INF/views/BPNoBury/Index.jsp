<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无埋点</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script src="${pageContext.request.contextPath}/js/NoBury.js"></script>
<script src="${pageContext.request.contextPath}/js/BPCommon.js"></script>
<style type="text/css">
.inputSearch {
	width: 200px;
	height: 28px;
}

.ListTable {
	border-collapse: collapse;
	border: 1px solid;
	width: 100%;
}

.ListTable tr {
	line-height: 23px;
}

fieldset {
	margin: 0px;
	padding: 1px;
}
</style>
</head>
<body>
	<form action="${pageContext.request.contextPath}/BPNoBury/Index/1"
		method="get">
		<span>${RegistInfo }</span> <input type="hidden"
			value="${pageContext.request.contextPath}" id="contentxPath" /> <input
			type="hidden" id="currentIndex" value="${currentIndex }" />
		<fieldset id="filSearch">
			<legend>无埋点查询</legend>
			埋点标识符：<input type="text" name="txtNoBuryKey" id="txtNoBuryKey"
				class="inputSearch" /> <input type="submit" id="btnSearch"
				value="查询" /> <input type="button" id="btnRegist" value="注册" />
		</fieldset>
		<fieldset id="filRegist">
			<legend>无埋点注册</legend>
			埋点标识符：<input type="text" name="txtSavaKey" id="txtSavaKey"
				class="inputSearch" /> 
埋点说明：<input type="text" name="txtNoBuryInfo"
				id="txtNoBuryInfo" class="inputSearch" /> 扩展参数说明：<input type="text"
				name="txtNoBuryExtend" id="txtNoBuryExtend" class="inputSearch" /> 
			<input type="button" id="btnSave" value="保存" />
		</fieldset>
		<div>
			<table class="ListTable" border="1">
				<thead>
					<tr
						style="background-color: #35B435; height: 38px; font-weight: bold; font-size: 18px; color: white;">
						<td width="50px">序号</td>
						<td width="250px">埋点标识符</td>
						<td>埋点说明</td>
						<td>扩展参数说明</td>
						<td width="100px">注册人</td>
						<td width="100px">注册时间</td>
						<td width="60px">操作</td>
					</tr>
				</thead>
				<tbody id="BPNoBuryLst">
					<c:forEach var="list" items="${BPNoBuryLst}" varStatus="vs">
						<tr NoBuryId='${list.getNoBuryId()}'>
							<td style='text-align: center;'></td>
							<td title="${list.getNoBuryKey()}">${list.getNoBuryKey()}</td>
							<td title="${list.getNoBuryInfo()}">${list.getNoBuryInfo()}</td>
							<td title="${list.getNoBuryExtend()}">${list.getNoBuryExtend()}</td>
							<td title="${list.getRegUserName()}">${list.getRegUserName()}</td>
							<td title="${list.getRegisterDate()}">${list.getRegisterDate()}</td>
							<td><input type="button" value="删除"
								onclick="DeleteBPNoBury(${list.getNoBuryId()})" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageNav">${pageNav}</div>
	</form>
</body>
</html>