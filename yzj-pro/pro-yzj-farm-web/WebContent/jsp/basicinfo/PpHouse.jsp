<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/PpHouse.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="tableToolbarList">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
		<button type="button" onclick="onBtnCopyAdd()"class="tableToolbarBtn btn-middle">复制新增</button>
		<button type="button" onclick="onBtnDisableStart()" class="tableToolbarBtn btn-middle">停用</button>
		<button type="button" onclick="onBtnEnable()"class="tableToolbarBtn btn-middle">启用</button>		
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('pigHouseAdSearch')"><span>高级查询</span></div>
	<div id="pigHouseAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('pigHouseAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">猪舍代码:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="businessCode" data-options="prompt:'请输入猪舍代码'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪舍名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="houseName" data-options="prompt:'请输入猪舍名称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">猪舍类型:</label></div>
				<div class="wd-com wd-15">
					<input id="houseTypeForAdvancedSearch" name="houseType" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
			<form id="editForm" method="post" class="form-inline">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-3"><label class="label">猪舍名称:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-textbox" name="houseName" data-options="prompt:'请输入猪舍名称',required:true,validType:'numOrLetterOrChineseOr_'" style="width:100%;height:35px">
							</div>
							<!-- <div class="wd-com wd-3"><label class="label">产线:</label></div>
							<div class="wd-com wd-5">
								<input id="lineId" name="lineId" style="width:100%;height:35px">
							</div> -->
							<div class="wd-com wd-3"><label class="label">猪舍类型:</label></div>
							<div class="wd-com wd-5">
								<input id="houseType" name="houseType" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">面积（平方米）:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" name="area" data-options="prompt:'请输入面积',validType:'noNegative',min:0,precision:2" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">造价:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" name="cost" data-options="prompt:'请输入造价',validType:'noNegative'" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">折旧期:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" name="depreciationPolicy" data-options="prompt:'请输入折旧期',validType:'noNegative'" style="width:100%;height:35px">
							</div>
							<div id="isFosterHouseItem" style="display:inline">
								<div class="wd-com wd-3"><label class="label">是否是培育舍:</label></div>
								<div class="wd-com wd-5">
									<input id="isFosterHouse" name="isFosterHouse" style="width:100%;height:35px">
								</div>
							</div>
							<div class="wd-com wd-3"><label class="label">单元号:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" name="houseUnitNumber" data-options="required:true,min:0,max:999" style="width:100%;height:35px">
							</div>
							<div class="wd-com wd-3"><label class="label">栋号:</label></div>
							<div class="wd-com wd-5">
								<input class="easyui-numberspinner" name="houseBuildingNumber" data-options="required:true,min:0,max:99" style="width:100%;height:35px">
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
			<div id="toolbar">
				<label>栏数：</label><input id="addNum" class="easyui-numberspinner" style="width:80px;height:26px;" data-options="value:1,required:true,validType:'range[1,999]'">
				<label>栏均饲养量：</label><input id="feedPigNum" class="easyui-numberspinner" style="width:80px;height:26px;" data-options="value:1,required:true">
				<label>栏长(cm)：</label><input id="pigpenLength" class="easyui-numberspinner" style="width:80px;height:26px;" data-options="value:120,required:true">
				<label>栏宽(cm)：</label><input id="pigpenWidth" class="easyui-numberspinner" style="width:80px;height:26px;" data-options="value:120,required:true">
				<button type="button" onclick="noEventDetailAdd()" class="listTableToolbarBtn">新增</button>
				<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
			</div>
			<div id="listTable"></div>
	</div>
	
		<div class="rightSlipWin_780" id="pigHouseCard" >
		<div class="rightSlipTitle">
			<span class="arrow_right"  onClick="rightSlipFun('pigHouseCard',1080)"></span><span>猪舍明细</span>
		</div>
		 <table id="showHouseHis1"></table>
		 <table id="showHouseHis"></table>
	</div>
	<!-- 停用小窗口 -->
	<div id="stopDatePanel" class="easyui-dialog" title="停用" style="width:300px;height:150px;"
			data-options="resizable:false,modal:true,closed:true">
		<div class="widgetGroup" style="padding:20px 0 0 0;">
			<div class="wd-com wd-2"></div>
			<div class="wd-com wd-6"><label class="label" style="font-size:12px !important;">停用时间:</label></div>
			<div class="wd-com wd-14">
				<input class="easyui-datebox" id="stopDate" name="stopDate" data-options="value:getCurrentDate()" style="width:100%;height:35px">
			</div>
		</div>
		<div class="dialog-button messager-button" style="width:290px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "onBtnDisableEnter()">确定</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onClick = "dialogCancel('stopDatePanel')">取消</a>
		</div>
	</div>	
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>