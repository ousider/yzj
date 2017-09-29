<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/supplyChian/SelectPurchaseBill.js?v=${param.randomnumber}"></script>
<div id="selectPurchaseBillWin" class="rightSlipWin_780" style="z-index: 9001;">
	<div class="rightSlipTitle">
		<span class="arrow_right" onClick="rightSlipFun('selectPurchaseBillWin',780,true)"></span><span>选择采购订单</span>
	</div>
	<div class="rightSlipContent heightFiexd">
		<div style="display:none">
			<input type="hidden" name="searchDetailType" id="searchDetailType"/>
		</div>
		<div id="purchaseBillListTable"></div>
	 </div>
	<div class="rightSlipFooter_height_50">
		<button type="button" onclick="selectPurchaseBillSure()" class="rightSlipBtn green">确定</button>
		<button type="button" onclick="selectPurchaseBillReset()" class="rightSlipBtn blue">重置</button>
	</div>
</div>