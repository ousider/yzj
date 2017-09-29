var title = '角色管理';
//var model = 'cdCode';
var editIndex = undefined;
var prUrl='/basicInfo/';
var saveUrl=prUrl+'editRole.do'; //角色CU操作
var deleteUrl=prUrl+'deletesRole.do'; //角色删除
var searchMainListUrl=prUrl+'searchRoleByFarmIdPage.do'; //角色分页查询
var searchDetailListUrl=prUrl+'searchEmployeeByPage.do';

$(document).ready(function(){
	
	//角色table
	$('#table').datagrid(
			j_grid({
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,businessCode,roleName,templateName,notes',
				columnTitles:'ID,deletedFlag,角色编码,角色名称,菜单模版,备注',
				hiddenFields:'rowId,deletedFlag',
				but:function(val,row,index){
					var btn = '<a class="editcls" onclick="setMenuLimit('+index+')" href="javascript:void(0)">设置权限</a>';  
	                return btn;  
				},
				btnName:'设置权限'
//				onDblClickRowFun:onDblClickRowEmp
			})
	);
	
	
	
	
	//初始化人员列表
	initializeEmpLoyee();
	
	//人员table			
	$('#listTable').datagrid(
		j_grid_view({		
			toolbarList:'#toolbar',
			pagination:false,
			columnFields:'rowId,deletedFlag,employeeCode,employeeName,sex,birthday,marryCd,nationality,employeeType,entryDate',
			columnTitles:'ID,deletedFlag,工号,姓名,性别,出生日期,婚姻状况,民族,人员类型,入职日期',
			hiddenFields:'rowId,deletedFlag'
			})
	);		
	
	//公司窗口
//	$('#CompanyWin').window({
//		title:"公司列表",
//		collapsible:false,
//		minimizable:false,
//		maximizable:true
//	});
	
	//人员窗口
	$('#EmpLoyeeWin').window({
		title:"人员选择",
		collapsible:false,
		minimizable:false,
		maximizable:true
	});
	
	$('#MenuWin').window({
		title:"权限选择",
		collapsible:false,
		minimizable:false,
		maximizable:true
	});
	
	//初始化公司树
//	initTreeData();
	
	//公司搜索事件
	$('#farmId').searchbox({
		searcher:function(value,name){		
			$('#CompanyWin').window('open');			
		}	
	});
		
	//角色类型下拉框
	xnCombobox({
		id:"roleType",
		url:prUrl+'searchRoleType.do?typeCode=ROLE_TYPE&farmId=0',
		valueField:"codeValue",
		textField:"codeName",
		onSelect:function(row){
			//菜单模板下拉框
			xnCombobox({
				id:"templateId",
				url:prUrl+'searchMuneType.do?farmId=0&roleType='+row.codeValue,
				valueField:"rowId",
				textField:"templateName",
			});
		},
		onChange:function(newValue,oldValue){	
			xnCombobox({
				id:"templateId",
				url:prUrl+'searchMuneType.do?farmId=0&roleType='+newValue,
				valueField:"rowId",
				textField:"templateName",
			});
		}
	});
	
})

onSetMenu = {
		onMenuWin:function(str){
			var nodes = $('#'+str).tree('getRoots');
			nodes=recursiveNodes(nodes);
			nodes=JSON.stringify(nodes);
//			console.log(nodes);
			jAjax.submit(
				prUrl+'setLimit.do',
				{roleId:this.roleId,nodes:nodes},
				function(data){
					if(data.success){
						$.messager.alert('提示','保存成功！');							 
					}
				},
				null,	   
				'post' ,
				null,
				null,
				true
			);
			$('#MenuWin').window('close');	
		},
		roleId:null
}

function recursiveNodes(root){
	var nodes=new Array();
	for (var i = 0; i < root.length; i++) {
		
		if(root[i].children != null ){
			
			var children = recursiveNodes(root[i].children);
			for (var j = 0; j < children.length; j++) {
				nodes.push(children[j]);
			}
//			root[i].children=null;
//			root[i].target=null;
		}
		root[i].children=null;
		root[i].target=null;
		nodes.push(root[i]);
	}
	
	return nodes;
}


function setMenuLimit(index){
	$('#table').datagrid('selectRow',index);// 关键在这里  
    var row = $('#table').datagrid('getSelected'); 

    if (row){
    	onSetMenu.roleId=row.rowId;
    	initMenuTreeData(row);   		
    	$('#MenuWin').window('open');
    }
}

function initMenuTreeData(row){
	$('#MenuTree').tree({
		url : basicUrl + '/basicInfo/searchMenuView.do?farmId='+row.companyId+'&roleId='+row.rowId+'&templateId='+row.templateId,
		rownumbers : true,
		border : false,
		checkbox:true,
		cascadeCheck:false,
		onCheck:function(node,checked){
			if (checked) {
                var parentNode = $("#MenuTree").tree('getParent', node.target);
                if (parentNode != null) {
                    $("#MenuTree").tree('check', parentNode.target);
                }
            } else {
                var childNode = $("#MenuTree").tree('getChildren', node.target);
                if (childNode.length > 0) {
                    for (var i = 0; i < childNode.length; i++) {
                        $("#MenuTree").tree('uncheck', childNode[i].target);
                    }
                }
            }   				   			 			
		}
	})
}


function initTreeData() {
	$('#treeTable').treegrid({
		url : basicUrl + '/basicInfo/searchCompanyTree.do',
		idField : 'cid',
		treeField : 'cname',
		rownumbers : true,
		parentField : 'pid',
		border : false,
		columns : [ [ {
			field : 'cname',
			title : '名称',
			width : 200
		}, {
			field : 'type',
			title : '类别',
			width : 50
		} ] ]
	});
}

//CompanyWin确认按钮
function onCompanyId(){
	var node = $('#treeTable').treegrid('getSelected');
	$('#CompanyWin').window('close');	
	$.messager.confirm('提示', '修改后将清空人员列表，确认修改吗？', function(r){
		if (r){	
			$('#farmId').searchbox('setValue',node.cname);
			$('#companyId').val(node.cid);
			$('#listTable').datagrid('loadData',{success:true,total:0,rows:[]});
			initializeEmpLoyee();
		}
	});
}
//自定义的关闭按钮
function Myclose(winId){
	$('#'+winId).window('close');
}
//listTable增加按钮
function detailEmpAdd() {	
	  $('#EmpLoyeeWin').window('open');	
} 
//listTable删除按钮
function detailEmpDelete(){	
	var row=$('#listTable').datagrid('getChecked');
	for (var int = 0; int < row.length; int++) {
		$('#listTable').datagrid('deleteRow',$('#listTable').datagrid('getRowIndex',row[int]));
		if(row[int].rowId != null){
			 row[int].operate='D';
			 $('#EmpLoyeeTable').datagrid('appendRow',row[int]);
		}
	}
}

function initializeEmpLoyee(){
	 
	$('#EmpLoyeeTable').datagrid(
		j_grid_view({		
			url:'/UserManage/searchEmpByRole.do?roleId=0&farmId='+$('#companyId').val(),
			pagination:false,
			columnFields:'rowId,deletedFlag,employeeCode,employeeName,sex,birthday,marryCd,nationality,employeeType,entryDate',
			columnTitles:'ID,deletedFlag,工号,姓名,性别,出生日期,婚姻状况,民族,人员类型,入职日期',
			hiddenFields:'rowId,deletedFlag',
			onDblClickRowFun:function(index,row){
				row.operate='C';
				$('#listTable').datagrid('appendRow',row);
				$('#EmpLoyeeTable').datagrid('deleteRow',index);
				$('#EmpLoyeeWin').window('close');
			}
		})
	);
}
//EmpLoyeeWin确认按钮
function onEmpLoyeeId(){
	var row=$('#EmpLoyeeTable').datagrid('getChecked');
	for (var int = 0; int < row.length; int++) {
		$('#EmpLoyeeTable').datagrid('deleteRow',$('#EmpLoyeeTable').datagrid('getRowIndex',row[int]));
		if(row[int].rowId != null){
			row[int].operate='C';
			
			 $('#listTable').datagrid('appendRow',row[int]);
		}
	}
	$('#EmpLoyeeWin').window('close');
}


function onBtnAdd(){
	isNullListTable('add');
}

function onBtnEdit(){
	isNullListTable('edit');
}


function refreshlistTable(record){
	if(listTable != null){					
		var rows= jAjax.submit(prUrl+'searchEmpByRoleId.do?roleId='+record[0].rowId+'&farmId='+record[0].companyId);											
		$('#listTable').datagrid('loadData',{success:true,total:0,rows: rows});	
		var empRows= jAjax.submit('/UserManage/searchEmpByRole.do?roleId=0'+'&farmId='+record[0].companyId);
		
		for (var i = 0; i < empRows.length; i++) {
			for (var j = 0; j < rows.length; j++) {					
				if( empRows[i].rowId == rows[j].rowId){
					empRows.splice(i,1);
					i--;
					break;						
				}
			}			
		}
		
		$('#EmpLoyeeTable').datagrid('loadData',{success:true,total:0,rows:empRows});	
		
	}
}

function isNullListTable(str){	
	var  strTile='';
	if (str == 'edit') {
		strTile='编辑';
	}else if(str == 'add'){
		strTile='新增';
	}else if(str == 'copy') {
		strTile='复制新增';
	}else {
		strTile='查看';
	}
	$('#editWin').window({
		title: strTile + title
	});		
	str == 'view' ? $('#btnSave').css('display','none') : $('#btnSave').css('display','inline-block');	
	var listTable = document.getElementById('listTable');
	if(listTable != null){
		if (str != 'add') {
			var record = $('#table').datagrid('getSelections');
			var length = record.length;
			if(length < 1){
				$.messager.alert('警告','请选择一条数据！');
			}else if(length > 1){
				$.messager.alert('警告','只能选择一条数据！');
			}else{
				$('#editWin').window('open');
				$('#editForm').form('load',record[0]);	
				$('#farmId').searchbox('setValue', record[0].companyName);	
				refreshlistTable(record);
			}				
		}else{
			$('#editWin').window('open');
			$('#editForm').form('reset');
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
			$('#EmpLoyeeTable').datagrid('reload');
		}
	}	
}

function onBtnView(){
	isNullListTable('view');
}

var cRows=new Array();
function onBtnCopyAdd(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#editWin').window({
			title: '复制新增' + title
		});
		$('#editWin').window('open');
		var rowId=record[0].rowId;
		record[0].rowId=0;
		$('#editForm').form('load',record[0]);	
		$('#farmId').searchbox('setValue', record[0].companyName);	
		$('#btnSave').linkbutton('enable')
		var listTable = document.getElementById('listTable');		
		if(listTable != null){
			
			var rows= jAjax.submit(prUrl+'searchEmpByRoleId.do?roleId='+rowId+'&farmId='+record[0].companyId);
			
			$('#listTable').datagrid('loadData',{success:true,total:0,rows: rows});	
								
			var empRows= jAjax.submit('/UserManage/searchEmpByRole.do?roleId=0'+'&farmId='+record[0].companyId);
			
			for (var i = 0; i < empRows.length; i++) {
				for (var j = 0; j < rows.length; j++) {					
					if( empRows[i].rowId == rows[j].rowId){
						empRows[i].operate='C';
						cRows.push(empRows[i]);
						empRows.splice(i,1);
						i--;
						break;						
					}
				}			
			}	
			$('#EmpLoyeeTable').datagrid('loadData',{success:true,total:0,rows:empRows});
			
			
		}
	}
}

function onBtnSave(){
	
	var queryParams = null;
	var listTable = document.getElementById("EmpLoyeeTable");
	//判断是否有明细表
	if(listTable!= null){
		//获取新增行	
		var insertRows = $('#EmpLoyeeTable').datagrid('getChanges','inserted');
		//获取删除行
		var deleteRows = $('#EmpLoyeeTable').datagrid('getChanges','deleted');

		//合并行
		var editRows = insertRows.concat(deleteRows);
		if(cRows != null){
			for (var i = 0; i < editRows.length; i++) {
				for (var j = 0; j < cRows.length; j++) {
					if(editRows[i].rowId == cRows[j].rowId ){
						cRows.splice(j,1);
						j--;
						break;
					}
				}
			}
			for (var i = 0; i < cRows.length; i++) {
				editRows.push(cRows[i]);
			}
		}
		
		cRows= null;
		var jsonString = JSON.stringify(editRows);
		queryParams = {
				status:1,
				deletedFlag:0,
				gridList:jsonString
		};
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}
	//判断是否是事件保存
	if(typeof(eventName) != "undefined"){
		queryParams.eventName = eventName;
	}
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();	
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		onSubmit: function(){
			var isValid = $('#editForm').form('validate');
			if (!isValid){
				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
				$.messager.progress('close');	
			}
			return isValid;
		},
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				$('#editWin').window('close');
				var table = document.getElementById("table");
				if(table != null){
					$('#table').datagrid('reload');
				}
				//重置
				$('#editForm').form('reset');
				if(listTable!= null){
					$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
				}
				$.messager.alert('提示','保存成功！');
			}else{
				 $.messager.alert({
                     title: '错误',
                     msg: response.errorMsg
                 });
			}
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			$.messager.progress('close');
		}
	});
}


