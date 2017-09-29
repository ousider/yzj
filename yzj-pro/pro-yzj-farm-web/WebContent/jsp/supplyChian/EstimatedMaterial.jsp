<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/DafengModel.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/EstimatedMaterial.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" name="dafengModel" id="dafengModel" value="-1"/>
	</div>
<!-- 	<div id="advancedSearch" onClick="leftSilpFun('cdMaterialAdSearch')"><span>高级查询</span></div> -->
<!-- 	<div id="cdMaterialAdSearch" class="rightSlipWin_390"> -->
<!-- 		<div class="rightSlipTitle"> -->
<!-- 			<span class="arrow_right" onClick="rightSlipFun('cdMaterialAdSearch',390)"></span><span>高级查询</span> -->
<!-- 		</div> -->
<!-- 		<div class="rightSlipContent"> -->
<!-- 			 <form id="searchForm" method="post" class="form-inline"> -->
<!-- 			 	<div class="wd-com wd-8"><label class="label">供应商:</label></div> -->
<!-- 				<div class="wd-com wd-15"> -->
<!-- 					<input id="supplierForSearch" name="supplierId" style="width:100%;height:35px"> -->
<!-- 				</div> -->
<!-- 				<div class="wd-com wd-8"><label class="label">物料名称:</label></div> -->
<!-- 				<div class="wd-com wd-15"> -->
<!-- 					<input class="easyui-textbox" name="materialName" data-options="prompt:'请输入物料名称'" style="width:100%;height:35px"> -->
<!-- 				</div> -->
<!-- 				<div class="wd-com wd-8"><label class="label">物料类型:</label></div> -->
<!-- 				<div class="wd-com wd-15"> -->
<!-- 					<input id="materialTypeForAdvancedSearch" name="materialType" style="width:100%;height:35px"> -->
<!-- 				</div> -->
<!-- 			 </form> -->
<!-- 		 </div> -->
<!-- 		<div class="rightSlipFooter"> -->
<!-- 			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button> -->
<!-- 			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<table id="table"></table>
	<div id="tableToolbarList">
			<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
			<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
			<button type="button" onclick="onBtnDelete()"class="tableToolbarBtn btn-middle">删除</button>
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
				<input type="hidden" name="editType" id="editType"/>
			</div>
		</form>
		<table id="listTable"></table>
	</div>
	<div id="tableToolbar">
			<button type="button" id="onDetailAdd" onclick="detailAdd()" class="tableToolbarBtn btn-middle">选择物料</button>
			<button type="button" id="onDetailDelete" onclick="detailDelete()" class="tableToolbarBtn btn-middle">删除</button>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
	<jsp:include page="SelectMaterial.jsp" />
</body>
</html>
