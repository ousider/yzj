<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<link href="${base_url}/lib/jQuery/plugins/viewer.css?v=${webVersion}" rel="stylesheet">
	
	<script src="${base_url}/lib/jquery-easyui-1.4.5/jquery.edatagrid.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/lib/jQuery/plugins/viewer.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/ReceiptSearch.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" id=base_url value="${base_url}"/>
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('advancedSearchId')"><span>高级查询</span></div>
	<div id="advancedSearchId" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('advancedSearchId',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">查看模式:</label></div>
				<div class="wd-com wd-15">
					<input id="searchModel" name="searchModel" style="width:100%;height:35px" />
				</div>
				<div class="wd-com wd-8"><label class="label">发票号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="receiptNumber" name="receiptNumber" style="width:100%;height:35px" />
				</div>
				<div id="hiddenItem">
					<div class="wd-com wd-8"><label class="label">入库单号:</label></div>
					<div class="wd-com wd-15">
						<input class="easyui-textbox" id="inputBillCode" name="inputBillCode" style="width:100%;height:35px" />
					</div>
					<div class="wd-com wd-8"><label class="label">物料名称:</label></div>
					<div class="wd-com wd-15">
						<input class="easyui-textbox" id="materialName" name="materialName" style="width:100%;height:35px" />
					</div>
				</div>
				<div class="wd-com wd-8"><label class="label">公司:</label></div>
				<div class="wd-com wd-15">
					<input id="companyId" name="companyId" style="width:100%;height:35px" />
				</div>
				<div class="wd-com wd-8"><label class="label">猪场:</label></div>
				<div class="wd-com wd-15">
					<input id="farmId" name="farmId" style="width:100%;height:35px" />
				</div>
			 	<div class="wd-com wd-8"><label class="label">供应商:</label></div>
				<div class="wd-com wd-15">
					<input id="supplierId" name="supplierId" style="width:100%;height:35px" />
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
		<!-- 占位用Button -->
		<button type="button" style="visibility:hidden;" class="tableToolbarBtn btn-middle"></button>
	</div>
	<div id="receiptBillMaterialDetail" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('receiptBillMaterialDetail',780)"></span><span>物料明细</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="receiptBillMaterialDetailTable"></div>
		</div>
	</div>
	<div id="imgPreviewPanel" class="easyui-dialog" title="图片预览" style="width:700px;height:520px;"
			data-options="resizable:false,modal:true,closed:true">
		<div id="imgPreviewDIV" class="img-preveiw-div" style="width:700px;height:478px;">
			<ul id="imgPreviewUl">
<!-- 				<li style="display:none;"><img id="imgPreview" class="img-preveiw" alt="附件预览"/></li> -->
			</ul>
		</div>
	</div>
</body>
</html>