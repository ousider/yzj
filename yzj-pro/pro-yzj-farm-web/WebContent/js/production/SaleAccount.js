var title = '销售结算';
var editIndex = undefined;
//var listTableTZEditIndex = undefined;
var prUrl='/account/';
var searchMainListUrl=prUrl+'searchSaleAccountToPage.do';
var searchDetailListUrl=prUrl+'searchSaleAccountDetailToList.do';
var saveUrl=prUrl+'editSaleAccount.do';

$(document).ready(function(){
	$('#upFiexDiv').height($('#editWin').height() - 83.2);
	$('#saleAccountDate').datebox({
		value:getCurrentDate()
	});
	//客户
	xnComboGrid({
		id:'customerId',
		idField:'rowId',
		textField:'companyName',
		width:550,
		editable:true,
		url:'/account/searchCustomerSaleAccountToList.do',
		onChange:customerChangefun,
		required:true,
		columns:[[ 	
                	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'companyCode',title:'客户编码',width:100},
			        {field:'companyName',title:'客户名称',width:100},
			    ]]
	});
	
	
	// 选择销售单据
	xnComboGrid({
		id:'saleBillId',
		idField:'saleBillId',
		textField:'billCode',
		url:'/account/selectSaleBillByCustomer?saleStatus=1',
		width:500,
		editable:true,
		required:true,
		onSelect:billOnSelectfun,
//		onChange:billOnChangefun,
		columns:[[
		          	{field:'saleBillId',title:'ID',width:100,hidden:true},
		          	{field:'billCode',title:'单据编号',width:60},
		          	{field:'saleDate',title:'单据日期',width:60},
		          	{field:'totalNum',title:'销售猪只数量',width:60},
		          	{field:'saleStatusName',title:'销售单状态',width:60}
		          ]]
	});
	
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
				toolbar:'#tableToolbarList',
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,accountBillCode,customerName,saleAccountDate,saleBillId,billCode,businessCode,saleDate,pigNum,pigWeight,nominalPrice,carcassTotalWeight,fateRate,totalItemPrice,totalAccountPrice,totalAccount,totalNum,totalWeight,farmName',
				columnTitles:'ID,deletedFlag,结算单编号,客户,结算日期,销售单Id,销售单号,码单号,销售日期,毛猪头数,毛猪重,挂牌价,胴体总重,原膘比例,扣款合计,结算总金额,结算价,总头数(销售),总重(销售),猪场',
				hiddenFields:'rowId,deletedFlag,saleBillId,saleBillTotalPrice,pigNum,pigWeight,nominalPrice,carcassTotalWeight,fateRate,totalItemPrice,totalAccount,totalNum,totalWeight,farmName'
			})
	);
	setTimeout(function () {
		$('#listTable').datagrid(
				j_detailGrid({
					height:'30%',
					columns:[[
				              	j_gridText({field:'itemId',title:'ID',hidden:true}),
				              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
				              	j_gridText({field:'itemType',title:'项目类别Id',hidden:true}),
				              	j_gridText({field:'itemTypeName',title:'项目类别'}),
				              	j_gridText({field:'itemStageName',title:'项目阶段'}),
				              	j_gridText({field:'itemName',title:'项目名称'}),
				              	j_gridText({field:'rewardPunishStandard',title:'奖惩标准',precision:2}),
				              	j_gridNumber({field:'pigNum',title:'数量',width:80,min:0,onChange:pigNumChangefun}),
				              	j_gridNumber({field:'itemValue',title:'奖惩金额',precision:2,width:80,onChange:itemValueChangefun}),

						    ]],
				onBeginEdit:function(index,row){
					if(3 == row.itemType){
						var pigNum = $('#listTable').datagrid('getEditor', {
					        index: index,
					        field: 'pigNum'
					    });
						if(pigNum != null){
							$(pigNum.target).numberspinner('disable');
						}
					}
				}
				})
		)
	});

})

// 屠宰质量
function accountListTableInit(){
	var customerName = $('#customerId').combobox('getText');
	if(customerName == '上海五丰上食食品有限公司'){
		if($("#listTableTZ").length<=0){
			$('#listTableTZField').css('display','block');
			$('#listTableTZField').after('<div id="listTableTZ"></div>');
		}
		$('#listTableTZ').edatagrid({
			rownumbers:true,
			height:200,
			fitColumns:true,
			singleSelect:true,
			rowStyler: function(index,row){
				if ((index+1) % 2 == 0){
					return 'background-color:#f7f7f7;';
				}
			},
			columns:[[
					j_gridText({field:'itemId',title:'ID',hidden:true}),
					j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
					j_gridText({field:'itemType',title:'项目类别Id',hidden:true}),
					j_gridText({field:'itemTypeName',title:'项目类别'}),
					j_gridText({field:'itemName',title:'项目名称'}),
					j_gridNumber({field:'pigNum',title:'头数',width:80,min:0,onChange:pigNumWFChangefun}),
					j_gridNumber({field:'itemValue',title:'比例',precision:2,
						formatter:function(value,row){
              			value = value == null || value == undefined || value == '' ? 0.00 : value;
              			row.itemValue = value;
	              		return value+'%';
	              	},width:80}),
			    ]],
            onLoadSuccess:function(data){
                	if(!data.success && data.success != undefined){
        				$.messager.alert({
        					title: '错误',
        					msg: data.errorMsg
        				});
        	    	}
            },
            onBeginEdit:function(index,row){
				if(3 == row.itemType){
					var pigNum = $('#listTable').datagrid('getEditor', {
				        index: index,
				        field: 'pigNum'
				    });
					if(pigNum != null){
						$(pigNum.target).numberspinner('disable');
					}
				}
			}
		});
		$('#accountBasisField').css('display','none');

	}else if(customerName == '中粮肉食（江苏）有限公司'){
		if($("#listTableTZ").length<=0){
			$('#listTableTZField').css('display','block');
			$('#listTableTZField').after('<div id="listTableTZ"></div>');
		}
		$('#listTableTZ').edatagrid({
			rownumbers:true,
			height:300,
			fitColumns:true,
			singleSelect:true,
			rowStyler: function(index,row){
				if ((index+1) % 2 == 0){
					return 'background-color:#f7f7f7;';
				}
			},
			columns:[[
					j_gridText({field:'itemId',title:'ID',hidden:true}),
					j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
					j_gridText({field:'itemType',title:'项目类别Id',hidden:true}),
					j_gridText({field:'itemTypeName',title:'项目类别'}),
					j_gridText({field:'itemName',title:'项目名称'}),
					j_gridNumber({field:'pigNum',title:'头数',width:80,min:0}),
					j_gridNumber({field:'itemValue',title:'重量',precision:2,width:80}),
			    ]],
            onLoadSuccess:function(data){
                	if(!data.success && data.success != undefined){
        				$.messager.alert({
        					title: '错误',
        					msg: data.errorMsg
        				});
        	    	}
            },
            onBeginEdit:function(index,row){
            	if(3 == row.itemType){
					var pigNum = $('#listTable').datagrid('getEditor', {
				        index: index,
				        field: 'pigNum'
				    });
					if(pigNum != null){
						$(pigNum.target).numberspinner('disable');
					}
				}
			}
		});
		$('#accountBasisField').css('display','none');
	}else if(customerName == '上海爱森肉食品有限公司'){
		if($("#listTableTZ").length>0){
			$('#listTableTZField').css('display','none');
			$('#listTableTZ').parent().parent().parent().remove();
		}
		$('#accountBasisField').css('display','block');
	}else{
		if($("#listTableTZ").length>0){
			$('#listTableTZField').css('display','none');
			$('#listTableTZ').parent().parent().parent().remove();
		}
		$('#accountBasisField').css('display','none');
	}
}

function customerChangefun(newValue,oldValue){
	
	$('#checkboxDiv input[type=checkbox]').attr('checked',false);
	formClear();
	var customerName = $('#customerId').combobox('getText');
	accountListTableInit();
	// 屠宰质量
	if(customerName == '上海五丰上食食品有限公司'){
		$('#nominalPrice').numberspinner({disabled:false});
		$('#accountBasis').numberspinner({disabled:true});
		$('#fatRate').numberspinner({disabled:true});
		$('#listTableTZ').datagrid('load',basicUrl+'/account/searchCusSaleItem?customerId='+newValue+'&itemType=2');
	}else if(customerName == '中粮肉食（江苏）有限公司'){
		$('#nominalPrice').numberspinner({disabled:true});
		$('#accountBasis').numberspinner({disabled:true});
		$('#fatRate').numberspinner({disabled:true});
		$('#listTableTZ').datagrid('load',basicUrl+'/account/searchCusSaleItem?customerId='+newValue+'&itemType=2');
	}else if(customerName == '上海爱森肉食品有限公司'){
		$('#nominalPrice').numberspinner({disabled:false});
		$('#accountBasis').numberspinner({disabled:false});
		$('#fatRate').numberspinner({disabled:false});
	}else{
		$('#nominalPrice').numberspinner({disabled:true});
		$('#accountBasis').numberspinner({disabled:true});
		$('#fatRate').numberspinner({disabled:true});
	}
	// 扣款明细
	$('#listTable').datagrid('load',basicUrl+'/account/searchCusSaleItem?customerId='+newValue+'&itemType=1,3');
	$('#saleBillId').combogrid('grid').datagrid('reload',basicUrl+'/account/selectSaleBillByCustomer?customerId='+newValue+'&saleStatus=1');
}

function pigNumChangefun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var row = $('#listTable').datagrid('getRows')[editIndex];
	var rewardPunishStandard = row.rewardPunishStandard == null?0:row.rewardPunishStandard;
	var itemValue= rewardPunishStandard * newValue;
	row.itemValue = itemValue;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'itemValue',
		value:row.itemValue
	}]);
	
	var rows = $('#listTable').datagrid('getRows');
	var totalItemPrice = 0;
	if(newValue != ''){
		totalItemPrice = strToFloat(itemValue);
	}
	$.each(rows,function(index,row){
		if( index != editIndex && row.itemValue != undefined && row.itemValue != ''){
			totalItemPrice += strToFloat(row.itemValue);
		}
	})
	$('#totalChargebacks').numberspinner('setValue',totalItemPrice);
}

function itemValueChangefun(newValue,oldValue){
	if(editIndex == undefined){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	var totalItemPrice = 0;
	if(newValue != ''){
		totalItemPrice = strToFloat(newValue);
	}
	$.each(rows,function(index,row){
		if( index != editIndex && row.itemValue != undefined && row.itemValue != ''){
			totalItemPrice += strToFloat(row.itemValue);
		}
	})
	$('#totalChargebacks').numberspinner('setValue',totalItemPrice);
}

function billOnSelectfun(index,row){
	var customerId = $('#customerId').combobox('getValue');
	var data = jAjax.submit('/account/selectSaleBillByCustomer.do?customerId='+customerId+'&billId='+row.saleBillId);
	$('#editForm').form('load', data[0]);
}

//function billOnChangefun(newValue, oldValue){
//	if(newValue == ''){
//		return;
//	}
//	var customerId = $('#customerId').combobox('getValue');
//	var data = jAjax.submit('/account/selectSaleBillByCustomer.do?customerId='+customerId);
//}

function pigWeightChange(newValue, oldValue){
	if(newValue == ''){
		return;
	}
	var pigNum = $('#pigNum').numberspinner('getValue');
	if(pigNum == '' || pigNum == 0){
		$('#pigWeight').numberspinner('setValue','');
		$.messager.alert('提示','请先填写毛猪数量！');
		return;
	}
	var pigAvgWeight = newValue/pigNum;
	$('#pigAvgWeight').textbox('setValue',pigAvgWeight.toFixed(2)+'KG');
	var carcassTotalWeight = $('#carcassTotalWeight').numberspinner('getValue');
	var meatYield = carcassTotalWeight/newValue * 100;
	$('#meatYield').textbox('setValue',meatYield.toFixed(2));
	var fatDiff = meatYield - 71.5;
	var fatResult = fatDiff * 0.2;
	$('#accountTable tr').eq(1).find("td").eq(2).html(fatDiff.toFixed(2));
	$('#accountTable tr').eq(1).find("td").eq(4).html(fatResult.toFixed(2));
	
	var accountPrice = $('#accountPrice').numberspinner('getValue');
	var result = strToFloat(accountPrice/newValue);
	$('#totalAccount').numberspinner('setValue',result.toFixed(2));

}

function carcassTotalWeightChange(newValue, oldValue){
	if (newValue == ''){
		return;
	}
	var pigWeight = $('#pigWeight').numberspinner('getValue');
	if(pigWeight == '' || pigWeight == 0){
		$('#carcassTotalWeight').numberspinner('setValue','');
		$.messager.alert('提示','请先填写毛猪重！');
		return;
	}
	var meatYield = newValue/pigWeight * 100;
	$('#meatYield').textbox('setValue',meatYield.toFixed(2));
	var meatDiff = meatYield - 71.5;
	var meatResult = meatDiff * 0.2;
	$('#accountTable tr').eq(1).find("td").eq(2).html(meatDiff.toFixed(2));
	$('#accountTable tr').eq(1).find("td").eq(4).html(meatResult.toFixed(2));

}

function fatRateChange(newValue, oldValue){
	if (newValue == ''){
		return;
	}
	var customerName = $('#customerId').combobox('getText');
	var nominalPrice = $('#nominalPrice').numberspinner('getValue');

	if(customerName == '上海爱森肉食品有限公司'){
		var fatDiff = newValue - 50.0;
		var fatResult = fatDiff * 0.02;
		$('#accountTable tr').eq(2).find("td").eq(2).html(fatDiff.toFixed(2));
		$('#accountTable tr').eq(2).find("td").eq(4).html(fatResult.toFixed(2));
		
		var meatResult = $('#accountTable tr').eq(1).find("td").eq(4).html();
		var result = Number(fatResult.toFixed(2)) + Number(nominalPrice) + Number(meatResult);
		$('#accountBasis').numberspinner("setValue",result.toFixed(2));
	}else{
		$('#accountBasis').numberspinner("setValue",'');
	}
}

function pigNumChange(newValue, oldValue){
	if (newValue == ''){
		return;
	}
	var pigWeight = $('#pigWeight').val();
	var pigAvgWeight = pigWeight/newValue;
	$('#pigAvgWeight').textbox('setValue',pigAvgWeight.toFixed(2)+'KG'); // numberspinner
}

function nominalPriceChange(newValue, oldValue){
	if (newValue == ''){
		return;
	}
	var customerName = $('#customerId').combobox('getText');
	
	if(customerName == '上海爱森肉食品有限公司'){
		var meatResult = $('#accountTable').find("tr").eq(1).find("td").eq(4).html();
		var fatResult = $('#accountTable').find("tr").eq(2).find("td").eq(4).html();
		var result = Number(newValue) + Number(meatResult) + Number(fatResult);
		$('#accountBasis').numberspinner("setValue",result.toFixed(2));
	}else{
		$('#accountBasis').numberspinner("setValue",'');
	}
}

function meatYieldChange(newValue, oldValue){
	if (newValue == ''){
		return;
	}
	var customerName = $('#customerId').combobox('getText');
	var nominalPrice = $('#nominalPrice').numberspinner('getValue');

	if(customerName == '上海爱森肉食品有限公司'){
		var meatDiff = (strToFloat(newValue) - 71.5).toFixed(2);
		var meatResult = (meatDiff * 0.2).toFixed(2);
		
		$('#accountTable tr').eq(1).find("td").eq(2).html(meatDiff);
		$('#accountTable tr').eq(1).find("td").eq(4).html(meatResult);
		var fatResult = $('#accountTable tr').eq(2).find("td").eq(4).html();

		var result = strToFloat(fatResult) + strToFloat(nominalPrice) + strToFloat(meatResult);
		$('#accountBasis').numberspinner("setValue",result.toFixed(2));
	}else{
		$('#accountBasis').numberspinner("setValue",'');
	}

}

function accountBasisChange(newValue, oldValue){
	if (newValue == ''){
		return;
	}
	var pigWeight = $('#pigWeight').numberspinner('getValue');
//	var accountBasis = $('#accountBasis').numberspinner('getValue');
	var totalChargebacks = $('#totalChargebacks').numberspinner('getValue');
	var result = strToFloat(pigWeight) * strToFloat(newValue) + strToFloat(totalChargebacks);
	$('#accountPrice').numberspinner('setValue',result.toFixed(2));
}

function totalChargebacksChange(newValue, oldValue){
	if (newValue == ''){
		return;
	}
	var accountBasis = $('#accountBasis').numberspinner('getValue');
	var pigWeight = $('#pigWeight').numberspinner('getValue');
	var result = strToFloat(newValue) + strToFloat(pigWeight) * strToFloat(accountBasis);
	$('#accountPrice').numberspinner('setValue',result.toFixed(2));

}

function accountPriceChange(newValue, oldValue){
	if (newValue == ''){
		return;
	}
	var pigWeight = $('#pigWeight').numberspinner('getValue');
	if (pigWeight == ''){
		return;
	}
	var result = strToFloat(newValue)/strToFloat(pigWeight);
	$('#totalAccount').numberspinner('setValue',result.toFixed(2));

}

function pigNumWFChangefun(newValue,oldValue){
	var listTableTZEditIndex = $('#listTableTZ').edatagrid('getEditIndex');
	if(listTableTZEditIndex<0){
		return;
	}
	var row = $('#listTableTZ').datagrid('getRows')[listTableTZEditIndex];
	var pigNum = $('#pigNum').numberspinner('getValue');
	if(pigNum == ''){
		$.messager.alert('提示','请先填写毛猪头数！');
		return;
	}
//	row.itemValue = (strToInt(newValue)*100/strToInt(pigNum)).toFixed(2);
	var itemValueEditor = $('#listTableTZ').edatagrid('getEditor',{index:listTableTZEditIndex,field:'itemValue'});
	$(itemValueEditor.target).numberspinner('setValue', (strToInt(newValue)*100/strToInt(pigNum)).toFixed(2));
	//$('#listTableTZ').datagrid('refreshRow');
//	$('#listTableTZ').datagrid('updateRow',{
//		index: listTableTZEditIndex,
//		row: row
//	});
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
			// 清空屠宰质量
			if($("#listTableTZ").length>0){
				$('#listTableTZField').css('display','none');
				$('#listTableTZ').parent().parent().parent().remove();
			}
			response=eval('('+response+')');
			if(response.success){
				if(typeof(eventName) == "undefined"){
					$('#editWin').window('close');
					if($("#listTableTZ").length>0){
						$('#listTableTZField').css('display','none');
						$('#listTableTZ').parent().parent().remove();
					}
					if(listTable != null){
						$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
					}
					$('#accountTable tr').find('td:eq(2)').html(0);
					$('#accountTable tr').find('td:eq(4)').html(0);
					$.messager.alert('提示','保存成功！');
				}else{
					//重置
					var preRecord = $('#listTable').datagrid('getData');
					leftSilpFun('preSaveRecord',true,9002);
					if(preRecord.success == undefined){
						preRecord.success = true;
					}
					$('#preSaveRecordTable').datagrid('loadData',preRecord);
					if(listTable != null){
						$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
					}
					var listTableTZ = document.getElementById("listTableTZ");
					if(listTableTZ != null){
						$('#listTableTZ').datagrid('loadData',{success:true,total: 0, rows: [] });
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

function collectDetailData(){
	var listTable = $('#listTable');
	var rows =  listTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length ; i ++){
		listTable.datagrid('endEdit',i);
	}
	var queryParams;
	if(rows.length < 1){
		return null;
	}
	
	var tzrows = undefined;
	var listTableTZ = $('#listTableTZ');
	if(listTableTZ.length>0){
		tzrows = $('#listTableTZ').datagrid('getRows');
		for(var i = 0 ; i < tzrows.length ; i ++){
			listTableTZ.datagrid('endEdit',i);
		}
	}
	//获取新增行
	var insertRows = listTable.datagrid('getRows');
	
	var customerName = $('#customerId').combobox('getText');
	if(tzrows){
		insertRows = insertRows.concat(tzrows);
	}
	var jsonString = JSON.stringify(insertRows);

	var billCode = $("#saleBillId").combobox("getText");
	queryParams = {
			status:1,
			deletedFlag:0,
			billCode:billCode,
			gridList:jsonString
	};
	return queryParams;
}

/**
 * 取消
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnCancel(){
	if($("#listTableTZ").length>0){
		$('#listTableTZField').css('display','none');
		$('#listTableTZ').parent().parent().parent().remove();
	}
	var listTable = document.getElementById("listTable");
	if(listTable != null){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}
	$('#accountTable tr').find('td:eq(2)').html(0);
	$('#accountTable tr').find('td:eq(4)').html(0);
	$('#editForm').form('reset');
	$('#editWin').window('close');
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
		$('#checkboxDiv').css('visibility','hidden');
		$('#editWin').window('open');
		$('#saleBillId').combogrid('enable');
		$('#pigNum').numberspinner('setValue',record[0].pigNum);
		$('#pigWeight').numberspinner('setValue',record[0].pigWeight);
		loadData(record[0]);
//		$('#editForm').form('load',record[0]);
//		$('#pigNum').numberspinner('setValue',record[0].pigNum);
//		$('#pigWeight').numberspinner('setValue',record[0].pigWeight);
		$('#saleBillId').combogrid('grid').datagrid('load',basicUrl+'/account/selectSaleBillByCustomer?customerId='+record[0].customerId+'&saleStatus=2&dateType=all');
		$('#customerId').combogrid('disable');
		$('#saleBillId').combogrid('disable');
		$('#btnSave').css('display','none');
		var listTable = document.getElementById('listTable');
		if(listTable!=null){
			var mainId;
			if(record[0].ROW_ID!=null){
				mainId=record[0].ROW_ID;
			}else{
				mainId=record[0].rowId;
			}
			$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId+'&itemType=1,3');
		}
		var listTableTZ = document.getElementById('listTableTZ');
		if(listTableTZ!=null){
			var mainId;
			if(record[0].ROW_ID!=null){
				mainId=record[0].ROW_ID;
			}else{
				mainId=record[0].rowId;
			}
			$('#listTableTZ').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId+'&itemType=2');
		}
	}
}

/**
 * 双击查看方法
 * pms{
 * 	model:模块代码
 * 	title:模块名称
 * }
 */
function onDblClickRow(index,row){
	if($("#listTableTZ").length>0){
		$('#listTableTZField').css('display','none');
		$('#listTableTZ').parent().parent().parent().remove();
	}
	$('#editWin').window({
		title:'查看'+ title
	});
	$('#editWin').window('open');
	$('#saleBillId').combogrid('enable');
	$('#pigNum').numberspinner('setValue',row.pigNum);
	$('#pigWeight').numberspinner('setValue',row.pigWeight);
	loadData(row);
//	$('#editForm').form('load',record[0]);
//	$('#pigNum').numberspinner('setValue',record[0].pigNum);
//	$('#pigWeight').numberspinner('setValue',record[0].pigWeight);
	$('#saleBillId').combogrid('grid').datagrid('load',basicUrl+'/account/selectSaleBillByCustomer?customerId='+row.customerId+'&saleStatus=2&dateType=all');
	$('#customerId').combogrid('disable');
	$('#saleBillId').combogrid('disable');
	$('#btnSave').css('display','none');
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		var mainId;
		if(row.ROW_ID!=null){
			mainId=row.ROW_ID;
		}else{
			mainId=row.rowId;
		}
		$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId+'&itemType=1,3');
	}
	var listTableTZ = document.getElementById('listTableTZ');
	if(listTableTZ!=null){
		var mainId;
		if(row.ROW_ID!=null){
			mainId=row.ROW_ID;
		}else{
			mainId=row.rowId;
		}
		$('#listTableTZ').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+mainId+'&itemType=2');
	}
//	$('#pigNum').numberspinner('setValue',row.pigNum);
//	$('#pigWeight').numberspinner('setValue',row.pigWeight);
//	$('#editForm').form('load',row);
//	$('#btnSave').css('display','none');
//	var listTable = document.getElementById('listTable');
//	if(listTable!=null){
//		var rowId = row.rowId;
//		$('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+rowId+'&itemType=1,3');
//	}
//	var listTableTZ = document.getElementById('listTableTZ');
//	if(listTableTZ!=null){
//		var rowId = row.rowId;
//		$('#listTableTZ').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+rowId+'&itemType=2');
//	}
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
	$('#saleBillId').combogrid('grid').datagrid('load',basicUrl+'/account/selectSaleBillByCustomer?customerId='+customerId+'&saleStatus=1');
	$('#editWin').window('open');
	$('#notBillInputForm').form('reset');
	$('#btnSave').css('display','inline-block');
	$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
	var listTable = document.getElementById('listTable');
	if(listTable!=null){
		$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	}
	$('#customerId').combogrid('enable');
	$('#saleBillId').combogrid('enable');
	// 隐藏结算依据
	$('#accountBasisField').css('display','none');
	$('#checkboxDiv').css('visibility','visible');

}

function changeBillIdUrl(checkboxItem){
	var customerId = $('#customerId').combobox('getValue');
	var checkedFlag = $(checkboxItem).is(':checked');
	$('#saleBillId').combogrid('setValue','');
//	$('#saleBillId').combogrid('setText','');
	var data = [];
	if(checkedFlag){
		$('#saleBillId').combogrid('grid').datagrid('load',basicUrl+'/account/selectSaleBillByCustomer?customerId='+customerId+'&saleStatus=2');
	}else{
		$('#saleBillId').combogrid('grid').datagrid('load',basicUrl+'/account/selectSaleBillByCustomer?customerId='+customerId+'&saleStatus=1');
	}
}

/**
 * 清空表单
 */
function formClear(){
	$('#saleBillId').combogrid('setValue','');
	$('#saleDate').datebox('setValue','');
	$('#farmName').textbox('setValue','');
	$('#totalNum').textbox('setValue','');
	$('#totalWeight').textbox('setValue','');
	$('#businessCode').textbox('setValue','');
	$('#pigNum').numberspinner('setValue','');
	$('#pigWeight').numberspinner('setValue','');
	$('#nominalPrice').numberspinner('setValue','');
	$('#carcassTotalWeight').numberspinner('setValue','');
	$('#fatRate').numberspinner('setValue','');
	$('#accountPrice').numberspinner('setValue','');
	$('#totalChargebacks').numberspinner('setValue','');
	$('#totalAccount').numberspinner('setValue','');
	$('#pigAvgWeight').textbox('setValue','');
	$('#meatYield').textbox('setValue','');
	$('#accountBasis').textbox('setValue','');
}

function loadData(record){
	$('#customerId').combogrid('setValue',record.customerId);
	$('#saleAccountDate').datebox('setValue',record.saleAccountDate);
	$('#saleBillId').combogrid('setValue',record.saleBillId);
	$('#saleDate').datebox('setValue',record.saleDate);
	$('#farmName').textbox('setValue',record.farmName);
	$('#totalNum').textbox('setValue',record.totalNum);
	$('#totalWeight').textbox('setValue',record.totalWeight);
	$('#businessCode').textbox('setValue',record.businessCode);
	$('#pigNum').numberspinner('setValue',record.pigNum);
	$('#pigWeight').numberspinner('setValue',record.pigWeight);
	$('#nominalPrice').numberspinner('setValue',record.nominalPrice);
	$('#carcassTotalWeight').numberspinner('setValue',record.carcassTotalWeight);
	$('#fatRate').numberspinner('setValue',record.fatRate);
	$('#totalChargebacks').numberspinner('setValue',record.totalItemPrice);
	$('#accountPrice').numberspinner('setValue',record.totalAccountPrice);
	$('#totalAccount').numberspinner('setValue',record.totalAccount);

//	$('#meatYield').numberspinner('setValue','');
//	$('#pigAvgWeight').numberspinner('setValue','');
//	$('#accountBasis').numberspinner('setValue','');
}

// 结算依据 = 挂牌价 + 出肉率和原膘比例的 奖惩结果
//$('#accountBasis')

// 所有 项目 奖惩总和 
//$('#totalChargebacks')

// 结算总金额 = 毛猪重 * 结算依据 + 扣款合计
//$('#accountPrice')

// 结算价 = 结算总金额 / 毛猪重
//$('#totalAccount')