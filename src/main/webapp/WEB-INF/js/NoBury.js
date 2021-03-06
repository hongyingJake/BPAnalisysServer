/// <reference path="BPCommon.js" />
var ServerUrl = "";
/**
 * 快速埋点注册页面JS
 */
$(function() {
	// 判断注册或查询
	checkSearchOrRegist();
	BPlstAppearence();
});
// 判断注册或查询
function checkSearchOrRegist() {
	var filSearch = $('#filSearch');
	var filRegist = $('#filRegist');
	filRegist.hide();
	// 为注册按钮设置点击事件
	$('#btnRegist').click(function() {
		filSearch.hide();
		filRegist.show();
	});
	// 为保存按钮注册事件
	$('#btnSave').click(function() {
		RegistBPNoBury();
	});
	// 设置请求地址
	ServerUrl = $('#contentxPath').val();
	// 无埋点标识符校验
	$('#txtSavaKey').blur(function() {
		checkBPNoBuryParams(this);
	});
}
// 无埋点标识符格式验证
function checkBPNoBuryParams(paramObj) {
	var NoBuryKey = $(paramObj).val().trim();
	if (NoBuryKey == '') {
		return false;
	}
	if (!CheckInfoISCharsAndNum(NoBuryKey)) {
		alert('参数格式只能有字母、数字或下划线组成！');
		$(paramObj).focus().val('');
		return false;
	} else {
		// 发送消息判断参数是否注册
		$.ajax({
			type : "POST",
			cache : false,
			url : ServerUrl + "/BPNoBury/CheckParms",
			contentType : 'application/x-www-form-urlencoded',
			data : {
				NoBuryKey : NoBuryKey
			},
			dataType : "json",
			success : function(data) {
				if (data == "1") {
					alert('参数已经注册，请重新输入参数！');
					$(paramObj).focus().val('');
					return false;
				} else {
					return true;
				}
			},
			error : function(dd) {
				alert('校验参数是否注册异常，请联系管理员！');
				return false;
			}
		});
	}
}
// 无埋点注册
function RegistBPNoBury() {
	var NoBuryKey = $('#txtSavaKey').val().trim();
	var NoBuryInfo = $('#txtNoBuryInfo').val();
	var NoBuryExtend = $('#txtNoBuryExtend').val();
	if (NoBuryKey == '') {
		alert('埋点标识符不可为空！');
		return;
	}
	// 发送请求
	$.ajax({
		type : "POST",
		cache : false,
		url : ServerUrl + "/BPNoBury/Regist",
		contentType : 'application/x-www-form-urlencoded',
		data : {
			NoBuryKey : NoBuryKey,
			NoBuryInfo : NoBuryInfo,
			NoBuryExtend : NoBuryExtend
		},
		dataType : "json",
		success : function(data) {
			alert(data.BPInfo.Msg);
			window.location.href = ServerUrl + "/BPNoBury/Index/1";

		},
		error : function(dd) {
			alert('error');
		}
	});
}
// 为列表设置样式信息
function BPlstAppearence() {
	// 设置列表序号信息
	var currentIndex = Number($('#currentIndex').val());
	$('#BPNoBuryLst tr').each(function(i, tr) {
		$('td:first', tr).text(currentIndex + i + 1);
	});
	// 设置表格行样式
	$('#BPNoBuryLst tr:even').css('backgroundColor', '#ECEFF2');
	$('#BPNoBuryLst tr').click(function() {
		$('#BPNoBuryLst tr').css('backgroundColor', '#fff');
		$('#BPNoBuryLst tr:even').css('backgroundColor', '#ECEFF2');
		$(this).css("backgroundColor", "#FFF200");
	});
}
// 删除无埋点
function DeleteBPNoBury(NoBuryId) {
	if (confirm("确定要删除无埋点吗？")) {
		// 发送请求
		$.ajax({
			type : "POST",
			cache : false,
			url : ServerUrl + "/BPNoBury/Delete",
			contentType : 'application/x-www-form-urlencoded',
			data : {
				NoBuryId : NoBuryId
			},
			dataType : "json",
			success : function(data) {
				alert(data.BPInfo.Msg);
				window.location.href = ServerUrl + "/BPNoBury/Index/1";
			},
			error : function() {
				alert('error');
			}
		});
	}
}
