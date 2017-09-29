<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"
 import="org.apache.shiro.SecurityUtils,xn.core.shiro.MyPrincipal,org.apache.shiro.subject.Subject" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>养猪匠智慧平台</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <link href="${base_url}/css/reset.css?v=${webVersion}" rel="stylesheet">
    <link href="${base_url}/css/login.css?v=${webVersion}" rel="stylesheet">
    <script src="${base_url}/js/GetRootPath.js?v=${webVersion}"></script>
    <script src="${base_url}/lib/jQuery/jquery-1.12.3.min.js?v=${webVersion}"></script>
    <script src="${base_url}/js/JAjax.js?v=${webVersion}"></script>
    <script src="${base_url}/lib/jquery-easyui-1.4.5/jquery.easyui.min.js?v=${webVersion}"></script>
	<script src="${base_url}/lib/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js?v=${webVersion}"></script>
	<!-- QQ互联 -->
	<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101370400" data-redirecturi="http://www.xnplus.cn/QQLogin.jsp" charset="utf-8"></script>
    <script type="text/javascript">
    	/* $(function(){
    		setTimeout(function(){
    			newYearClose();
    		},5000);
    	});  */
	    function upload(){
			var width = window.screen.width;
			var height = window.screen.height;
			document.getElementById("width_id").value = width;
			document.getElementById("height_id").value = height;
		    /* var username =document.getElementById("username").value;        
		    var password =document.getElementById("password").value;
		    var companyCode =document.getElementById("password").value;
		    $.ajax({    
			    url:"http://116.236.130.126:4380/WebReport/ReportServer?op=fs_load&cmd=sso",//单点登录的报表服务器    
			    dataType:"jsonp",//跨域采用jsonp方式    
			  	data:{"fr_username":username,"fr_password":password,"companyCode":companyCode},   
				jsonp:"callback"
			});      */    
	    }
		<%
			String username = "";
			String companycode = "";
			String password="";
	  		Subject subject = SecurityUtils.getSubject();
			if(subject!=null&& (subject.isRemembered()||subject.isAuthenticated())){
				MyPrincipal myPrincipal = (MyPrincipal)subject.getPrincipal();
				if(myPrincipal!=null){
				    username=myPrincipal.getUserName();
				    companycode=myPrincipal.getCompanyCode();
				}
			}
	    %>
	    if (window != top) 
		top.location.href = location.href; 
	    
	    function loginFormSubmit(){
	    	$('#lodding').css('display','block');
			var width = window.screen.width;
			var height = window.screen.height;
			document.getElementById("width_id").value = width;
			document.getElementById("height_id").value = height;
	    	jAjax.submit('/login/login.do', $('#loginForm').serialize(), 
	    		function() {
					window.location.href="jsp/Main.jsp";
				},function (response){ 
					$('#lodding').css('display','none');
					$("#message").text(response.errorMsg);
				},null,true); 
	    }
	    
	    document.onkeydown = function(e) {
	    	//判断按键为回车ENTER键  
		   	 if(event.keyCode == 13 || e.which == 13){
		   		loginFormSubmit();
		   	 }
	    }
	/*    	function loginByOther(type){
	   		if(type == 'wechat'){
	   			
	   			var url = 'http%3a%2f%2fwww.xnplus.cn%2fWechatLogin.jsp';
	   			var appid = 'wxc301eea2e26d7e75';
	   			window.location.href="https://open.weixin.qq.com/connect/qrconnect?appid="+appid+"&redirect_uri="+url+"&response_type=code&scope=snsapi_login#wechat_redirect";
	   		}
	   	} */
	   	/* if(QC.Login.check()){
	   		QC.Login.signOut()
	   	}
   		QC.Login({
	        btnId:"qqLoginBtn",   //插入按钮的节点id
	        size: "C_S"
	   	}); */
   		/*  function newYearClose(){
   			$('#newYear').css('display','none');
   		}  */
    </script>
  </head>
  
  <body>
  	<!--  <div class="new-year" id="newYear" onclick="newYearClose()">
  		<span class="new-year-close" onclick="newYearClose()"></span>
  	</div>  -->
  	<div id="lodding">
  		<div class="loadEffect">
	        <span></span>
	        <span></span>
	        <span></span>
	        <span></span>
	        <span></span>
	        <span></span>
	        <span></span>
	        <span></span>
		</div>
  	</div>
	<div class="header">
		<div class="contain_header">
			<div class="logo fl"></div>
			<!-- <div class="link fr">
				<ul>
					<li class="xn_group" >
						<a href="http://www.xinnongfeed.com">新农集团</a>
					</li>
				</ul>
			</div> -->
		</div>
	</div>
	<div class="main">
		<div class="contain_main">
			<div class="login_pic fl">
				<img alt="图片" src="${base_url}/img/yzj_little_pig.png">
			</div>
			<div class="login_form fr">
				<div class="login_please">
					<span class="please">请登录</span>
				</div>
				<div class="login_message">
					<span id="message" style="color:red"></span>
				</div>
				<div class="login">
					<form  id="loginForm" >
						<div class="loginItem">
							<div class="inSpan"><span class="company"></span></div>
							<input class="intext" type="text" name="companycode" placeholder="公司编码" value="<%=companycode%>">
						</div>
						<div class="loginItem mt_30">
							<div class="inSpan"><span class="userName"></span></div>
							<input class="intext" type="text" id="username" name="username" placeholder="用户名" value='<%=username%>' >
						</div>
						<div class="loginItem mt_30">
							<div class="inSpan"><span class="password"></span></div>
							<input class="intext" type="password" id="password" name="password" placeholder="密码" value='<%=password%>'>
						</div>
						<div class="remberItem">
							<input type="checkbox" name="isUseCookie" id="isUseCookie" checked="checked"/>
							<span style="font-size:16px;">七天内记住我的登录状态</span>
						</div>
							<input type="hidden" name="isCheckPassword" value="Y" />
							<input id = "width_id" type="hidden" name="width" value="" />
							<input id = "height_id" type="hidden" name="height" value="" />
						<button type="button" class="loginBtn" onclick="loginFormSubmit()">登录</button>
						<div class="otherLoginWay">
							<span>其他登录方式：</span>
							<span class="qq-icon"></span>
							<span class="wechat-icon"></span>
							<!-- <span class="qq-icon" id="qqLoginBtn"></span> -->
							<!-- <span class="wechat-icon" onclick="loginByOther('wechat')"></span> -->
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<span class="footer_span">公司名称：匠方馆（昆山）科技网络有限公司</span><br>
		<!-- <span class="footer_span">联系电话：400-810-5353     传真：010-82856430     联系地址：上海市松江工业区江田东路128号    邮编：201613</span><br>
		<span class="footer_span">2013-2016 新农+版权所有 沪ICP备15048968版权所有新农饲料</span> -->
	</div>
  </body>
</html>