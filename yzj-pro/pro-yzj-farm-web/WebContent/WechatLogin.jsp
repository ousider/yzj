<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"
 import="org.apache.shiro.SecurityUtils,xn.core.shiro.MyPrincipal,org.apache.shiro.subject.Subject" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*" />
    <title>养猪匠智慧平台</title>
    <link href="${base_url}/css/reset.css?v=${webVersion}" rel="stylesheet">
    <link href="${base_url}/css/login.css?v=${webVersion}" rel="stylesheet">
    <script src="${base_url}/js/GetRootPath.js?v=${webVersion}"></script>
    <script src="${base_url}/lib/jQuery/jquery-1.12.3.min.js?v=${webVersion}"></script>
    <script src="${base_url}/js/JAjax.js?v=${webVersion}"></script>
    <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
	<script type="text/javascript">
		$('#loading').css('display','block');
		function getQueryString(name) { 
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.search.substr(1).match(reg); 
			if (r != null) {
				return unescape(r[2]); 
			}
			return null; 
		} 
		var code = getQueryString('code');
		jAjax.submit_dir('/wechat/getWechatOpenId.do', {
			code:code
		}, function(data) {
			//请求成功获取openId
			if(data.openid){
				jAjax.submit_dir('/login/checkOpenId.do', {
					OpenID:data.openid,
					checkType:'wechat'
				}, function(response) {
					if(response.loginResult == "N"){
						window.location.href="OtherLoginSuccess.jsp?loginType=wechat&openId="+data.openid+"&accessToken"+data.access_token;
					}else{
						var width = window.screen.width;
						var height = window.screen.height;
				    	jAjax.submit('/login/login.do', {
				    		companycode:response.companyCode,
				    		username:response.userName,
			    			password:response.password,
		    				width:width,
	    					height:height,
	    					isCheckPassword:'N'
				    	}, 
			    		function() {
				    		jAjax.submit_dir('/wechat/getWechatUserInfo.do', {
				    			openId:data.openid,
				    			accessToken:data.access_token
				    		}, function(userInfo) {
				    			window.location.href="jsp/Main.jsp?headimgurl="+userInfo.headimgurl;
				    		})
						},function (){
							window.location.href="login.jsp";
							alert(response.errorMsg);
						},null,true);
					}
				},null,null,true); 
			}else{
				window.location.href="login.jsp";
				alert(data.errcode);
			}
		},null,null,true);
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
</body>
</html>