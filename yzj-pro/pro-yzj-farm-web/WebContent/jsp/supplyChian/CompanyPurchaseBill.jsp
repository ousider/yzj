<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/lib/jquery-easyui-1.4.5/datagrid-detailview.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/CompanyPurchaseBill.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="advancedSearch" onClick="leftSilpFun('advancedSearchId')"><span>高级查询</span></div>
	<div id="advancedSearchId" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('advancedSearchId',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">采购单据号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="billCode" name="billCode" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">状态:</label></div>
				<div class="wd-com wd-15">
					<input id="searchStatus" name="searchStatus" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪场:</label></div>
				<div class="wd-com wd-15">
					<input id="searchFarmId" name="searchFarmId" style="width:100%;height:35px">
				</div>
			 	<div class="wd-com wd-8"><label class="label">供应商:</label></div>
				<div class="wd-com wd-15">
					<input id="searchSupplierId" name="searchSupplierId" style="width:100%;height:35px">
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
		<button type="button" onclick="onBtnAddSelf()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnScrap()" class="tableToolbarBtn btn-middle">作废</button>
		<button type="button" onclick="onBtnOver()" class="tableToolbarBtn btn-middle">完结</button>
<!-- 		<button type="button" onclick="onBtnFinish()" class="tableToolbarBtn btn-middle">完成</button> -->
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看明细</button>
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
				<input type="hidden" name="status" id="status" value="0"/>
				<input type="hidden" name="farmId" id="farmId" value="${userDetail.farmId}"/>
				<input type="hidden" name="groupOrSelf" value="2"/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>采购信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">采购类型:</label></div>
						<div class="wd-com wd-5">
							<input id="eventCode" name="eventCode" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">采购日期:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:true" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"></div>
						<div class="wd-com wd-5">
						</div>
						<div class="wd-com wd-3"><label class="label">供应商:</label></div>
						<div class="wd-com wd-5">
							<input id="supplierId" name="supplierId" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">预计到货日期:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-datebox" id="arriveDate" name="arriveDate" data-options="prompt:'请输入到货日期'" style="width:100%;height:32px">
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
		 <div id="listTableToolbar">
<!-- 			<button type="button" onclick="selectRequireMaterial()" id="selectRequireMaterial" class="listTableToolbarBtn" style="width:120px;">选择需求物料</button> -->
			<button type="button" onclick="selectMaterial()" id="selectMaterial" class="listTableToolbarBtn">选择物料</button>
<!-- 			<button type="button" onclick="selectFeed()" id="selectFeed" class="listTableToolbarBtn">已入库饲料</button> -->
			<button type="button" onclick="detailDelete()" id="detailDelete" class="listTableToolbarBtn">删除</button>
			<button type="button" onclick="detailClear()" id="detailClear" class="listTableToolbarBtn">清空</button>
		</div>
		<div id="listTable"></div>
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
	<div id="purchaseBillDetailId" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipAll()"></span><span>采购单明细</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="purchaseBillDetailTable"></div>
		</div>
	</div>
	<div id="inputInfoDetailId" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('inputInfoDetailId',390)"></span><span>采购单入库明细</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="inputInfoDetailTable"></div>
		</div>
	</div>
	<div id="freeQtyPanel" class="easyui-dialog" title="赠送信息" style="width:300px;height:240px;"
			data-options="resizable:false,modal:false,closed:true">
		<form id="freeInfoForm" class="form-inline">
			<div style="display:none;">
				<input type="hidden" id="nodeIndex" name="nodeIndex"/>
				<input type="hidden" id="billIndex" name="billIndex"/>
				<input type="hidden" id="freeMaterialName" name="freeMaterialName"/>
				<input type="hidden" id="specAll" name="specAll"/>
				<input type="hidden" id="freeUnit" name="freeUnit"/>
			</div>
			<div class="widgetGroup" style="padding:20px 0 0 0;">
				<div class="wd-com wd-2"></div>
				<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">选择物料:</label></div>
				<div class="wd-com wd-12">
					<input id="freeMaterialId" style="width:100%;height:32px">
				</div>
				<div class="wd-com wd-4"></div>
				<div class="wd-com wd-2"></div>
				<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">赠送数量:</label></div>
				<div class="wd-com wd-12">
					<input class="easyui-numberspinner" id="freeQty" name="freeQty" data-options="min:0,precision:3,required:true" style="width:100%;height:32px">
				</div>
				<div class="wd-com wd-4"></div>
				<div class="wd-com wd-2"></div>
				<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">备注:</label></div>
				<div class="wd-com wd-12">
					<input class="easyui-textbox" id="freeNotes" name="freeNotes" style="width:100%;height:32px">
				</div>
				<div class="wd-com wd-4"></div>
			</div>
		</form>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "freeQtyInfoSure()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('freeQtyPanel')">取消</a>
		</div>
	</div>
	<div id="scrapReasonWin" class="easyui-dialog" title="作废原因" style="width:300px;height:150px;"
			data-options="resizable:false,modal:true,closed:true">
		<div class="widgetGroup" style="padding:20px 0 0 0;">
			<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">作废原因:</label></div>
			<div class="wd-com wd-16">
				<input class="easyui-textbox" id="scrapReason" name="scrapReason" data-options="prompt:'请输入作废原因'" style="width:100%;height:32px">
			</div>
		</div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "scrapReasonEnter()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('scrapReasonWin')">取消</a>
		</div>
	</div>
	<jsp:include page="SelectRequiredMaterial.jsp" />
	<jsp:include page="SelectMaterial.jsp" />
	<jsp:include page="SelectMaterialFeed.jsp" />
</body>
</html>
