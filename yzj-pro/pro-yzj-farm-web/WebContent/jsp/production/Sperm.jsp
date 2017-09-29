<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/production/Sperm.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none;">
		<input type="hidden" id="farmName" name="farmName" value="${userDetail.farmName}"/>
		<input type="hidden" id="farmId" name="farmId" value="${userDetail.farmId}"/>
		<input type="hidden" id="employName" name="employName" value="${userDetail.employName}"/>
		<input type="hidden" id="finereport_url" name="finereport_url" value="${finereport_url}"/>
		<input type="hidden" id="finereport_username" name="finereport_username" value="${finereport_username}"/>
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('cdMaterialAdSearch')"><span>高级查询</span></div>
	<div id="cdMaterialAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('cdMaterialAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			<form id="searchForm" method="post" class="form-inline">
			 	<div class="wd-com wd-8"><label class="label">公猪耳号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="earBrand" name="earBrand" style="width:100%;height:35px">
				</div>
			 	<div class="wd-com wd-8"><label class="label">公猪品种:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="breedName" name="breedName" style="width:100%;height:35px">
				</div>
			 	<div class="wd-com wd-8"><label class="label">精液批号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="semenBatchNo" name="semenBatchNo"  style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">采精日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox"  id="semenDate" name="semenDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">失效日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox"  id="validDate" name="validDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">库存数量:</label></div>
					<div class="wd-com wd-15">
						<input type="checkbox" id="requestType" name="requestType" value="1">
				</div>
				<div class="wd-com wd-8"><label class="label">采精升序排列:</label></div>
					<div class="wd-com wd-15">
						<input type="checkbox" id="ascending" name="ascending" value="1">
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
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="spermInput()" class="tableToolbarBtn btn-middle">精液入库</button>
		<button type="button" onclick="batchScrap()" class="tableToolbarBtn btn-middle">批量报废</button>
		<button type="button" onclick="batchTrunOverScrap()" class="tableToolbarBtn btn-middle" style="width:100px;">批量反报废</button>
		<button type="button" onclick="tagBatchPrint()" class="tableToolbarBtn btn-middle" style="width:100px;">标签批量打印</button>
		<button type="button" onclick="spermSell()" class="tableToolbarBtn btn-middle">精液销售</button>
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<br>
		<div id="spermDetailTable2"></div>
	</div>
	<div id="spermDetailId" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('spermDetailId',780)"></span><span>精液明细批号</span>
		</div>
		<div id="semenBatchNoTaleToolbarDiv" style="display:none;">
			<div id="semenBatchNoTaleToolbar">
				<button type="button" onclick="print()" class="tableToolbarBtn btn-middle">打印</button>
				<button type="button" onclick="scrap()" class="tableToolbarBtn btn-middle">报废</button>
				<button type="button" onclick="trunOverScrap()" class="tableToolbarBtn btn-middle">反报废</button>
			</div>
		</div>	
		 <table id="semenBatchNoClick"></table>
	</div>
	<!-- 精液明细批号 -->
	
	<!-- 批量报废小窗口 -->
	<div id="commenScrapTypePanel" class="easyui-dialog" title="报废原因" style="width:300px;height:150px;"
			data-options="resizable:false,modal:true,closed:true">
		<div class="widgetGroup" style="padding:20px 0 0 0;">
			<div class="wd-com wd-2"></div>
			<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">报废原因:</label></div>
			<div class="wd-com wd-14">
				<input class="easyui-textbox" id="scrapReason" name="scrapReason"  style="width:100%;height:35px">
			</div>
		</div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "showBatchScrapEnter()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('commenScrapTypePanel')">取消</a>
		</div>
	</div>
	<!--报废窗口 -->
	<div id="ScrapTypePanel" class="easyui-dialog" title="报废原因" style="width:300px;height:150px;"
			data-options="resizable:false,modal:true,closed:true">
		<div class="widgetGroup" style="padding:20px 0 0 0;">
			<div class="wd-com wd-2"></div>
			<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">报废原因:</label></div>
			<div class="wd-com wd-14">
				<input class="easyui-textbox" id="scrapReason2" name="scrapReason2"  style="width:100%;height:35px">
			</div>
		</div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "showScrapEnter()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('ScrapTypePanel')">取消</a>
		</div>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
