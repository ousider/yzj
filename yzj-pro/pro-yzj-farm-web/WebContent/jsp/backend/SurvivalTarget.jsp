<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/SurvivalTarget.js?v=${param.randomnumber}"></script>
</head>
<body>
	<table id="table"></table>
	<div id="tableToolbar">
			<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle" style="width:120px;">新增</button>
			<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle" style="width:120px;">编辑</button>
			<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle" style="width:120px;">删除</button>
	</div>

	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">开始日期:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-datebox" id="startDate" name="startDate"  data-options="prompt:'请输入开始日期',required:true,validType:'ltCurrentDate'" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">结束日期:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-datebox" id="endDate" name="endDate"  data-options="prompt:'请输入结束日期',required:true" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">目标数值:</label></div>
						<div class="wd-com wd-5">
							<input id="targetNumber" name="targetNumber" data-options="required:true" style="width:100%;height:32px">
						</div>
					</div>
				</div>
			</div>
			<div class="collapseField">
					<div class="fieldTitle"><span id="notes" class="arrowUp" onclick="upOrDown(this)"></span>备注</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-24">
								<input class="easyui-textbox" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:70px">
							</div>
						</div>
					</div>
			</div>
		</form>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
