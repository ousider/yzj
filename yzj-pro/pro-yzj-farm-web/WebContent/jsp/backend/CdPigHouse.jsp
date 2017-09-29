<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/CdPigHouse.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="advancedSearch" onClick="leftSilpFun('pigHouseAdSearch')"><span>高级查询</span></div>
	<div id="pigHouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigHouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">猪舍类别:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="houseTypeName" data-options="prompt:'请输入猪舍类别'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">清洗消毒天数:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="disinfectDay" data-options="prompt:'请输入清洗消毒天数'" style="width:100%;height:35px">
				</div>
				
				<!-- <div class="wd-com wd-8"><label class="label">消毒方法:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="disinfectMethod" data-options="prompt:'请输入消毒方法'" style="width:100%;height:35px">
				</div> -->
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
