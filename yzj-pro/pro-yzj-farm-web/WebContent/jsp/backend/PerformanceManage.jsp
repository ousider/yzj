<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/backend/PerformanceManage.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="tableToolbar">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
	</div>
		<div id="advancedSearch" onClick="leftSilpFun('performanceManageSearch')"><span>高级查询</span></div>
	<div id="performanceManageSearch" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('performanceManageSearch',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-5"><label class="label">考核内容:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" id="assessContent_" name="assessContent" data-options="prompt:'请输入考核内容'" style="width:100%;height:35px"> 
				</div>
				<div class="wd-com wd-5"><label class="label">区域:</label></div>
				<div class="wd-com wd-15">
					<input id="assessRegion_" name="assessRegion" data-options="prompt:'请输入区域'" style="width:100%;height:35px"> 
				</div>
			 </form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchPerformanceManageToPageUrl)" class="rightSlipBtn blue">搜索</button>
			<button type="button" onclick="onBtnReSearch()" class="rightSlipBtn green">重置</button>
		</div>
	</div>
	<table id="table"></table>
	<div id="editWin" class="easyui-window windowStyle" data-options="draggable:false,inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'">
		<form id="editForm" method="post">
			<div style="display:none">
				<input class="easyui-textbox" name="rowId" id="rowId"/>
			</div>
			<div class="collapseField">
				<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
				<div class="fieldContent">
					<div class="wd-com wd-3"><label class="label">区域:</label></div>
					<div class="wd-com wd-5"> 
						<input id="assessRegion" name="assessRegion" data-options="prompt:'请输入区域',required:true" style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">考核内容:</label></div>
					<div class="wd-com wd-5"> 
						<input id="assessContent" name=assessContent class="easyui-textbox" data-options="prompt:'请输入考核内容',required:true,validType:'numOrLetterOrChineseOr_'" style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">开始日期:</label></div>
					<div class="wd-com wd-5"> 
						<input id="startDate" name="startDate" class="easyui-datebox" data-options="prompt:'请输入开始日期',required:true" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">结束日期:</label></div>
					<div class="wd-com wd-5"> 
						<input id="endDate" name="endDate" class="easyui-datebox" data-options="prompt:'请输入结束日期',required:true" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">第一梯度:</label></div>
					<div class="wd-com wd-5"> 
						<input id="minAssessIndex" name="minAssessIndex" class="easyui-numberspinner" data-options="prompt:'请输入最小考核指标',precision:2,increment:1,min:0,max:500" style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">达标指标:</label></div>
					<div class="wd-com wd-5"> 
						<input id="assessIndex" name="assessIndex" class="easyui-numberspinner" data-options="prompt:'请输入考核指标',precision:2,increment:1,min:0,max:500" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">第二梯度:</label></div>
					<div class="wd-com wd-5"> 
						<input id="maxAssessIndex" name="maxAssessIndex" class="easyui-numberspinner" data-options="prompt:'请输入最大考核指标',precision:2,increment:1,min:0,max:500" style="width:90%;height:35px">
					</div>
					<div class="wd-com wd-3"><label class="label">奖励金额:</label></div>
					<div class="wd-com wd-5"> 
						<input id="reward" name="reward" class="easyui-numberspinner" data-options="prompt:'请输入奖励金额',precision:0,required:true,increment:100,min:0,max:10000" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">增减量:</label></div>
					<div class="wd-com wd-5"> 
						<input id="offset" name="offset" class="easyui-numberspinner" data-options="prompt:'请输入增减量',required:true,precision:1,increment:0.1,min:-1,max:10" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">增加金额:</label></div>
					<div class="wd-com wd-5"> 
						<input id="increasedAmount" name="increasedAmount" class="easyui-numberspinner" data-options="prompt:'请输入增加金额',required:true,precision:0,increment:100,min:0,max:10000" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">计算日期范围:</label></div>
					<div class="wd-com wd-5"> 
						<input id="performanceDateArea" name="performanceDateArea" data-options="prompt:'请输入计算开始日期',required:true" style="width:90%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">是否启动:</label></div>
					<div class="wd-com wd-5">
						<input id="isStart" name="isStart"  data-options="prompt:'请输入是否启动',required:true" style="width:90%;height:32px">
					</div>
				</div>
			</div>
			<div class="collapseField">
					<div class="fieldTitle"><span id="designFormulas" class="arrowUp" onclick="upOrDown(this)"></span>计算公式</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-24">
								<input class="easyui-textbox" id="designFormulas" name="designFormulas" data-options="prompt:'请输入计算公式',multiline:true" style="width:100%;height:70px">
							</div>
						</div>
					</div>
				</div>
			<div class="collapseField">
					<div class="fieldTitle"><span id="notes" class="arrowUp" onclick="upOrDown(this)"></span>备注</div>
					<div class="fieldContent">
						<div class="widgetGroup">
							<div class="wd-com wd-24">
								<input class="easyui-textbox" id="inputNotes" name="notes" data-options="prompt:'请输入备注',multiline:true" style="width:100%;height:70px">
							</div>
						</div>
					</div>
				</div>
		</form>	
	</div>	
	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>
