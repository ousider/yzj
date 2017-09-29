<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/SupplierAndMaterial.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="advancedSearch" onClick="leftSilpFun('cdMaterialAdSearch')"><span>高级查询</span></div>
	<div id="cdMaterialAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('cdMaterialAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
			 	<div class="wd-com wd-8"><label class="label">供应商:</label></div>
				<div class="wd-com wd-15">
					<input id="supplierForSearch" name="supplierId" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">厂家名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="manufacturer" data-options="prompt:'请输入厂家名称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">物料名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="materialName" data-options="prompt:'请输入物料名称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">物料大类:</label></div>
				<div class="wd-com wd-15">
					<input id="materialFirstClassForAdvancedSearch" name="materialFirstClass" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">物料中类:</label></div>
				<div class="wd-com wd-15">
					<input id="materialSecondClassForAdvancedSearch" name="materialSecondClass" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="listTable"></table>
	<div id="tableToolbar">
<!-- 			<div class="wd-com wd-2"><label class="label">供应商:</label></div><input id="supplier" name="supplierId"> -->
			<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
			<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
			<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
			<button type="button" onclick="editMaterialToFarms('synchronize')" class="tableToolbarBtn btn-middle">同步</button>
			<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
<!-- 			<button type="button" onclick="onBtnCopyAdd()"class="tableToolbarBtn btn-middle">复制新增</button> -->
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
				<input type="hidden" name="materialId" id="materialId" value="0"/>
				<input type="hidden" name="childTableId" value="0"/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">物料名称:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" name="materialName" data-options="prompt:'请输入物料名称',required:true" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">物料类型:</label></div>
						<div class="wd-com wd-5">
							<input id="materialType" name="materialType" style="width:100%;height:32px">
						</div>
<!-- 						<div class="wd-com wd-3"><label class="label">物料组名称:</label></div> -->
<!-- 						<div class="wd-com wd-5"> -->
<!-- 							<input id="groupId" name="groupId" style="width:100%;height:32px"></div> -->
						<div class="wd-com wd-3"><label class="label">计量单位:</label></div>
						<div class="wd-com wd-5">
							<input id="unit" name="unit" style="width:100%;height:32px">
						</div>
						<div id="editHidden01">
							<div class="wd-com wd-3"><label class="label">仓库物料:</label></div>
							<div class="wd-com wd-5">
								<div id="isWarehouse"></div>
							</div>
							<div class="wd-com wd-3"><label class="label">采购物料:</label></div>
							<div class="wd-com wd-5">
								<div id="isPurchase"></div>
							</div>
							<div class="wd-com wd-3"><label class="label">销售物料:</label></div>
							<div class="wd-com wd-5">
								<div id="isSell"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="collapseField" id="supplyChianComInfo">
				<div class="fieldTitle"><span  class="arrowUp" onclick="upOrDown(this)"></span>供应链通用信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">生产厂家:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" id="manufacturer" name="manufacturer" data-options="prompt:'请输入生产厂家',required:true" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">供应商:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-combobox" id="supplierId" name="supplierId" data-options="prompt:'请输入供应商'" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">计算规格:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-numberbox" id="specNum" name="specNum" data-options="prompt:'计算数量',precision:3" style="width:40%;height:32px">
							<input class="easyui-textbox" id="spec" name="spec" data-options="prompt:'计算单位（计量单位/购买单元）'" style="width:40%;height:32px">
						</div>
						<div id="editHidden02">
							<div class="wd-com wd-3"><label class="label">单价:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberbox" name="price" data-options="prompt:'请输入单价',precision:3" style="width:100%;height:32px">
							</div>
							<div class="wd-com wd-3"><label class="label">赠送比率:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox"  name="freePercent" data-options="prompt:'赠送比率(例：60%为百分之60,10/4为10赠4)',validType:'numAddPercent'" style="width:100%;height:32px">
							</div>
						</div>
						<div class="wd-com wd-3"><label class="label">显示规格:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-textbox" id="specAll" name="specAll" data-options="prompt:'显示规格'" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">领用最小规格:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-numberbox" name="outputMinQty" data-options="min:0,precision:3,value:1.00" style="width:20%;height:32px">&nbsp;&nbsp;<span id="outputMinQtyUnit"></span>
						</div>
						<div class="wd-com wd-3"><label class="label">物料大类:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-combobox" id="materialFirstClass" name="materialFirstClass"  data-options="required:true" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">物料中类:</label></div>
						<div class="wd-com wd-5">
							<input class="easyui-combobox" id="materialSecondClass" data-options="required:true" name="materialSecondClass"  style="width:100%;height:32px">
						</div>
					</div>
				</div>
			</div>
			<div id="include"></div>
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
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
	<div id="showMaterialTypePanel" class="easyui-dialog" title="选择物料类型" style="width:300px;height:150px;"
			data-options="resizable:false,modal:true,closed:true">
		<div class="widgetGroup" style="padding:20px 0 0 0;">
			<div class="wd-com wd-4"></div>
			<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">物料类型:</label></div>
			<div class="wd-com wd-8">
				<input id="showMaterialType" style="width:100%;height:28px">
			</div>
		</div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "showMaterialTypeEnter()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('showMaterialTypePanel')">取消</a>
		</div>
	</div>
	<div id="farmSelect" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('farmSelect',390)"></span><span>猪场选择</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<input id="selectedIndex" name="selectedIndex" type="hidden" />
			 <div id="farmTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSelectFarm()" class="rightSlipBtn blue">确定</button>
			<button type="button" onclick="rightSlipFun('farmSelect',390)" class="rightSlipBtn green">取消</button>
		</div>
	</div>
</body>
</html>
