<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/PerformanceCalculate.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="toolbar">
		<span>员工类型：</span>
		<input id="roleType" name="roleType" data-options="prompt:'请输入员工类型',required:true" style="width:100px">
		<button type="button" onclick="doCalculate()" class="tableToolbarBtn btn-middle">计算</button>
		<button type="button" id="save" onclick="doSave()" class="tableToolbarBtn btn-middle">保存</button>
		<button type="button" onclick="doSearCh()" class="tableToolbarBtn btn-middle">查看</button>
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>查询信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">员工类型:</label></div>
						<div class="wd-com wd-5">
							<input id="searchRoleType" name="searchRoleType" style="width:100%;height:32px" >
						</div>
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-datebox" id="searchDate" name="searchDate" data-options="prompt:'请输入查询日期',required:true,editable:false" style="width:100%;height:32px" >
						</div>
						<div class="wd-com wd-3"><label class="label">单名称位:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="applyUnitName" data-options="value:'${userDetail.farmName}',disabled:true" style="width:100%;height:32px">
						</div>
					</div>
				</div>
			</div>
		</form>
		<div id="listTableRecord"></div>
		<div id="dlg-buttons" style="height:35px;text-align:right;padding:5px;">
			<button type="button" onclick="onSearchRecord()" class="winFooterBtn green">查询</button>
			<button type="button" onclick="doPrint(${userDetail.farmId},'${userDetail.farmName}','${userDetail.employName}','${finereport_url}','${finereport_username}')" class="winFooterBtn green">打印</button>
			<button type="button" onclick="onBtnCancel()" class="winFooterBtn green">取消</button>
		</div>
	</div>
	<table id="listTable"></table>
</body>
</html>
