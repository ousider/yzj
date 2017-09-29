<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/ReplaceAndPackage.js?v=${param.randomnumber}"></script>
</head>
<body>
	<table id="table"></table>
	<div id="tableToolbarList">
			<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
			<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
			<button type="button" onclick="onBtnDelete()"class="tableToolbarBtn btn-middle">删除</button>
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">分组名称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" id="groupName" name="groupName" data-options="prompt:'请输入物料名称',required:true" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">供应商:</label></div>
						<div class="wd-com wd-5">
							<input id="supplierId" name="supplierId" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">分组模式:</label></div>
						<div class="wd-com wd-5">
							<input id="groupModel" name="groupModel" style="width:100%;height:32px">
						</div>
					</div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>备注</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-24">
							<input class="easyui-textbox" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:70px">
						</div>
					</div>
				</div>
			</div>
		</form>
		<table id="listTable"></table>
	</div>
	<div id="tableToolbar">
			<button type="button" id="onDetailAdd" onclick="onDetailAdd()" class="tableToolbarBtn btn-middle">选择物料</button>
			<button type="button" id="onDetailDelete" onclick="detailDelete()" class="tableToolbarBtn btn-middle">删除</button>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
	<jsp:include page="SelectMaterial.jsp" />
</body>
</html>
