<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/ToFatten.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增转育肥',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
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
				<button type="button" onclick="selectToFattenPig()" class="listTableToolbarBtn">选择猪只</button>
			</div>
			<div id="listTable"></div>
	</div>
	<div id="selectToFattenPigWin" class="rightSlipWin_780 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('selectToFattenPigWin',780,true)"></span><span>选择猪只</span>
		</div>
		<div class="rightSlipContent heightFiexd">
		<form id="selectPigSearchForm" method="post">
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>查询条件</div>
				<div class="fieldContent">
					<div class="widgetGroup">
						<div class="wd-com wd-4"><label class="label">耳牌号:</label></div>
						<div class="wd-com wd-8">
							<input id="earBrand" name="earBrand" class="easyui-textbox" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">猪只状态:</label></div>
						<div class="wd-com wd-8">
							<input id="pigClassId" name="pigClassId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">品种:</label></div>
						<div class="wd-com wd-8">
							<input id="breedId" name="breedId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">性别:</label></div>
						<div class="wd-com wd-8">
							<input id="sex" name="sex" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">转舍类型:</label></div>
						<div class="wd-com wd-8">
							<input id="changeHouseType" name="changeHouseType" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">猪舍:</label></div>
						<div class="wd-com wd-8">
							<input id="houseId" name="houseId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label">猪群:</label></div>
						<div class="wd-com wd-8">
							<input id="swineryId" name="swineryId" style="width:100%;height:35px">
						</div>
						<div class="wd-com wd-4"><label class="label" id="seedLabel">留种猪:</label></div>
						<div class="wd-com wd-8">
							<input type="checkbox" id="seedPigFlag" name="seedPigFlag" value="1">
						</div>
					</div>
				</div>
			</div>
	 	</form>
			<div id="searchToFattenPigTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button id="searchPigShowBy2" type="button" onclick="searchPigSearch()" class="rightSlipBtn green">搜索</button>
			<button type="button" onclick="selectToFattenPigSure()" class="rightSlipBtn blue">确定</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn blue">重置</button>
		</div>
	</div>
	<jsp:include page="/jsp/EventWinFooter.jsp" />
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
</body>
</html>