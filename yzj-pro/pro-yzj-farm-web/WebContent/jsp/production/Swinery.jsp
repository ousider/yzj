<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/production/Swinery.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="swineryTableToolbar">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
		<button type="button" onclick="onBtnCopyAdd()"class="tableToolbarBtn btn-middle">复制新增</button>
		<button type="button" onclick="onBtnOpen()"class="tableToolbarBtn btn-middle">开启</button>
		<button type="button" onclick="onBtnClose()"class="tableToolbarBtn btn-middle">关闭</button>
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('pigHouseAdSearch')"><span>高级查询</span></div>
	<div id="pigHouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigHouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">批次代码:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="businessCode" name="businessCode" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">批次名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="swineryName" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪类别:</label></div>
				<div class="wd-com wd-15">
					<input name="pigTypeName" id="pigTypeName" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">计划头数:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-numberspinner" name="planHead" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪只数:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-numberspinner" name="pigQty" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">开始日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" name="beginDate" data-options="prompt:'请输入开始日期',required:false" style="width:100%;height:32px">
				</div>
				<div class="wd-com wd-8"><label class="label">结束日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" name="endDate" data-options="prompt:'请输入结束日期',required:false" style="width:100%;height:32px">
				</div>
				<div class="wd-com wd-8"><label class="label">对应周:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-numberspinner" name="weekNum" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪舍类型:</label></div>
				<div class="wd-com wd-15">
					<input id="searchHouseType" name="houseType" style="width:100%;height:35px">
				</div>
				<input type="checkbox" name="displayClose" style="margin-left: 130px">已关闭猪群
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
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
							<!-- <div class="wd-com wd-3"><label class="label">批次代码:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="businessCode" data-options="prompt:'请输入批次代码',required:true" style="width:100%;height:35px">
							</div> -->
							<div class="wd-com wd-3"><label class="label">批次名称:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" id="swineryName" name="swineryName" data-options="prompt:'请输入批次名称',required:true" style="width:100%;height:35px">
							</div>
							<!-- <div class="wd-com wd-3"><label class="label">产线:</label></div>
							<div class="wd-com wd-5">
								<input id="lineId" name="lineId" style="width:100%;height:35px">
							</div> -->
							<!-- <div class="wd-com wd-3"><label class="label">建群方式:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="createType" data-options="prompt:'请输入批次代码',required:true" style="width:100%;height:35px">
							</div> -->
							<div class="wd-com wd-3"><label class="label">猪类别:</label></div>
							<div class="wd-com wd-5">
								<div id="pigType"></div>
							</div>
							<div class="wd-com wd-3"><label class="label">计划头数:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" id="planHead" name="planHead" data-options="prompt:'请输入计划头数',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">开始日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="beginDate" name="beginDate" data-options="prompt:'请输入开始日期',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">对应周:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" id="weekNum" name="weekNum" data-options="prompt:'请输入对应周',required:true" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">猪舍类型:</label></div>
							<div class="wd-com wd-5">
								<input id="houseType" name="houseType" style="width:100%;height:35px">
							</div>
						</div>
						</div>
					</div>
					<div class="collapseField">
					<div class="fieldTitle"><span id="notes" class="arrowUp" onclick="upOrDown(this)"></span>备注</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-24">
								<input class="easyui-textbox" id="inputNotes" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:70px">
							</div>
						</div>
					</div>
				</div>
			</form>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
