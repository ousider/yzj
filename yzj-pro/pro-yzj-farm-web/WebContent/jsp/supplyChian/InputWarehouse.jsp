<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/DafengModel.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/InputWarehouse.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" name="dafengModel" id="dafengModel" value="-1"/>
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('cdMaterialAdSearch')"><span>高级查询</span></div>
	<div id="cdMaterialAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('cdMaterialAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			<form id="searchForm" method="post" class="form-inline">
			 	<div class="wd-com wd-8"><label class="label">入库单编号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="advancedBillCode" name="advancedBillCode" data-options="prompt:'请输入出库号'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">入库日期:</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="advancedStartDate" name="advancedStartDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-1"><label class="label">~</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="advancedEndDate" name="advancedEndDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">入库类型:</label></div>
				<div class="wd-com wd-15">
					<input id="advancedEventCode" name="advancedEventCode" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">入库员:</label></div>
				<div class="wd-com wd-15">
					<input id="advancedInputerId" name="advancedInputerId" style="width:100%;height:35px">
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
		<button type="button" onclick="materialInput()" class="tableToolbarBtn btn-middle">物资入库</button>
		<div id="materialInputPrint" style="display:none;">
			<button type="button" onclick="materialInputPrint()" class="tableToolbarBtn btn-middle">打印入库单</button>
		</div>
		<div id="materialInputCommit" style="display:none;">
			<button type="button" onclick="materialInputCommit()" class="tableToolbarBtn btn-middle">提交入库单</button>
			<button type="button" onclick="materialInputScrap()" class="tableToolbarBtn btn-middle">作废入库单</button>
		</div>
		<button type="button" onclick="returnInput()" class="tableToolbarBtn btn-middle">退货入库</button>
		<button type="button" onclick="trunOverScrap()"class="tableToolbarBtn btn-middle">反报废</button>
		<button type="button" onclick="allotInput()" class="tableToolbarBtn btn-middle">调拨入库</button>
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
<!-- 				<input type="hidden" name="rowId" id="rowId" value="0"/> -->
				<input type="hidden" name="purchaseId" id="purchaseId" value="0"/>
				<input type="hidden" name="supplierId" id="supplierId" value=""/>
				<input type="hidden" name="purchaseEventCode" id="purchaseEventCode" value=""/>
				<input type="hidden" name="useId" id="useId" value="0"/>
				<input type="hidden" name="scrapId" id="scrapId" value="0"/>
				<input type="hidden" name="allotId" id="allotId" value="0"/>
				<input type="hidden" name="outputFarmId" id="outputFarmId" value="0"/>
			</div>
			<div id="inputInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>入库信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">入库日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入入库日期',required:true,editable:false" style="width:100%;height:32px">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="returnInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>退库信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">退库日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="returnDate" name="returnDate" data-options="prompt:'请输入退库日期',required:true,editable:false" style="width:100%;height:32px">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="trunOverScrapInfo">
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>反报废信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">反报废日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="trunOverScrapDate" name="trunOverScrapDate" data-options="prompt:'请输入反报废日期',required:true,editable:false" style="width:100%;height:32px">
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
		<div id="materialListTableToolbar">
			<button type="button" onclick="selectPurchaseBill()" class="listTableToolbarBtn" style="width:120px;">选择采购订单</button>
			<button type="button" onclick="splitOutput()" class="listTableToolbarBtn">拆分</button>
			<button type="button" onclick="mergeOutput()" class="listTableToolbarBtn">合并</button>
			<div style="height:40px;line-height:40px">
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>采购单据编号</span>&nbsp;&nbsp;<span id="purchaseBillCode"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>采购时间</span>&nbsp;&nbsp;<span id="purchaseDate"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>采购员</span>&nbsp;&nbsp;<span id="purchaserName"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>供应商</span>&nbsp;&nbsp;<span id="supplierName"></span></div>
			</div>
		</div>
		<div id="returnListTableToolbar">
			<button type="button" onclick="selectReceptionBill()" class="listTableToolbarBtn" style="width:120px;">选择领用单</button>
			<div style="height:40px;line-height:40px;">
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>领用单据编号</span>&nbsp;&nbsp;<span id="outputUseBillCode"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>领用时间</span>&nbsp;&nbsp;<span id="outputUseBillDate"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>领用人</span>&nbsp;&nbsp;<span id="outputUseUserName"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>出库人</span>&nbsp;&nbsp;<span id="outputUseOutputerName"></span></div>
			</div>
		</div>
		<div id="trunOverScrapListTableToolbar">
			<button type="button" onclick="selectScrapBill()" class="listTableToolbarBtn" style="width:120px;">选择报废单</button>
			<div style="height:40px;line-height:40px;">
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>报废单据编号</span>&nbsp;&nbsp;<span id="outputScrapBillCode"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>出库时间</span>&nbsp;&nbsp;<span id="outputScrapBillDate"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>出库人</span>&nbsp;&nbsp;<span id="outputScrapOutputerName"></span></div>
			</div>
		</div>
		<div id="allotListTableToolbar">
			<button type="button" onclick="selectAllotBill()" class="listTableToolbarBtn" style="width:120px;">选择调拨单</button>
			<div style="height:40px;line-height:40px;">
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>调拨单据编号</span>&nbsp;&nbsp;<span id="outputAllotBillCode"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>出库时间</span>&nbsp;&nbsp;<span id="outputAllotBillDate"></span></div>
				<div style="margin-right:100px;display:inline-block;"><span style="color:red;">※</span><span>出库人</span>&nbsp;&nbsp;<span id="outputAllotOutputerName"></span></div>
			</div>
		</div>
	</div>
	<div id="dlg-buttons" style="height:65px;">
		<div style="height:20px;background-color:#DCEA60;padding:0 10px;text-align:right;">
				<span style="color:red;">合计：&nbsp;&nbsp;</span><span>总价：</span><span id="totalPrice">0元</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<div style="height:35px;text-align:right;padding:5px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn blue">保存</button>
			<button type="button" onclick="onBtnCancel()" class="winFooterBtn green">取消</button>
		</div>
	</div>
	<jsp:include page="SelectMaterial.jsp" />
	<jsp:include page="SelectPurchaseBill.jsp" />
	<jsp:include page="SelectSupplychianBill.jsp" />
	<div id="splitOutputPanel" class="easyui-dialog" title="拆分" style="width:300px;height:150px;"
		data-options="resizable:false,modal:false,closed:true">
		<form id="splitOutputForm" class="form-inline">
			<div style="display:none;">
				<input type="hidden" id="purchaseQty" name="purchaseQty"/>
				<input type="hidden" id="arriveQty" name="arriveQty"/>
			</div>
			<div class="widgetGroup" style="padding:20px 0 0 0;">
				<div class="wd-com wd-2"></div>
				<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">购买数量:</label></div>
				<div class="wd-com wd-12">
					<input class="easyui-numberspinner" id="purchaseQtyNew" name="purchaseQtyNew" data-options="prompt:'拆分后的购买数量',precision:3,required:true" style="width:100%;height:28px">
				</div>
			</div>
		</form>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "splitOutputSure()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('splitOutputPanel')">取消</a>
		</div>
	</div>
	<div id="inputBillDetailId" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('inputBillDetailId',780)"></span><span>入库明细</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="inputBillDetailTable"></div>
		</div>
	</div>
	<div id="showMaterialCommitPanel" class="easyui-dialog" title="本次提交的入库信息" style="width:100%;height:100%;"
		data-options="resizable:false,modal:true,closed:true">
		<form id="showMaterialCommitForm">
			<input type="hidden" id="commitInputId" name="commitInputId" value="0"/>
		</form>
		<div id="showMaterialDetailCommit"></div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "materialInputCommitSure()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('showMaterialCommitPanel')">取消</a>
		</div>
	</div>
</body>
</html>
