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
	<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101370400" charset="utf-8"></script>
	<script type="text/javascript">
		QC.Login.getMe(function (openId, accessToken) {
			jAjax.submit_dir('/login/checkOpenId.do', {
				OpenID:openId,
				checkType:'QQ'
			}, function(response) {
				if(response.loginResult == "N"){
					window.opener.location.href="OtherLoginSuccess.jsp?loginType=QQ&openId="+openId;
				}else{
					$('#loading').css('display','block');
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
			    		var paras = {};
			    		QC.api("get_user_info", paras)
			    			.success(function(s){
			    				window.opener.location.href="jsp/Main.jsp?figureurl_qq_1="+s.data.figureurl_qq_1;
			    			})
			    			.error(function(f){
			    				window.opener.location.href="login.jsp";
								alert('获取用户信息失败！');
			    			})
					},function (){
						window.opener.location.href="login.jsp";
						alert(response.errorMsg);
					},null,true);
				}
			},null,null,true); 
		});
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