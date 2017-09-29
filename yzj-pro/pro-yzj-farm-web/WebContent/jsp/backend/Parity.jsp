<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/Parity.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/BaseFunction.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="toolbar">
		<button type="button" onclick="doAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" id="btnSave" onclick="doSave()" class="tableToolbarBtn btn-middle">保存</button>
		<button type="button" onclick="doDelete()" class="tableToolbarBtn btn-middle">删除</button>
		<button type="button" id="btnSaveNew" onclick="doSaveNew()" class="tableToolbarBtn btn-middle">保存为新版</button>
	</div>
	<table id="listTable"></table>
	<jsp:include page="/jsp/EventListTableToolbar.jsp" />
</body>
</html>