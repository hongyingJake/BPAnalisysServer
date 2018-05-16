/// <reference path="BPCommon.js" />
/// <reference path="jquery-1.8.3.js" />
var serverUrl = "BPPointList?n=" + new Date().valueOf();// 服务请求页面
var detailBPUrl = "RegisterBP/";// 埋点详细页面
var dateFormatStr = "yyyy-MM-dd hh:mm";// 日期格式yyyy-MM-dd hh:mm:ss
$(function() {
	// 为查询按钮注册事件
	// $('#btnSearch').click(function () {
	// GetBPList();
	// });
	// 页面加载时进行查询
	// GetBPList();
	//设置列表样式信息
	BPlstAppearence();
});

//为列表设置样式信息
function BPlstAppearence() {
	//设置列表序号信息
	var currentIndex=Number($('#currentIndex').val());
	$('#BPLst tr').each(function(i,tr){
		$('td:first',tr).text(currentIndex+i+1);
	});
	// 为行注册双击事件，进入埋点详细页面
	$('#BPLst tr').dblclick(function() {
		var BPId = $(this).attr('BPId');
		if (BPId != '') {
			detailBPUrl=$('#ContextPath').val()+"/BPPoint/RegisterBP/";
			var url = detailBPUrl + BPId;
			window.location.href = url;
		}
	});
	// 设置表格行样式
	$('#BPLst tr:even').css('backgroundColor', '#ECEFF2');
	$('#BPLst tr').click(function() {
		$('#BPLst tr').css('backgroundColor', '#fff');
		$('#BPLst tr:even').css('backgroundColor', '#ECEFF2');
		$(this).css("backgroundColor", "#FFF200");
	});
}

// 查询埋点信息--废弃
function GetBPList() {
	// 获取数据
	var paramsData = {
		BPName : $('#txtBPName').val(),
		Operation : "BPPointList"
	};
	// 发送请求
	$.ajax({
		type : "POST",
		cache : false,
		url : serverUrl,
		data : paramsData,
		dataType : "json",
		success : function(data) {
			if (data != null) {
				RefreshBPList(data.BPPoints);
				// 设置导航信息
				$('#pageNav').html(data.pageNav[0].BPName);
			}
		},
		error : function(dd) {
			alert('error');
		}
	});
}
// 刷新埋点列表信息
function RefreshBPList(BPLst) {
	var trs = "";
	$('#BPLst').html(trs);
	for (var i = 0; i < BPLst.length; i++) {
		var model = BPLst[i];
		trs += "<tr BPId='" + model.BPId + "'>"
				+ "<td style='text-align:center;'>" + (i + 1) + "</td>"
				+ "<td>" + model.BPFlg + "</td>" + "<td>" + model.BPName
				+ "</td>" + "<td>" + model.RegUserId + "</td>"
				// + "<td>" +
				// Date.FromMSJsonString(model.RegisterDate,dateFormatStr) +
				// "</td>"
				// + "<td>" +
				// Date.FromMSJsonString(model.LastUpDate,dateFormatStr) +
				// "</td>"
				+ "<td>" + model.RegisterDate + "</td>" + "<td>"
				+ model.LastUpDate + "</td>" + "</tr>";
	}
	if (trs != "") {
		$('#BPLst').html(trs);
	}

}
