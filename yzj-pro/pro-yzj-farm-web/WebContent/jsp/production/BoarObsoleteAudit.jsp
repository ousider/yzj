<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/production/BoarObsoleteAudit.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="tableToolbar">
		<div class="wd-com wd-1"></div>
		<span>淘汰申请单号：</span><input id="businessCode" >
		<div class="wd-com wd-2"><label class="label">单据日期:</label></div>
		<div class="wd-com wd-3"><input class="easyui-datebox" id="enterDate" name="enterDate" data-options="prompt:'请输入单据日期',required:false,validType:'ltCurrentDate'" style="width:100%;height:32px"></div>
		<div class="wd-com wd-1"></div>
		<button type="button" id="passBtn" onclick="pass()" class="tableToolbarBtn btn-middle">审批通过</button>
		<button type="button" id="notPassBtn" onclick="notPass()" class="tableToolbarBtn btn-middle">退回</button>
	</div>
	<table id="table"></table>
</body>
</html>