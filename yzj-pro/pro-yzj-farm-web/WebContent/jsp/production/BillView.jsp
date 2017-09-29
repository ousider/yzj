<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/production/BillView.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="tableToolbar">
		<button type="button" onclick="searchEventDetailByBtn()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="editEventCancelByBtn('table')" class="tableToolbarBtn btn-middle">撤销</button>
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('advancedSearchId')"><span>高级查询</span></div>
	<div id="advancedSearchId" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('advancedSearchId',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
			 <div class="wd-com wd-8"><label class="label">单据号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="businessCode" name="businessCode" data-options="prompt:'请输入耳牌号'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">事件类型：</label></div>
				<div class="wd-com wd-15">
					<input id="eventCode" name="eventCode" style="width:100%;height:35px">
				</div>
				<!-- <div class="wd-com wd-8"><label class="label">录入员:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="createName" name="createName" data-options="prompt:'请输入耳缺号'" style="width:100%;height:35px">
				</div> -->
				
				<div class="wd-com wd-8"><label class="label">单据日期:</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="billDate" name="billDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-1"><label class="label">~</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="billDate2" name="billDate2" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">创建日期：</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="createDate" name="createDate" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-1"><label class="label">~</label></div>
				<div class="wd-com wd-7">
					<input class="easyui-datebox"  id="createDate2" name="createDate2" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		 <div class="rightSlipFooter">
				<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
		</div>
	</div>
	
	<table id="table"></table>
	
	<div id="eventDetailId" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<div style="display:none">
					<input type="hidden" id="billIdForDetail" />
					<input type="hidden" id="eventCodeForDetail" />
					<input type="hidden" id="eventNameChineseForDetail" />
			</div>
			<span class="arrow_right" onClick="rightSlipFun('eventDetailId',780)"></span><span>事件详情</span>
		</div>
		<div id="eventToolBar">
			<button type="button" onclick="editEventCancelByBtn('eventDetailTbale')" class="tableToolbarBtn btn-middle">撤销</button>
		</div>
		<table id="eventDetailTbale"></table>
	</div>
</body>
</html>