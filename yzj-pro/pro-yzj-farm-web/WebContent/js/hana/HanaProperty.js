var title = 'HANA配置管理';
var editIndex = undefined;
var prUrl='/hanaProperty/';
var saveUrl='';
var deleteUrl=prUrl+'deleteHanas.do';
var searchMainListUrl=prUrl+'searchHanaToPage.do';
var searchDetailListUrl=prUrl+'searchDbAndFarmToList.do';
/** *********页面渲染开始*************************************************************************/
//查询按钮
$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:'#hanaPropertyTableToolbar',
				url:searchMainListUrl,
				columnFields:'rowId,beanName,ipAndPort,dbName,dbUserName',
				columnTitles:'ID,数据源名称,数据源地址及端口号,DB名,数据库账号',
				hiddenFields:'rowId',
				onDblClickRowFun:showDbAndFarm
			})
	);
	
	$('#dbAndFarmTable').datagrid(
			j_grid_view({
				haveCheckBox:false,
//				url:searchDetailListUrl,
				columnFields:'farmName',
				columnTitles:'猪场',
				hiddenFields:'',
				fit:false,
				pagination:false,
				height:'100%',
				width:'100%'
			})
		);
	
	// DB数据
	xnCombobox({
		id:'beanNameSelect',
		valueField:'rowId',
		textField:'beanName',
		required:true
	});
	
	// DB猪场
	xnCombobox({
		id:'farmIds',
		valueField:'farmId',
		textField:'farmName',
		url:'/basicInfo/searchGroupCompanyByFarmIdToList.do',
		editable:false,
		multiple:true,
		required:true
	});
	
});



/*************页面渲染结束******************************************************************************/

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
		$('#editWin').window({
			title:'编辑'+ title
		});
		$('#editWin').window('open');
		$('#editForm').form('load',record[0]);
		saveUrl = prUrl+'editHana.do';
		dbInfoShow();
		dbAndFarmInfoHide();
		$('#btnSave').css('display','inline-block');
		$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	}
}
/**
 * 新增
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnAdd(){
	$('#editWin').window({
		title:'新增'+ title
	});
	$('#editForm').form('reset');
	$('#rowId').val(0);
	$('#editWin').window('open');
	saveUrl = prUrl+'editHana.do';
	dbInfoShow();
	dbAndFarmInfoHide();
	$('#btnSave').css('display','inline-block');
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
}

function dbInfoShow(){
	$('#dbInfo').css('display','block');
	$('#beanName').textbox('enable');
	$('#ipAndPort').textbox('enable');
	$('#dbName').textbox('enable');
	$('#dbUserName').textbox('enable');
	$('#dbPassword').textbox('enable');
	$('#testButton').css('display','inline-block');
}

function dbInfoHide(){
	$('#dbInfo').css('display','none');
	$('#beanName').textbox('disable');
	$('#ipAndPort').textbox('disable');
	$('#dbName').textbox('disable');
	$('#dbUserName').textbox('disable');
	$('#dbPassword').textbox('disable');
	$('#testButton').css('display','none');
}

function dbAndFarmInfoShow(){
	$('#dbAndFarmInfo').css('display','block');
	$('#beanNameSelect').combobox('enable');
	var beanNameData = jAjax.submit(prUrl + 'searchHanaToList.do')
	$('#beanNameSelect').combobox('loadData',beanNameData);
	$('#farmIds').combobox('enable');
}

function dbAndFarmInfoHide(){
	$('#dbAndFarmInfo').css('display','none');
	$('#beanNameSelect').combobox('disable');
	$('#farmIds').combobox('disable');
}

/**
 * 新增数据源和猪场关系
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnAddDbAndFarm(){
	$('#editWin').window({
		title:'新增'+ title
	});
	$('#editForm').form('reset');
	$('#rowId').val(0);
	$('#editWin').window('open');
	saveUrl = prUrl+'editDbAndFarm.do';
	dbInfoHide();
	dbAndFarmInfoShow();
	$('#btnSave').css('display','inline-block');
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
}

/**
 * 查看
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onBtnView(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}else{
		$('#editWin').window({
			title:'查看'+ title
		});
		$('#editWin').window('open');
		$('#editForm').form('load',record[0]);
		saveUrl = prUrl+'editHana.do';
		dbInfoShow();
		dbAndFarmInfoHide();
		$('#btnSave').css('display','none');
		$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	}
}

/**
 * 双击查看方法
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function showDbAndFarm(index,row){
	$('#dbAndFarmTable').datagrid('load', basicUrl + searchDetailListUrl + '?dbBeanId='+row.rowId);
	leftSilpFun('dbAndFarm');
}

/**
 * 删除
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnDelete(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				$.messager.progress();	
				var ids = [];
				$.each(record,function(index,data){
					if(data.ROW_ID!=null){
						ids.push(data.ROW_ID);
					}else{
						ids.push(data.rowId);
					}
					
				});
				$.ajax({
				    type: 'POST',
				    url: basicUrl+deleteUrl ,
				    data: {
				    	'ids':ids
				    } ,
				    success: function(response){
				    	$.messager.progress('close');
				    	if(response.success){
				    		$.messager.alert('警告','删除成功！');
				    		$('#table').datagrid('reload');
				    	}else{
				    		$.messager.alert({
			                     title: '错误',
			                     msg: response.errorMsg
			                 });
				    	}
				    	
				    },
				    dataType:'JSON'
				});
			}
		});
		
	}
}

function testDbConnect(){
	$('#testButton').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();	
	$('#editForm').form('submit',{
		url:basicUrl+prUrl+"testDbConnect.do",
		onSubmit: function(){
			var isValid = $('#editForm').form('validate');
			if (!isValid){
				$.messager.progress('close');	
				$('#testButton').attr('disabled',false).removeClass('btn-disabled');
			}
			return isValid;
		},
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				$.messager.alert('提示','连接测试通过！');
				$('#testButton').attr('disabled',false).removeClass('btn-disabled');
				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			}else{
				 $.messager.alert({
                     title: '错误',
                     msg: response.errorMsg
                 });
			}
			$.messager.progress('close');
			$('#testButton').attr('disabled',false).removeClass('btn-disabled');
		}
	});
}

function textChange(){
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
}

/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	var queryParams;
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	var listTable = document.getElementById("listTable");
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
		if(queryParams == null){
			$.messager.alert('提示','前台提示--未添加明细不能提交！');
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			return;
		}
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}
	//判断是否是事件保存
	if(typeof(eventName) != "undefined"){
		queryParams.eventName = eventName;
		var saveValidFalg = true;
		var errorEarBrand = null;
		$.each(eval(queryParams.gridList),function(i,item){
			if(item.status == -1){
				saveValidFalg = false;
				errorEarBrand = item.earBrand;
				return false;
			}
		});
		if(!saveValidFalg){
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			$.messager.alert('提示','前台提示--耳牌号：【'+errorEarBrand+'】没有对应的猪只数据，请重新选择！');
			return;
		}
	}
	$.messager.progress();	
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		onSubmit: function(){
			var isValid = $('#editForm').form('validate');
			if (!isValid){
				$.messager.progress('close');	
				$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
			}
			return isValid;
		},
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				if(typeof(eventName) == "undefined"){
					$('#editWin').window('close');
					$.messager.alert('提示','保存成功！');
				}else{
					//重置
					var preRecord = $('#listTable').datagrid('getData');
					leftSilpFun('preSaveRecord',true,9002);
					if(preRecord.success == undefined){
						preRecord.success = true;
					}
					$('#preSaveRecordTable').datagrid('loadData',preRecord);
					if(listTable!= null){
						$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
					}
				}
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
			$.messager.progress('close');
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		}
	});
}