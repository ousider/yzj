<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/Template.js?v=${param.randomnumber}"></script>
</head>
<body>
<jsp:include page="/jsp/TableToolbar.jsp" />
	<div id="advancedSearch" onClick="leftSilpFun('pigHouseAdSearch')"><span>高级查询</span></div>
	<div id="pigHouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigHouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">模板名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="templateName" name="templateName" data-options="prompt:'请输入模板名称'" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
			<form id="editForm" method="post" class="form-inline" style="height:100%;">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-2"><label class="label">模板名称:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="templateName" data-options="prompt:'请输入模板名称',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">角色类型:</label></div>
							<div class="wd-com wd-5">
								<input id="roleType" name="roleType" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">首页:</label></div>
							<div class="wd-com wd-5">
								<input id="moduleId" name="moduleId" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-2"><label class="label">微信首页:</label></div>
							<div class="wd-com wd-5">
								<input id="wechatModuleId" name="wechatModuleId" style="width:100%;height:35px">
							</div>
						</div>
					</div>
				</div>
				
				<div class="collapseField">
					<div class="fieldTitle"><span id="notes" class="arrowDown" onclick="upOrDown(this)"></span>备注</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-12">
									<input class="easyui-textbox" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:70px">
							</div>
						</div>
					</div>
				</div> 
				
				<table style="width:100%;height:75%;">
					<tr>
						<td style="width:25%;"><table id="treeTable" ></table></td>
						<td style="width:75%;"><table id="listTable" ></table></td>
					</tr>
				</table>
			</form>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
