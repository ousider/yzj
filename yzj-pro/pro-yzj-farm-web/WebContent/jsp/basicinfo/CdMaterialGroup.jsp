<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/CdMaterialGroup.js?v=${param.randomnumber}"></script>
</head>
<body>
	<jsp:include page="/jsp/TableToolbar.jsp" />
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
			</div>
			<div class="collapseField">
			<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
			<div class="fieldContent">
				<div class="widgetGroup">
					<!-- <div class="wd-com wd-3"><label class="label">物料组编码:</label></div>
					<div class="wd-com wd-5">
						<input class="easyui-textbox" name="businessCode" data-options="prompt:'请输入物料组编码',required:true" style="width:100%;height:35px">
					</div> -->
					<div class="wd-com wd-3"><label class="label">物料组名称:</label></div>
					<div class="wd-com wd-5">
						<input class="easyui-textbox" name="groupName" data-options="prompt:'请输入物料组名称',required:true , validType:'numOrLetterOrChineseOr_'" style="width:100%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">上级物料组:</label></div>
					<div class="wd-com wd-5">
						<input id="supGroupId" name="supGroupId" style="width:100%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">对应科目:</label></div>
					<div class="wd-com wd-5">
						<input class="easyui-textbox" name="subjectId" data-options="prompt:'请输入对应科目',required:true" style="width:100%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">物料类型:</label></div>
					<div class="wd-com wd-5">
						<input id="materialType" name="materialType" style="width:100%;height:35px">
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
