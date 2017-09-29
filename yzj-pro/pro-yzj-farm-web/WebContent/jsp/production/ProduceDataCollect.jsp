<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/lib/jquery-easyui-1.4.5/jquery.edatagrid.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/ProduceDataCollect.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div style="display:none;">
		<input type="hidden" id="farmName" name="farmName" value="${userDetail.farmName}"/>
		<input type="hidden" id="farmId" name="farmId" value="${userDetail.farmId}"/>
		<input type="hidden" id="employName" name="employName" value="${userDetail.employName}"/>
		<input type="hidden" id="finereport_url" name="finereport_url" value="${finereport_url}"/>
		<input type="hidden" id="finereport_username" name="finereport_username" value="${finereport_username}"/>
	</div>
	<div id="tableToolbar">
		<span>猪场：</span><input id="selectFarm" style="width:150px;">
		<span>报表类型：</span><input id="reportType" style="width:80px;">
		<span id="month">月：</span><input class="easyui-datebox" id="searchMonth" data-options="value:getCurrentDate(),onChange:selectMonth" style="width:80px;">
		<span id="week" style="display:none;">周：</span><input class="easyui-textbox" id="searchWeek" data-options="validType:'yearWeek[weekRows.year,weekRows.week]',disabled:true,onChange:selectWeek" style="width:80px;">
		<span id="quarter" style="display:none;">季度：</span><input id="searchQuarter" class="easyui-combobox" style="width:80px;">
		<span id="year" style="display:none;">年：</span><input class="easyui-datebox" id="searchYear" data-options="validType:'value:getCurrentDate()',onChange:selectYear" style="width:80px;">
		<span>开始时间：</span><input class="easyui-datebox" id="startDate" data-options="disabled:true" style="width:100px;">
		<span>结束时间：</span><input class="easyui-datebox" id="endDate" data-options="disabled:true" style="width:100px;">
		<button type="button" onclick="onBtnCalculate()" class="tableToolbarBtn btn-middle">计算</button>
		<button type="button" onclick="onBtnCheck()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="onBtnNotes()" class="tableToolbarBtn btn-middle">备注</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
		<button type="button" onClick="leftSilpFun('productionCollectSearch')" class="tableToolbarBtn btn-middle">高级查询</button>
	</div>
	<div id="productionCollectSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('productionCollectSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">报表类型:</label></div>
				<div class="wd-com wd-15">
					<input id="searchReportType" name="searchReportType" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">开始日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" id="searchStartDate" name="searchStartDate" data-options="prompt:'请输入开始日期',required:false" style="width:100%;height:32px">
				</div>
				<div class="wd-com wd-8"><label class="label">结束日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" id="searchEndDate" name="searchEndDate" data-options="prompt:'请输入结束日期',required:false" style="width:100%;height:32px"> 
				</div>
				<div class="wd-com wd-8"><label class="label">版本号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-numberspinner" id="searchVersion" name="version" style="width:100%;height:35px"> 
				</div>
				<div class="wd-com wd-8"><label class="label">历史版本:</label></div>
				<div class="wd-com wd-15">
					<input type="checkbox" id="isHis" name="isHis" value="1">
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchProduceDataCollectUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'周月报',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'">
		<form id="editForm" method="post" class="form-inline" style="height:100%;">
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>生产数据汇总</div>
				<div class="fieldContent">
					<div id="proDataListTable1"></div>
					<div id="proDataListTable2"></div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>商品猪销售数据</div>
				<div class="fieldContent">
					<div id="goodPigSellDataListTable"></div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>断奶数据</div>
				<div class="fieldContent">
					<div id="weanDataListTable"></div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>各场关键指标对比排名</div>
				<div class="fieldContent">
					<div id="keyIndicatorDataListTable"></div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span id="CHANGE_CONTENT" class="arrowUp" onclick="upOrDown(this)"></span>生产数据分析</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-24">
							<input class="easyui-textbox" id="productionNotes" name="productionNotes" data-options="prompt:'请输入生产数据分析',multiline:true" style="width:100%;height:70px">
						</div>
					</div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span id="CHANGE_CONTENT" class="arrowUp" onclick="upOrDown(this)"></span>大生物安全分析</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-24">
							<input class="easyui-textbox" id="biologicalSafetyNotes" name="biologicalSafetyNotes" data-options="prompt:'请输入大生物安全分析',multiline:true" style="width:100%;height:70px">
						</div>
					</div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span id="CHANGE_CONTENT" class="arrowUp" onclick="upOrDown(this)"></span>种群规划分析</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-24">
							<input class="easyui-textbox" id="populationPlanningNotes" name="populationPlanningNotes" data-options="prompt:'请输入种群规划分析',multiline:true" style="width:100%;height:70px">
						</div>
					</div>
				</div>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span id="CHANGE_CONTENT" class="arrowUp" onclick="upOrDown(this)"></span>销售分析</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-24">
							<input class="easyui-textbox" id="saleNotes" name="saleNotes" data-options="prompt:'请输入销售分析',multiline:true" style="width:100%;height:70px">
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>