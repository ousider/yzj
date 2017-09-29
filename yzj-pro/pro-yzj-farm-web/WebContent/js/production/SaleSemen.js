var prUrl='/production/';
var saveUrl=prUrl+'editSaleSemenList.do';
//选择精液搜索
var selectSpermSearchUrl=prUrl+'selectSpermSearchToPage.do';
var searchSpermToSaleToListUrl=prUrl+'searchSpermToSaleToList.do';
//var searchSpermToSalePageUrl=prUrl+'searchSpermToSalePage.do';
var editIndex = undefined;
var eventName = 'SALE_SEMEN';
var oldPigType = '';
$(document).ready(function(){
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			columns:[[
			          	{field:'ck',checkbox:true},
		              	j_gridText({field:'semenId',title:'ID',hidden:true}),
		              	j_gridText({field:'boarId',title:'猪只Id',hidden:true}),
		              	j_gridText({field:'houseId',title:'猪舍Id',hidden:true}),
		              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
		              	j_gridText({field:'semenDate',title:'采精日期',hidden:true}),
		              	//精液批号
		              	j_gridText(earBrandTextBox({field:'semenBatchNo',textField:'semenBatchNo',valueField:'semenBatchNo',title:'精液批号',width:100})),
		              	//增加公猪耳号、品种-日龄两个字段
		              	j_gridText({field:'earBrand',title:'公猪耳号',width:80}),
		              	j_gridText({field:'breedAndDayAge',title:'品种-日龄',width:80}),
		              	j_gridText({field:'semenNum',title:'库存数量',width:80}),
		              	j_gridText({field:'validDate',title:'有效日期',width:80}),
		              	j_gridText({field:'warehouseId',title:'仓库',
		              		formatter:function(value,row){
		              			return row.warehouseName;
		              		},width:100}),
	              		j_gridText({field:'saleNum',title:'销售数量',editor:{
	        				type:'numberbox',
	        				options:{
	        					editable:false,
	        					icons: [{
		    						iconCls:'icon-search',
		    						handler: function(e){
		    							onShowSpermSelect();
		    						}
		        				}]
	        				}
	              		},width:80,min:0}),
		              	j_gridText({field:'saleDate',title:'销售日期',width:100,
		              		editor:{
		              			type:'datebox',
		              			options:{editable:false}
		              		}
		              	}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
				    /*onEndEdit:function(index,row){
				    	var warehouse = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'warehouseId'
			            });
			            if(warehouse != null){
			            	returnTarName({
			            		tarType:'grid',
			            		tarName:'warehouseName',
			            		tarFiled:'warehouseId',
			            		target:warehouse.target
			            	},row)
			            }
				    }*/
				    /*onBeginEdit:function(index,row){
				    	var warehouse = $('#listTable').datagrid('getEditor', {
			                index: index,
			                field: 'warehouseId'
			            });
						if(warehouse != null){
							var warehouseGrid = $(warehouse.target).combogrid('grid');
							warehouseGrid.datagrid('load',basicUrl+'/supplyChian/searchWarehouseToList.do');
						}
			        }*/
			})
	);
	//客户
	customerComboGrid({required:true});
	
	//精液管理公猪品种下拉加载
	xnCombobox({
		id:'breedName',
		valueField:'rowId',
		textField:'breedName',
		url:'/backEnd/searchBreedToList.do',
		hasAll:true
	});
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'pigId,houseId,earBrand,breedAndDayAge,semenBatchNo,semenNum,validDate,warehouseName,saleNum,saleDate,workerName,notes',
				columnTitles:'猪只ID,猪舍Id,公猪耳号,品种-日龄,精液批号,库存数量,有效日期,仓库,销售数量,销售日期,技术员,备注',
				hiddenFields:'pigId,houseId,notes,workerName',
				columnWidths:'100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				width:'auto',
				height:'100%',
				pagination:false
			},'preSaveRecordTable')
	);
	
	//选择精液
	$('#chooseSpermTable').datagrid(
			j_grid({
				width:'auto',
				height:'auto',
				columnFields:'rowId,earBrand,breedAndDayAge,semenBatchNo,numberOfSales,semenDate,validDate',
				columnTitles:'rowId,耳牌号,品种-日龄,精液批号,可销售份数,采精日期,失效日期',
				hiddenFields:'rowId',
				onLoadSuccess:function(data){
					$('#selectSpermSure').attr('disabled',false).removeClass('btn-disabled');
				}
				//columnWidths:'5,5,5,5,5,5,5',
				//fit:false,
				//pagination:false
		},'chooseSpermTable')
	);
	
	$('#spermTable').datagrid(
			j_grid_view({
				width:'auto',
				height:'auto',
				columnFields:'spermBatchNo',
				columnTitles:'精液编号',
				hiddenFields:'',
				fit:false,
				pagination:false,
				onLoadSuccess : function(data) {
					if (!data.success) {
						$.messager.alert({
							title : '错误',
							msg : data.errorMsg
						});
					}else{
						var tableRow = $('#listTable').datagrid('getRows')[editIndex];
						var row = data.rows;
						var spermList = tableRow.spermList;
						if(spermList != null){
							for(var i=0;i<row.length;i++){
								var eachSpermId = row[i].spermId;
								for(var j=0;j<spermList.length;j++){
									var spermId = spermList[j].spermId;
									if(eachSpermId == spermId){
										$('#spermTable').datagrid('checkRow',i);
										break;
									}
								}
							}
						}
					}
				}
			},'spermTable')
		)
	
});
/***************************************页面渲染************************************************************/
function chooseSperm(){
	var selectRows = $('#listTable').datagrid('getRows');
	leftSilpFun('chooseSpermWin',true);
	if(selectRows.length<1){
		//选择精液的加载方法
		 $('#chooseSpermTable').datagrid({
	    	url:basicUrl+'/production/selectSpermSearchToPage.do'
	    });
	}
	else{
		var rowIds = '';
 		$.each(selectRows,function(i,data){
			if(i == selectRows.length - 1){
				rowIds += data.rowId;
 			}else{
 				rowIds += data.rowId + ',';
 			}
 		});
		$('#chooseSpermTable').datagrid("load", basicUrl + selectSpermSearchUrl+"?rowIds="+rowIds);
}
}
/**
 * 选择精液搜索
 */
function chooseSpermSearch(){
	var earBrand =  $('#earBrand').textbox('getValue');
	var breedName = $('#breedName').combogrid('getValue');
	var semenBatchNo =  $('#semenBatchNo').textbox('getValue');
	var semenDate = $('#semenDate').datebox('getValue');
	var validDate = $('#validDate').datebox('getValue');
	var hasSelectRows = $('#listTable').datagrid('getData').rows;
	var rowIds = '';
		$.each(hasSelectRows,function(i,data){
		if(i == hasSelectRows.length - 1){
			rowIds += data.rowId;
			}else{
				rowIds += data.rowId + ',';
			}
		});
	$('#chooseSpermTable').datagrid("load", basicUrl + selectSpermSearchUrl+"?rowIds="+rowIds+'&earBrand='+earBrand
			+'&breedName='+breedName+'&semenBatchNo='+semenBatchNo+'&semenDate='+semenDate+'&validDate='+validDate);
	/*$('#chooseSpermTable').datagrid('load',{
    	earBrand:earBrand,
    	breedName:breedName,
    	semenBatchNo:semenBatchNo,
    	semenDate:semenDate,
    	validDate:validDate,
    	rowIds_:rowIds
    });*/
}

	//选择精液确定
 function chooseSpermSure(){
 	var selectRows = $('#chooseSpermTable').datagrid('getSelections');
 	if(selectRows.length < 1){
 		$.messager.alert('警告', '请选择一条以上的数据！');
 	}else{
 		$('#selectSpermSure').attr('disabled',true).addClass('btn-disabled');
 		$.each(selectRows,function(index,data){
 			var row = new Object(); 
 			if(typeof(detailDefaultValues) == "undefined"){
 				row ={
 					status:'1',
 					deletedFlag:'0'	
 				};
 			}else{
 				for(var p in detailDefaultValues) { 
 					var name=p;//属性名称 
 					var value=detailDefaultValues[p];//属性对应的值 
 					row[name]=detailDefaultValues[p]; 
 				} 
 			}
 			row.semenId = data.rowId;
 			row.rowId = data.rowId;
 			row.earBrand = data.earBrand;
			row.breedAndDayAge = data.breedAndDayAge;
			row.semenBatchNo = data.semenBatchNo;
			row.numberOfSales = data.numberOfSales,
			row.semenDate = data.semenDate;
			row.validDate = data.validDate;
			row.semenNum = data.semenNum;
			row.validDate = data.validDate;
			row.warehouseId = data.warehouseId;
			row.warehouseName = data.warehouseName;
			row.houseId=data.houseId;
			row.boarId=data.pigId;
			row.pigId=data.pigId;
 			$('#listTable').datagrid('appendRow',row);
 			
 		});
 		var hasSelectRows = $('#listTable').datagrid('getData').rows;
 		var rowIdsArray = [];
 		$.each(hasSelectRows,function(i,data){
 			if(data.rowId != null && data.rowId != ''){
 				rowIdsArray.push(data.rowId);
 			}
 		});
 		var rowIds = '';
 		$.each(rowIdsArray,function(i,data){
 			if(i == rowIdsArray.length - 1){
 				rowIds += data;
 			}else{
 				rowIds += data + ',';
 			}
 		});
 		var earBrand =  $('#earBrand').textbox('getValue');
		var breedName = $('#breedName').combogrid('getValue');
		var semenBatchNo =  $('#semenBatchNo').textbox('getValue');
		var semenDate = $('#semenDate').datebox('getValue');
		var validDate = $('#validDate').datebox('getValue');
		$('#chooseSpermTable').datagrid("load", basicUrl + selectSpermSearchUrl+"?rowIds="+rowIds+'&earBrand='+earBrand
				+'&breedName='+breedName+'&semenBatchNo='+semenBatchNo+'&semenDate='+semenDate+'&validDate='+validDate);
 		/*$('#chooseSpermTable').datagrid('load',{
         	earBrand:earBrand,
			breedName:breedName,
			semenBatchNo:semenBatchNo,
			semenDate:semenDate,
			validDate:validDate,
			rowIds_:rowIds
         });*/
 	}
 }
	 //重置
 function onBtnReSearch(){
		$('#chooseSpermForm').form('reset');
		var hasSelectRows = $('#listTable').datagrid('getData').rows;
		var rowIdsArray = [];
		$.each(hasSelectRows,function(i,data){
			if(data.rowId != null && data.pigId != ''){
				rowIdsArray.push(data.rowId);
			}
		});
		var rowIds = '';
		$.each(rowIdsArray,function(i,data){
			if(i == rowIdsArray.length - 1){
				rowIds += data;
			}else{
				rowIds += data + ',';
			}
		});
		$('#chooseSpermTable').datagrid("load", basicUrl + selectSpermSearchUrl+"?rowIds="+rowIds);
//		$('#chooseSpermTable').datagrid('load',{
//	    	queryType:2,
//	    	//pigType:oldPigType,
//	    	specifyPigs:0,
//	    	choice:1,
//	    	rowIds:rowIds
//	    });
	}

//取消
function onBtnCancel(){
	var parentDocument = $(window.parent.document);
	var tabNum = parseInt((parentDocument.find('#header')[0].clientWidth - 175 - 74 -20)/130);
	var liLength = parentDocument.find('.tabList').find('li').length;
	var cancelEventName = 'SaleSemen';
	var tabList = parentDocument.find('.tabList').find('li');
	var hideTabList = parentDocument.find('#hideTabList').find('li');
	$.each(tabList,function(index,data){
		if(data.dataset.eventname == cancelEventName){
			if(data.className == 'listSelected'){
				//如果只有一个tab，则显示首页
				if(tabList.length == 1){
					parentDocument.find('#tabContent_homePage').css('display','block');
					parentDocument.find('.hompageBorder')[0].className = 'hompageBorder fl listSelected';
				}else{
					if(parentDocument.find('#tabContent_Sperm').length > 0){
						parentDocument.find('li[data-eventname=Sperm]').addClass('listSelected');
						parentDocument.find('#tabContent_Sperm').css('display','block');
					}else{
						//如果删除的是当前选中的tab
						//当前选中的tab是最后一个
						for(var i = tabList.length - 1 ; i >= 0 ; i --){
							if(tabList[i].dataset.eventname != cancelEventName && tabList[i].style.display != 'none'){
								tabList[i].className = 'listSelected';
								parentDocument.find('#tabContent_'+tabList[i].dataset.eventname).css('display','block');
								break;
							}
						}
					}
				}
			}
			parentDocument.find('li[data-eventname=SaleSemen]').remove();
			parentDocument.find('#tabContent_SaleSemen').remove();
		}
	});
	
	if(liLength > tabNum){
		for(var i in tabList){
			if(tabList[i].style.display == 'none'){
				tabList[i].style.display = 'inline-block';
				$.each(hideTabList,function(index,hideTab){
					if(hideTab.dataset.eventname == tabList[i].dataset.eventname){
						$(hideTab).remove();
					}
				})
				break;
			}
		}
		hideTabNum --;
		$('#hideTabNum').html(hideTabNum);
	}
	if(liLength - 1 == tabNum){
		$('#tabMore').css('display','none');
		$('#hideTabList').css('display','none');
	}
	
}

function loadListTableFromSperm(mainIds){
	$('#listTable').datagrid("load", basicUrl + searchSpermToSaleToListUrl+"?mainIds="+mainIds);
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
				var iframeSperm = parent.document.getElementById("iframe_Sperm");
				if(iframeSperm){
					var spermContentWindow = iframeSperm.contentWindow;
					spermContentWindow.tableReload();
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

function onShowSpermSelect(){
	$('#selectedIndex').val(editIndex);
	var tableRow = $('#listTable').datagrid('getRows')[editIndex];
	$('#spermTable').datagrid('load',basicUrl + prUrl + 'selectUsableSpermToList.do?semenId='+tableRow.semenId);
	leftSilpFun('spermSelect',true);
}
function onBtnSelectSperm(){
	var tableRow = $('#listTable').datagrid('getRows')[$('#selectedIndex').val()];
	var spermList = $('#spermTable').datagrid('getChecked');
	tableRow.spermList = spermList;

	var saleNumEditor = $('#listTable').datagrid('getEditor', {
        index: $('#selectedIndex').val(),
        field: 'saleNum'
    });
	if(saleNumEditor != null){
		$(saleNumEditor.target).numberbox('setValue',spermList.length);
	}
	rightSlipFun('spermSelect',390,true)
}
