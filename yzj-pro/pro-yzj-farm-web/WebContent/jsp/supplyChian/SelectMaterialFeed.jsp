<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script src="${base_url}/js/supplyChian/SelectMaterialFeed.js?v=${param.randomnumber}"></script>
<div id="SelectMaterialFeedWin" class="rightSlipWin_780" style="z-index: 9001;">
	<div class="rightSlipTitle">
		<span class="arrow_right" onClick="rightSlipFun('SelectMaterialFeedWin',780,true)"></span><span>选择需求物料</span>
	</div>
	<div class="rightSlipContent heightFiexd">
		<div id="selectMaterialFeedListTable"></div>
		<div id="sMFLTToolbarList">
		<div class="wd-com wd-3"><label class="label">入库日期：</label></div>
		<div class="wd-com wd-5">
			<input class="easyui-datebox" id="startDate" name="startDate" data-options="prompt:'请输入开始日期',required:true,onChange:startDateChange" style="width:100%;height:32px">
		</div>
		<div class="wd-com wd-1"><label class="label"></label></div>
		<div class="wd-com wd-5">
			<input class="easyui-datebox" id="endDate" name="endDate" data-options="prompt:'请输入日期',required:true,onChange:endDateChange" style="width:100%;height:32px">
		</div>
		</div>
	 </div> 
	<div class="rightSlipFooter_height_50">
		<button type="button" onclick="selectMaterialFeedSure()" class="rightSlipBtn green">确定</button>
		<button type="button" onclick="selectMaterialFeedReset()" class="rightSlipBtn blue">重置</button>
	</div>
</div>
