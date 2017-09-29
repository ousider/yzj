var title = '供应商';
var editIndex = undefined;
var prUrl='/basicInfo/';
var saveUrl=prUrl+'editCustomerAndSupplier.do';
var deleteUrl=prUrl+'deleteCustomesrAndSuppliers.do?cussupType=S';
var searchMainListUrl=prUrl+'searchGroupCustomerAndSupplierToPage.do';
var editCustomerAndSupplierToFarmsUrl=prUrl+'editCustomerAndSupplierToFarms.do'
/** *********页面渲染开始*************************************************************************/
//查询按钮
var PROVIDER_flag = jAjax.submit('/param/getSettingValueByCode.do?settingCode=SC_GROUP_PROVIDER');
var buttonList = getButtonByLimit({});
var detailDefaultValues = {
		status:'1',
		deletedFlag:'0',
		//设置默认值
		expiryType:'1',
		expiryTypeName:'有效期'
};

$(document).ready(function(){
	/**
	 * 主表加载
	 */
	$('#table').datagrid(
			j_grid({
//				toolbarList:buttonList,
				toolbar:'#tableToolbar',
				url:searchMainListUrl,
				queryParams: {
					cussupType: 'S'
				},
				columnFields:'rowId,deletedFlag,companyCode,createTypeName,companyName,sortName,catalog,paymnent,creditLimit,createDate,createrName,changeHistory,notes,alreadyUpdateFarmCount,requestUpdateFarm',
				columnTitles:'ID,deletedFlag,供应商编号,创建类型,供应商名称,供应商简称,采购目录,付款条件,信用额度,创建时间,创建人,变更历史,备注,同步数量,需要同步的猪场',
				hiddenFields:'rowId,deletedFlag',
				columnWidths:'100,100,100,100,250,100,100,100,100,100,100,100,100,80,120',
				rightColumnFields:'listQty',
				formatterFields:'changeHistory,catalog,requestUpdateFarm',
				formatter:[linkChangeHistory,linkCatalog,linkRequestUpdateFarm],
				onLoadSuccess : function(data) {
					if (!data.success) {
						$.messager.alert({
							title : '错误',
							msg : data.errorMsg
						});
					}else{
						selectAndAllCount(data.rows, $('#farmTable').datagrid('getRows'));
					}
				}
			})
	);
	setTimeout(function(){
		//创建类型
		xnCdCombobox({
			id:'createType',
			editable:false,
			required:true,
			typeCode:'CREATE_SUP_TYPE',
			excludeValue:[2],
			onChange:createTypeChange
		});
		//供应商分类
		xnCdCombobox({
			id:'supplierType',
			editable:false,
			required:true,
			typeCode:'CREATE_SUP_CLASS'
		});
		//业务伙伴类型
		xnCdCombobox({
			id:'businessPartnerType',
			editable:false,
			required:true,
			typeCode:'BUSINESS_PARTNER_TYPE'
		});
		// 初始使用猪场
		xnCombobox({
			id:"initialFarmIds",
			url:'/basicInfo/searchGroupCompanyByFarmIdToList.do',
			valueField:"farmId",
			textField:"farmSortName",
			editable:false,
			multiple:true,
			prompt:'初始使用猪场'
		});
		//交易币种
		xnCdCombobox({
			id:'tradeCurrency',
			editable:false,
			required:true,
			typeCode:'moneyType'
		});
		//付款条件
		xnCdCombobox({
			id:'payCondition',
			editable:false,
			required:true,
			typeCode:'TERM_OF_PAYMENT'
		});			
		//付款账期
		xnCdCombobox({
			id:'payDays',
			editable:false,
			required:true,
			typeCode:'PAY_DATE_LIST'
		});
		//国家
		xnCdCombobox({
			id:'country',
			editable:false,
			required:true,
			typeCode:'COUNTRY_TYPE',
			onChange:createCountryChange
		});			
				
		//创建类型
		xnCdCombobox({
			id:'createTypeSearch',
			editable:false,
			typeCode:'CREATE_SUP_TYPE',
			hasAll:true
		});
		//供应商全称
		$('#cussupId').textbox({
			prompt:'请输入供应商全称',
			required:true
		});
		//省份
		xnCdCombobox({
		    id:'province',
		    typeCode:'PROVINCE',
		    panelHeight:300,
		    linkField:'city',
		    linkCode:'CITY'
		});
		//市
		xnCdCombobox({
		    id:'city',
		    panelHeight:300,
		    typeCode:'CITY',
		    linkField:'county',
		    linkCode:'COUNTY'
		});
		//县
		xnCdCombobox({
		    id:'county',
		    panelHeight:300,
		    typeCode:'COUNTY'
		});
		$('#showChangeHistory').datagrid(
				j_grid_view({
					haveCheckBox:false,
					columnFields:'paperName,cussupId,changeWorker,changeName,changeDate,changeContent,paperCode,notes,version',
					columnTitles:'证件类型,ID,变更人,变更人名,变更时间,变更内容,附件变更,备注,版本号',
					hiddenFields:'cussupId,changeWorker',
					columnWidths:'100,100,100,100,100,200,200,200,100',
					fit:false,
					height:'92%',
					width:'100%',
					formatterFields:'paperCode',
					formatter:[linkPaperCode]
				},'showChangeHistory')
		);		
		$('#paperListTable').edatagrid(
			j_editableTable({
				toolbar:'#paperListTableToolbar',
				columns:[[
				          	{field:'ck',checkbox:true},
							j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'paperType',title:'证照类型',width:150,
			              		formatter:function(value,row){
			              			return row.paperTypeName;
			              		},
			              		editor:
			              		xnGridCdComboBox({
			              			listTableId:'paperListTable',
			              			field:'paperType',
			              			typeCode:'LICENSE_TYPE',
			              			editable:false
			              		})
			              	}),
			              	j_gridText({field:'paperCode',title:'证照编码',width:150,editor:'textbox'}),
			              	j_gridText({
			              		field:'expiryType',
			              		title:'有效期类型',
			              		width:150,
			              		formatter:function(value,row){
			              			return row.expiryTypeName;
			              		},
			              		editor:
				              	xnGridCdComboBox({
				              		listTableId:'paperListTable',
			              			field:'expiryType',
			              			typeCode:'EXPIRY_TYPE',
			              			editable:false,
			              			onChange:function(newValue,oldValue){
			              				var index = $('#paperListTable').edatagrid('getEditIndex');
		              					var expiryDate = $('#paperListTable').datagrid('getEditor', {
		    				                index: index,
		    				                field: 'expiryDate'
		    				            });
		    				            if(expiryDate != null){
		    				            	// 长期
				              				if(newValue == '2'){
				              					$(expiryDate.target).datebox('setValue','');
		    				            		$(expiryDate.target).datebox('disable');
				              				}else{
		    				            		$(expiryDate.target).datebox('enable');
				              				}
		    				            }
			              			}
				              	})
			              	}),
			              	j_gridText({field:'expiryDate',title:'有效期',width:150,editor:{
			              		type:'datebox',
			              		options:{
			              			editable:false
			              		}
			              	}}),
			              	j_gridText({field:'file',title:'上传附件',width:150,formatter:function(value,row,index){
								if(value){
									return value+'&nbsp;&nbsp;<a class="editcls" onclick="reUpLoad('+index+')" href="javascript:void(0)">重新上传</a>';
								}else{
									return '<input type="file" name="paperFile" onChange="paperFileOnChange('+index+',this)" accept="image/jpg,image/jpeg,image/png" multiple="multiple" value="'+value+'"/>'
								}
							}}),
							j_gridText({field:'preview',title:'附件预览',width:40,sortable:false,
								formatter:function(value,row,index){
									return '<a class="editcls" onclick="imgPreviewFun('+index+')" href="javascript:void(0)">附件预览</a>';
								}
							})
					    ]],
					    onBeginEdit:function(index,row){
				            var expiryDate = $('#paperListTable').datagrid('getEditor', {
				                index: index,
				                field: 'expiryDate'
				            });
				            if(expiryDate != null){
				            	if(row.expiryType == '2'){
				            		$(expiryDate.target).datebox('setValue','');
				            		$(expiryDate.target).datebox('disable');
				            	}
				            }
					    },
					    onEndEdit:function(index,row){
					    	 var paperType = $('#paperListTable').datagrid('getEditor', {
				                index: index,
				                field: 'paperType'
				            });
					    	if(paperType != null){
					    		row.paperTypeName = $(paperType.target).combobox('getText');
					    	}
				            var expiryType = $('#paperListTable').datagrid('getEditor', {
				                index: index,
				                field: 'expiryType'
				            });
				            if(expiryType != null){
				            	row.expiryTypeName = $(expiryType.target).combogrid('getText');
				            }
					    }
				})
		);
		$('#accountListTable').edatagrid(
				j_editableTable({
					toolbar:'#accountListTableToolbar',
					columns:[[  
					          	{field:'ck',checkbox:true},
								j_gridText({field:'rowId',title:'ID',hidden:true}),
				              	j_gridText({field:'bankName',title:'开户行名称',width:150,editor:'textbox'}),
				              	j_gridText({field:'bankAccount',title:'银行账号',width:150,editor:'textbox'}),
				              	j_gridText({field:'notes',title:'备注',width:150,editor:'textbox'})
						    ]]
				})	
			);
		$('#linkListTable').edatagrid(
				j_editableTable({
					toolbar:'#linkListTableToolbar',
					columns:[[  
					          	{field:'ck',checkbox:true},
								j_gridText({field:'rowId',title:'ID',hidden:true}),
				              	j_gridText({field:'linkmanName',title:'联系人姓名',width:150,editor:'textbox'}),
				              	j_gridText({field:'mobileNumber',title:'手机号',width:150,editor:'textbox'}),
				              	j_gridText({field:'telNumber',title:'座机号',width:150,editor:'textbox'}),				              	
				              	j_gridText({field:'notes',title:'备注',width:150,editor:'textbox'})
						    ]]
				})	
			);

	},500)
	
	$('#farmTable').datagrid(
			j_grid_view({
				width:'auto',
				height:'auto',
				url:'/basicInfo/searchGroupCompanyByFarmIdToList.do',
				columnFields:'farmSortName',
				columnTitles:'猪场名称',
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
						selectAndAllCount($('#table').datagrid('getRows'), data.rows);
					}
				}
			},'farmTable')
		)
});

/*************页面渲染结束******************************************************************************/

/********************页面特殊方法开始***************************************************************************/
function createTypeChange(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	//清空之前的值
	$('#sortName').textbox('setValue','');
	$('#companyNameEn').textbox('setValue','');
	$('#province').combobox('setValue','');
	$('#country').combobox('setValue','');
	$('#city').combobox('setValue','');
	$('#county').combobox('setValue','');
	$('#companyAddress').textbox('setValue','');
	$('#longitude').numberspinner('setValue','');
	$('#latitude').numberspinner('setValue','');
	
	changeCussupStyle(newValue);
	if(newValue == '1'){
		$('#country').combobox('setValue','1');
		enableCompanyInfo();
	}else{
		disableCompanyInfo();
	}
}

function createCountryChange(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	//清空之前的值
	$('#province').combobox('setValue','');
	$('#city').combobox('setValue','');
	$('#county').combobox('setValue','');
	if(newValue == '1'){
		$('#province').combobox('enable');
		$('#city').combobox('enable');
		$('#county').combobox('enable');					
	}else{
		$('#province').combobox('disable');
		$('#city').combobox('disable');
		$('#county').combobox('disable');	
	}

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
		$('#createType').combobox('readonly',false);
		changeCussupStyle(record[0].createType);
		$('#editWin').window('open');
		$('#editForm').form('reset');
		$('#editForm').form('load',record[0]);
		if(record[0].createType == '1'){
			$('#cussupId').textbox('setValue',record[0].companyName);
		}
		$('#initialFarmIdsDIV').css('display','none');
		$('#btnSave').css('display','none');
		$('#createType').combobox('readonly',true);
		disableCompanyInfo();
		disableCussupId(record[0].createType);
	}
	var paperListTable = document.getElementById('paperListTable');
	var accountListTable = document.getElementById('accountListTable');
	var linkListTable = document.getElementById('linkListTable');
	
	if(paperListTable!=null){
		$('#paperListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryPaper.do?cussupId='+record[0].rowId);
	}
	
	if(accountListTable!=null){
		$('#accountListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryAccount.do?cussupId='+record[0].rowId+'&cussupType=S');
	}
	
	if(linkListTable!=null){
		$('#linkListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryLink.do?cussupId='+record[0].rowId+'&cussupType=S');
	}
}
/**
 * 编辑
 */
function onBtnEdit(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else if(length > 1){
		$.messager.alert('警告','只能选择一条数据！');
	}
//	else if(record[0].createType!=1){
//		$.messager.alert('警告','只可编辑自建供应商！');
//	}
	else{
		$('#editWin').window({
			title:'编辑'+ title
		});
		
		$('#paperListTable').datagrid('loadData',{success:true,total: 0, rows: []});
		$('#accountListTable').datagrid('loadData',{success:true,total: 0, rows: []});
		$('#linkListTable').datagrid('loadData',{success:true,total: 0, rows: []});
		
		changeCussupStyle(record[0].createType);
		
		$('#initialFarmIdsDIV').css('display','none');
		$('#btnSave').css('display','inline-block');
		$('#createType').combobox('readonly',true);
		$('#editForm').form('reset');
		
		if(record[0].createType == '1'){
			enableCompanyInfo();
		}else{
			disableCompanyInfo();
		}
		
		$('#editForm').form('load',record[0]);
		
		if(record[0].createType == '1'){
			$('#cussupId').textbox('setValue',record[0].companyName);
			$('#cussupId').textbox('readonly',false);
		}else{
			$('#cussupId').combobox('readonly',true);
		}
		
		$('#editWin').window('open');
		var paperListTable = document.getElementById('paperListTable');
		var accountListTable = document.getElementById('accountListTable');
		var linkListTable = document.getElementById('linkListTable');
		
		if(paperListTable!=null){
			$('#paperListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryPaper.do?cussupId='+record[0].rowId);
		}
		
		if(accountListTable!=null){
			$('#accountListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryAccount.do?cussupId='+record[0].rowId+'&cussupType=S');
		}
		
		if(linkListTable!=null){
			$('#linkListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryLink.do?cussupId='+record[0].rowId+'&cussupType=S');
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
	
	$('#editWin').window({
		title:'查看'+ title
	});
	$('#createType').combobox('readonly',false);
	changeCussupStyle(row.createType);
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#editForm').form('load',row);
	if(row.createType == '1'){
		$('#cussupId').textbox('setValue',row.companyName);
	}
	$('#btnSave').css('display','none');
	$('#createType').combobox('readonly',true);
	disableCompanyInfo();
	disableCussupId(row.createType);
	var paperListTable = document.getElementById('paperListTable');
	var accountListTable = document.getElementById('accountListTable');
	var linkListTable = document.getElementById('linkListTable');
	
	if(paperListTable!=null){
		$('#paperListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryPaper.do?cussupId='+row.rowId);
	}
	
	if(accountListTable!=null){
		$('#accountListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryAccount.do?cussupId='+row.rowId+'&cussupType=S');
	}
	
	if(linkListTable!=null){
		$('#linkListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryLink.do?cussupId='+row.rowId+'&cussupType=S');
	}
}
/**
 * 删除
 */
function onBtnDelete(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		var deleteList = [];
		$.each(record,function(index,data){
			deleteList.push({
				id:data.rowId,
				createType:data.createType
			});
		});
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				var str= JSON.stringify(deleteList);
				jAjax.submit(deleteUrl, {'deleteList':str}, 
					 function(){
							$.messager.alert('警告','删除成功！');
				    		$('#table').datagrid('reload');
						},null,null,true);
			}
		});
		
	}
}

/**
 * 新增
 */
function onBtnAdd(){
	$('#editWin').window({
		title:'新增'+ title
	});
	
	$('#editForm').form('reset');
	$('#initialFarmIdsDIV').css('display','block');
	$('#btnSave').css('display','inline-block');
	
	$('#createType').combobox('readonly',false);
	enableCompanyInfo();
	enableCussupId("1");
	changeCussupStyle("1");
	$('#createType').combobox('setValue','1');
	$('#country').combobox('setValue','1')	
	$('#editWin').window('open');
	$('#paperListTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#accountListTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#linkListTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	if(PROVIDER_flag == "ON"){
		$('#supplierType').combobox({required:true});
		$('#businessPartnerType').combobox({required:true});
		$('#payCondition').combobox({required:true});
		$('#payDays').combobox({required:true});
		$('#creditLimit').textbox({required:true});
	}else{
		$('#supplierType').combobox({required:false});
		$('#businessPartnerType').combobox({required:false});
		$('#payCondition').combobox({required:false});
		$('#payDays').combobox({required:false});
		$('#creditLimit').textbox({required:false});
	}
}

/**
 * 根据CREATE_TYPE创建不同的cussupId
 */
function changeCussupStyle(createType){
	$('#paperListTable').datagrid('loadData',{success:true,total: 0, rows: []});
	$('#accountListTable').datagrid('loadData',{success:true,total: 0, rows: []});
	$('#linkListTable').datagrid('loadData',{success:true,total: 0, rows: []});
	
	$('#cussupId').textbox('destroy');
	$('#companyNameDiv').append('<input id="cussupId" name="cussupId" style="width:100%;height:35px">');
	if(createType == '1'){
		$('#cussupId').textbox({
			prompt:'请输入供应商全称',
			required:true
		});
	}else{
		xnComboGrid({
			id:'cussupId',
			url:'/basicInfo/searchCusSupForCreate.do?cussupType=S&createType='+createType,
			idField:'rowId',
			textField:'companyName',
			required:true,
			editable:true,
			columns:[[ 	
			    {field:'rowId',title:'ID',width:100,hidden:true},
		        {field:'companyCode',title:'客户编码',width:100},
		        {field:'companyName',title:'客户名称',width:100},
		        {field:'companyNameEn',title:'英文名称',width:100}
		    ]],
		    onSelect:function(index,row){
		    	$('#sortName').textbox('setValue',row.sortName);
		    	$('#companyNameEn').textbox('setValue',row.companyNameEn);
		    	$('#country').combobox('setValue',row.country);		    	
		    	$('#province').combobox('setValue',row.province);
		    	$('#city').combobox('setValue',row.city);
		    	$('#county').combobox('setValue',row.county);
		    	$('#companyAddress').textbox('setValue',row.companyAddress);
		    	$('#longitude').numberspinner('setValue',row.longitude);
		    	$('#latitude').numberspinner('setValue',row.latitude);
		    },
		    onChange:function(newValue,oldValue){
		    	$('#paperListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryPaper.do?cussupId='+newValue);
		    	$('#accountListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryAccount.do?cussupId='+newValue+'&cussupType=S');
		    	$('#linkListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryLink.do?cussupId='+newValue+'&cussupType=S');
		    }
		});
		addFocus('cussupId','grid');
	}
}

function disableCompanyInfo(){
	$('#sortName').textbox('disable');
	$('#companyNameEn').textbox('disable');
	$('#province').combobox('disable');
	$('#country').combobox('disable');
	$('#city').combobox('disable');
	$('#county').combobox('disable');
	$('#companyAddress').textbox('disable');
	$('#longitude').numberspinner('disable');
	$('#latitude').numberspinner('disable');
	$('#postcode').textbox('disable');
	$('#supplierType').combobox({required:false});
	$('#businessPartnerType').combobox({required:false});
	$('#payCondition').combobox({required:false});
	$('#payDays').combobox({required:false});
	$('#creditLimit').textbox({required:false});
}

function disableCussupId(createType){
	if(createType == '1'){
		$('#cussupId').textbox('readonly',true);
	}else{
		$('#cussupId').combobox('readonly',true);
	}
}

function enableCompanyInfo(){
	$('#sortName').textbox('enable');
	$('#companyNameEn').textbox('enable');
	$('#province').combobox('enable');
	$('#country').combobox('enable');	
	$('#city').combobox('enable');
	$('#county').combobox('enable');
	$('#companyAddress').textbox('enable');
	$('#longitude').numberspinner('enable');
	$('#latitude').numberspinner('enable');
	$('#postcode').textbox('enable');
	if(PROVIDER_flag == "ON"){
		$('#supplierType').combobox({required:true});
		$('#businessPartnerType').combobox({required:true});
		$('#payCondition').combobox({required:true});
		$('#payDays').combobox({required:true});
		$('#creditLimit').textbox({required:true});
	}else{
		$('#supplierType').combobox({required:false});
		$('#businessPartnerType').combobox({required:false});
		$('#payCondition').combobox({required:false});
		$('#payDays').combobox({required:false});
		$('#creditLimit').textbox({required:false});
	}
}

function enableCussupId(createType){
	if(createType == '1'){
		$('#cussupId').textbox('readonly',false);
	}else{
		$('#cussupId').combobox('readonly',false);
	}
}
function paperFileOnChange(index,obj){
	var rows = $('#paperListTable').datagrid('getRows');
	var valueArry = $(obj).val().split("\\"); 
	rows[index].file = valueArry[valueArry.length - 1];
	fileUp(obj,rows[index]);
}
function fileUp(obj,row){
	if(window.FormData){
		if($(obj).context.ownerDocument.activeElement.files){
			var files = $(obj).context.ownerDocument.activeElement.files;
			var allIsFile = true;
			for(var i = 0; i < files.length; i++){
				if(!files[i]){
					allIsFile = false;
				}
			}

			var picSrc = [];
			if(allIsFile){
				for(var i = 0; i < files.length; i++){
					var reader = new FileReader();   
					reader.onload = function(e){  
						picSrc.push(e.target.result);
//						row.picSrc = e.target.result;
					}
					reader.readAsDataURL(files[i]); 
				}
				row.picSrc = picSrc;
				var index = $('#paperListTable').datagrid('getRowIndex',row);
				uploadFile(files,index);

			}else{
				$.messager.alert('错误','上传未知文件！');
				row.picSrc = undefined;
			}
		}
	}else{
		$.messager.alert('错误','当前浏览器不支持此上传方法，请更换浏览器！');
	}
}
function reUpLoad(index){
	var rows = $('#paperListTable').datagrid('getRows');
	rows[index].file = undefined;
	rows[index].picSrc = undefined;
	rows[index].fileNameList = [];
	var eidtIndex = $('#paperListTable').edatagrid('getEditIndex');
	if(eidtIndex == index){
		$('#paperListTable').datagrid('endEdit',index).edatagrid('editRow',index);
	}else{
		$('#paperListTable').datagrid('refreshRow',index);
	}
}
function imgPreviewFun(index){
	var picSrc = $('#paperListTable').datagrid('getRows')[index].picSrc;
	$('#imgPreviewDIV').empty();
	$('#imgPreviewDIV').append('<ul id="imgPreviewUl"></ul>');
	if(picSrc != "undefined" && picSrc != undefined){
		var base_url = $('#base_url').val();
		for(var i = 0; i < picSrc.length ; i++){
			$('#imgPreviewUl').append('<li style="display:none;"><img id="imgPreview'+i+'" class="img-preveiw" alt="附件预览"/></li>'); 
			if(picSrc[i].substring(0,5) == 'data:'){
				document.getElementById('imgPreview'+i).setAttribute("src",picSrc[i]); 
			}else{
				document.getElementById('imgPreview'+i).setAttribute("src",base_url + picSrc[i]); 
			}
		}
	}
	
	$('#imgPreviewUl').viewer({
		inline:true
	});
	$('#imgPreviewPanel').dialog('open');
}

/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	$('#btnSave').attr('disabled',true).addClass('btn-disabled');
	var paperListTable = $('#paperListTable'),
	accountListTable = $('#accountListTable'),
	linkListTable = $('#linkListTable');
	var paperRows =  paperListTable.datagrid('getRows');
	var accountRows =  accountListTable.datagrid('getRows');
	var linkRows =  linkListTable.datagrid('getRows');
	for(var i = 0 ; i < paperRows.length ; i ++){
		paperListTable.datagrid('endEdit',i);
	}
	for(var i = 0 ; i < accountRows.length ; i ++){
		accountListTable.datagrid('endEdit',i);
	}
	for(var i = 0 ; i < linkRows.length ; i ++){
		linkListTable.datagrid('endEdit',i);
	}
	//获取新增行
	var paperInsertRows = paperListTable.datagrid('getRows');
	var paperActualInsertRows = [];
	var accountInsertRows = accountListTable.datagrid('getRows');
	var linkInsertRows = linkListTable.datagrid('getRows');
	//给提交数据加上行号
	var paperFlag1 = false;//统一社会信用、营业执照、组织机构代码证、税务登记证四选一必填
	var paperFlag2 = false;//生产许可证必填
	var otherMessage = "";
	var otherMessage1 = "";
	var otherMessage2 = "";		
	$.each(paperInsertRows,function(index,data){
		paperInsertRows[index].lineNumber = paperListTable.datagrid('getRowIndex',data)+1;
		if(!data.hasOwnProperty('fileNameList') || !data.fileNameList || data.fileNameList.length == 0){
			otherMessage += '第'+paperInsertRows[index].lineNumber+'行没有上传附件！<br>'
		}
		if($.inArray(data.paperType, ['1','2','3','4']) >= 0){
			paperFlag1 = true;
		}
//		if(data.paperType == '5'){
//			paperFlag2 = true;
//		}
		// 拷贝元素（除去picSrc）
		var paperItem = new Object();
		paperItem.status = data.status;
		paperItem.deletedFlag = data.deletedFlag;
		paperItem.lineNumber = data.lineNumber;
		paperItem.paperCode = data.paperCode;
		paperItem.paperType = data.paperType;
		paperItem.expiryType = data.expiryType;
		paperItem.expiryDate = data.expiryDate;
		paperItem.fileNameList = data.fileNameList;
		paperActualInsertRows.push(paperItem);
	});
	if(otherMessage != ""){
		$.messager.alert('前台提示',otherMessage);
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		return;
	}
	var createType = $('#createType').combobox('getValue');
	if(createType == '1'){
		if(PROVIDER_flag == "ON"){
			if($("#businessPartnerType").combobox('getValue') == "1"){		
				if(!paperFlag1){
					$.messager.alert('前台提示','统一社会信用、营业执照、组织机构代码证、税务登记证四选一必填！');
					$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
					return;
				}
			}
		}
	}
	if(createType == '1'){
		if(linkInsertRows.length <= 0){
			$.messager.alert('前台提示','至少填写一行联系方式！');
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');			
			return;					
		}
	}
//	if(!paperFlag2){
//		$.messager.alert('前台提示','生产许可证必填！');
//		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
//		return;
//	}
	
	$.each(accountInsertRows,function(index,data){
		accountInsertRows[index].lineNumber = accountListTable.datagrid('getRowIndex',data)+1;
		if(!data.bankName || data.bankName ==""){
			otherMessage2 += '第'+accountInsertRows[index].lineNumber+'行没有填写开户行名称！<br>'
		}	
		if((!data.bankAccount||data.bankAccount =="")&&(!data.telNumber||data.telNumber =="")){
			otherMessage2 += '第'+accountInsertRows[index].lineNumber+'行没有填写银行账号！<br>'			
		}		
	});
	if(otherMessage2 != ""){
		$.messager.alert('前台提示',otherMessage2);
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		return;
	}		
	$.each(linkInsertRows,function(index,data){
		linkInsertRows[index].lineNumber = linkListTable.datagrid('getRowIndex',data)+1;
		if(!data.linkmanName || data.linkmanName ==""){
			otherMessage1 += '第'+linkInsertRows[index].lineNumber+'行没有填写联系人！<br>'
		}	
		if((!data.mobileNumber||data.mobileNumber =="")&&(!data.telNumber||data.telNumber =="")){
			otherMessage1 += '第'+linkInsertRows[index].lineNumber+'行座机号或手机号必须填一个！<br>'			
		}
	});
	if(otherMessage1 != ""){
		$.messager.alert('前台提示',otherMessage1);
		$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		return;
	}	
	var paperJsonString = JSON.stringify(paperActualInsertRows);
	var accountJsonString = JSON.stringify(accountInsertRows);
	var linkJsonString = JSON.stringify(linkInsertRows);
	var queryParams = {
			status:1,
			deletedFlag:0,
			paperGridList:paperJsonString,
			accountGridList:accountJsonString,
			linkGridList:linkJsonString
		};
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
			$.messager.progress('close');
			$('#btnSave').attr('disabled',false).removeClass('btn-disabled');
		}
	});
}
/********************页面特殊方法结束***************************************************************************/
/**
 * 变更历史的超链接
 */
function linkChangeHistory(value,rowData,rowIndex){
	
	//添加超链接 
	return "<a href='javacript:;' class='editcls' onclick='showChangeHistory("+rowData.cussupId+")'>"+value+"</a>";  
}

/**
 * 采购目录的超链接
 */
function linkCatalog(value,rowData,rowIndex){
	
	//添加超链接 
	return "<a href='javacript:;' class='editcls' onclick='showCatalog("+rowData.cussupId+")'>"+value+"(暂无效)</a>";  
}

/**
 * 根据row_id号显示变更历史
 */
function showChangeHistory(cussupId){

	$('#showChangeHistory').datagrid('load',basicUrl+'/basicInfo/searchChangeHistory.do?cussupId='+cussupId);
	leftSilpFun('changeHistory');
}
function linkPaperCode(value,rowData,rowIndex){
	//添加超链接 
	return "<a href='javacript:;' class='editcls' onclick='showPaperPic("+rowIndex+")'>"+value+"</a>";  
}
function showPaperPic(rowIndex){
	var picSrc = $('#showChangeHistory').datagrid("getRows")[rowIndex].picSrc;
	$('#imgPreviewDIV').empty();
	$('#imgPreviewDIV').append('<ul id="imgPreviewUl"></ul>');
	if(picSrc != "undefined" && picSrc != undefined){
		var base_url = $('#base_url').val();		
		for(var i = 0; i < picSrc.length ; i++){
			$('#imgPreviewUl').append('<li style="display:none;"><img id="imgPreview'+i+'" class="img-preveiw" alt="附件预览"/></li>'); 
			if(picSrc[i].substring(0,5) == 'data:'){
				document.getElementById('imgPreview'+i).setAttribute("src",picSrc[i]); 
			}else{
				document.getElementById('imgPreview'+i).setAttribute("src",base_url + picSrc[i]); 
			}
		}
	}
	$('#imgPreviewUl').viewer({
		inline:true
	});
	$('#imgPreviewPanel').dialog('open');
}
/**
 * 根据row_id号显示销售信息
 */
function showCatalog(cussupId){
//	$('#showChangeHistory').datagrid('load',basicUrl+'/basicInfo/searchChangeHistory.do?rowId='+rowId+'&cussupId='+cussupId);
//	leftSilpFun('changeHistory');
}

function uploadFile(files,index){
	var formData = new FormData();
	for(var i = 0; i < files.length; i++){
		formData.append('file'+i, files[i]);
	}
	var rows = $('#paperListTable').edatagrid('getRows');
	$.messager.progress();
	jAjax.submit_file(prUrl + 'editUploadCustomerAndSupplierTempFile.do', formData
	, function(data){
		rows[index].fileNameList = data;
		rows[index].file = '上传'+data.length+'个附件';
		$('#paperListTable').edatagrid('editRow',index).datagrid('endEdit',index);
		$.messager.progress('close');
	}, function(response){
		jAjax.errorFunc(response.errorMsg);
		$.messager.progress('close');
	},true);
}

function linkRequestUpdateFarm(value,rowData,rowIndex){
		return '<a class="editcls" onclick="selectRequestUpdateFarm('+rowIndex+')" href="javascript:void(0)">需同步的猪场</a>'
		+'(<span id="selectCount'+rowIndex+'"></span>/<span id="allCount'+rowIndex+'" name="allCount"></span>)';	
}
function selectRequestUpdateFarm(index){
	// 记录选中的行数
	$('#selectedIndex').val(index);
	// 清空选中的猪场
	$('#farmTable').datagrid('uncheckAll');
	
	// 获取上一次选中的猪场，并且勾选上一次勾选的猪场
	var data = $('#farmTable').datagrid('getRows');
	var requestFarm = $('#table').datagrid('getRows')[index].requestUpdateFarm;
	if(requestFarm != null){
		for(var i=0;i<data.length;i++){
			var eachFarmId = data[i].farmId;
			for(var j=0;j<requestFarm.length;j++){
				var requestFarmId = requestFarm[j].farmId;
				if(eachFarmId == requestFarmId){
					$('#farmTable').datagrid('checkRow',i);
					break;
				}
			}
		}
	}
	leftSilpFun('farmSelect');
}

function onBtnSelectFarm(){
	var data = $('#farmTable').datagrid('getSelections');
	var tableRow = $('#table').datagrid('getRows')[$('#selectedIndex').val()];
	tableRow.requestUpdateFarm = data;
	$('#selectCount'+$('#selectedIndex').val()).html(data.length);
	rightSlipFun('farmSelect',390);
//	$.messager.alert('提示','修改成功!');
	
}

function selectAndAllCount(listTableData, farmTableData){
	var allCount = farmTableData.length;
	$.each(listTableData,function(index,data){
		$('#selectCount'+index).html(data.requestUpdateFarm.length);
		$('#allCount'+index).html(allCount);
	});
}

function editCustomerAndSupplierToFarms(){
	var record = $('#table').datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
//		for(var i=0;i<record.length;i++){
//			var requestFarm = record[i].requestUpdateFarm;
//			if(requestFarm==null || requestFarm.length==0){
//				$.messager.alert('警告','物料名称【'+record[i].materialName+'】没有勾选同步猪场！');
//				return;
//			}
//		}
		
		var submitData = [];
		for(var i=0;i<record.length;i++){
			var requestFarm = record[i].requestUpdateFarm;
			if(requestFarm!=null && requestFarm.length!=0){
				submitData.push(record[i]);
			}
		}
		if(submitData.length!=0){
			$.messager.progress();	
			jAjax.submit(editCustomerAndSupplierToFarmsUrl,{json:JSON.stringify(submitData)}
				,function(response){
					$('#table').datagrid('reload');
					$.messager.alert('提示','同步更新成功!');
					$.messager.progress('close');	
				},function(response){
		    		jAjax.errorFunc(response.errorMsg);
		    		$.messager.progress('close');
		    	},null,true,null,false);
		}else{
			$('#table').datagrid('reload');
			$.messager.alert('提示','同步更新成功!');
		}
	}
}


