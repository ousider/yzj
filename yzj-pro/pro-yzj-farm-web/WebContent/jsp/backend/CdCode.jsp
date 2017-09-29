<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/CdCode.js?v=${param.randomnumber}"></script>
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
				<div class="wd-com wd-8"><label class="label">分类代码:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="typeCode" data-options="prompt:'请输入分类代码'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">分类名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="typeName" data-options="prompt:'请输入分类名称'" style="width:100%;height:35px">
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
		<span style="color:red;">注意：改变上级分类会清空明细列表，请慎重选择！</span>
			<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">分类代码:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="typeCode" data-options="prompt:'请输入分类代码',required:true" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">分类名称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="typeName" data-options="prompt:'请输入分类名称',required:true" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">上级分类:</label></div>
						<div class="wd-com wd-5">
							<input id="supType" name="supType" style="width:100%;height:35px">
						</div>
					</div>
					</div>
				</div>
				<div class="fieldTitle"><span id="notes" class="arrowUp" onclick="upOrDown(this)"></span>备注</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-24">
							<input class="easyui-textbox" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:70px">
						</div>
					</div>
				</div>
			</form>
	<jsp:include page="/jsp/ListTableToolbar.jsp" />
	<div id="listTable"></div>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
