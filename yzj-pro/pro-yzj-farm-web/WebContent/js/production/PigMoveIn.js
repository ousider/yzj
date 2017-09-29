var eventName = 'PIG_MOVE_IN';
var prUrl='/production/';
var saveUrl=prUrl+'editPigMoveIn.do';
var editIndex = undefined;

var downTemplateParam="xmlName=EarBrandPigInputEmportExcel.xml&downName=猪只耳牌号入场.xls&typeName=EAR_BLAND_INITIALIZE&lineId=0&pigType=2&cussupType=S";
var importParam={xmlName:'EarBrandPigInputEmportExcel.xml'};
//获取猪只类型值
var obj_pigType = document.getElementsByName('pigType');
//默认值为2
var oldPigType = 2;
//获取入场方式值
var obj_enterType  = document.getElementsByName('enterType');
//默认值为1
var oldEnterType = 1;
//获取产线Id
var lineValue = 0;
//判断产线是否启用
var lineUseFlag = 0;

// 目前为止：产线已经被取消，没有这样的业务
//$.ajax({  
//    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=HFCX', 
//    async:false,
//    type : "POST",  
//    success : function(data) { 
//    	var obj = eval('(' + data + ')');
//    	if(obj.rows == 'ON'){
//    		lineUseFlag = 1;
//    	}
//    }
//});

$(document).ready(function(){
	/**
	 * 封装控件调用方法
	 */
	//猪只类型
	xnRadioBox({
		id:'pigType',
		name:'pigType',
		typeCode:'PIG_TYPE',
		//是否有监听事件
		onChange:true,
		defaultValue:2
	});
	//入场类型
	xnRadioBox({
		id:'enterType',
		name:'enterType',
		typeCode:'ENTER_TYPE',
		//是否有监听事件
		onChange:true,
		defaultValue:1,
		excludeValue:['3']
	});
	//物料名称
	materialComboGrid({required:true});
	$('#materialId').combogrid('grid').datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+oldPigType);
	addFocus('materialId','grid')
	
	//供应商
	supplierComboGrid({});
	addFocus('supplierId','grid')
	//猪只状态
	pigClassComboGrid({
		required:true
	});
	$('#pigClass').combogrid('grid').datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName);
	//产线
	xnComboGrid({
		id:'lineId',
		idField:'rowId',
		textField:'lineName',
		url:'/basicInfo/searchLineToList.do',
		width:550,
		onChange:lineOnChangefun,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'产线代码',width:100},
			        {field:'lineName',title:'产线名称',width:100}
			    ]]
	});
	
	//批次
	swineryComboGrid({});
	$('#swineryId').combogrid('grid').datagrid('load',basicUrl+'/production/searchSwineryToList.do?lineId=0&pigType='+oldPigType+'&eventName='+eventName);
	addFocus('swineryId','grid');
	
	//猪舍
	houseComboGrid({
		required:true
	});
	$('#houseId').combogrid('grid').datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName);
	addFocus('houseId','grid');
	
	// 销售单据
	xnComboGrid({
		id:'billId',
		idField:'billId',
		textField:'billCode',
		url:'/production/selectSellBill?pigType='+oldPigType,
		width:500,
		disabled:true,
		editable:true,
		onSelect:billOnSelectfun,
		columns:[[
		          	{field:'rowId',title:'ID',width:100,hidden:true},
		          	{field:'billCode',title:'单据编号',width:60},
		          	{field:'billDate',title:'单据日期',width:60},
		          	{field:'supplierIdName',title:'供应商',width:100},
		          	{field:'saleNum',title:'销售猪只数量',width:60}
		          ]]
	});

	//判断产线是否启用
	if(lineUseFlag == 1){
		$('#lineId').combogrid('enable');
	    //$('#listTable').datagrid('showColumn','lineId');
	    //form批次猪舍初始化
		 var swineryGrid = $('#swineryId').combogrid('grid');
		 swineryGrid.datagrid('load',basicUrl+'/production/searchSwineryToList.do?lineId='+lineValue+'&pigType=2&eventName='+eventName);
		 var houseGrid = $('#houseId').combogrid('grid');
		 houseGrid.datagrid('load',basicUrl+'/basicInfo/searchHouseToList.do?lineId='+lineValue+'&eventName='+eventName);
	}else{
		$('#lineId').combogrid('disable');
		//$('#listTable').datagrid('hideColumn','lineId');
		 //form批次猪舍初始化
		var swineryGrid = $('#swineryId').combogrid('grid');
		swineryGrid.datagrid('load',basicUrl+'/production/searchSwineryToList.do?lineId=0&pigType=2&eventName='+eventName);
		var houseGrid = $('#houseId').combogrid('grid');
		houseGrid.datagrid('load',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName);
	}
	
	$('#total-plan').css('visibility','hidden');
});
/**
 * 当猪只类型改变事方法（命名规则on+控件ID+Change）
 */
function onPigTypeChange(){
	var newPigType = null;
	var index = null;
	var listTable = $('#listTable');
	 for(var i = 0 ; i < obj_pigType.length ; i ++){
		 if(obj_pigType[i].checked){
			 newPigType = obj_pigType[i].value;
			 index = i;
		 }
	 }
	 if(index != null && newPigType != oldPigType){
		 if(oldPigType == null){
			 oldPigType = obj_pigType[index].value;
		 }else{

			 $.messager.confirm('提示', '改变会清空数据，确定改变吗？', function(r){
				 if(r){
					 $('#billId').combogrid('setValue','');
					 $('#billId').combogrid('grid').datagrid('load',basicUrl+'/production/selectSellBill.do?lineId=0&pigType='+newPigType);

					 oldPigType = obj_pigType[index].value;
					 if(oldEnterType == 2){
						 //清空明细表数据
						 listTable.datagrid('loadData', { success:true,total: 0, rows: [] });
						 if(oldPigType == 3){
							 //肉猪耳号入场
							 document.getElementById('isSell').checked = true;
							 //$('#isSell').attr('checked',true);
							 $('#isSell').attr('disabled',true);
							 $('#billId').combogrid('enable');
							 $("#addPig").attr({"disabled":"disabled"});
							 $("#addPig").css('background-color','rgb(220, 220, 220)');
							 $("#deletePig").attr({"disabled":"disabled"});
							 $("#deletePig").css('background-color','rgb(220, 220, 220)');
							 $("#inputPig").attr({"disabled":"disabled"});
							 $("#inputPig").css('background-color','rgb(220, 220, 220)');
							 earPorkMoveIn();
						 }else{
							 //公猪母猪耳号入场
							 document.getElementById('isSell').checked = false;
							 $('#isSell').removeAttr('disabled');
							 $('#billId').combogrid('disable');
							 $("#addPig").removeAttr("disabled");
							 $("#addPig").css('background-color','rgb(113, 175, 76)');
							 $("#deletePig").removeAttr("disabled");
							 $("#deletePig").css('background-color','rgb(113, 175, 76)');
							 $("#inputPig").removeAttr("disabled");
							 $("#inputPig").css('background-color','rgb(113, 175, 76)');
							 earPigMoveIn();
						 }
					 }else{
						 if(oldPigType == 3){
							 //肉猪批次入场
							 $('#swineryId').combogrid({
								required:true 
							 });
							 addFocus('swineryId','grid');
							 $('#averageWeight').numberspinner({required:true });
						 }else{
							 //公猪母猪批次入场
							 $('#swineryId').combogrid({
								required:false 
							 });
							 addFocus('swineryId','grid');
							 $('#averageWeight').numberspinner({required:false });
						 }
						 $('#billId').combogrid('setValue','');
						 $('#supplierId').combogrid('setValue','');
						 $('#houseId').combogrid('setValue','');
						 $('#materialId').combogrid('setValue','');
						 $('#pigClass').combogrid('setValue','');
						 $('#swineryId').combogrid('setValue','');
						 $('#totalNum').numberspinner('setValue','');
						 $('#averageWeight').numberspinner('setValue','');
						 $('#averagePrice').numberspinner('setValue','');
						 $('#dayAge').numberspinner('setValue','');
						 
						 var materialGrid = $('#materialId').combogrid('grid');
						 materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+oldPigType);
						 $('#pigClass').combogrid('setValue','');
						 var pigClassGrid = $('#pigClass').combogrid('grid');
						 pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName);
						 $('#swineryId').combogrid('setValue','');
						 var swineryGrid = $('#swineryId').combogrid('grid');
						 if(lineUseFlag == 1){
							 swineryGrid.datagrid('reload',basicUrl+'/production/searchSwineryToList.do?lineId='+lineValue+'&pigType='+oldPigType+'&eventName='+eventName);
						 }else{
							 swineryGrid.datagrid('reload',basicUrl+'/production/searchSwineryToList.do?lineId=0&pigType='+oldPigType+'&eventName='+eventName);
						 }
					 }
				 }else{
					 for(var j = 0 ; j < obj_pigType.length ; j ++){
						 if(obj_pigType[j].value == oldPigType){
							 obj_pigType[j].checked = true;
						 }else{
							 obj_pigType[j].checked = false;
						 }
					 }
				 }
			 });
		 }
	 }
}

/**
 * 当入场方式改变事方法（命名规则on+控件ID+Change）
 */
function onEnterTypeChange(){
	var newEnterType = null;
	var index = null;
	var hideTable = $('#hideTable');
	var listTable = $('#listTable');
	var pigType = null;
	 for(var i = 0 ; i < obj_enterType.length ; i ++){
		 if(obj_enterType[i].checked){
			 newEnterType = obj_enterType[i].value;
			 index = i;
		 }
	 }
	 if(index != null && newEnterType != oldEnterType){
		 if(oldEnterType == null){
			 oldEnterType = obj_enterType[index].value;
		 }else{

			 $.messager.confirm('提示', '改变会清空数据，确定改变吗？', function(r){
				 if(r){
					 $('#billId').combogrid('setValue','');
					 for(var i = 0 ; i < obj_pigType.length ; i ++){
						 if(obj_pigType[i].checked){
							 pigType = obj_pigType[i].value;
						 }
					 }
					 $('#billId').combogrid('grid').datagrid('load',basicUrl+'/production/selectSellBill.do?lineId=0&pigType='+pigType);
					 //批次入场
					 oldEnterType = obj_enterType[index].value;
					 if(oldEnterType == 1){
						$('#total-plan').css('visibility','hidden');

						 hideTable.css('display','block');
						 $('#hidePanel').panel('close'); 
						 document.getElementById('isSell').checked = false;
						 $('#isSell').attr('disabled',true);
						 $('#billId').combogrid('disable');
						 
						 if(oldPigType == 3){
							 //肉猪批次入场
							 $('#swineryId').combogrid({
								required:true 
							 });
							 addFocus('swineryId','grid');
							 $('#averageWeight').numberspinner({required:true });
						 }else{
							 //公猪母猪批次入场
							 $('#swineryId').combogrid({
								required:false 
							 });
							 addFocus('swineryId','grid');
							 $('#averageWeight').numberspinner({required:false });
						 }
						 $('#billId').combogrid('setValue','');
						 $('#supplierId').combogrid('setValue','');
						 $('#houseId').combogrid('setValue','');
						 $('#materialId').combogrid('setValue','');
						 $('#pigClass').combogrid('setValue','');
						 $('#swineryId').combogrid('setValue','');
						 $('#totalNum').numberspinner('setValue','');
						 $('#averageWeight').numberspinner('setValue','');
						 $('#averagePrice').numberspinner('setValue','');
						 $('#dayAge').numberspinner('setValue','');
						 
						 var materialGrid = $('#materialId').combogrid('grid');
						 materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+oldPigType);
						 $('#pigClass').combogrid('setValue','');
						 var pigClassGrid = $('#pigClass').combogrid('grid');
						 pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName);
						 $('#swineryId').combogrid('setValue','');
						 var swineryGrid = $('#swineryId').combogrid('grid');
						 if(lineUseFlag == 1){
							 swineryGrid.datagrid('reload',basicUrl+'/production/searchSwineryToList.do?lineId='+lineValue+'&pigType='+oldPigType+'&eventName='+eventName);
						 }else{
							 swineryGrid.datagrid('reload',basicUrl+'/production/searchSwineryToList.do?lineId=0&pigType='+oldPigType+'&eventName='+eventName);
						 }
					 }else{
						 if(oldPigType == 3){
							//肉猪耳号入场
							 document.getElementById('isSell').checked = true;
							 $('#isSell').attr('disabled',true);
							 $('#billId').combogrid('enable');
							 $("#addPig").attr({"disabled":"disabled"});
							 $("#addPig").css('background-color','rgb(220, 220, 220)');
							 $("#deletePig").attr({"disabled":"disabled"});
							 $("#deletePig").css('background-color','rgb(220, 220, 220)');
							 $("#inputPig").attr({"disabled":"disabled"});
							 $("#inputPig").css('background-color','rgb(220, 220, 220)');
							 earPorkMoveIn();
							 hideTable.css('display','none');
							 $('#hidePanel').panel('open');
						 }else{
							//耳号入场
							 document.getElementById('isSell').checked = false;
							 $('#isSell').removeAttr('disabled');
							 $('#billId').combogrid('disable');
							 $("#addPig").removeAttr("disabled");
							 $("#addPig").css('background-color','rgb(113, 175, 76)');
							 $("#deletePig").removeAttr("disabled");
							 $("#deletePig").css('background-color','rgb(113, 175, 76)');
							 $("#inputPig").removeAttr("disabled");
							 $("#inputPig").css('background-color','rgb(113, 175, 76)');
							 earPigMoveIn();
							 hideTable.css('display','none');
							 $('#hidePanel').panel('open');
							 $('#isSell').removeAttr('disabled');
						 }
						 //清空明细表数据
						 listTable.datagrid('loadData', { success:true,total: 0, rows: [] });
					 }
				 }else{
					 for(var j = 0 ; j < obj_enterType.length ; j ++){
						 if(obj_enterType[j].checked){
							 obj_enterType[j].checked = false;
						 }else if(obj_enterType[j].value == oldEnterType){
							 obj_enterType[j].checked = true;
						 }
					 }
				 }
				 
			 });
		 }
	 }
}
/**
 * 保存
 */
function onBtnSave(){
	var enterType  = document.getElementsByName('enterType');
	var isSell = document.getElementById('isSell').checked;
	var billId = $('#billId').combogrid('getValue');
	var relateBillCode = $('#billId').combogrid('getText');
	var enterTypeValue = null;
	for(var j = 0 ; j < enterType.length ; j ++){
		if(enterType[j].checked){
			  enterTypeValue = enterType[j].value;
		  }
	}
	var queryParams = null;
	//批次入场
	if(enterTypeValue == 1){
		queryParams = {
				status:1,
				deletedFlag:0
			};
	}else{
	//耳号入场
		var listTable = $('#listTable');
		var rows =  listTable.datagrid('getRows');
		for(var i = 0 ; i < rows.length ; i ++){
			listTable.datagrid('endEdit',i);
		}
		//获取新增行
		var insertRows = listTable.datagrid('getRows');
		//给提交数据加上行号
		$.each(insertRows,function(index,data){
			insertRows[index].lineNumber = listTable.datagrid('getRowIndex',data)+1;
		})
		var jsonString = JSON.stringify(insertRows);
	    queryParams = {
				status:1,
				deletedFlag:0,
				isSell:isSell,
				relateBillId:billId,
				relateBillCode:relateBillCode,
				gridList:jsonString
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
			$.messager.alert('提示','前台提示--耳牌号：【'+errorEarBrand+'】没有对应的猪只数据，请重新选择！');
			return;
		}
	}
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	$.messager.progress();	
	$('#editForm').form('submit',{
		url:basicUrl+saveUrl,
		queryParams:queryParams,
		onSubmit: function(){
			if(enterTypeValue == 1){
				var isValid = $('#editForm').form('validate');
				if (!isValid){
					$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
					$.messager.progress('close');	
				}
				return isValid;
			}
			
		},
		success: function(response){
			response=eval('('+response+')');
			if(response.success){
				//批次入场重置form
				$('#billId').combogrid('setValue','');
				$('#billId').combogrid('grid').datagrid('reload',basicUrl+'/production/selectSellBill?pigType='+oldPigType);
				addFocus('billId','grid');
				if(oldEnterType == 1){
					$('#editForm').form('reset');
					for(var j = 0 ; j < obj_pigType.length ; j ++){
						if(obj_pigType[j].value == oldPigType){
							obj_pigType[j].checked = true;
						}else{
							obj_pigType[j].checked = false;
						}
					 }
					$.messager.alert({
	                    title: '提示',
	                    msg: '保存成功！'
	                });
				}else{
					leftSilpFun('preSaveRecord',true,9002);
					var preRecord = $('#listTable').datagrid('getData');
					preRecord.success = true;
					$('#preSaveRecordTable').datagrid('loadData',preRecord);
					$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
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
/**
 * 重置方法
 */
function onBtnReset(){
	$.messager.confirm('提示', '重置将清空数据，确定要重置吗？', function(r){
		if (r){
			//批次入场
			if(oldEnterType == 1){
				$('#editForm').form('reset');
				for(var j = 0 ; j < obj_pigType.length ; j ++){
					if(obj_pigType[j].value == oldPigType){
						obj_pigType[j].checked = true;
					}else{
						obj_pigType[j].checked = false;
					}
				 }
			}
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
		}
	});
}
function lineOnChangefun(newValue,oldValue){
	 lineValue = newValue;
	 $('#swineryId').combogrid('setValue','');
	 var swineryGrid = $('#swineryId').combogrid('grid');
	 swineryGrid.datagrid('reload',basicUrl+'/production/searchSwineryToList.do?lineId='+newValue+'&pigType='+oldPigType);
	 $('#houseId').combogrid('setValue','');
	 var houseGrid = $('#houseId').combogrid('grid');
	 houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId='+newValue+'&eventName='+eventName);
}
function girdLineChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].houseId = '';
	rows[editIndex].houseName = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'houseId',
		value:''
	}]);
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:rows[editIndex]
//		});
//	 },100);
}


/**
 * 导入
 */
function onBtnImport(importParam){
//    oldPigType==
    var formData = new FormData($("#importForm")[0]);
    formData.append("xmlName",importParam.xmlName);
    $.ajax({
      url: basicUrl+"/excel/earBlandUpload.do",  
      type: "POST",
      data : formData,  
      async: false,  
      cache: false,  
      contentType: false,  
      processData: false,  
      success: function (response) {  
	response=eval('('+response+')');
	if(response.success){
          //var data = response.rows;
          $('#listTable').datagrid('loadData',response);
          rightSlipFun('eventImport',390,true);
	}
      },  
      error: function (response) {  
          alert(returndata);  
      }  
    });

}
function isSellChange(para){

	 $.messager.confirm('提示', '改变会清空数据，确定改变吗？', function(r){
		 if(r){
				$('#billId').combogrid('setValue','');
				var checkedFalg = $(para).is(':checked');
				var listTable = $('#listTable');
				if(checkedFalg){
					$('#billId').combogrid('enable');
					$("#addPig").attr({"disabled":"disabled"});
					$("#addPig").css('background-color','rgb(220, 220, 220)');
					$("#inputPig").attr({"disabled":"disabled"});
					$("#inputPig").css('background-color','rgb(220, 220, 220)');
					$("#deletePig").attr({"disabled":"disabled"});
					$("#deletePig").css('background-color','rgb(220, 220, 220)');
					earPigSellMoveIn();
				}else{
					$('#billId').combogrid('disable');
					$("#addPig").removeAttr("disabled");
					$("#addPig").css('background-color','rgb(113, 175, 76)');
					$("#inputPig").removeAttr("disabled");
					$("#inputPig").css('background-color','rgb(113, 175, 76)');
					$("#deletePig").removeAttr("disabled");
					$("#deletePig").css('background-color','rgb(113, 175, 76)');
					earPigMoveIn();
				}
				 //清空明细表数据
				 listTable.datagrid('loadData', { success:true,total: 0, rows: [] });
		 	}else{
				var checkedFalg = $(para).is(':checked');
				if(checkedFalg){
					document.getElementById('isSell').checked = false;
				}else{
					document.getElementById('isSell').checked = true;
				}
		 	}
		 });
}

function billOnSelectfun(index,row){

//	$('#listTable').datagrid('loadData',(function(){
//		$('#listTable').datagrid('loading');
//		return rows;
//	}));
	$('#listTable').datagrid({
		url:basicUrl+'/production/selectPigListByBillId?billId='+row.billId+'&pigType='+oldPigType,
		loadFilter:function(data){
			if(data.errorMsg != '' && data.errorMsg != undefined){
				$.messager.alert('提示',data.errorMsg);
			}else{
				return data;
			}
		},
		onLoadSuccess:function(){
			if(oldPigType == 3){
				var rows = $('#listTable').datagrid('getRows');
				var totalPrice = 0,totalWeight = 0,totalNum = 0;
				if(rows.length>=1){
					$.each(rows,function(index,eachRow){
						totalPrice += strToFloat(eachRow.averagePrice);
						totalWeight += strToFloat(eachRow.averageWeight);
						totalNum += strToFloat(eachRow.totalNum);
					})
				}
				$('#sellNumSum').html(totalNum);
				$('#weightSum').html(totalWeight);
				$('#moneySum').html(totalPrice);
			}
			$('#listTable').datagrid('loaded');
		}
	});
}

function earPigMoveIn(){
	//猪只清单
	$('#total-plan').css('visibility','hidden');
	
	$('#listTable').datagrid(
			j_detailGrid({
				height:'100%',
			dbClickEdit:true,
			//fitColumns:true,
			frozenColumns:[[
			                {field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({field:'relationPigId',title:'猪只Id',hidden:true}),
			              	j_gridText({field:'leaveDate',title:'销售离场日期',hidden:true}),
			              	j_gridText({field:'earBrand',title:'耳牌号',width:100,editor:{
			              		type:'textbox',
			              		options:{
			              				validType:'numOrLetterOr_',
			              			}
		              			}
		                    }),
			              	j_gridText({field:'earShort',title:'耳缺号',width:100,editor:{
			              		type:'textbox',
			              		options:{
			              				validType:'numOrLetterOr_',
			              			}
		              			}
		              		})
			              	]],
			columns:[[
		              	//物料名称
		              	j_gridText(materialGridComboGrid({width:100})),
		              	j_gridNumber({field:'onPrice',title:'初始成本',width:100,editor:'textbox',
		              		precision:2,
		              		increment:0.1,
		              		min:0,
		              		max:100000,
		              		formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              		return value+'元';
		              	}}),
		              	j_gridText({field:'fatherEar',title:'父亲耳号',width:100,editor:{
		              		type:'textbox',
		              		options:{
		              				validType:'numOrLetterOr_',
		              			}
	              			}}),
		              	j_gridText({field:'motherEar',title:'母亲耳号',width:100,editor:{
		              		type:'textbox',
		              		options:{
		              				validType:'numOrLetterOr_',
		              			}
	              			}}),
		              	j_gridText({field:'lineId',title:'产线',width:100,hidden:true,
		              		formatter:function(value,row){
			                     return row.lineIdName;  
							},
							editor:xnGridComboGrid({
								field:'lineId',
			        			idField:'rowId',
			        			textField:'lineName',
			        			width:550,
			        			url:'/basicInfo/searchLineToList.do',
			        			onChange:girdLineChangeFun,
			        			columns:[[ 	
											{field:'rowId',title:'ID',width:100,hidden:true},
									        {field:'businessCode',title:'产线代码',width:100},
									        {field:'lineName',title:'产线名称',width:100}
			        				    ]]
		              			})
		              	}),
		              	//猪舍
		              	j_gridText(houseGridComboGrid({width:100})),
		              	//猪只状态
		              	j_gridText(pigClassGridComboGrid({width:100})),
		              	j_gridNumber({field:'leftTeatNum',width:100,title:'左乳头',min:1,max:10}),
		              	j_gridNumber({field:'rightTeatNum',width:100,title:'右乳头',min:1,max:10}),
		              	j_gridNumber({field:'parity',width:100,title:'胎次',min:0,max:12}),
		              	j_gridText({field:'birthDate',width:100,title:'出生日期',width:80,editor:{type:'datebox',options:{editable:false}}}),
		              	j_gridNumber({field:'birthWeight',
		              		precision:2,
		              		increment:0.1,
		              		min:1,
		              		max:5,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.birthWeight = value;
		              			return value+'KG';
			              	},
		              		width:100,title:'出生体重'}),
		              	j_gridNumber({field:'enterWeight',
		              		precision:2,
		              		increment:0.1,
		              		min:1,
		              		max:500,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.enterWeight = value;
		              			return value+'KG';
			              	},
		              		width:100,title:'入场体重'}),
		              	//供应商
		              	j_gridText(supplierGridComboGrid({width:100})),
		              	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
	            var material = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'materialId'
	            });
	            if(material != null){
	            	row.materialIdName = $(material.target).combogrid('getText');
	            }
	            var pigHouse = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	            if(pigHouse != null){
	            	row.houseIdName = $(pigHouse.target).combogrid('getText');
	            }
	            var line = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'lineId'
	            });
	            if(line != null){
	            	row.lineIdName = $(line.target).combogrid('getText');
	            }
	            var pigClass = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'pigClass'
	            });
	            if(pigClass != null){
	            	row.pigClassName = $(pigClass.target).combogrid('getText');
	            }
	            var supplier = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'supplierId'
	            });
	            if(supplier != null){
	            	row.supplierIdName = $(supplier.target).combogrid('getText');
	            }
	        },
	        onBeginEdit:function(index,row){
	        	var house = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	        	if(house != null){
	        		var houseGrid = $(house.target).combogrid('grid');
        			if(lineUseFlag == 1){
		        		var lineId = row.lineId == null || row.lineId == undefined || row.lineId == ''? 0 : row.lineId;
		        		houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId='+ lineId+'&eventName='+eventName);
		        	}else{
		        		houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName);
		        	}
	        	}
	        	var material = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'materialId'
	            });
	        	if(material != null){
	        		var materialGrid = $(material.target).combogrid('grid');
	        		materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+oldPigType);
	        	}
	        	var pigClass = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'pigClass'
	            });
	        	if(pigClass != null){
	        		var pigClassGrid = $(pigClass.target).combogrid('grid');
	        		pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName);
	        	}
			}
		})
	);
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'earBrand,earShort,materialIdName,onPrice,fatherEar,motherEar,lineIdName,houseIdName,pigClassName,leftTeatNum,rightTeatNum,parity,birthDate,birthWeight,enterWeight,supplierIdName,notes',
				columnTitles:'耳牌号,耳缺号,物料名称,初始成本,父亲耳号,母亲耳号,产线,猪舍名称,猪只状态,左乳头,右乳头,胎次,出生日期,出生体重(KG),入场体重(KG),供应商,备注',
				hiddenFields:'fatherEar,motherEar,lineIdName,supplierIdName,notes',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100,90,110,110,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
}

function earPigSellMoveIn(){
	//猪只清单
	$('#listTable').datagrid(
			j_detailGrid({
				height:'100%',
			dbClickEdit:true,
			//fitColumns:true,
			frozenColumns:[[
			                {field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({field:'relationPigId',title:'猪只Id',hidden:true}),
			              	j_gridText({field:'supplierId',title:'供应商Id',hidden:true}),
			              	j_gridText({field:'materialId',title:'物料Id',hidden:true}),
			              	j_gridText({field:'seedFlag',title:'留种标识',hidden:true}),
			              	j_gridText({field:'preLineNumber',title:'原场行号',hidden:true}),
			              	j_gridText({field:'earBrand',title:'耳牌号',width:100,editor:{
			              		type:'textbox',
			              		options:{
			              				validType:'numOrLetterOr_',
			              			}
		              			}
		                    }),
			              	j_gridText({field:'earShort',title:'耳缺号',width:100,editor:{
			              		type:'textbox',
			              		options:{
			              				validType:'numOrLetterOr_',
			              			}
		              			}
		              		})
			              	]],
			columns:[[
		              	//物料名称
		              	j_gridText({field:'materialIdName',title:'物料名称',width:100}),
		              	j_gridText({field:'onPrice',title:'初始成本',width:100,
		              		precision:2,
		              		increment:0.1,
		              		min:0,
		              		max:100000,
		              		formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              		return value+'元';
		              	}}),
		              	j_gridText({field:'fatherEar',title:'父亲耳号',width:100
		              		}),
		              	j_gridText({field:'motherEar',title:'母亲耳号',width:100
	              			}),
		              	j_gridText({field:'lineId',title:'产线',width:100,hidden:true,
		              		formatter:function(value,row){
			                     return row.lineIdName;  
							},
							editor:xnGridComboGrid({
								field:'lineId',
			        			idField:'rowId',
			        			textField:'lineName',
			        			width:550,
			        			url:'/basicInfo/searchLineToList.do',
			        			onChange:girdLineChangeFun,
			        			columns:[[ 	
											{field:'rowId',title:'ID',width:100,hidden:true},
									        {field:'businessCode',title:'产线代码',width:100},
									        {field:'lineName',title:'产线名称',width:100}
			        				    ]]
		              			})
		              	}),
		              	//猪舍
		              	j_gridText(houseGridComboGrid({width:100})),
		              	//猪只状态
		              	j_gridText(pigClassGridComboGrid({width:100})),
		              	j_gridText({field:'leftTeatNum',width:100,title:'左乳头'}),
		              	j_gridText({field:'rightTeatNum',width:100,title:'右乳头'}),
		              	j_gridText({field:'parity',width:100,title:'胎次',min:0,max:12}),
		              	j_gridText({field:'birthDate',width:100,title:'出生日期',width:80}),
		              	j_gridText({field:'birthWeight',
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.birthWeight = value;
		              			return value+'KG';
			              	},
		              		width:100,title:'出生体重'}),
		              	j_gridText({field:'enterWeight',
		              		precision:2,
		              		increment:0.1,
		              		min:1,
		              		max:500,
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.enterWeight = value;
		              			return value+'KG';
			              	},
		              		width:100,title:'入场体重'}),
		              	//供应商
		              	j_gridText({field:'supplierIdName',title:'供应商',width:100}),
		              	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'}),
		              	j_gridText({field:'leaveDate',title:'销售离场日期',hidden:true})
				    ]],
		    onEndEdit:function(index,row){
	            var material = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'materialId'
	            });
	            if(material != null){
	            	row.materialIdName = $(material.target).combogrid('getText');
	            }
	            var pigHouse = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	            if(pigHouse != null){
	            	row.houseIdName = $(pigHouse.target).combogrid('getText');
	            }
	            var line = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'lineId'
	            });
	            if(line != null){
	            	row.lineIdName = $(line.target).combogrid('getText');
	            }
	            var pigClass = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'pigClass'
	            });
	            if(pigClass != null){
	            	row.pigClassName = $(pigClass.target).combogrid('getText');
	            }
	            var supplier = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'supplierId'
	            });
	            if(supplier != null){
	            	row.supplierIdName = $(supplier.target).combogrid('getText');
	            }
	        },
	        onBeginEdit:function(index,row){
	        	var house = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	        	if(house != null){
	        		var houseGrid = $(house.target).combogrid('grid');
        			if(lineUseFlag == 1){
		        		var lineId = row.lineId == null || row.lineId == undefined || row.lineId == ''? 0 : row.lineId;
		        		houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId='+ lineId+'&eventName='+eventName);
		        	}else{
		        		houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName);
		        	}
	        	}
	        	var material = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'materialId'
	            });
	        	if(material != null){
	        		var materialGrid = $(material.target).combogrid('grid');
	        		materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+oldPigType);
	        	}
	        	var pigClass = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'pigClass'
	            });
	        	if(pigClass != null){
	        		var pigClassGrid = $(pigClass.target).combogrid('grid');
	        		pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchSeedPigClassToList.do?pigType='+oldPigType+'&eventName='+eventName);
	        	}
			}
		})
	);
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'earBrand,earShort,materialIdName,onPrice,fatherEar,motherEar,lineIdName,houseIdName,pigClassName,leftTeatNum,rightTeatNum,parity,birthDate,birthWeight,enterWeight,supplierIdName,notes',
				columnTitles:'耳牌号,耳缺号,物料名称,初始成本,父亲耳号,母亲耳号,产线,猪舍名称,猪只状态,左乳头,右乳头,胎次,出生日期,出生体重(KG),入场体重(KG),供应商,备注',
				hiddenFields:'fatherEar,motherEar,lineIdName,supplierIdName,notes',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100,90,110,110,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
}

function earPorkMoveIn(){
	//猪只清单
	$('#total-plan').css('visibility','visible');

	$('#listTable').datagrid(
			j_detailGrid({
				height:'100%',
			dbClickEdit:true,
			//fitColumns:true,
			frozenColumns:[[
			                {field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({field:'preHouseId',title:'原场猪舍Id',hidden:true}),
			              	j_gridText({field:'preSwineryId',title:'原场批次Id',hidden:true}),
			              	j_gridText({field:'birthDate',title:'出生日期',hidden:true}),
			              	j_gridText({field:'materialId',title:'物料Id',hidden:true}),
			              	j_gridText({field:'preLineNumber',title:'行号',hidden:true})
			              	]],
			columns:[[
			          	j_gridText({field:'preHouseName',title:'原场猪舍',width:80}),
			          	j_gridText({field:'preSwineryName',title:'原场批次',width:80}),
			          	//物料
		              	j_gridText({field:'materialIdName',title:'物料名称',width:80}),

		              	//猪舍
		              	j_gridText(houseGridComboGrid({
		              		width:100,
	              		})),
	              		//批次
	              		j_gridText(swineryGridComboGrid({
		              		width:150,
	              		})),
		              	//猪只状态
		              	j_gridText(pigClassGridComboGrid({width:100})),
		              	//数量
		              	j_gridText({field:'totalNum',title:'数量',width:80,min:0}),
		              	j_gridText({field:'dayAge',width:100,title:'日龄',width:80}),
		              	
		              	j_gridText({field:'averagePrice',title:'总价',width:100,
		              		formatter:function(value,row){
	              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              		return value+'元';
		              	}}),
		              	
		              	j_gridText({field:'averageWeight',
		              		formatter:function(value,row){
		              			value = value == null || value == undefined || value == '' ? 0.00 : value;
		              			row.enterWeight = value;
		              			return value+'KG';
			              	},
		              		width:100,title:'总重'}),
		              	//供应商
		              	j_gridText(supplierGridComboGrid({width:100,disabled:true})),
		              	j_gridText({field:'notes',title:'备注',width:100,editor:'textbox'}),
		              	j_gridText({field:'leaveDate',title:'销售离场日期',hidden:true})
				    ]],
		    onEndEdit:function(index,row){
	            var material = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'materialId'
	            });
	            if(material != null){
	            	row.materialIdName = $(material.target).combogrid('getText');
	            }
	            var pigHouse = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	            if(pigHouse != null){
	            	row.houseIdName = $(pigHouse.target).combogrid('getText');
	            }
	            var swinery = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'swineryId'
	            });
	            if(swinery != null){
	            	row.swineryIdName = $(swinery.target).combogrid('getText');

//	            	returnTarName({
//	            		tarType:'grid',
//	            		tarName:'swineryIdName',
//	            		tarFiled:'swineryId',
//	            		target:swinery.target
//	            	},row)
	            }
	            var pigClass = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'pigClass'
	            });
	            if(pigClass != null){
	            	row.pigClassName = $(pigClass.target).combogrid('getText');
	            }
	            var supplier = $(this).datagrid('getEditor', {
	                index: index,
	                field: 'supplierId'
	            });
	            if(supplier != null){
	            	row.supplierIdName = $(supplier.target).combogrid('getText');
	            }
	        },
	        onBeginEdit:function(index,row){
	        	var house = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	        	if(house != null){
	        		var houseGrid = $(house.target).combogrid('grid');
        			if(lineUseFlag == 1){
		        		var lineId = row.lineId == null || row.lineId == undefined || row.lineId == ''? 0 : row.lineId;
		        		houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId='+ lineId+'&eventName='+eventName);
		        	}else{
		        		houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName);
		        	}
	        	}
		    	var swinery = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'swineryId'
			    });
				if(swinery != null){
					var swineryGrid = $(swinery.target).combogrid('grid');
					swineryGrid.datagrid('reload',basicUrl+'/production/searchSwineryToList.do?lineId=0&pigType=3&eventName='+eventName);
				}
	        	var material = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'materialId'
	            });
	        	if(material != null){
	        		var materialGrid = $(material.target).combogrid('grid');
	        		materialGrid.datagrid('reload',basicUrl+'/material/searchMaterialToList.do?pigType='+oldPigType);
	        	}
	        	var pigClass = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'pigClass'
	            });
	        	if(pigClass != null){
	        		var pigClassGrid = $(pigClass.target).combogrid('grid');
	        		pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName+"&enterType"+oldEnterType);
	        	}
			}
		})
	);
	columnFields:'earBrand,earShort,materialIdName,onPrice,fatherEar,motherEar,lineIdName,houseIdName,pigClassName,leftTeatNum,rightTeatNum,parity,birthDate,birthWeight,enterWeight,supplierIdName,notes',

	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'preHouseName,preSwineryName,materialIdName,houseIdName,swineryIdName,pigClassName,totalNum,dayAge,averagePrice,averageWeight,supplierIdName,notes',
				columnTitles:'原场猪舍,原场批次,物料名称,猪舍名称,批次名称,猪只状态,数量,日龄,总价,总重,供应商,备注',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100',
				hiddenFields:'notes',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
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
	$('#editWin').window('open');
	$('#notBillInputForm').form('reset');
	$('#btnSave').css('display','inline-block');
}