<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/supplyChian/SelectRequiredMaterial.js?v=${param.randomnumber}"></script>
<div id="selectRequiredMaterialWin" class="rightSlipWin_780" style="z-index: 9001;">
	<div class="rightSlipTitle">
		<span class="arrow_right" onClick="rightSlipRequireAll()"></span><span>选择需求物料</span>
	</div>
	<div class="rightSlipContent heightFiexd">
		<div id="selectRequiredMaterialListTable"></div>
		<div id="sRMLTToolbarList">
			<button type="button" onclick="onBtnSplit()" class="tableToolbarBtn btn-middle">拆分</button>
			<button type="button" onclick="onBtnMerge()" class="tableToolbarBtn btn-middle">合并</button>
		</div>
	 </div> 
	<div class="rightSlipFooter_height_50">
		<button type="button" onclick="selectRequiredMaterialSure()" class="rightSlipBtn green">确定</button>
		<button type="button" onclick="selectRequiredMaterialReset()" class="rightSlipBtn blue">重置</button>
	</div>
	<div id="splitMaterialWin" class="easyui-dialog" title="拆分" style="width:300px;height:150px;"
			data-options="resizable:false,modal:false,closed:true,inline:true,top:200">
		<form id="splitMaterialForm" class="form-inline">
			<div style="display:none">
				<input type="hidden" id="requireQty" name="requireQty"/>
			</div>
			<div class="widgetGroup" style="padding:20px 0 0 0;">
				<div class="wd-com wd-2"></div>
				<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">需求量:</label></div>
				<div class="wd-com wd-12">
					<input class="easyui-numberspinner" id="requireQtyNew" name="requireQtyNew" data-options="min:0,precision:3,required:true" style="width:100%;height:32px">
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
<div id="selectRequiredMaterialDetailId" class="rightSlipWin_390">
	<div class="rightSlipTitle">
		<span class="arrow_right" onClick="rightSlipFun('selectRequiredMaterialDetailId',390)"></span><span>采购单入库明细</span>
	</div>
	<div class="rightSlipContent heightFiexd">
		<div id="selectRequiredMaterialDetailListTable"></div>
	</div>
</div>