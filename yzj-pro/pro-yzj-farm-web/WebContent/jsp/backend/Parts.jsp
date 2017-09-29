<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/Parts.js?v=${param.randomnumber}"></script>
</head>
	<jsp:include page="/jsp/TableToolbar.jsp" />
		<table id="table"></table>
		<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
			<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label"><span style="color:red;" >※</span>部件编号:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="partsCode" data-options="prompt:'系统将自动生成部件编号',required:false"  disabled="disabled" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label"><span style="color:red;" >※</span>部件名称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="partsName" data-options="prompt:'请输入部件名称',required:true" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label"><span style="color:red;" >※</span>模块名:</label></div>
						<div class="wd-com wd-5">
							<input id="moduleName" name="moduleName"  style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">图标地址:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="iconCls" data-options="prompt:'请输入部件编号'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">图标字体:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="glyph" data-options="prompt:'请输入部件名称'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label"><span style="color:red;" >※</span>能否使用:</label></div>
						<div class="wd-com wd-5">
							<div id="usingFlag"></div>
						</div>
					</div>
					</div>
				</div>
				<br>
			</form>
			</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
	</body>
</html>