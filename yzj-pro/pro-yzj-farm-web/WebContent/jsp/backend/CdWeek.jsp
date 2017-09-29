<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/jsp/Head.jsp" />
		<script src="${base_url}/js/backend/CdWeek.js?v=${param.randomnumber}"></script>
	</head>
	<body>
	<div id="advancedSearch" onClick="leftSilpFun('pigHouseAdSearch')"><span>高级查询</span></div>
	<div id="pigHouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigHouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">公司ID:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="companyId" data-options="prompt:'请输入公司ID'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">年:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="year" data-options="prompt:'请输入年份'" style="width:100%;height:35px">
				</div>
				
				<div class="wd-com wd-8"><label class="label">周:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="week" data-options="prompt:'请输入周数'" style="width:100%;height:35px">
				</div>
				
				<div class="wd-com wd-8"><label class="label">开始日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" name="startDate" style="width:100%;height:35px">
				</div>
				
				<div class="wd-com wd-8"><label class="label">结束日期:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-datebox" name="endDate" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
		<table id="table"></table>
	</body>
</html>