<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/CdBcodeCust.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="toolbar">
		<button type="button" id="onBtnSave" onclick="doSave()" class="tableToolbarBtn btn-middle">保存</button>
		<button type="button" onclick="doRecover()" class="tableToolbarBtn btn-middle">恢复</button>
	</div>
		<div id="advancedSearch" onClick="leftSilpFun('cdBcodeCustAdSearch')"><span>高级查询</span></div>
	<div id="cdBcodeCustAdSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('cdBcodeCustAdSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">业务编码名称:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="bcodeName" data-options="prompt:'请输入业务编码名称'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">前缀:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="prifixCode" data-options="prompt:'请输入前缀'" style="width:100%;height:35px">
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchCdBcodeCustToListForAdvancedSearchUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<!-- <table  id="listTable" class="easyui-datagrid" 
			data-options="rownumbers:true,
						  fitColumns:true,
						  height:'100%',
						  width:'100%',
						  fit:true,
						  toolbar:'#toolbar',
						  url:basicUrl+'/backEnd/searchBcodeToPage.do',
						  pagination:true,
						  checkOnSelect:false,
						  onClickCell:onClickCell,
						  rowStyler: function(index,row){
								if ((index+1) % 2 == 0){
									return 'background-color:#e4f4fe;';
								}
							},
						onLoadSuccess:function(data){
								if(!data.success){
									$.messager.alert({
										title: '错误',
										msg: data.errorMsg
									});
						    	}
							},
						  method:'get'">
			<thead>
				<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'bcodeName',width:50,align:'left'" style="background-color:#fff">业务编码名称</th>
				<th data-options="field:'serialLength',width:50,align:'left'">流水码长度</th>
				<th data-options="field:'prifixCode',width:50,align:'left'">前缀</th>
				<th data-options="field:'isUseBdate',width:50,align:'left'">是否时间</th>
				<th data-options="field:'limitNum',width:50,align:'left'">起始编码</th>
				</tr>
			</thead>
	</table> -->
	<table id="listTable"></table>
</body>
</html>