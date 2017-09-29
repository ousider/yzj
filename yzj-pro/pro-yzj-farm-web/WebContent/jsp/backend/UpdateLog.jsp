<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/UpdateLog.js?v=${param.randomnumber}"></script>
	
</head>
<body>
	<table id="table"></table>
	<div id="tableToolbarList">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>		
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>		
	</div>	
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
			</div>
			<div id="scrapOutputInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">更新日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datetimebox" id="updateTime" name="updateTime" data-options="prompt:'请输入更新日期',required:true" style="width:100%;height:32px">
							</div>
							<div class="wd-com wd-3"><label class="label">更新类型:</label></div>
							<div class="wd-com wd-5"> 
								<input class="easyui-textbox" id="updateType" name="updateType" data-options="required:true" style="width:100%;height:32px">							
							</div>	
							<div class="wd-com wd-3"><label class="label">更新版本:</label></div>
							<div class="wd-com wd-5"> 
								<input class="easyui-textbox" id="version" name="version" data-options="required:true" style="width:100%;height:32px">							
							</div>					
						</div>
					</div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>更新内容</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-24">
							<input class="easyui-textbox" id="updateLog" name="updateLog" data-options="prompt:'请输入更新内容',multiline:true" style="width:100%;height:140px">
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
	<div id="dlg-buttons" style="height:35px;text-align:right;padding:5px;">
		<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn blue">保存</button>
		<button type="button" onclick="onBtnCancel()" class="winFooterBtn green">取消</button>
	</div>
</body>
</html>