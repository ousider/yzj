<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/PpIndicatorCust.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="toolbar">
		<button type="button" id="btnSave" onclick="doSave()" class="tableToolbarBtn btn-middle">保存</button>
		<button type="button" onclick="doRecover()" class="tableToolbarBtn btn-middle">恢复</button>
	</div>
	<table id="listTable"></table>
</body>
</html>
