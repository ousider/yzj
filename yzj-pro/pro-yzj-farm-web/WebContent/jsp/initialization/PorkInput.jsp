<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/jsp/Head.jsp" />
		<script src="${base_url}/js/initialization/PorkInput.js?v=${param.randomnumber}"></script>
	</head>
	<body>
		<div id="tableToolbar">
 			<!-- <button type="button" onclick="searchUnexecutedRecords()" class="tableToolbarBtn btn-middle">未执行记录</button> -->
			<button type="button" onclick="detailDelete()" class="tableToolbarBtn btn-middle red">删除</button>
			<button type="button" onclick="importWinShow()" class="tableToolbarBtn btn-middle">导入</button>
			<button type="button" onclick="execute()"class="tableToolbarBtn btn-middle">执行</button>
		</div>
		<table id="table"></table>
		<div id="eventImport" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('eventImport',390,true)"></span><span>从excel中导入数据</span>
		</div>
		<div class="rightSlipContent">
			 <form id="importForm" method="post" enctype="multipart/form-data" class="form-inline">
				<div class="wd-com wd-4"></div>
				<div class="wd-com wd-16">
					<button type="button"  onclick="downLoadEventTemplate()" class="downloadBtn">下载模板</button>
				</div>
				<div class="wd-com wd-4"></div>
				<div class="wd-com wd-1"></div>
				<div class="wd-com wd-22">
					<span style="color:red;">导入前请先下载对应模板（模板中前50行存在样式，其后需自行复制前50行样式）</span>
					<input class="easyui-filebox" id="uploadFile" name="uploadFile" data-options="buttonText:'选择文件'" style="width:100%;height:35px;">
				</div>
				<div class="wd-com wd-1"></div>
			
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnImport()" class="rightSlipBtn blue">导入</button>
			<button type="button" onclick="onBtnReImport()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	
	</body>
</html></html>