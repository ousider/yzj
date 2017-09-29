var title = '肉猪盘点';
var eventName = 'PORK_PIG_CHECK';
var editIndex = undefined;
var prUrl = '/production/';
var searchPorkCountBillListUrl = prUrl + 'searchPorkCountBillList.do';
var searchPorkPigCountListUrl = prUrl + 'searchPorkPigCountList.do';
var saveUrl=prUrl+'editPorkPigCountList.do';

var clickIndex = undefined;
/** *********页面渲染开始*************************************************************************/
$(document).ready(function(){
	clickIndex = 0;
	xnComboGrid({
		id:'checkUser',
		url:'/UserManage/searchEmployeeToList.do',
		idField:'rowId',
		textField:'employeeName',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'deptName',title:'部门名称',width:100},
			        {field:'employeeName',title:'员工名称',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	//盘点类型
	xnCdCombobox({ 
		id:'checkType',
		typeCode:'CHECK_TYPE',
		onChange:checkType
	});
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid_view({
				toolbarList:"#tableToolbar",
				url:searchPorkCountBillListUrl,
				columnFields:'billId,checkDate,houseTypeName,totalCheckQty,totalWeight,checkOrgan,accountUserName',
				columnTitles:'单据编号,盘点日期,盘点舍,盘点头数,盘点总重,核算单位,盘点人',
				hiddenFields:'',
				pagination:false,
				onDblClickRowFun:showCheckedData,
			}));
	//延时加载
	setTimeout(function () {
		xnComboGrid({
			id:'accountUser',
			url:'/UserManage/searchEmployeeToList.do',
			idField:'rowId',
			textField:'employeeName',
			width:550,
			columns:[[ 	
			           	{field:'rowId',title:'ID',width:100,hidden:true},
				        {field:'deptName',title:'部门名称',width:100},
				        {field:'employeeName',title:'员工名称',width:100},
				        {field:'notes',title:'备注',width:100}
				    ]]
		});
	},500);
});

//猪只需求单listTable初始化
function initPigListTable(billId){
	
	if(billId != '' && billId != undefined){
		url = searchPorkPigCountListUrl+'?billId='+billId;
	}else{
		var billDate = $('#billDate').datebox('getValue');
		url = searchPorkPigCountListUrl+'?billDate='+billDate;
	}
	$('#listTable').datagrid(
			j_detailGrid({
				toolbar:"#listTableToolbar",
				dbClickEdit:true,
				url:url,
				columns:[[
			      	{field:'ck',checkbox:true},
			    	{field:'rowId',title:'ID',width:80,hidden:true},
			    	j_gridText({field:'checkPigType',title:'盘点猪只类型',width:100,formatter:function(value,row){
              			return row.checkPigTypeName
          			}}),
	              	j_gridText({field:'houseId',title:'猪舍',width:100,formatter:function(value,row){
              			return row.houseName
          			}}),
	              	j_gridText({field:'swineryId',title:'批次',width:100,formatter:function(value,row){
              			return row.swineryName
          			}}),
          			j_gridText({field:'swineryAge',title:'批次日龄',width:100}),
	              	j_gridNumber({field:'checkQty',title:'盘点头数',width:100,increment:1,min:0,onChange:differQtyChangeFun}),
	              	j_gridText({field:'accountQty',title:'账上头数',width:100}),
	              	j_gridText({field:'differQty',title:'差异头数',width:100}),
	              	j_gridNumber({field:'avgWeight',title:'均重KG',width:80,precision:2,increment:1,min:0,onChange:totalWeightChangeFun}),
	              	j_gridText({field:'totalWeight',title:'总重KG',width:80}),
	              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
			    ]],
		})
	);
}
/**************************************2017-6-6 新增种猪盘点**************************************/
function checkType(){
	 var listTable = document.getElementById('listTable');
     if(listTable != null){
    	 var billDate = $('#billDate').datebox('getValue');
    	 var checkType = $('#checkType').combobox("getValue");
     	$('#listTable').datagrid('load',basicUrl+searchPorkPigCountListUrl+'?checkType='+checkType+'&billDate='+billDate);
     }
}

/**************************************2017-6-6 新增种猪盘点**************************************/

//新增肉猪盘点但均
function onBtnAddPorkCheck(){
	$('#editWin').window({
		title:title + '清算(每月结算日期为上月最后一天到本月5号)'
	});
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#btnSave').css('display','inline-block');
	$('#onBtnDelete').css('display','inline-block');
	initPigListTable();
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}
	$('#checkNumSum').html(0);
	$('#accountNumSum').html(0);
	$('#differNumSum').html(0);
}
//根据条件搜索猪至
function detailSearch(){
	var billDate = $('#billDate').datebox('getValue');
	if(billDate == ''){
		return ;
	}
	var rows = jAjax.submit(searchPorkPigCountListUrl,{billDate:billDate});
	if(rows == undefined){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}else{
		$('#listTable').datagrid('loadData',{success:true,total:0,rows: rows});	
	}
	
	$('#checkNumSum').html(0);
	$('#accountNumSum').html(0);
	$('#differNumSum').html(0);
	
}
//根据盘点头数修改差异头数
function differQtyChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	var checkQty = newValue;
	var accountQty = row.accountQty;
	row.differQty = checkQty - accountQty;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'differQty',
		value:row.differQty
	}]);
	calculate(editIndex,newValue);
	
}
//根据均种修改总重
function totalWeightChangeFun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	var avgWeight = newValue;
	var accountQty = row.accountQty;
	row.totalWeight = avgWeight * accountQty;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'totalWeight',
		value:row.totalWeight
	}]);
}
//合计
function calculate(editIndex,newValue){
	var rows = $('#listTable').datagrid('getRows');
	var checkNumSum = 0;
	var accountNumSum =0;
	var differNumSum = 0;
	if(editIndex != undefined && newValue !=0){
		checkNumSum = strToInt(newValue);
	}
	$.each(rows,function(i,row){
		if(i != editIndex){
			checkNumSum += strToInt(row.checkQty);
		}
		accountNumSum += strToInt(row.accountQty);
	});
	differNumSum = checkNumSum - accountNumSum;
	$('#checkNumSum').html(checkNumSum);
	$('#accountNumSum').html(accountNumSum);
	$('#differNumSum').html(differNumSum);
}
/**
 * 删除明细
 */
function detailDelete(){
	var record = $('#listTable').datagrid('getSelections');
	var length = record.length;
	var checkNumSum = 0;
	var accountNumSum =0;
	var differNumSum = 0;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				if(endEditing()){
					var rows = $('#listTable').datagrid('getRows');
					$.each(rows,function(i,row){
						checkNumSum += strToInt(row.checkQty);
						accountNumSum += strToInt(row.accountQty);
					});
					$.each(record,function(index,data){
						var row = $('#listTable').datagrid('getRowIndex',data);
						data.deletedFlag = "1";
						$('#listTable').datagrid('deleteRow',row);
						checkNumSum = checkNumSum - data.checkQty;
						accountNumSum = accountNumSum - data.accountQty
						
					});
					differNumSum = checkNumSum - accountNumSum;
					$('#checkNumSum').html(checkNumSum);
					$('#accountNumSum').html(accountNumSum);
					$('#differNumSum').html(differNumSum);
				}
			}
		});
	}
}
/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	var checkNumSum = strToInt($('#checkNumSum').html());
	var accountNumSum = strToInt($('#accountNumSum').html());
	var differNumSum = strToInt($('#differNumSum').html());
	var checkType = $('#checkType').combobox("getValue");
	var queryParams;
	var listTable = document.getElementById("listTable");
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			return;
		}
		if(checkNumSum != accountNumSum || differNumSum != 0){
			$.messager.alert('提示','盘点与账上存在差异不能保存！');
			return;
		}
		if(checkNumSum ==0 || accountNumSum == 0){
			$.messager.alert('提示','盘点或账上数据有误不能保存！');
			return;
		}
		queryParams.eventName = eventName;
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
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
				$.messager.alert('提示','保存成功！');
				$('#editForm').form('reset');
				var table = document.getElementById("table");
				if(table != null){
					$('#table').datagrid('reload');
				}
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
function showCheckedData(index,row){
	$('#editWin').window({
		title: title
	});
	$('#editWin').window('open');
	$('#billDate').datebox('readonly',true);
	$('#btnSave').css('display','none');
	$('#onBtnDelete').css('display','none');
	initPigListTable(row.billId);
	$('#editForm').form('load',row);
	$('#checkNumSum').html(row.totalCheckQty);
	$('#accountNumSum').html(row.totalAccountQty);
	$('#differNumSum').html(row.totalCheckQty - row.totalAccountQty);
}

/**
 * 编辑
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnEdit(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		showCheckedData(0,record[0]);
		$('#btnSave').css('display','inline-block');
	}
}


/**
 * 上传致SAP财务系统
 * }
 */
function onBtnSAPInsert(){
	var rows = $('#table').datagrid('getRows');
	clickIndex = clickIndex+1;
//	if(rows.length == 0){
//		$.messager.alert('警告','请选择一条数据！');
//		return;
//	};
	$.messager.progress();
	if(rows.length == 0){
		$.post(basicUrl + "/production/insertSAPDateBase.do",{
			billDate:null,
			billId:null,
			clickIndex:clickIndex,
			code:9997
		},
		function(response){
			response=eval('('+response+')');
			$.messager.progress('close');
			if(response.success){
				$.messager.alert('提示','上传成功！');
			}else{
				$.messager.alert({
	                title: '错误',
	                msg: response.errorMsg
	            });
			}
		}
		);
	}else{
	$.post(basicUrl + "/production/insertSAPDateBase.do",{
		billDate:rows[0].checkDate,
		billId:rows[0].billId,
		clickIndex:clickIndex,
		code:9997
	},
	function(response){
		response=eval('('+response+')');
		$.messager.progress('close');
		if(response.success){
			$.messager.alert('提示','上传成功！');
		}else{
			$.messager.alert({
                title: '错误',
                msg: response.errorMsg
            });
		}
	}
	
	);
	}


}
