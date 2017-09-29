<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/supplyChian/SelectWarehouseMaterial.js?v=${param.randomnumber}"></script>
<div id="selectWarehouseMaterialWin" class="rightSlipWin_780" style="z-index: 9001;">
	<div class="rightSlipTitle">
		<span class="arrow_right" onClick="rightSlipFun('selectWarehouseMaterialWin',780,true)"></span><span>选择仓库物料</span>
	</div>
	<div class="rightSlipContent heightFiexd">
			<form id="selectMaterialSearchForm" method="post">
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>查询条件</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">出库仓库:</label></div>
						<div class="wd-com wd-5">
							<input id="outputWarehouseId" name="outputWarehouseId" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">物料名称:</label></div>
						<div class="wd-com wd-5">
							<input id="showWarehouseMaterialName" style="width:100%;height:35px">
						</div>
					</div>
				</div>
			</div>
	 	</form>
		<div id="selectWarehouseMaterialListTable"></div>
	 </div> 
	<div class="rightSlipFooter_height_50">
		<button type="button" onclick="selectWarehouseMaterialSure()" class="rightSlipBtn green">确定</button>
		<button type="button" onclick="selectWarehouseMaterialCancel()" class="rightSlipBtn blue">取消</button>
	</div>
</div>
