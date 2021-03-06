<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/AdditiveUseMaterial.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
				<input type="hidden" name="supplychianEventCode" id="supplychianEventCode"/>
			</div>
<!-- 			<div id="allotInfo"> -->
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>领用信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">领用日期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入领用日期',required:true" style="width:100%;height:32px">
							</div>
							
							<div class="wd-com wd-3"><label class="label">领用仓库:</label></div>
							<div class="wd-com wd-5"> 
								<input id="allotOutputWarehouseId" name="allotOutputWarehouseId" style="width:100%;height:32px">
							</div>
							<div class="wd-com wd-3"><label class="label">领用猪场:</label></div>
							<div class="wd-com wd-5"> 
								<input id="allotFarmId" name="allotFarmId" style="width:100%;height:32px;">
							</div>
						</div>
					</div>
				</div>
<!-- 			</div> -->
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
	</div>
	<div id="outputBillDetailId" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('outputBillDetailId',780)"></span><span>出库明细</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="outputBillDetailTable"></div>
		</div>
	</div>
	<div id="dlg-buttons" style="height:35px;text-align:right;padding:5px;">
		<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn blue">保存</button>
	</div>
	<jsp:include page="SelectWarehouseMaterial.jsp" />
</body>
</html>
