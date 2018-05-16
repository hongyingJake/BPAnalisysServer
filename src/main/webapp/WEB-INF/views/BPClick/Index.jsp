<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>快速埋点注册</title>
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script src="${pageContext.request.contextPath}/js/BPSpeed.js"></script>
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
	<form action="${pageContext.request.contextPath}/BPClick/Index/1"
		method="get">
		<span>${RegistInfo }</span> <input type="hidden"
			value="${pageContext.request.contextPath}" id="contentxPath" /> <input
			type="hidden" id="currentIndex" value="${currentIndex }" />
		<fieldset id="filSearch">
			<legend>埋点查询</legend>
			埋点标题：<input type="text" name="txtSpeedTitle" id="txtSpeedTitle"
				class="inputSearch" /> <input type="submit" id="btnSearch"
				value="查询" /> <input type="button" id="btnRegist" value="注册" />
		</fieldset>
		<fieldset id="filRegist">
			<legend>埋点注册(参数值依次输入)</legend>
			标题：<input type="text" name="txtSaveTitle" id="txtSaveTitle"
				class="inputSearch" /> 参数1：<input type="text" name="params1"
				id="params1" class="inputSearch" /> 说明1：<input type="text"
				name="title1" id="title1" class="inputSearch" /> 
				<br/>参数2：<input
				type="text" name="params2" id="params2" class="inputSearch" /> 说明2：<input
				type="text" name="title2" id="title2" class="inputSearch" />
			<!-- 参数3：<input type="text" name="params3" id="params3" class="inputSearch" /> 
				说明3：<input type="text" name="title3" id="title3" class="inputSearch" />  -->
			<input type="button" id="btnSave" value="保存" />
		</fieldset>
		<div>
			<table class="ListTable" border="1">
				<thead>
					<tr
						style="background-color: #35B435; height: 38px; font-weight: bold; font-size: 18px; color: white;">
						<td width="50px">序号</td>
						<td width="150px">埋点标题</td>
						<td>参数1</td>
						<td>说明1</td>
						<td>参数2</td>
						<td>说明2</td>
						<!-- <td>参数3</td>
						<td>说明3</td> -->
						<td width="100px">注册人</td>
						<td width="100px">注册时间</td>
						<td width="60px">操作</td>
					</tr>
				</thead>
				<tbody id="BPSpeedLst">
					<c:forEach var="list" items="${BPSpeedLst}" varStatus="vs">
						<tr SpeedId='${list.getSpeedId()}'>
							<td style='text-align: center;'></td>
							<td title="${list.getSpeedTitle()}">${list.getSpeedTitle()}</td>
							<td title="${list.getExtendParams01()}">${list.getExtendParams01()}</td>
							<td title="${list.getExtendParams01Title()}">${list.getExtendParams01Title()}</td>
							<td title="${list.getExtendParams02()}">${list.getExtendParams02()}</td>
							<td title="${list.getExtendParams02Title()}">${list.getExtendParams02Title()}</td>
							<%-- <td title="${list.getExtendParams03()}">${list.getExtendParams03()}</td>
							<td title="${list.getExtendParams03Title()}">${list.getExtendParams03Title()}</td> --%>
							<td title="${list.getRegUserName()}">${list.getRegUserName()}</td>
							<td title="${list.getRegisterDate()}">${list.getRegisterDate()}</td>
							<td><input type="button" value="删除"
								onclick="DeleteBPSpeed(${list.getSpeedId()})" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pageNav">${pageNav}</div>
	</form>
</body>
</html>