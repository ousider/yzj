<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/supplyChian/UpdatePrice.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="advancedSearch" onClick="leftSilpFun('advancedSearchId')"><span>高级查询</span></div>
	<div id="advancedSearchId" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('advancedSearchId',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">供应商:</label></div>
				<div class="wd-com wd-15">
					<input id="searchSupplierId" name="searchSupplierId" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">月份:</label></div>
				<div class="wd-com wd-15">
					<input id="searchMonth" name="searchMonth" style="width:100%;height:35px">
				</div>
			 	<div class="wd-com wd-8"><label class="label">日期范围:</label></div>
				<div class="wd-com wd-15">
					<input id="serchDateRange" name="serchDateRange" style="width:100%;height:35px">
				</div>
			 	<!-- <div class="wd-com wd-8"><label class="label">日期范围:</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="advancedStartDate" name="advancedStartDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-1"><label class="label">~</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="advancedEndDate" name="advancedEndDate" style="width:100%;height:35px">
				</div> -->
			 </form>
		 </div>
		 <div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="tableToolbarList">
			<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
			<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看明细</button>
<!-- 			<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button> -->
<!-- 			<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button> -->
	</div>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post" class="form-inline">
			<div style="display:none">
				<input type="hidden" name="rowId" id="rowId" value="0"/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">供应商:</label></div>
						<div class="wd-com wd-5">
							<input id="supplierId" name="supplierId" style="width:100%;height:32px">
						</div>
						
						<!-- <div class="wd-com wd-2"><label class="label">日期:</label></div>
						<div class="wd-com wd-2">
							<input class="easyui-datebox"  id="startDate" name="startDate" data-options="prompt:'',required:true,editable:false" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-1"><label class="label">~</label></div>
						<div class="wd-com wd-2">
							<input class="easyui-datebox"  id="endDate" name="endDate" data-options="prompt:'',required:true,editable:false" style="width:100%;height:35px">
						</div> -->
						
						
						<div class="wd-com wd-2"><label class="label">月份:</label></div>
						<div class="wd-com wd-2">
							<input id="month" name="month" style="width:100%;height:32px">
						</div>
						
						<div class="wd-com wd-3"><label class="label">日期范围:</label></div>
							<div class="wd-com wd-2">
							<input id="monthSplit" name="monthSplit" style="width:100%;height:32px">
						</div>
					</div>
				</div>
			</div>
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
		<div id="listTable"></div>
	</div>
		
		<div id="updatePriceDetailId" class="rightSlipWin_780">
			<div class="rightSlipTitle">
				<span class="arrow_right" onClick="rightSlipAll()"></span><span>明细</span>
			</div>
			<div class="rightSlipContent heightFiexd">
				<div id="updatePriceDetailInfoTable"></div>
			</div>
		</div>
	
		<%-- <jsp:include page="/jsp/ListTableToolbar.jsp" /> --%>
		<!-- 引入保存取消 -->
		<jsp:include page="/jsp/WinFooter.jsp" />
		<!-- <div id="updatePriceDetailTable"></div> -->
	
</body>
</html>
