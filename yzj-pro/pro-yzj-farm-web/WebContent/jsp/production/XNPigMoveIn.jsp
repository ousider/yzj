<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/FieldFactory.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/production/XNPigMoveIn.js?v=${param.randomnumber}"></script>
	<script src="${base_url}/js/SelectPig.js?v=${param.randomnumber}"></script>
</head>
<body onKeyDown="keyBoardDown(event)" onKeyUp="keyBoardUp(event)">
	<div id="editWin" class="easyui-window windowStyle" data-options="title:'新增虚拟公猪入场',draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:false,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
		<form id="editForm" method="post">
				<div style="display:none">
					<input class="easyui-textbox" name="rowId" id="rowId" value="0"/>
					<input class="easyui-textbox" name="pigType" id="pigType" value="2"/>
				</div>
				<div class="collapseField">
					<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
					<div class="fieldContent">
						<div class="wd-com wd-3"><label class="label">单据日期:</label></div>
						<div class="wd-com wd-5"><input class="easyui-datebox" id="enterDate" name="enterDate" data-options="prompt:'请输入单据日期',required:true,validType:'ltCurrentDate'" style="width:100%;height:32px"></div>
						<div class="wd-com wd-2"></div>
						<div class="wd-com wd-5"><input id="isCheck" name="isCheck" type="checkBox" checked="checked" onchange="isCheckChange(this)">虚拟平台内猪只</div>
					</div>
				</div>
			</form>
			<div id="toolbar">
				<input id="addNum" class="easyui-numberspinner" style="width:80px;height:26px;" data-options="value:1,required:true,validType:'range[1,999]'">
				<button id = "addButton" type="button" onclick="detailAdd()" class="listTableToolbarBtn">新增</button>
				<button type="button" onclick="detailDelete()" class="listTableToolbarBtn">删除</button>
				<button type="button" onclick="detailClear()" class="listTableToolbarBtn">清空</button>
				<button id = "selectButton" type="button" onclick="selectBreedPig()" class="listTableToolbarBtn">选择猪只</button>
				<!-- <button type="button" onclick="importWinShow()" class="listTableToolbarBtn">导入</button> -->
			</div>
			<div id="listTable"></div>
	</div>
<!-- 	<div id="dlg-buttons" style="height:65px;"> -->
<!-- 		<div style="height:35px;text-align:right;padding:5px;"> -->
<!-- 			<button type="button" id="btnSave" onclick="onBtnSave()" class="winFooterBtn green">保存</button> -->
<!-- 			<button type="button" onclick="onBtnReset()" class="winFooterBtn blue">重置</button> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
		<div id="selectBreedPigWin" class="rightSlipWin_780 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('selectBreedPigWin',780)"></span><span>选择种猪</span>
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
						<div class="wd-com wd-4"><label class="label">场区:</label></div>
						<div class="wd-com wd-8">
							<input id="farmId" name="farmId" class="easyui-textbox" style="width:100%;height:35px">
						</div>
					</div>
				</div>
			</div>
	 	</form>
			<div id="selectBreedPigTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="searchPigSearch()" class="rightSlipBtn green">搜索</button>
			<button type="button" onclick="selectBreedPigSure()" class="rightSlipBtn green">确定</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn blue">重置</button>
		</div>
	</div>
	<jsp:include page="/jsp/EventWinFooter.jsp" />
	<jsp:include page="/jsp/EventImportWin.jsp" />
	<jsp:include page="/jsp/PreSaveRecordWin.jsp" />
</body>
</html>