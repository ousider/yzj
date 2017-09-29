<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${base_url}/js/supplyChian/SelectMaterial.js?v=${param.randomnumber}"></script>
<div id="selectMaterialWin" class="rightSlipWin_780" style="z-index: 9001;">
	<div class="rightSlipTitle">
		<span class="arrow_right" onClick="rightSlipFun('selectMaterialWin',780,true)"></span><span>选择物料</span>
	</div>
	<div class="rightSlipContent heightFiexd">
		 <form id="selectMaterialSearchForm" method="post">
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>查询条件</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">供应商:</label></div>
						<div class="wd-com wd-5">
							<input id="supplier" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">物料大类:</label></div>
						<div class="wd-com wd-5">
							<input id="showMaterialFirstClass" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">物料中类:</label></div>
						<div class="wd-com wd-5">
							<input id="showMaterialSecondClass" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">物料名称:</label></div>
						<div class="wd-com wd-5">
							<input id="showMaterialName" style="width:100%;height:35px">
						</div>
					</div>
				</div>
			</div>
	 	</form>
		<div id="selectMaterialListTable"></div>
	 </div>
	<div class="rightSlipFooter_height_50">
		<button type="button" onclick="selectMaterialSure()" class="rightSlipBtn green">确定</button>
		<button type="button" onclick="selectMaterialReset()" class="rightSlipBtn blue">重置</button>
	</div>
</div>