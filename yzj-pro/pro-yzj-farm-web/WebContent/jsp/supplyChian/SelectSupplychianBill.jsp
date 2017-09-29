<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/supplyChian/SelectSupplychianBill.js?v=${param.randomnumber}"></script>
<div id="selectSupplychianBillWin" class="rightSlipWin_780" style="z-index: 9001;">
	<div class="rightSlipTitle">
		<span class="arrow_right" onClick="rightSlipFun('selectSupplychianBillWin',780,true)"></span><span>选择供应链单据</span>
	</div>
	<div class="rightSlipContent heightFiexd">
		 <form id="selectReceptionBillSearchForm" method="post">
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>查询条件</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-4"><label class="label">供应链单据事件:</label></div>
						<div class="wd-com wd-5">
							<input id="supplychianEventCode" style="width:100%;height:35px">
						</div>
					</div>
				</div>
			</div>
	 	</form>
		<div id="supplychianBillListTable"></div>
	 </div>
	<div class="rightSlipFooter_height_50">
		<button type="button" onclick="selectSupplychianBillSure()" class="rightSlipBtn green">确定</button>
		<button type="button" onclick="selectSupplychianBillReset()" class="rightSlipBtn blue">重置</button>
	</div>
</div>