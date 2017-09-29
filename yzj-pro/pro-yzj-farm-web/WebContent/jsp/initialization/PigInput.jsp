<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/jsp/Head.jsp" />
		<script src="${base_url}/js/initialization/PigInput.js?v=${param.randomnumber}"></script>
	</head>
	<body>
		<table id="table"></table>
		<div id="tableToolbar">
 			<!-- <button type="button" onclick="searchUnexecutedRecords()" class="tableToolbarBtn btn-middle">未执行记录</button> -->
			<button type="button" onclick="importWinShow()" class="tableToolbarBtn btn-middle">导入</button>
			<button type="button" onclick="execute()"class="tableToolbarBtn btn-middle">执行</button>
			<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle red">删除</button>
			<button type="button" onclick="searchExceptionalRecords()" class="tableToolbarBtn btn-middle">失败记录</button>
			<button type="button" onclick="searchSuccessRecords()" class="tableToolbarBtn btn-middle">成功记录</button>
		</div>
		<div id="eventImport" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('eventImport',390,true)"></span><span>从excel中导入数据</span>
		</div>
		<div class="rightSlipContent">
			 <form id="importForm" method="post" enctype="multipart/form-data" class="form-inline">
				<div class="wd-com wd-14">
					<div class="wd-com wd-10"><label class="label">猪类别:</label></div>
					<div class="wd-com wd-14"><div id="pigType"></div></div>
				</div>
				<div class="wd-com wd-8">
					<button type="button" onclick="downLoadEventTemplate()" class="downloadBtn">下载模板</button>
				</div>
				<div class="wd-com wd-2"></div>
				<div class="wd-com wd-1"></div>
				<div class="wd-com wd-22">
					<span style="color:red;">导入前请先下载对应模板（模板中前50行存在样式，其后需自行复制前50行样式）</span>
					<input class="easyui-filebox" id="uploadFile" name="uploadFile" data-options="buttonText:'选择文件'" style="width:100%;height:35px;">
				</div>
			
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnImport()" class="rightSlipBtn blue">导入</button>
			<button type="button" onclick="onBtnReImport()" class="rightSlipBtn green">重置</button>
		</div>
	</div>

	<div id="errorWin" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('errorWin',780)"></span><span>执行失败数据</span>
		</div>
		 <table id="errorTable"></table>
	</div>
	
	<div id="successWin" class="rightSlipWin_780">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('successWin',780)"></span><span>执行成功数据</span>
		</div>
		 <table id="successTable"></table>
	</div>
	
	</body>
</html>