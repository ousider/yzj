<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/DafengModel.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/supplyChian/GiveOutMaterial.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none">
		<input type="hidden" name="dafengModel" id="dafengModel" value="-1"/>
	</div>
	<table id="table"></table>
	<div id="tableToolbarList">
			<button type="button" onclick="onBtnGiveOutTrue()" class="tableToolbarBtn btn-middle">确认领用</button> 
			<button type="button" onclick="onBtnGiveOutFalse()" class="tableToolbarBtn btn-middle">未领用</button>
	</div>
	<div id="giveOutMaterialDetailId" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('giveOutMaterialDetailId',780)"></span><span>领用明细</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="listTable"></div>
		</div>
		<div id="sRMLTToolbarList">
			<button type="button" onclick="onBtnSplit()" class="tableToolbarBtn btn-middle">拆分</button>
			<button type="button" onclick="onBtnMerge()" class="tableToolbarBtn btn-middle">合并</button>
		</div>
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
			</div>
		</form>
		<div id="dlg-buttons" class="rightSlipFooter_height_50">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="rightSlipBtn green">保存</button>
			<button type="button" onclick="onBtnCancel()" class="rightSlipBtn blue">取消</button>
		</div>
		<div id="splitMaterialWin" class="easyui-dialog" title="拆分" style="width:300px;height:150px;"
			data-options="resizable:false,modal:false,closed:true,inline:true,top:200">
			<form id="splitMaterialForm" class="form-inline">
				<div style="display:none">
					<input type="hidden" id="outputQty" name="outputQty"/>
					<input type="hidden" id="outputMinQty" name="outputMinQty"/>
				</div>
				<div class="widgetGroup" style="padding:20px 0 0 0;">
					<div class="wd-com wd-2"></div>
					<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">拆分量:</label></div>
					<div class="wd-com wd-12">
						<input class="easyui-numberspinner" id="outputQtyNew" name="outputQtyNew" data-options="min:0,precision:3,required:true" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-4"></div>
				</div>
			</form>
			<div class="dialog-button messager-button" style="width:290px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "splitMaterialSure()">确定</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "splitMaterialCancel()">取消</a>
			</div>
		</div>
	</div>
</body>
</html>