<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="/jsp/Head.jsp" />
	<script src="${base_url}/js/basicinfo/UserManage.js?v=${param.randomnumber}"></script>
</head>
<body>
	<div id="tableToolbarList">
		<button type="button" onclick="onBtnAdd()" class="tableToolbarBtn btn-middle">新增</button>
		<button type="button" onclick="onBtnEdit()" class="tableToolbarBtn btn-middle">编辑</button>
		<button type="button" onclick="onBtnView()" class="tableToolbarBtn btn-middle">查看</button>
		<button type="button" onclick="onBtnDelete()" class="tableToolbarBtn btn-middle">删除</button>
		<button type="button" onclick="onBtnCopyAdd()"class="tableToolbarBtn btn-middle">复制新增</button>
		<button type="button" onclick="onBtnDisable()" class="tableToolbarBtn btn-middle">停用</button>
		<button type="button" onclick="onBtnEnable()"class="tableToolbarBtn btn-middle">启用</button>		
	</div>
	<div id="advancedSearch" onClick="leftSilpFun('advancedSearchId')"><span>高级查询</span></div>
	<div id="advancedSearchId" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('advancedSearchId',390)"></span><span>高级查询</span>
		</div>
		<div class="rightSlipContent">
			 <form id="searchForm" method="post" class="form-inline">
				<div class="wd-com wd-8"><label class="label">工号:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="employeeCode" data-options="prompt:'请输入工号'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">姓名:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-textbox" name="employeeName" data-options="prompt:'请输入姓名'" style="width:100%;height:35px">
				</div>
				<div class="wd-com wd-8"><label class="label">人员类型:</label></div>
				<div class="wd-com wd-15">
					<input class="easyui-combobox" id='employeeTypeForSearch' name="employeeType" style="width:100%;height:32px">
				</div>
			 </form>
		 </div>
		 <div class="rightSlipFooter">
			<button type="button" onclick="onBtnSearch(this,searchMainListUrl)" class="rightSlipBtn blue">搜索</button>
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
				<div class="fieldTitle"><span id="employeeInfo" class="arrowUp" onclick="upOrDown(this)"></span>人员信息</div>
				<div class="fieldContent">
					<div class="wd-com wd-3"><label class="label">工号:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="employeeCode" data-options="prompt:'系统自动生成',disabled:true" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">姓名:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="employeeName"  data-options="prompt:'请输入姓名',required:true,validType:'chinese'"  style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">英文名:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox"  name="employeeEnNm" data-options="validType:'english'" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">部门:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-searchbox" id="deptName" name="deptName" data-options="prompt:'请选择部门',required:true"  style="width:100%;height:32px">
						<input type="hidden" id="deptId" name="deptId"  />
					</div>
					<div class="wd-com wd-3"><label class="label">岗位:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-combobox" id="postId" name="postId" data-options="required:true" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">性别:</label></div>
					<div class="wd-com wd-5"> 
						<div id="sex"></div>
					</div>
					<div class="wd-com wd-3"><label class="label">身份证件类型:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-combobox" id="cardType" name="cardType"  style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">身份证件号码:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="idcard" data-options="validType:'numOrLetterOr_'" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">出生日期:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-datebox"  name="birthday" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">婚姻状况:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-combobox" id="marryCd" name="marryCd"  style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">政治面貌:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="pcode" data-options="validType:'chinese'" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">民族:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox"  name="nationality" data-options="validType:'chinese'" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">学历:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="edubg" data-options="validType:'chinese'" style="width:100%;height:32px">
						</div>
						<!-- <div class="wd-com wd-3"><label class="label">全日制最高学历:</label></div>
						<div class="wd-com wd-5"> 
							<input class="easyui-textbox" name="degree"  style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">非全日制最高学历:</label></div>
						<div class="wd-com wd-5"> 
							<input class="easyui-textbox"  name="pedubg" style="width:100%;height:32px">
						</div>
						<div class="wd-com wd-3"><label class="label">全日制最高学位:</label></div>
						<div class="wd-com wd-5"> 
							<input class="easyui-textbox" name="pdegree"  style="width:100%;height:32px">
						</div> -->
					<div class="wd-com wd-3"><label class="label">人员类型:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-combobox" id='employeeType' name="employeeType"  style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">入职日期:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-datebox"  name="entryDate" style="width:100%;height:32px">
					</div>
						<!-- <div class="wd-com wd-3"><label class="label">工作日期:</label></div>
						<div class="wd-com wd-5"> 
							<input class="easyui-datebox" name="workDate"  style="width:100%;height:32px">
						</div> -->
						<!-- <div class="wd-com wd-3"><label class="label">主要联系方式:</label></div>
						<div class="wd-com wd-5"> 
							<input class="easyui-textbox" name="priCntct"  style="width:100%;height:32px">
						</div> -->
						<!-- <div class="wd-com wd-3"><label class="label">传真号:</label></div>
						<div class="wd-com wd-5"> 
							<input class="easyui-textbox"  name="fax" style="width:100%;height:32px">
						</div> -->
					<div class="wd-com wd-3"><label class="label">固定电话:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="tel" data-options="validType:'numOrLetterOr_'" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">移动电话:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="mobile" data-options="validType:'integer_'" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">邮编:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox"  name="postcode" data-options="validType:'integer_'" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">邮箱:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="email" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">QQ:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox" name="qq" data-options="validType:'integer_'" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">微信:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox"  name="wechat" style="width:100%;height:32px">
					</div>
					<div class="wd-com wd-3"><label class="label">企业微信用户ID:</label></div>
					<div class="wd-com wd-5"> 
						<input class="easyui-textbox"  name="qyUserId" style="width:100%;height:32px">
					</div>
					<div id="userManage">
						<div class="wd-com wd-3"><label class="label">是否添加成用户:</label></div>
						<div class="wd-com wd-5">
							<input type="checkbox" onclick="upOrDownForUserInfo(this);" name="userInfoCheckbox" id="userInfoCheckbox" class="checkboxStyle"><label for="userInfoCheckbox"></label>
						</div>
						<div id="userInfo">
							<div class="wd-com wd-3"><label class="label">账号:</label></div>
							<div class="wd-com wd-5"> 
								<input class="easyui-textbox" id="userName" name="userName" data-options="prompt:'请输入账号',required:true,validType:'userExists'" style="width:100%;height:32px" />
							</div>
							<div class="wd-com wd-3"><label class="label">角色:</label></div>
							<div class="wd-com wd-5"> 
								<input id="roleId" name="roleId" style="width:100%;height:32px" />
							</div>
							<div class="wd-com wd-3"><label class="label">主页:</label></div>
							<div class="wd-com wd-5"> 
								<input class="easyui-combobox" id="moduleId" name="moduleId" data-options="valueField: 'id',textField: 'text',panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px" />
							</div>
							<div class="wd-com wd-3"><label class="label">微信主页:</label></div>
							<div class="wd-com wd-5"> 
								<input class="easyui-combobox" id="wechatModuleId" name="wechatModuleId" data-options="valueField: 'id',textField: 'text',panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px" />
							</div>
							<div class="wd-com wd-3"><label class="label">OA账号:</label></div>
							<div class="wd-com wd-5"> 
								<input class="easyui-textbox" id="oaUsername" name="oaUsername" data-options="prompt:'请输入OA账号'" style="width:100%;height:32px" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<div id="deptSearch" class="rightSlipWin_390 z-index_10000">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('deptSearch',390)"></span><span>部门</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<div id="treeTable"></div>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="onDeptId()" class="rightSlipBtn blue">确认</button>
			<button type="button" onclick="closeDept()" class="rightSlipBtn green">关闭</button>
		</div>
	</div>
	<div id="updatePassWord" class="rightSlipWin_390">
		<div class="rightSlipTitle">
			<span class="arrow_right" onClick="rightSlipFun('updatePassWord',390)"></span><span>用户管理</span>
		</div>
		<div class="rightSlipContent heightFiexd">
			<form id="updatePasForm" method="post">
				<div>
					<div style="display:none">
						<input type="hidden" id="userRowId" name="rowId" >
						<input type="hidden" id="employeeIdUser" name="employeeIdUser" >
						<input type="hidden" id="employeeName" name="employeeName" >
					</div>
					<div class="wd-com wd-8"><label class="label">账号:</label></div>
					<div class="wd-com wd-15" id="userNameLabelDiv" > 
						<span id="userNameLabel"></span>
					</div>
					<div class="wd-com wd-15" id="userNameTextDiv" > 
						<input class="easyui-textbox" id=userNameUser name="userName" data-options="prompt:'当前还不是用户，请输入账号',required:true,validType:'userExists'" style="width:100%;height:32px" />
					</div>
					<div class="wd-com wd-8"><label class="label">角色:</label></div>
					<div class="wd-com wd-15"> 
						<input id="roleIdUser" name="roleId" style="width:100%;height:32px" />
					</div>
					<div class="wd-com wd-8"><label class="label">主页:</label></div>
					<div class="wd-com wd-15"> 
						<input class="easyui-combobox" id="moduleIdUser" name="moduleId" data-options="valueField: 'id',textField: 'text',panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px" />
					</div>
					<div class="wd-com wd-8"><label class="label">微信主页:</label></div>
					<div class="wd-com wd-15"> 
						<input class="easyui-combobox" id="wechatModuleIdUser" name="wechatModuleId" data-options="valueField: 'id',textField: 'text',panelHeight:'auto',editable:false,required:true" style="width:100%;height:32px" />
					</div>
				</div>
			</form>
		 </div>
		<div class="rightSlipFooter">
			<button type="button" onclick="editUser('updatePasForm')" class="rightSlipBtn blue">确认</button>
			<button type="button" onclick="resetPassword('updatePasForm')" class="rightSlipBtn green">重置密码</button>
		</div>
	</div>

	<jsp:include page="/jsp/WinFooter.jsp" />
</body>
</html>