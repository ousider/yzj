<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/production/PpPig.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="pigTableToolbar">
<!-- 		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button> -->
		<button type="button" onclick="editPigEventCancelByBtn()" class="tableToolbarBtn btn-middle">撤销事件</button>
		<button type="button" onclick="pigCard(${userDetail.farmId},'${userDetail.farmName}','${userDetail.employName}','${finereport_url}','${finereport_username}')" class="tableToolbarBtn btn-middle">繁殖卡片</button>
		<button type="button" onclick="pigCheckLogo()" class="tableToolbarBtn btn-middle">查情标识</button>
		<button type="button" onclick="pigCancelLogo()" class="tableToolbarBtn btn-middle">取消查情</button>	
	</div>	
	<div id="advancedSearch" onClick="leftSilpFun('pigHouseAdSearch')"><span>高级查询</span></div>
	<div id="pigHouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigHouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">耳牌号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="earBrand" data-options="prompt:'请输入耳牌号'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">耳缺号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox"  name="earShort" data-options="prompt:'请输入耳缺号'" style="width:100%;height:35px">
				</div>
				
				<div class="wd-com wd-8"><label class="label">猪只批次:</label></div>
				<div class="wd-com wd-15">
					<input id="swineryName" name="swineryName" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪舍名称:</label></div>
				<div class="wd-com wd-15">
					<input id="houseName" name="houseName" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">物料名称:</label></div>
				<div class="wd-com wd-15">
					<input id="materialId" name="materialId" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪类别:</label></div>
				<div class="wd-com wd-15">
					<input name="pigTypeName" id="pigTypeName" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">品种名称:</label></div>
				<div class="wd-com wd-15">
					<input id="breedName" name="breedName" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪只状态:</label></div>
				<div class="wd-com wd-15">
					<input id="pigClass" name="pigClass" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">胎次:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-numberspinner" name="parity" style="width:100%;height:35px">
				</div>
				
					<input type="checkbox" name="moveOut" value="18" style="margin-left: 130px">离场
					<input type="checkbox" name="orderByASC" value="18" style="margin-left: 30px">状态日龄排序 ↑
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div class="rightSlipWin_780" id="pigCard">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigCard',780)"></span><span>猪只卡片</span>
		</div>
		<div class="rightSlipContent">
		 	<form id="showPigHisForm" method="post" class="form-inline">
		 		<div style="display: none">
					<input class="easyui-textbox" name="pigId" id="pigId" value="0" />
				</div>
					<div class="wd-com wd-5">
						<label class="label">事件开始时间:</label>
					</div>
					<div class="wd-com wd-7">
						<input class="easyui-datebox" name="endBeginDate" data-options="prompt:'请输入事件结束时间',required:false"  style="width: 96%; height: 35px"/>
					</div>
					
					<div class="wd-com wd-5">
						<label class="label">事件结束时间:</label>
					</div>
					<div class="wd-com wd-7">
						<input class="easyui-datebox" name="endEventDate" data-options="prompt:'请输入事件结束时间',required:false" style="width: 96%; height: 35px"/>
					</div>
					
					<div class="wd-com wd-5">
						<label class="label">录入开始时间:</label>
					</div>
					<div class="wd-com wd-7">
						<input class="easyui-datebox" name="beginCreateDate" data-options="prompt:'请输入录入开始时间',required:false" style="width: 96%; height: 35px"/>
					</div>
					
					<div class="wd-com wd-5">
						<label class="label">录入结束时间:</label>
					</div>
					<div class="wd-com wd-7">
						<input class="easyui-datebox" name="endCreateDate" data-options="prompt:'请输入录入结束时间',required:false" style="width: 96%; height: 35px"/>
					</div>
					
					<div class="wd-com wd-5">
						<label class="label">事件名称</label>
					</div>
					<div class="wd-com wd-7">
						<input id="eventName" name="eventName" style="width: 96%; height: 35px"/>
					</div>
					
					<div class="wd-com wd-5">
						<label class="label"></label>
					</div>
					<div class="wd-com wd-7">
						<button type="button" onclick="searchPigHis()" class="tableToolbarBtn btn-middle">查询</button>
					</div>
		 	</form>
		 </div>
		 <table id="showPigHis"></table>
	</div>
	
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
