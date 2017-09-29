<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/OrganizationalStructure.js?v=${param.randomnumber}"></script>
</head>
<body>
		<div class="easyui-layout" data-options="fit:true,border:false" >
			<div id="p" data-options="region:'west',split:true"  style="width:21%;min-width:270px">
				<div id="treeTable"></div>
			</div>
			<div data-options="region:'center'" >
				<div id="editCompany" class="easyui-window windowStyle" data-options="inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dlg-buttons'" style="overflow-x:hidden;">
				<!-- <div id="editCompany" class="easyui-panel"  data-options="inline:true,fit:true,border:false,left:'0px',top:'0px',closed:true,footer:'#dlg-buttons'"> -->
					<form id="editFormCompany" method="post">
						<div style="display:none">
							<input class="easyui-textbox" name="rowId" id="companyRowId"/>
						</div>
						 <div class="collapseField">
							<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基础信息</div>
							<div class="fieldContent">
								<div class="widgetGroup">
									<div class="wd-com wd-4"><label class="label">公司编号:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="companyCode" data-options="prompt:'系统自动分配编码',disabled:true"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">公司全称:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="companyName"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">公司简称:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="sortName"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">英文名称:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="companyNameEn"  style="width:100%;height:35px">
									</div>
										<div class="wd-com wd-4"><label class="label">上级公司:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-searchbox" id="cname" name="cname"  style="width:100%;height:35px">
										<input type="hidden" id="supCompanyId" name="supCompanyId" >
										<input type="hidden" id="oldCompanyMark" name="oldCompanyMark" >
										<input type="hidden" id="companyMark" name="companyMark" >
									</div>
									<div class="wd-com wd-4"><label class="label">是否集团:</label></div>
									<div class="wd-com wd-7">
										<div id="isBloc" ></div>
									</div>
									<div class="wd-com wd-4"><label class="label">公司类别:</label></div>
									<div class="wd-com wd-7">
										<input id="companyClass" name="companyClass"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">公司类型:</label></div>
									<div class="wd-com wd-7">
										<input id="companyType" name="companyType"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">核算类型:</label></div>
									<div class="wd-com wd-7">
										<input id="accountCompanyClass" name="accountCompanyClass"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label"></label></div>
									<div class="wd-com wd-7">
									</div>
									<div class="wd-com wd-4"><label class="label">是否开启:</label></div>
									<div class="wd-com wd-7">
										<input id="openOrClose" name="openOrClose"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">关闭日期:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-datebox" name="closeDate"  style="width:100%;height:35px">
									</div>																											
								</div>
							</div>
						</div>
						<div class="collapseField">
							<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>注册信息</div>
							<div class="fieldContent">
								<div class="widgetGroup">
									<div class="wd-com wd-4"><label class="label">省:</label></div>
									<div class="wd-com wd-4">
										<input class="easyui-combobox" id="province" name="province" style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-3"><label class="label">市:</label></div>
									<div class="wd-com wd-4">
										<input class="easyui-combobox" id="city" name="city" style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-3"><label class="label">县:</label></div>
									<div class="wd-com wd-4">
										<input class="easyui-combobox" id="county" name="county" style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">公司地址:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="companyAddress"  required="true"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">身份证号码:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="idCard"  style="width:100%;height:35px">
									</div>
									<!-- <div class="wd-com wd-4"><label class="label">规模:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="companyScale"  style="width:100%;height:35px">
									</div> -->
									<div class="wd-com wd-4"><label class="label">了解途径:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="traceSource"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">介绍人:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="tracePeople"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">介绍人手机号码:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="tpCell"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">0~180°经度:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="longitude" required="true"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">0~90°纬度:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="latitude" required="true"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">注册日期:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-datebox" id='registerTime' name="registerTime"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">注册ip:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="registerIp"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">开通日期:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-datebox" id='openTime' name="openTime"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"><label class="label">面积:</label></div>
									<div class="wd-com wd-7">
										<input class="easyui-textbox" name="area"  style="width:100%;height:35px">
									</div>
									<div class="wd-com wd-4"></div>
									<div class="wd-com wd-5"><input id="isCreate" name="isCreate" type="checkBox" onchange="isCreateWeek(this)">是否重建周次</div>
									
								</div>
							</div>
						</div>
					<div id="dlg-buttons" style="height:35px;text-align:right;padding:5px;">
						<button id="btnCompanySave" type="button" onclick="onBtnSave('company')" class="winFooterBtn blue">保存</button>
						<button type="button" onclick="onBtnCancel('company')" class="winFooterBtn green">取消</button>
					</div>
				</form>
			</div>
			<div id="editDept" class="easyui-window windowStyle" data-options="inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#dept-buttons'" style="overflow-x:hidden;">
			<!-- <div id="editDept" class="easyui-panel"  data-options="inline:true,fit:true,border:false,left:'0px',top:'0px',closed:true,footer:'#dept-buttons'"> -->
				<form id="editFormDept" method="post">
					<div style="display:none">
						<input class="easyui-textbox" name="rowId" id="deptRowId"/>
					</div>
					<div class="collapseField">
						<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基本信息</div>
						<div class="fieldContent">
							<div class="widgetGroup">
								<div class="wd-com wd-4"><label class="label">部门编号:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" id='businessCode' name="businessCode" data-options="prompt:'系统自动分配编码',disabled:true"  style="width:100%;height:35px">
								</div>
								<div class="wd-com wd-4"><label class="label">部门名称:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" name="deptName"  style="width:100%;height:35px">
								</div>
								<div class="wd-com wd-4"><label class="label">公司:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" id='farmName' name="farmName" readonly="readonly"  style="width:100%;height:35px">
		                    		<input type="hidden" name="farmId" >
								</div>
								<div class="wd-com wd-4"><label class="label">上级部门:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-searchbox" id="supDeptName"  name="supDeptName" style="width:100%;height:35px">
			                   	    <input type="hidden" id="supDeptId" name="supDeptId" >
								</div>
								<div class="wd-com wd-4"><label class="label">部门详情:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" id='deptDesc' name="deptDesc"  style="width:100%;height:35px">
								</div>
								<div class="wd-com wd-4"><label class="label">猪舍名称:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-searchbox" id='houseNames' name="houseNames" style="width:100%;height:35px">
									<input type="hidden" id="houseIds" name="houseIds" />
								</div>
							</div>
						</div>
					</div>
					<div id="dept-buttons" style="height:35px;text-align:right;padding:5px;">
						<button id="btnDeptSave" type="button" onclick="onBtnSave('dept')" class="winFooterBtn blue">保存</button>
						<button type="button" onclick="onBtnCancel('dept')" class="winFooterBtn green">取消</button>
					</div>
				</form>
			</div>
			
			<div id="postPanel" class="easyui-panel"  data-options="inline:true,fit:true,border:false,left:'0px',top:'0px',closed:true">
				<table id="postList"></table>
			</div>
			<div id="editPost" class="easyui-window windowStyle" data-options="inline:true,left:'0px',top:'0px',collapsible:false,minimizable:false,maximizable:false,closable:false,closed:true,draggable:false,fit:true,footer:'#post-buttons'" style="overflow-x:hidden;">
			<!-- <div id="editPost" class="easyui-panel"  data-options="inline:true,fit:true,border:false,left:'0px',top:'0px',closed:true,footer:'#post-buttons'"> -->
				<form id="editFormPost" method="post">
					<div style="display:none">
						<input class="easyui-textbox" name="rowId" id="postRowId"/>
					</div>
					<div class="collapseField">
						<div class="fieldTitle"><span class="arrowUp" onclick="upOrDown(this)"></span>基本信息</div>
						<div class="fieldContent">
							<div class="widgetGroup">
								<div class="wd-com wd-4"><label class="label">岗位:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" name="duty" data-options="required:true" style="width:100%;height:35px">
								</div>
								<div class="wd-com wd-4"><label class="label">职称:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" name="jobTitle"  style="width:100%;height:35px">
								</div>
								<div class="wd-com wd-4"><label class="label">职级:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" name="dutyLvl"  style="width:100%;height:35px">
								</div>
								<div class="wd-com wd-4"><label class="label">部门:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" name="deptName" readonly="readonly"  style="width:100%;height:35px">
		                    		<input id = "deptId" type="hidden" name="deptId">
								</div>
								<div class="wd-com wd-4"><label class="label">任职资格:</label></div>
								<div class="wd-com wd-7">
									<input class="easyui-textbox" name="qualification"  style="width:100%;height:35px">
								</div>
							</div>
						</div>
					</div>
					 <div id="post-buttons" style="height:35px;text-align:right;padding:5px;">
						<button id="btnPostSave" type="button" onclick="onBtnSave('post')" class="winFooterBtn blue">保存</button>
						<button type="button" onclick="onBtnCancel('post')" class="winFooterBtn green">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
		
	<div id="meuCompany" class="easyui-menu" style="width: 120px;">
		<div onclick="editCompany()" data-options="iconCls:'icon-edit'">编辑[公司]</div>
		<div onclick="addChildCompany()" data-options="iconCls:'icon-add'">增加[子公司]</div>
		<div onclick="addDept()" data-options="iconCls:'icon-add'">增加[部门]</div>
		<div onclick="deleteCompany()" data-options="iconCls:'icon-remove'">删除[公司]</div>
	</div>
	
	<div id="meuDept" class="easyui-menu" style="width: 120px;">
		<div onclick="postManage()" data-options="iconCls:'icon-edit'">岗位管理</div>
		<div onclick="editDept()" data-options="iconCls:'icon-edit'">编辑[部门]</div>
		<div onclick="addDeptChild()" data-options="iconCls:'icon-add'">增加[子部门]</div>
		<div onclick="deleteDept()" data-options="iconCls:'icon-remove'">删除[部门]</div>
	</div>
	
	<div id="posthd" style="height:auto">
		<button type="button" onclick="appendPost()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="editPost()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnViewPost()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="deletePost()" class="tableToolbarBtn btn-middle">删除</button>
		<button type="button" onclick="copAddPost()"class="tableToolbarBtn btn-middle">复制新增</button>
	</div>
	
	<div id="treeTableToolbar" style="text-align:center;">
		<button type="button" onclick="collapseAll()" class="tableToolbarBtn btn-middle" style="width:70px;">折叠</button>
		<button type="button" onclick="expandAll()" class="tableToolbarBtn btn-middle" style="width:70px;">展开</button>
		<button type="button" onclick="refresh()" class="tableToolbarBtn btn-middle" style="width:70px;">刷新</button>
	</div>
	<div id="supSearch" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('supSearch',390)"></span><span>上级公司/部门</span>
		</div>
		<div class="rightSlipContent heightFiexd" style="padding:0">
			<div id="supTreeTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onSupSure()" class="rightSlipBtn blue">确认</button>
			<button type="button" onclick="closeSup()" class="rightSlipBtn green">关闭</button>
		</div>
	</div>
	<div id="houseSearch" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('houseSearch',390)"></span><span>猪舍</span>
		</div>
		<div class="rightSlipContent heightFiexd" style="padding:0">
			<div id="houseTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onHouseSure()" class="rightSlipBtn blue">确认</button>
			<button type="button" onclick="closeHouse()" class="rightSlipBtn green">关闭</button>
		</div>
	</div>
	</body>
</html>