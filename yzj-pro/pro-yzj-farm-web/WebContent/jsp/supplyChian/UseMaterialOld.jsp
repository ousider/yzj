<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/DafengModel.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/UseMaterial.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" name="dafengModel" id="dafengModel" value="-1"/>
	</div>
	<table id="table"></table>
	<div id="tableToolbarList">
		<button type="button" onclick="materialReception()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看明细</button>
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="supplychianEventCode" id="supplychianEventCode"/>
			</div>
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
								<input id="warehouseId" name="warehouseId" style="width:100%;height:32px">
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
	<div id="receptionListTableToolbar">
		<button type="button" onclick="selectWarehouseMaterial()" class="listTableToolbarBtn" style="width:120px;">选择仓库物料</button>
		<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
		<button type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
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
</body>
</html>
