<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/DafengModel.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/StatisticalMaterial.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" name="dafengModel" id="dafengModel" value="-1"/>
	</div>
	<table id="table"></table>
	<div id="tableToolbarList">
			<span>物料类型：</span><input id="showApplyType">
			<button type="button" onclick="onBtnCreateRequireBill()" class="tableToolbarBtn btn-middle">生成需求单</button> 
			<button type="button" onclick="onBtnSearchDailyRecordDetail()" class="tableToolbarBtn btn-middle">查看明细</button>
			<button type="button" onclick="onBtnWarehouseEnough()" class="tableToolbarBtn btn-middle">库存足够</button>
	</div>
	<div id="dailyRecordDetailId" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('dailyRecordDetailId',780)"></span><span>预计用料明细</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="dailyRecordDetailTable"></div>
		</div>
	</div>
</body>
</html>
