<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"
 import="org.apache.shiro.SecurityUtils,xn.core.shiro.MyPrincipal,org.apache.shiro.subject.Subject" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>养猪匠智慧平台</title>
    <link href="${base_url}/css/reset.css?v=${webVersion}" rel="stylesheet">
    <link href="${base_url}/css/login.css?v=${webVersion}" rel="stylesheet">
    <script src="${base_url}/js/GetRootPath.js?v=${webVersion}"></script>
    <script src="${base_url}/lib/jQuery/jquery-1.12.3.min.js?v=${webVersion}"></script>
    <script src="${base_url}/js/JAjax.js?v=${webVersion}"></script>
    <script src="${base_url}/lib/jquery-easyui-1.4.5/jquery.easyui.min.js?v=${webVersion}"></script>
	<script src="${base_url}/lib/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js?v=${webVersion}"></script>
	<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101370400" charset="utf-8"></script>
	<script type="text/javascript">
		function getQueryString(name) { 
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r != null) {
				return unescape(r[2]); 
			}
			return null; 
		} 
		function bindAccount(){
			var companycode = $('#companycode').val();
			var username = $('#username').val();
			var password = $('#password').val();
			jAjax.submit('/login/bindAccount.do',{
				companycode:companycode,
	    		username:username,
    			password:password,
    			openId:getQueryString('openId'),
    			bindType:getQueryString('loginType')
			}, 
    		function() {
				$('#loading').css('display','block');
				var width = window.screen.width;
				var height = window.screen.height;
		    	jAjax.submit('/login/login.do', {
		    		companycode:companycode,
		    		username:username,
	    			password:password,
    				width:width,
					height:height
		    	}, function() {
		    		if(getQueryString('loginType') == 'QQ'){
		    			var paras = {};
		    			QC.api("get_user_info", paras)
		    			.success(function(s){
		    				window.location.href="jsp/Main.jsp?figureurl_qq_1="+s.data.figureurl_qq_1;
		    			})
		    			.error(function(f){
		    				window.opener.location.href="login.jsp";
							alert('获取用户信息失败！');
		    			});
		    		}else{
		    			jAjax.submit_dir('/wechat/getWechatUserInfo.do', {
			    			openId:getQueryString('openId'),
			    			accessToken:getQueryString('accessToken')
			    		}, function(userInfo) {
			    			window.location.href="jsp/Main.jsp?headimgurl="+userInfo.headimgurl;
			    		})
		    		}
				},function (){
					$('#loading').css('display','none');
					$("#message").text(response.errorMsg);
				},null,true);
			},function (response){
				$('#loading').css('display','none');
				$("#message").text(response.errorMsg);
			},null,true); 
		}	
	</script>
  </head>
  <body>
  	<div id="loading">
		<div id="loading-center">
			<div id="loading-center-absolute">
				<div class="object" id="object_one"></div>
				<div class="object" id="object_two" style="left:20px;"></div>
				<div class="object" id="object_three" style="left:40px;"></div>
				<div class="object" id="object_four" style="left:60px;"></div>
				<div class="object" id="object_five" style="left:80px;"></div>
			</div>
		</div>
	</div>
	<div class="header">
		<div class="contain_header">
			<div class="logo fl"></div>
			<div class="link fr">
				<ul>
					<li class="xn_group" >
						<a href="http://www.xinnongfeed.com">新农集团</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="bind-main">
		<div class="bind-contain">	
			<div style="text-align:center;">
				<span class="bind-warnning" id="message">您还没有绑定账号</span><br>
				<span class="bind-message">绑定已有账号可以使用QQ/微信账号快速登录</span>
			</div>
			<form  id="bindForm" >
				<div class="loginItem mt_30">
					<span class="inSpan company"></span>
					<input class="intext" type="text" id="companycode" name="companycode" placeholder="公司编码">
				</div>
				<div class="loginItem mt_30">
					<span class="inSpan userName"></span>
					<input class="intext" type="text" id="username" name="username" placeholder="用户名">
				</div>
				<div class="loginItem mt_30">
					<span class="inSpan password"></span>
					<input class="intext" type="password" id="password" name="password" placeholder="密码">
				</div>
				<button type="button" class="loginBtn mt_30" onclick="bindAccount()">账号绑定</button>
			</form>
		</div>
	</div>
	<div class="footer">
		<span class="footer_span">公司名称：上海新农饲料股份有限公司</span><br>
		<span class="footer_span">联系电话：400-810-5353     传真：010-82856430     联系地址：上海市松江工业区江田东路128号    邮编：201613</span><br>
		<span class="footer_span">2013-2016 新农+版权所有 沪ICP备15048968版权所有新农饲料</span>
	</div>
  </body>
</html>