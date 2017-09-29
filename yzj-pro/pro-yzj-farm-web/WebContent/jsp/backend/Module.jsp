<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/Module.js?v=${param.randomnumber}"></script>
</head>
<body>
<jsp:include page="/jsp/TableToolbar.jsp" />
</head>
<body>
	<div id="advancedSearch" onClick="leftSilpFun('pigHouseAdSearch')"><span>高级查询</span></div>
	<div id="pigHouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigHouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">菜单名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="moduleName" name="moduleName" data-options="prompt:'请输入菜单名称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">功能代码:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="component" name="component" data-options="prompt:'请输入功能代码'" style="width:100%;height:35px">
				</div>
				
				<div class="wd-com wd-8"><label class="label">是否启用:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="usingFlag" id="usingFlag" name="usingFlag" data-options="prompt:'请输入Y或者N'" style="width:100%;height:35px">
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
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">模块名称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="moduleName" data-options="prompt:'请输入模块名称',required:true" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">模块简称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="sortName" data-options="prompt:'请输入模块简称'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">模块英文名称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="moduleEnNa" data-options="prompt:'请输入模块英文名称'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">模块英文简称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="sortEnNa" data-options="prompt:'请输入模块英文简称'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">图标地址:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="iconCls" data-options="prompt:'请输入图标地址'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">图标字体:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="glyph" data-options="prompt:'请输入图标字体'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">点击事件:</label></div>
						<div class="wd-com wd-5">
							<input id="clickEvent" name="clickEvent" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">类型:</label></div>
						<div class="wd-com wd-5">
							<input id="menuType" name="menuType" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">功能ID:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="component" data-options="prompt:'请输入功能ID'" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"></div>
						<div class="wd-com wd-5">
							<input type="checkbox" name="usingFlag" checked value='Y'/>是否启用
						</div>
						<div class="wd-com wd-3"><label class="label">模块访问地址:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="moduleUrl" data-options="prompt:'请输入模块访问地址'" style="width:100%;height:35px">
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
				<br>
				<jsp:include page="/jsp/ListTableToolbar.jsp" />
				<div id="listTable"></div>
			</form>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
