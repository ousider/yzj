<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/DafengModel.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/OutputWarehouse.js?v=${param.randomnumber}"></script>
</head>
<body>
 	<div style="display:none">
		<input type="hidden" name="dafengModel" id="dafengModel" value="-1"/>
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('outputWarehouseAdSearch')"><span>高级查询</span></div>
	<div id="outputWarehouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('outputWarehouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
			 	<div class="wd-com wd-8"><label class="label">出库编号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="advancedBillCode" name="advancedBillCode" data-options="prompt:'请输入出库号'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">出库日期:</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="advancedStartDate" name="advancedStartDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-1"><label class="label">~</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="advancedEndDate" name="advancedEndDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">出库类型:</label></div>
				<div class="wd-com wd-15">
					<input id="advancedEventCode" name="advancedEventCode" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">领用人员:</label></div>
				<div class="wd-com wd-15">
					<input id="advancedUserId" name="advancedUserId" style="width:100%;height:35px">
				</div> 
				<div class="wd-com wd-8"><label class="label">出库员:</label></div>
				<div class="wd-com wd-15">
					<input id="advancedOutputerId" name="advancedOutputerId" style="width:100%;height:35px">
				</div> 
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="tableToolbarList">
	<!-- 	<button type="button" onclick="materialReception()" class="tableToolbarBtn btn-middle">物资领用</button> -->
		<button type="button" onclick="scrapOutput()" class="tableToolbarBtn btn-middle">报废出库</button>
		<button type="button" onclick="returnPurchase()" class="tableToolbarBtn btn-middle">采购退货</button>
<!-- 		<button type="button" onclick="pigSell()" class="tableToolbarBtn btn-middle">卖猪</button> -->
		<button type="button" onclick="allotOutput()" class="tableToolbarBtn btn-middle">调拨</button>
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看明细</button>
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
<!-- 				<input type="hidden" name="rowId" id="rowId" value="0"/> -->
				<input type="hidden" name="supplychianEventCode" id="supplychianEventCode"/>
			</div>
			<div id="receptionInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>领用信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">领用日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入领用日期',required:true,value:getCurrentDate()" style="width:100%;height:32px">
							</div>
<!-- 							<div class="wd-com wd-3"><label class="label">领用人:</label></div> -->
<!-- 							<div class="wd-com wd-5"> -->
<!-- 								<input id="userId" name="userId" style="width:100%;height:32px"> -->
<!-- 							</div> -->
<!-- 							<div class="wd-com wd-3"><label class="label">出库人员:</label></div> -->
<!-- 							<div class="wd-com wd-5"> -->
<!-- 								<input id="outputerId" name="outputerId" style="width:100%;height:32px"> -->
<!-- 							</div> -->
 							<div class="wd-com wd-3"><label class="label">领用仓库:</label></div>
							<div class="wd-com wd-5"> 
								<input id="receptOutputWarehouseId" name="receptOutputWarehouseId" style="width:100%;height:32px">
							</div> 
							<div class="wd-com wd-3"><label class="label">领用去向:</label></div>
							<div class="wd-com wd-5">
								<input id="outputDestination" name="outputDestination" style="width:100%;height:32px">
							</div>
							<div id="outputDestinationLabel" class="wd-com wd-3" style="display:none;"><label class="label"></label></div>
							<div class="wd-com wd-5">
								<input id="outputDesSwineryId" name="outputDesSwineryId" style="width:100%;height:32px;">
							</div>
							<div class="wd-com wd-5">
								<input id="outputDesHouseId" name="outputDesHouseId" style="width:100%;height:32px;">
							</div>
							<div class="wd-com wd-5">
								<input id="outputDesDeptId" name="outputDesDeptId" style="width:100%;height:32px;">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="scrapOutputInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>报废信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">报废日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="scrapDate" name="scrapDate" data-options="prompt:'请输入报废日期',required:true,value:getCurrentDate()" style="width:100%;height:32px">
							</div>
<!-- 							<div class="wd-com wd-3"><label class="label">报废人:</label></div> -->
<!-- 							<div class="wd-com wd-5"> -->
<!-- 								<input id="scraperId" name="scraperId" style="width:100%;height:32px"> -->
<!-- 							</div> -->
<!-- 							<div class="wd-com wd-3"><label class="label">出库人员:</label></div> -->
<!-- 							<div class="wd-com wd-5"> -->
<!-- 								<input id="scrapOutputerId" name="outputerId" style="width:100%;height:32px"> -->
<!-- 							</div> -->
							<div class="wd-com wd-3"><label class="label">报废仓库:</label></div>
							<div class="wd-com wd-5"> 
								<input id="scrapOutputWarehouseId" name="scrapOutputWarehouseId" style="width:100%;height:32px">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="returnPurchaseInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>退货信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">退货日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="returnDate" name="returnDate" data-options="prompt:'请输入退货日期',required:true,value:getCurrentDate()" style="width:100%;height:32px">
							</div>
<!-- 							<div class="wd-com wd-3"><label class="label">负责人:</label></div> -->
<!-- 							<div class="wd-com wd-5"> -->
<!-- 								<input id="principalId" name="principalId" style="width:100%;height:32px"> -->
<!-- 							</div> -->
							<div class="wd-com wd-3"><label class="label">退货来源:</label></div>
							<div class="wd-com wd-5">
								<input id="returnOrgin" name="returnOrgin" style="width:100%;height:32px">
							</div>
							<div class="wd-com wd-3"><label id="returnOutputWarehouseIdLabel" class="label">退货仓库:</label></div>
							<div class="wd-com wd-5"> 
								<input id="returnOutputWarehouseId" name="returnOutputWarehouseId" style="width:100%;height:32px">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="allotInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>调拨信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">调拨日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="allotDate" name="allotDate" data-options="prompt:'请输入退货日期',required:true,value:getCurrentDate()" style="width:100%;height:32px">
							</div>
<!-- 							<div class="wd-com wd-3"><label class="label">调拨员:</label></div> -->
<!-- 							<div class="wd-com wd-5"> -->
<!-- 								<input id="alloterId" name="alloterId" style="width:100%;height:32px"> -->
<!-- 							</div> -->
							<div class="wd-com wd-3"><label class="label">物资调拨去向:</label></div>
							<div class="wd-com wd-5">
								<input id="allotDestination" name="allotDestination" style="width:100%;height:32px">
							</div>
							<div class="wd-com wd-3"><label class="label">调出仓库:</label></div>
							<div class="wd-com wd-5"> 
								<input id="allotOutputWarehouseId" name="allotOutputWarehouseId" style="width:100%;height:32px">
							</div>
							<div id="allotDestinationLabel" class="wd-com wd-3" style="display:none;"><label class="label"></label></div>
							<div class="wd-com wd-5">
								<input id="allotWareHouseId" name="allotWareHouseId" style="width:100%;height:32px;">
							</div>
							<div class="wd-com wd-5">
								<input id="allotFarmId" name="allotFarmId" style="width:100%;height:32px;">
							</div>
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
		<div id="listTable"></div>
	</div>
	<div id="toolbarDiv" style="display:none;">
		<div id="receptionListTableToolbar">
			<button type="button" onclick="selectWarehouseMaterial()" class="listTableToolbarBtn" style="width:120px;">选择仓库物料</button>
			<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
			<button type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
		</div>
		<div id="returnListTableToolbar">
			<button type="button" onclick="selectPurchaseBill()" class="listTableToolbarBtn" style="width:120px;">选择采购订单</button>
			<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
			<button type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
			<div style="height:40px;line-height:40px;">
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>采购单据编号</span>&nbsp;&nbsp;<span id="purchaseBillCode"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>采购时间</span>&nbsp;&nbsp;<span id="purchaseDate"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>采购员</span>&nbsp;&nbsp;<span id="purchaserName"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span>供应商</span>&nbsp;&nbsp;<span id="supplierName"></span></div>
			</div>
		</div>
	</div>
	<div id="outputBillDetailId" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('outputBillDetailId',780)"></span><span>出库明细</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="outputBillDetailTable"></div>
		</div>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
	<jsp:include page="SelectWarehouseMaterial.jsp" />
	<jsp:include page="SelectPurchaseBill.jsp" />
</body>
</html>
