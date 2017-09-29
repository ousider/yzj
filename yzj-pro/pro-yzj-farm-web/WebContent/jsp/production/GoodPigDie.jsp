<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/GoodPigDie.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增商品猪死亡',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input class="easyui-textbox" name="pigType" id="pigType" value="3"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5"><input class="easyui-datebox" id="billDate" name="billDate" data-options="prompt:'请输入单据日期',required:false,validType:'ltCurrentDate',readonly:true" style="width:100%;height:32px"></div>
					</div>
				</div>
			</form>
			<div id="toolbar">
				<input id="addNum" class="easyui-numberspinner"  style="width:80px;height:26px;" data-options="value:1,required:true,validType:'range[1,999]'">
				<button type="button" onclick="detailAdd()" class="listTableToolbarBtn">新增</button>
				<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
				<button type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
				<button type="button" onclick="importWinShow()" class="listTableToolbarBtn">导入</button>
				<button type="button" onclick="selectGoodPig()" class="listTableToolbarBtn">选择猪只</button>
			</div>
			<div id="listTable"></div>
	</div>
	<div id="dlg-buttons" style="height:65px;">
		<div style="height:20px;background-color:#DCEA60;padding:0 10px;text-align:right;">
				<span style="color:red;">合计：&nbsp;&nbsp;</span><span>死亡数量：</span><span id="leaveQtySum">0</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		<div style="height:35px;text-align:right;padding:5px;">
			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn green">保存</button>
			<button type="button" onclick="onBtnReset()" class="winFooterBtn blue">重置</button>
		</div>
	</div>
	<div id="selectPigWin" class="rightSlipWin_780" style="z-index: 9002;">
	<div class="rightSlipTitle">
		<span class="arrow_right" onClick="rightSlipFun('selectPigWin',780,true)"></span><span>选择猪只</span>
	</div>
	<div class="rightSlipContent heightFiexd">
		 <form id="selectPigSearchForm" method="post">
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>查询条件</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-3"><label class="label">猪舍:</label></div>
						<div class="wd-com wd-5">
							<input id="houseId" name="houseId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label" id="swineryLabel">批次:</label></div>
						<div class="wd-com wd-5">
							<input id="swineryId" name="swineryId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label" id="pigClassLabel">猪只状态:</label></div>
						<div class="wd-com wd-5">
							<input id="pigClassId" name="pigClassId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">品种:</label></div>
						<div class="wd-com wd-5">
							<input id="breedId" name="breedId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">最小日龄:</label></div>
						<div class="wd-com wd-5">
							<input  id="minDateage" name="minDateage" class="easyui-numberspinner" data-options="value:0" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label">最大日龄:</label></div>
						<div class="wd-com wd-5">
							<input  id="maxDateage" name="maxDateage" class="easyui-numberspinner" data-options="value:9999" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-3"><label class="label" id="seedLabel">留种猪:</label></div>
						<div class="wd-com wd-5">
							<input type="checkbox" id="seedPigFlag" name="seedPigFlag" value="1">
						</div>
					</div>
				</div>
			</div>
	 	</form>
		<div id="selectPigListTable"></div>
	 </div>
	<div class="rightSlipFooter">
		<button type="button" onclick="selectGoodPigSearch()" class="rightSlipBtn green">搜索</button>
		<button type="button" id="selectPigSureBtn" onclick="selectGoodPigSure()" class="rightSlipBtn green">确定</button>
		<button type="button" onclick="selectPigReset()" class="rightSlipBtn blue">重置</button>
	</div>
</div>
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
</body>
</html>