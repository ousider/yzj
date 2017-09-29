var title = '供应商';
var editIndex = undefined;
var prUrl='/basicInfo/';
var searchMainListUrl=prUrl+'searchCustomerAndSupplierToPage.do';
/** *********页面渲染开始*************************************************************************/
//查询按钮
var buttonList = getButtonByLimit({});

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
				columnFields:'rowId,deletedFlag,companyCode,createTypeName,companyName,sortName,catalog,paymnent,creditLimit,createDate,createrName,changeHistory,notes',
				columnTitles:'ID,deletedFlag,供应商编号,创建类型,供应商名称,供应商简称,采购目录,付款条件,信用额度,创建时间,创建人,变更历史,备注',
				hiddenFields:'rowId,deletedFlag',
				columnWidths:'100,100,100,100,250,100,100,100,100,100,100,100,100',
				rightColumnFields:'listQty',
				formatterFields:'changeHistory,catalog',
				formatter:[linkChangeHistory,linkCatalog]
			})
	);
	setTimeout(function(){
		//创建类型
		xnCdCombobox({
			id:'createType',
			editable:false,
			typeCode:'CREATE_SUP_TYPE'
		});
		//供应商分类
		xnCdCombobox({
			id:'supplierType',
			editable:false,
			typeCode:'CREATE_SUP_CLASS'
		});
		//业务伙伴类型
		xnCdCombobox({
			id:'businessPartnerType',
			editable:false,
			typeCode:'BUSINESS_PARTNER_TYPE'
		});
		//交易币种
		xnCdCombobox({
			id:'tradeCurrency',
			editable:false,
			typeCode:'moneyType'
		});
		//付款条件
		xnCdCombobox({
			id:'payCondition',
			editable:false,
			typeCode:'TERM_OF_PAYMENT'
		});			
		//付款账期
		xnCdCombobox({
			id:'payDays',
			editable:false,
			typeCode:'PAY_DATE_LIST'
		});
		//国家
		xnCdCombobox({
			id:'country',
			editable:false,
			typeCode:'COUNTRY_TYPE'
		});			
				
		//创建类型
		xnCdCombobox({
			id:'createTypeSearch',
			editable:false,
			typeCode:'CREATE_SUP_TYPE',
			hasAll:true
		});
		
		//供应商全称
		xnComboGrid({
			id:'cussupId',
			// url:'/basicInfo/searchCusSupForCreate.do?cussupType=S&createType=2',
			idField:'rowId',
			textField:'companyName',
			readonly:true,
			columns:[[ 	
			    {field:'rowId',title:'ID',width:100,hidden:true},
		        {field:'companyCode',title:'客户编码',width:100},
		        {field:'companyName',title:'客户名称',width:100},
		        {field:'companyNameEn',title:'英文名称',width:100}
		    ]]
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
		
		$('#paperListTable').datagrid(
				j_grid_view({
					haveCheckBox:false,
					pagination:false,
					columnFields:'paperTypeName,paperCode,expiryTypeName,expiryDate,file,preview',
					columnTitles:'证照类型,证照编码,有效期类型,有效期,上传附件,附件预览',
					hiddenFields:'',
					fit:false,
					height:'200px',
					width:'100%',
					formatterFields:'preview',
					formatter:[linkPreview]
				})
		);
		
		$('#accountListTable').datagrid(
				j_grid_view({
					haveCheckBox:false,
					pagination:false,
					columnFields:'bankName,bankAccount,notes',
					columnTitles:'开户行名称,银行账号,备注',
					hiddenFields:'',
					fit:false,
					height:'200px',
					width:'100%'
				})
		);
		
		$('#linkListTable').datagrid(
				j_grid_view({
					haveCheckBox:false,
					pagination:false,
					columnFields:'linkmanName,mobileNumber,telNumber,notes',
					columnTitles:'联系人姓名,手机号,座机号,备注',
					hiddenFields:'',
					fit:false,
					height:'200px',
					width:'100%'
				})
		);

	},500)
});

/*************页面渲染结束******************************************************************************/

/********************页面特殊方法开始***************************************************************************/
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
		showMessage(record[0]);
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
	showMessage(row);
}

function showMessage(row){
	$('#editWin').window({
		title:'查看'+ title
	});
	$('#cussupId').combogrid('grid').datagrid('load', basicUrl + '/basicInfo/searchCusSupForCreate.do?cussupType=S&createType='+row.createType)
	$('#editWin').window('open');
	$('#editForm').form('reset');
	$('#editForm').form('load',row);
	disableCompanyInfo();
	var paperListTable = document.getElementById('paperListTable');
	var accountListTable = document.getElementById('accountListTable');
	var linkListTable = document.getElementById('linkListTable');
	
	if(paperListTable!=null){
		$('#paperListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryPaper.do?cussupId='+row.rowId);
	}
	
	if(accountListTable!=null){
		$('#accountListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryAccount.do?cussupId='+row.rowId);
	}
	
	if(linkListTable!=null){
		$('#linkListTable').datagrid('load',basicUrl+'/basicInfo/searchHistoryLink.do?cussupId='+row.rowId);
	}
}

function disableCompanyInfo(){
	$('#createType').combobox('disable');
	$('#cussupId').combogrid('disable');
	$('#sortName').textbox('disable');
	$('#legalPerson').textbox('disable');
	$('#supplierType').combobox('disable');
	$('#businessPartnerType').combobox('disable');
	$('#tradeCurrency').combobox('disable');
	$('#payCondition').combobox('disable');
	$('#payDays').combobox('disable');
	$('#creditLimit').textbox('disable');
	$('#country').combobox('disable');
	$('#province').combobox('disable');
	$('#city').combobox('disable');
	$('#county').combobox('disable');
	$('#companyAddress').textbox('disable');
	$('#postcode').textbox('disable');
	$('#longitude').numberspinner('disable');
	$('#latitude').numberspinner('disable');
	$('#changeContentText').textbox('disable');
	$('#notesText').textbox('disable');
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
	return "<a href='javacript:;' class='editcls' onclick='showCatalog("+rowData.rowId+","+rowData.cussupId+")'>"+value+"(暂无效)</a>";  
}

function linkPreview(value,rowData,rowIndex){
	//添加超链接 
	return '<a class="editcls" onclick="imgPreviewFun('+rowIndex+')" href="javascript:void(0)">附件预览</a>';
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
 * 根据row_id号显示变更历史
 */
function showCatalog(rowId,cussupId){
//	$('#showChangeHistory').datagrid('load',basicUrl+'/basicInfo/searchChangeHistory.do?rowId='+rowId+'&cussupId='+cussupId);
//	leftSilpFun('changeHistory');
}
