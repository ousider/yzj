<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/production/PorkCount.js?v=${param.randomnumber}"></script>
</head>
<body>
	<table id="table"></table>
	<div id="tableToolbar">
			<button type="button" onclick="onBtnAddPorkCheck()" class="tableToolbarBtn btn-middle" style="width:120px;">新增</button>
			<!-- <button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle" style="width:120px;">编辑</button> -->
<!-- 			<button type="button" onclick="onBtnSAPInsert()" class="tableToolbarBtn btn-middle" style="width:120px;">上传财务系统</button> -->
			<div id="advancedSearch" onClick="leftSilpFun('porkCheckSearch')"><span>高级查询</span></div>
	</div>
	<div id="porkCheckSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('porkCheckSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">单据编号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="billId" data-options="prompt:'请输入单据编号'" style="width:100%;height:32px">
				</div>
				<div class="wd-com wd-8"><label class="label">单据日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" name="billDate" data-options="prompt:'请输入单据日期',required:false" style="width:100%;height:32px">
				</div>
				<div class="wd-com wd-8"><label class="label">核算单位:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="checkOrgan" data-options="prompt:'请输入核算单位'" style="width:100%;height:35px"> 
				</div>
				<div class="wd-com wd-8"><label class="label">盘点人:</label></div>
				<div class="wd-com wd-15">
					<input id="checkUser" name="checkUser" style="width:100%;height:35px"> 
				</div>
				<!-- <div class="wd-com wd-8"><label class="label">备注:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="notes" data-options="prompt:'请输入区域'" style="width:100%;height:35px"> 
				</div> -->
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchPorkCountBillPageUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>

	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:true,validType:'ltCurrentDate',readonly:true"  style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">核算单位:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox"id="checkOrgan" name="checkOrgan" data-options="value:'${userDetail.farmName}'" readonly=readonly style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">盘点人:</label></div>
						<div class="wd-com wd-5">
							<input id="accountUser" name="accountUser" style="width:100%;height:32px">
						</div>
						<div id="wd-com wd-3" class="wd-com wd-3"><label class="label">盘点类型:</label></div>
						<div id="wd-com wd-5" class="wd-com wd-5">
							<input id="checkType" name="checkType" style="width:100%;height:32px">
						</div>
					</div>
				</div>
			</div>
			<!-- <div class="collapseField">
				<div class="fieldTitle"><span id="notes" class="arrowUp" onclick="upOrDown(this)"></span>备注</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-24">
							<input class="easyui-textbox" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:70px">
						</div>
					</div>
				</div>
			</div> -->
		</form>
		<div id="toolbar">
			<!-- <button type="button" onclick="detailSearch()" class="listTableToolbarBtn">搜索</button> -->
			<!-- <button type="button" id="onBtnDelete" onclick="detailDelete()" class="listTableToolbarBtn">删除</button> -->
		</div>
		<div id="listTable"></div>
	</div>
	<div id="dlg-buttons" style="height:60px;text-align:right;padding:5px;">
		<div style="height:20px;background-color:#DCEA60;padding:0 10px;text-align:right;">
				<span style="color:red;">合计：&nbsp;&nbsp;</span><span>盘点头数：</span><span id="checkNumSum">0</span>头&nbsp;&nbsp;<span>账上头数：</span><span id="accountNumSum">0</span>头&nbsp;&nbsp;<span>差异头数：</span><span id="differNumSum">0</span>头&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<div style="display:inline-block;float:right;padding:10px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn blue">保存</button>
			<button type="button" onclick="onBtnCancel()" class="winFooterBtn green">取消</button>
		</div>
	</div>
	
	
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
