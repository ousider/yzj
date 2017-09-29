var title = '销售结算项目维护';
var editIndex = undefined;
var buttonList = getButtonByLimit({});
var prUrl='/basicInfo/';
var searchMainListUrl=prUrl+'searchSaleItemToPage.do';
var searchDetailListUrl=prUrl+'searchSaleItemDetailToList.do';
var saveUrl=prUrl+'editSaleItem.do';


$(document).ready(function(){
	/**
	 * 主表加载
	 */

	$('#table').datagrid(
			j_grid({
				toolbar:'#tableToolbarList',
				url:searchMainListUrl,
				columnFields:'rowId,deletedFlag,customerName,notes',
				columnTitles:'ID,deletedFlag,客户名称,备注',
				hiddenFields:'rowId,deletedFlag'
			})
	);
	xnCdCombobox({
	    id:'customerId',
	    typeCode:'COMPANY_NAME',
	    disabled:true
	});
	//延时加载细表
	setTimeout(function () { 
		/**
		 * 细表加载
		 */
		$('#listTable').datagrid(
				j_detailGrid({
				dbClickEdit:true,
				columns:[[
			              	{field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({field:'itemType',title:'项目类别',						
								formatter:function(value,row){
									return row.itemTypeName;
								},
			              		editor:
								xnGridCdComboBox({
									field:'itemType',
									typeCode:'ITEM_TYPE',
			              			editable:false
								})}),
			              	j_gridText({field:'itemStage',title:'项目阶段',
								formatter:function(value,row){
									return row.itemStageName;
								},
			              		editor:
								xnGridCdComboBox({
									field:'itemStage',
									typeCode:'ITEM_STAGE',
			              			editable:false
								})}),
			              	j_gridText({field:'itemName',title:'项目名称',editor:'textbox'}),
			              	j_gridText({field:'rewardPunishStandard',title:'奖惩标准',editor:'textbox'}),
			              	j_gridText({field:'codeName',title:'备注',editor:'textbox'}),

					    ]],
			    onEndEdit:function(index,row){
			    	var itemType = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'itemType'
		            });
		            if(itemType != null){
		            	row.itemTypeName = $(itemType.target).combogrid('getText');
		            };
			    	var itemStage = $('#listTable').datagrid('getEditor', {
		                index: index,
		                field: 'itemStage'
		            });
		            if(itemStage != null){
		            	row.itemStageName = $(itemStage.target).combogrid('getText');
		            }
			    },
				onBeginEdit:function(index,row){
				}
			})
		);
    },500);
});

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
		$('#btnSave').css('display','none');
		var listTable = document.getElementById('listTable');
		if(listTable!=null){
		    var rowId=record[0].rowId;
		    $('#listTable').datagrid('load',basicUrl+searchDetailListUrl+'?mainId='+rowId);
		}
	}
}

