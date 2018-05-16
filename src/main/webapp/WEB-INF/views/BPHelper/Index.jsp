<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>埋点系统帮助</title>
<link href="${pageContext.request.contextPath}/images/Favicon.ico.ico" type="image/x-icon" rel="icon">
<link href="${pageContext.request.contextPath}/images/Favicon.ico.ico" type="image/x-icon" rel="shortcut icon">
<link
	href="${pageContext.request.contextPath}/js/codemirror/codemirror.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/codemirror/eclipse.css"
	rel="stylesheet" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script
	src="${pageContext.request.contextPath}/js/codemirror/codemirror.js"></script>
<script
	src="${pageContext.request.contextPath}/js/codemirror/javascript.js"></script>
<script src="${pageContext.request.contextPath}/js/codemirror/xml.js"></script>
<style type="text/css">
body {
	font-familly: 'Source Sans Pro', Helvetica, Arial, sans-serif;
	line-height: 1.5;
}

section {
	border-top: 1px solid #E30808;
	margin: 1.5em 0;
}

section, article {
	display: block;
	padding: 0;
}

.sharpen {
	color: red;
}

.tblConfig {
	width: 100%;
	border: 1px solid;
	border-collapse: collapse;
}

.tblConfig tr {
	line-height: 23px;
}

.tblConfig tr td, th {
	border: 1px solid;
}
.numList{
	list-style-type:decimal;
}
</style>
</head>
<body>
	<article> <section id="getMTBPBB">
	<h1>麦田埋点服务必备</h1>
	<p>
	<ul>
		<li>埋点JS包依赖<span class="sharpen">插件JQuery1.7以上版本</span>，推荐使用<span
			class="sharpen">JQuery1.8版本</span></li>
		<li>埋点服务系统支持的浏览器需支持<span class="sharpen">HTML5</span>，如IE8以下版本不支持
		</li>
	</ul>
	</p>
	</section> </article>
	<article> <section id="getMTBPJS">
	<h1>页面引入埋点JS包(mtbp对象)</h1>
	<p>
		埋点JS包可传入参数配置，只需要在页面声明全局
		<code>
			<span class="sharpen">_configMT</span>
		</code>
		字典数组变量。字典中的所有参数都有默认值，如页面 指定参数后，可替换默认参数。<br />字典参数有：<br />
	<table class='tblConfig'>
		<thead>
			<tr>
				<th>键值名称</th>
				<th>键值类型</th>
				<th>说明</th>
				<th>默认值</th>
				<th>是否必须</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>_setAsync</td>
				<td>boolean</td>
				<td>JS包的请求方式异步或同步</td>
				<td>true(异步)</td>
				<td>否</td>
			</tr>
			<tr>
				<td>_setApiUrl</td>
				<td>字符串</td>
				<td>发送埋点的API地址(可指定发送其他API地址)</td>
				<td>默认埋点服务API地址</td>
				<td>否</td>
			</tr>
			<tr>
				<td>_setReqType</td>
				<td>字符串</td>
				<td>JS包默认发送请求方式“GET”或“POST”</td>
				<td>GET</td>
				<td>否</td>
			</tr>
			<tr>
				<td>_setCache</td>
				<td>Boolean</td>
				<td>JS包中是否使用缓存</td>
				<td>false</td>
				<td>否</td>
			</tr>
			<tr>
				<td>_noBuryKey</td>
				<td>字符串</td>
				<td>无埋点注册时的埋点标识符(无埋点必须提供)</td>
				<td>空字符串</td>
				<td><span class="sharpen">是</span></td>
			</tr>
			<tr>
				<td>_noBuryEvent</td>
				<td>字符串</td>
				<td>页面元素的哪个事件上注册埋点</td>
				<td>click</td>
				<td>否</td>
			</tr>
			<tr>
				<td>_noBuryElemets</td>
				<td>字符串数组</td>
				<td>页面的哪些元素进行埋点(jQuery选择器[<span class="sharpen">|</span>事件名称])
				</td>
				<td>['a','input[type!=button][type!=radio][type!=checkbox]|blur',<br/>
				'select|change',':button',':submit',':radio',':checkbox',<br/>
				'textarea|blur' ]
				</td>
				<td>否</td>
			</tr>
			<tr>
				<td>_oPageVisitDuration</td>
				<td>boolean</td>
				<td>是否开启页面访问时长埋点(关闭页面时统计访问时长)</td>
				<td>true</td>
				<td>否</td>
			</tr>
		</tbody>
	</table>
	推荐加载埋点JS包通过<span class="sharpen">异步方式加载</span>，如下所示：
	</p>
	<textarea class="jsStyle">
var _configMT = _configMT || [];
_configMT.push(['_setAsync', true]);
_configMT.push(['_setReqType', "POST"]);
(function () {
    var ma = document.createElement('script'); ma.type = 'text/javascript'; ma.async = true;
    ma.src = ('https:' == document.location.protocol ? 'https://localhost' : 'http://localhost') + ':8080/BPAnalisysServer/mtscript/mtbpmin.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ma, s);
})();
	</textarea>
	<p>页面直接引用埋点JS包，如下所示：</p>
	<textarea class="htmlStyle">
<script src="http://localhost:8080/BPAnalisysServer/mtscript/mtbpmin.js"></script>
	</textarea> </section> </article>
	<article> <section id="getmt">
	<h1>传递JSON对象埋点</h1>
	<p>
		埋点mtbp全局对象提供<span class="sharpen">mtbp.mt(key, content,
			successCal, failCal)</span>方法进行埋点<br /> Key：注册埋点时的埋点标识符<br />
		content：上传的埋点JSON对象数据<br /> successCal：请求成功时的回调函数，可不提供<br />
		failCal：请求失败时的回调函数，可不提供<br /> 请求方式：GET或POST由configMT提供的默认请求方式
	</p>
	<textarea class="htmlStyle">
<script type="text/javascript">
	var content = {
		"CurrentThreadId" : "11111111111",
		"EventType" : "click",
		"eventNumber" : "6",
		"eventAreaFlg" : "地铁区",
		"elementID" : "2017042001",
		"eventTitle" : "北京",
		"IP" : "111.207.209.2",
		"ClientFlg" : "1",
		"BrowserType" : "Firefox_54.0"
	};
</script> />
<input type="button" onclick="mtbp.mt('keyClickNumber', content)"
			value="顺义地铁站" /><br />
<input type="button"
			onclick="mtbp.mtGET('keyClickNumber', content, success)" value="望京东" /><br />
	</textarea> </section> </article>

	<article> <section id="getmtpl">
	<h1>批量上传</h1>
	<p>
		埋点服务系统提供同一个埋点标识符可批量上传埋点信息，上传的JSON对象为<span class="sharpen">JSON对象数组</span>
	</p>
	<textarea class="htmlStyle">
<script type="text/javascript">
	var content = {
		"eventType" : "click",
		"areaTitle" : "高端房源",
		"areaFlg" : "gaoduanfangyuan"
	};
	var contentSum = [ content, content, content ];
</script> />
<input type="button" onclick="mtbp.mt('gaoduanFangyuan', contentSum)"
			value="批量上传" /><br />
	</textarea> </section> </article>

	<article> <section id="getAndPost">
	<h1>GET和POST请求</h1>
	<p>
		埋点服务系统提供<span class="sharpen">GET</span>和<span class="sharpen">POST</span>请求API，请求方式分别对应GET请求和POST请求
	</p>
	<textarea class="htmlStyle">
<input type="button"
			onclick="mtbp.mtGET('gaoduanFangyuan', { 'areaFlg': 'gaoduanfangyuan'})"
			value="北京二手房" /><br />
<input type="button"
			onclick="mtbp.mtPOST('gaoduanFangyuan',{ 'areaFlg': 'gaoduanfangyuan'})"
			value="高端房源" /><br />
	</textarea> </section> </article>

	<article> <section id="customProp">
	<h1>自定义页面元素属性上传埋点</h1>
	<p>
		埋点mtbp全局对象提供<span class="sharpen">mtbp.mtCustomAttr(customObj)</span>方法进行埋点<br />
		customObj：JSON对象，属性有：<span class="sharpen">reqType，eventType，key，selector，customAttrs，defAttrVal，successCal，failCal</span><br />
		reqType：API请求方式GET或POST，默认GET请求<br />
		eventType：在元素的哪个事件类型上添加埋点，默认click事件<br /> <span class="sharpen">key</span>：注册埋点时的埋点标识符，必须提供<br />
		<span class="sharpen">selector</span>：jQuery选择器，必须提供<br /> <span
			class="sharpen">customAttrs</span>：自定义属性字符串数组，告诉mtbp需要获取元素的哪些属性进行埋点，上传的埋点键值对(属性名称:属性值),必须提供<br />
		defAttrVal：默认键值对参数，会作为埋点键值对集合上传，可不提供</br> successCal：请求成功时的回调函数，可不提供<br />
		failCal：请求失败时的回调函数，可不提供
		<textarea class="jsStyle">
<script type="text/javascript">
	//自定义属性埋点
	var customObj = {
		reqType : "GET",//有默认值
		eventType : "click",//默认值为click
		key : "keyClickNumber",//必须提供
		selector : "[bpcusattr='BPClick']",//必须提供
		customAttrs : [ "CurrentThreadId", "elementID", "eventAreaFlg" ],//必须提供
		defAttrVal : {
			EventType : "Click",
			eventNumber : "6",
			eventTitle : "北京",
			IP : "111.207.209.2",
			ClientFlg : "1",
			BrowserType : "Firefox_54.0"
		},
		successCal : null, //可不提供
		failCal : null
	//可不提供
	};
	//注意：方法mtCustomAttr需要在页面的mtbp.js加载(异步加载)完成后才可调用，否则访问不到对象
	window.onload = function() {
		mtbp.mtCustomAttr(customObj);
	};
</script>
	</textarea>
		<textarea class="htmlStyle">
<a href="#" bpcusattr="BPClick" currentthreadid="1"
				elementid="2017042001" eventareaflg="地铁区">顺义</a><br />
	</textarea>
	</section> </article>

	<article> <section id="speedBp">
	<h1>快速实现事件埋点统计</h1>
	<p>
		埋点服务系统提供<span class="sharpen">mtbp.mtClick()</span>方法，该方法直接调用，必须提供一个参数，最多提供<span
			class="sharpen">3</span>个参数，表示埋点统计位置的标识符<br />
		埋点服务系统默认注册(defaultFunClickCount|默认方法统计点击数量)埋点(管理员可查看)，该埋点中已注册3个键值对集合参数，
		键名称分别是：extendParams01，extendParams02，extendParams03。键集合分别对应mtClick方法的3个扩展参数。<br />
		如果提供少于3个参数，埋点时会进行校验是否已注册，提供<span class="sharpen">3</span>个参数，在埋点时只会校验前2个参数是否已经注册，第3个参数不会校验可根据实际业务填写。
	</p>
	<textarea class="htmlStyle">
<input type="button" onclick="mtbp.mtClick('xinzengfangyuan')"
			value="新增房源" /><br />
<input type="button" onclick="mtbp.mtClick('ditumoshi', 'shenghuoquan')"
			value="地图模式->生活圈" /><br />
<input type="button"
			onclick="mtbp.mtClick('ershoufangliebiao', 'fangyuantupian', '1_5_600万元')"
			value="房源图片" /><br />
	</textarea> </section> </article>

	<article> <section id="noBuryBp">
	<h1>无埋点统计</h1>
	<p>
		页面中在进入埋点JS包插件时，需提供默认配置_configMT对象，在无埋点中此对象必须提供_noBuryKey值，
		为无埋点注册时的埋点标识符，只有_noBuryKey提供了值页面才会进行无埋点注入，否则不会进行无埋点注入。<br />
		其中_noBuryEvent和_noBuryElemets值可不提供。
		_noBuryEvent表示在页面元素的哪个事件上注册埋点，默认为click事件。
		_noBuryElemets是一个字符串数组，提供页面的哪些元素进行埋点，值为jQuery选择器[|事件名称],可指定当前元素的哪个事件进行埋点，通过"|"分隔。
		默认会对页面的a、input、下拉列表、按钮、多行文本框等元素进行埋点。
	</p>
	<textarea class="jsStyle">
 	<script type="text/javascript">
			var _configMT = _configMT || [];
			_configMT.push([ '_noBuryKey', 'MTOnLineSecondF' ]);
			_configMT.push([ '_noBuryEvent', 'click' ]);
			_configMT.push([
					'_noBuryElemets',
					[ 'a', 'input|blur', 'select|change', 'button',
							'textarea|blur' ] ]);
		</script>
	无埋点实际上传参数：{"NoBuryKey":"无埋点注册的标识符","NoBuryValue":"","NoBuryTitle":"","NoBuryExtend":""}
	</textarea>
	<p>
		注意： 1:NoBuryValue获取元素的value属性值，NoBuryTitle获取元素的text属性值。
		a和img等控件没有value属性，分别获取href和src属性作为NoBuryValue值。
		NoBuryTitle默认取text属性值，无text值则获取Title值，否则获取value属性值。<br />
		2:noBuryElemets数组中当多个jQuery选择器包含同一个元素，如果选择器的事件不一致则这个元素就会多次埋点(多次绑定事件),
		如果选择器的事件一致则只会进行一次埋点;<br /> 3:如果当前元素为单选按钮或者多选按钮、只有在选中状态下才会埋点;<br />
		4:如果获取的NoBuryValue值为空则不进行埋点；
	</p>
	</section> </article>
	
	<article> <section id="getVisit">
	<h1>页面访问时长统计</h1>
	<p>埋点服务器中已注册埋点PageVisitDurationKey(页面访问时长),默认已开启页面访问时长统计。在关闭页面时，会进行埋点。
	如果要关闭页面访问时长埋点只需要在配置变量<span class="sharpen">_configMT</span>中设置<span class="sharpen">_oPageVisitDuration</span>值为false</p>
	<textarea class="jsStyle">
<script type="text/javascript">
	var _configMT = _configMT || [];
	_configMT.push([ '_oPageVisitDuration', false ]);
</script>
	</textarea>
	页面访问时长埋点统计的参数有：
	<table class='tblConfig'>
		<thead>
			<tr>
				<th style="width:100px">参数名称</th>
				<th>参数值</th>
			</tr>
		</thead>
		<tbody>
			<tr><td style="text-align:center;">pt</td><td>页面标题</td></tr>
			<tr><td style="text-align:center;">dm</td><td>访问站点域名</td></tr>
			<tr><td style="text-align:center;">pu</td><td>页面URL地址</td></tr>
			<tr><td style="text-align:center;">st</td><td>页面访问时长(单位:秒)</td></tr>
			<tr><td style="text-align:center;">sh</td><td>页面可视化屏幕高度</td></tr>
			<tr><td style="text-align:center;">sw</td><td>页面可视化屏幕宽度</td></tr>
			<tr><td style="text-align:center;">pr</td><td>页面跳转地址，通过document.referrer获取</td></tr>
			<tr><td style="text-align:center;">bt</td><td>浏览器类型</td></tr>
			<tr><td style="text-align:center;">cd</td><td>页面颜色深度screen.colorDepth</td></tr>
		</tbody>
	</table>
	</section> </article>
	
	<article> <section id="getTrack">
	<h1>用户行为轨迹统计</h1>
	<p>埋点服务系统JS包统计插件，在每次埋点时都添加了唯一用户ID(<span class="sharpen">mtbpAccountID</span>)。
	在客户端第一次埋点时，生成唯一用户ID存储到本地或cookie中，如果客户端不进行缓存或cookie清空，此ID不会重新生成，只有在
	客户端清空缓存或cookie时重新生成。在后续的统计分析阶段可根据ID和访问时间分析出用户的行为轨迹。
	</p>
	</section> </article>

<article> <section id="BackBP">
	<h1>系统后台埋点</h1>
	<p>
	<ul class="numList">
		<li>
		系统后台可通过<span class="sharpen">HttpClient</span>或<span class="sharpen">HttpWebRequest</span>对象请求埋点API进行埋点，支持GET或POST请求
		</li>
		<li>
		埋点对外API地址：/BPAnalisysServer/MtBPAPI
		</li>
		<li>
		请求头Content-Type：application/x-www-form-urlencoded(<span class="sharpen">推荐</span>)或application/json
		</li>
		<li>
		请求参数key和content，key为埋点标识符，content参数值必须为<span class="sharpen">JSON字符串</span>，JSON对象属性必须和注册埋点时的所有键一一对应
		</li>
	</ul>
	</p>
	<textarea class="jsStyle">
1、后台埋点:
string pars = "key=keyClickNumber&content={\"eventAreaFlg\":\"区域标识符\",\"EventType\":\"事件类型\"}";
JsonResult result= httpHelper.HttpGet<JsonResult>(url,pars);
2、快速埋点
快速埋点的key不可修改；content的键名称不可修改；参数1和参数2的值必须和快速埋点注册时的参数一致
string post = "{\"key\":\"defaultFunClickCount\",\"content\":{\"extendParams01\":\"ditumoshi\",\"extendParams02\":\"shenghuoquan\",\"extendParams03\":\"这里是扩展参数\"}}";
JsonResult resu = httpHelper.HttpPostByJson<JsonResult>(path, post);
3、无埋点
无埋点的key不可修改；content的键名称不可修改；NoBuryKey的键值必须和注册无埋点的埋点标识符一致
string post = "{\"key\":\"NoBuryPointsKey\",\"content\":{\"NoBuryKey\":\"MTOnLinePointsKey\",\"NoBuryValue\":\"埋点值信息\",\"NoBuryTitle\":\"无埋点标题信息\",\"NoBuryExtend\":\"扩展参数可为空\"}}";
JsonResult resu = httpHelper.HttpPostByJson<JsonResult>(path, post);
	</textarea> 
	</section> </article>

	<article> <section id="resultState">
	<h1>埋点服务响应结果状态说明</h1>
	<p></p>
	<textarea class="jsStyle">
BPInfo :{"Status":"OK","Msg":"捕捉信息成功!"}
BPInfo :{"Status":"Fail","Msg":"埋点校验失败说明"}
BPInfo :{"Status":"Fail","Msg":"埋点内容信息转换JSON格式失败！"}
	</textarea> </section> </article>
	
	<article> <section id="getBpValid">
	<h1>埋点校验规则</h1>
	<ul>
		<li>注册埋点校验：注册的埋点键和上传的埋点键一一校验</li>
		<li>快速埋点校验：注册快速埋点的参数1和参数2分别和调用快速埋点方法的第一参数和第二参数进行一一校验</li>
		<li>无埋点校验：注册的无埋点标识符和上传的埋点标识符进行一一校验</li>
	</ul>
	</section> </article>


	<!-- 遍历textarea元素，设置codeMirror样式 -->
	<script>
		$(function() {
			$('.jsStyle').map(function(i, e) {
				var editor = CodeMirror.fromTextArea($(e)[0], {
					lineNumbers : true,
					mode : "javascript",
					theme : "eclipse"
				});
				editor.setOption('lineWrapping', true);//设置自动换行
				editor.setSize('auto', 'auto');//设置自适应高度
			});
			$('.htmlStyle').map(function(i, e) {
				var editor = CodeMirror.fromTextArea($(e)[0], {
					lineNumbers : true,
					mode : "text/html",
				});
				editor.setOption('lineWrapping', true);//设置自动换行
				editor.setSize('auto', 'auto');//设置自适应高度
			});
		});
	</script>
</body>
</html>