var prUrl='/production/';
var saveUrl=prUrl+'goodPigSell.do';
var editIndex = undefined;
var eventName = 'GOOD_PIG_SELL';
//默认值为3
var oldPigType = 3;
var toHana = "OFF"
	$.ajax({  
	    url : basicUrl+'/param/getSettingValueByCode.do?settingCode=SAP_TOHANA', 
	    async:false,
	    type : "POST",  
	    success : function(data) { 
	    	var obj = eval('(' + data + ')');
	    	toHana = obj.rows;
	    }
	});

$(document).ready(function(){
	//销售类型
	xnCdCombobox({ 
		id:'leaveReason',
		typeCode:'SELL_TYPE',
		required:true,
		onChange:selectType
	});
	//猪性别
	xnCdCombobox({
	    id:'sexName',
	    typeCode:'PIG_SEX',
	    hasAll:true
	});
	//销售单类型
	xnCdCombobox({ 
		id:'saleBillType',
		typeCode:'SALE_BILL_TYPE',
		required:true,
	});

	// 选择业务员
	xnComboGrid({
		id:'salesman',
		idField:'rowId',
		textField:'employeeName',
		width: 550,
		url: '/UserManage/searchEmployeeByIdToList',
		columns:[[ 
					{field:'rowId',title:'ID',width:100,hidden:true},
					{field:'employeeName',title:'人员名称',width:100},
			        {field:'employeeCode',title:'人员编码',width:100}
			    ]]
	})

	
	//猪只状态
	xnComboGrid({
		id:'pigClassId',
		idField:'rowId',
		textField:'pigClassName',
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'状态代码',width:100},
			        {field:'pigClassName',title:'状态名称',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});
	//品种名称
	xnCombobox({
	    id:'breedIds',
	    url:'/backEnd/searchBreedToList.do',
	    valueField:'rowId',
	    textField:'breedName'
		
	});
	//猪舍
	xnComboGrid({
		id:'houseIds',
		idField:'rowId',
		textField:'houseName',
		url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName,
		width:550,
		columns:[[ 	
		           	{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'猪舍代码',width:100},
			        {field:'houseName',title:'猪舍名称',width:100},
			        {field:'houseVolume',title:'猪舍容量',width:100},
			        {field:'pigQty',title:'猪只数量',width:100},
			        {field:'houseTypeName',title:'猪舍类别',width:100},
			        {field:'notes',title:'备注',fitColumns:true}
			    ]],
		onChange:houseIdsChange
	});
	//批次
	xnComboGrid({
		id:'swineryIds',
		idField:'swineryId',
		textField:'swineryName',
		width:550,
		columns:[[ 	
					{field:'rowId',title:'ID',width:100,hidden:true},
			        {field:'businessCode',title:'批次代码',width:100},
			        {field:'swineryName',title:'批次名称',width:100},
			        {field:'pigQty',title:'猪只数量',width:100},
			        {field:'notes',title:'备注',width:100}
			    ]]
	});

	//客户
	xnCombobox({
	    id:'showCustomerId',
	    url:'/basicInfo/searchCustomerAndSupplierToList.do?cussupType=C',
	    valueField:'rowId',
	    textField:'companyName',
	    required:true
	});
	//customerComboGrid({required:true});
	addFocus('showCustomerId','combobox');
	$('#listTable').datagrid(
			j_eventGrid({
			dbClickEdit:true,
			height:'74%',
			fitColumns:false,
			frozenColumns:[[{field:'ck',checkbox:true},
			              	j_gridText({field:'rowId',title:'ID',hidden:true}),
			              	j_gridText({field:'pigIds',title:'猪只ID',hidden:true}),
			              	j_gridText({field:'minValidDate',title:'minValidDate',hidden:true}),
			              	j_gridText({field:'maxValidDate',title:'maxValidDate',hidden:true}),
			              	j_gridText({field:'deletedFlag',title:'deletedFlag',hidden:true}),
			              	j_gridText({
			              		field:'saleDescribe',
			              		title:'销售品名',
			              		width:80,
			              		formatter:function(value,row){
			              			return row.saleDescribeName;
			              		},
			              		editor:
			              		xnGridCdComboBox({
			              			field:'saleDescribe',
			              			typeCode:'SELL_GOOD',
			              			excludeValue:['1','2','6','7','8','9','10']
			              		})
			              	}),
			              	j_gridText({field:'breedId',title:'品种',width:80,
			              		formatter:function(value,row){
			              			return row.breedName;
			              		},
			              	}),
			              	j_gridText({
			              		field:'sex',
			              		title:'猪只性别',
			              		width:80,
			              		formatter:function(value,row){
			              			return row.sexName;
			              		},
			              		editor:
			              		xnGridCdComboBox({
			              			field:'sex',
			              			typeCode:'PIG_SEX',
			              			excludeValue:['3']
			              		})
			              	}),
			              	
			              	//猪舍
			              	j_gridText(houseGridComboGrid({
			              		width:100,
			              		formatter:function(value,row){
			              			return row.houseIdName
			              			},
			              		onSelect:houseSelectFun,
			              		onChange:houseChangeFun
		              		})),
		              		//批次
		              		j_gridText(swineryGridComboGrid({
			              		width:150,
			              		formatter:function(value,row){
			              			return row.swineryIdName
			              			},
			              		idField:'swineryId',
			              		onSelect:swinerySelectFun
		              		}))
			                ]],
			columns:[[
			          	j_gridText({field:'pigInfo',title:'猪只信息',width:100}),
		          		j_gridText({field:'pigQty',title:'猪只数量',width:80}),
			          	j_gridText({field:'saleDate',title:'销售日期',width:150
			          	}),
			            j_gridNumber({field:'saleNum',title:'销售数量',width:80,min:0,
			            	onChange:function(newValue,oldValue){
			            		if(editIndex == undefined){
			              			return;
			              		}
			            		if(newValue == ""){
			            			newValue = 0;
			            		}
//			            		var changeFalg = false;
			            		var rows = $('#listTable').datagrid('getRows');
			            		rows[editIndex].saleNum = newValue;
			            		if(rows[editIndex].baseWeight != undefined && rows[editIndex].totalWeight != undefined){
				              		var totalWeight = strToFloat(rows[editIndex].totalWeight),
				              		baseWeight = strToFloat(rows[editIndex].baseWeight),
				              		overWeight = (totalWeight - baseWeight * strToFloat(newValue)).toFixed(2);
					            	rows[editIndex].overWeight = overWeight;
					            	if(rows[editIndex].overUnitPrice != undefined){
					            		rows[editIndex].overPrice = (overWeight * strToFloat(rows[editIndex].overUnitPrice)).toFixed(2);
					            	}
//					            	changeFalg = true;
					            	changeTableDisplayValue('listTable',editIndex,[{
					            		field:'overWeight',
					            		value:rows[editIndex].overWeight
					            	},{
					            		field:'overPrice',
					            		value:rows[editIndex].overPrice
					            	}]);
			            		}
			            		if(rows[editIndex].baseWeight != undefined){
			            			var baseUnitPrice = strToFloat(rows[editIndex].baseUnitPrice == undefined ? 0 : rows[editIndex].baseUnitPrice);
			            			rows[editIndex].unitPricePrice = (strToFloat(rows[editIndex].baseWeight) * strToFloat(newValue) * baseUnitPrice).toFixed(2);
//			            			changeFalg = true;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'unitPricePrice',
					            		value:rows[editIndex].unitPricePrice
					            	}]);
			            		}
			            		//总重、底重、底重单价、超重、超重单价都不为空，按公式1计算总金额
			            		if(rows[editIndex].totalWeight != undefined && 
			            				rows[editIndex].baseWeight != undefined && 
			            				rows[editIndex].baseUnitPrice != undefined &&
			            				rows[editIndex].overWeight != undefined && 
			            				rows[editIndex].overUnitPrice != undefined){
//			            			changeFalg = true;
			            			var newTotalPrice = (strToFloat(newValue) * strToFloat(rows[editIndex].baseWeight) * strToFloat(rows[editIndex].baseUnitPrice)
			            					+ strToFloat(rows[editIndex].overWeight) * strToFloat(rows[editIndex].overUnitPrice)).toFixed(2);
			            			rows[editIndex].totalPrice =  newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
			            		//头单价、超重,超重单价都不为空，按公式2计算总金额
			            		if(rows[editIndex].preUnitPrice != undefined && rows[editIndex].preUnitPrice != ''){
//			            			changeFalg = true;
			            			var overWeight = strToFloat(rows[editIndex].overWeight == undefined?0:rows[editIndex].overWeight);
			            			var overUnitPrice = strToFloat(rows[editIndex].overUnitPrice == undefined?0:rows[editIndex].overUnitPrice);
			            			var newTotalPrice = (strToFloat(newValue) * strToFloat(rows[editIndex].preUnitPrice)
			            					+ overWeight * overUnitPrice).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
//			            		if(changeFalg){
//			            			setTimeout(function () {
//					            		$('#listTable').datagrid('endEdit',editIndex);
//					            		$('#listTable').datagrid('updateRow',{
//					            			index:editIndex,
//					            			row:rows[editIndex]
//					            		});
//					            	 },100);
//			            		}
			            		calculate(newValue,'saleNum');
			            	}
			            }),
		              	j_gridNumber({field:'totalWeight',title:'总重(KG)',precision:2,increment:0.1,width:80,min:0,
		              		onChange:function(newValue,oldValue){
		              			if(editIndex == undefined){
			              			return;
			              		}
		              			if(newValue == ""){
			            			newValue = 0;
			            		}
//		              			var changeFalg = false;
		              			var rows = $('#listTable').datagrid('getRows');
		              			if(rows[editIndex].baseWeight != undefined && rows[editIndex].saleNum != undefined){
				              		var saleNum = strToFloat(rows[editIndex].saleNum),
				              		baseWeight = strToFloat(rows[editIndex].baseWeight),
				              		overWeight = (strToFloat(newValue) - baseWeight * saleNum).toFixed(2);
					            	rows[editIndex].overWeight = overWeight;
					            	if(rows[editIndex].overUnitPrice != undefined){
					            		rows[editIndex].overPrice = (overWeight * strToFloat(rows[editIndex].overUnitPrice)).toFixed(2);
					            	}
//					            	changeFalg = true;
					            	changeTableDisplayValue('listTable',editIndex,[{
					            		field:'overWeight',
					            		value:rows[editIndex].overWeight
					            	},{
					            		field:'overPrice',
					            		value:rows[editIndex].overPrice
					            	}]);
			            		}
		              			//头数、底重、底重单价、超重、超重单价都不为空，按公式1计算总金额
			            		if(rows[editIndex].saleNum != undefined && 
			            				rows[editIndex].baseWeight != undefined && 
			            				rows[editIndex].baseUnitPrice != undefined &&
			            				rows[editIndex].overWeight != undefined && 
			            				rows[editIndex].overUnitPrice != undefined){
//			            			changeFalg = true;
			            			var newTotalPrice = (strToFloat(rows[editIndex].saleNum) * strToFloat(rows[editIndex].baseWeight) * strToFloat(rows[editIndex].baseUnitPrice)
			            					+ strToFloat(rows[editIndex].overWeight) * strToFloat(rows[editIndex].overUnitPrice)).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
			            		//重量单价不为空，按公式3计算总金额
			            		if(rows[editIndex].totalUnitPrice != undefined){
//			            			changeFalg = true;
			            			var newTotalPrice = (strToFloat(rows[editIndex].totalUnitPrice) * strToFloat(newValue)).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
//			            		if(changeFalg){
//			            			setTimeout(function () {
//					            		$('#listTable').datagrid('endEdit',editIndex);
//					            		$('#listTable').datagrid('updateRow',{
//					            			index:editIndex,
//					            			row:rows[editIndex]
//					            		});
//					            	 },100);
//			            		}
			            		calculate(newValue,'totalWeight');
			            	}
		              	}),
		              	j_gridNumber({field:'totalUnitPrice',title:'重量单价',precision:2,increment:0.1,width:80,min:0,
		              		onChange:function(newValue,oldValue){
		              			if(editIndex == undefined){
			              			return;
			              		}
		              			if(newValue == ""){
			            			newValue = 0;
			            		}
//		              			var changeFalg = false;
		              			var rows = $('#listTable').datagrid('getRows');
		              			//总重不为空，按公式3计算总金额
			            		if(rows[editIndex].totalWeight != undefined){
//			            			changeFalg = true;
			            			var newTotalPrice = (strToFloat(rows[editIndex].totalWeight) * strToFloat(newValue)).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
//			            		if(changeFalg){
//			            			setTimeout(function () {
//					            		$('#listTable').datagrid('endEdit',editIndex);
//					            		$('#listTable').datagrid('updateRow',{
//					            			index:editIndex,
//					            			row:rows[editIndex]
//					            		});
//					            	 },100);
//			            		}
		              		}
		              	}),
		              	j_gridNumber({field:'baseWeight',title:'底重(KG)',precision:2,increment:0.1,width:80,min:0,
			              	onChange:function(newValue,oldValue){
			              		if(editIndex == undefined){
			              			return;
			              		}
			              		if(newValue == ""){
			              			newValue = 0;
			              		}
//			              		var changeFalg = false;
			              		var rows = $('#listTable').datagrid('getRows');
			              		if(rows[editIndex].saleNum != undefined && rows[editIndex].totalWeight != undefined){
				              		var totalWeight = strToFloat(rows[editIndex].totalWeight),
				              		saleNum = strToFloat(rows[editIndex].saleNum),
				              		baseUnitPrice = strToFloat(rows[editIndex].baseUnitPrice == undefined ? 0 :rows[editIndex].baseUnitPrice),
				              		overWeight = (totalWeight - saleNum * strToFloat(newValue)).toFixed(2);
					            	rows[editIndex].overWeight = overWeight;
					            	if(rows[editIndex].overUnitPrice != undefined){
					            		rows[editIndex].overPrice = (overWeight * strToFloat(rows[editIndex].overUnitPrice)).toFixed(2);
					            	}
//					            	changeFalg = true;
					            	changeTableDisplayValue('listTable',editIndex,[{
					            		field:'overWeight',
					            		value:rows[editIndex].overWeight
					            	},{
					            		field:'overPrice',
					            		value:rows[editIndex].overPrice
					            	}]);
			              		}
			              		if(rows[editIndex].saleNum != undefined){
			              			var baseUnitPrice = strToFloat(rows[editIndex].baseUnitPrice == undefined ? 0 :rows[editIndex].baseUnitPrice);
			              			rows[editIndex].unitPricePrice = (baseUnitPrice * strToFloat(newValue) * strToFloat(rows[editIndex].saleNum)).toFixed(2);
//			              			changeFalg = true;
			              			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'unitPricePrice',
					            		value:rows[editIndex].unitPricePrice
					            	}]);
			              		}
			              		//头数、总重、底重单价、超重、超重单价都不为空，按公式1计算总金额
			            		if(rows[editIndex].saleNum != undefined && 
			            				rows[editIndex].totalWeight != undefined && 
			            				rows[editIndex].baseUnitPrice != undefined &&
			            				rows[editIndex].overWeight != undefined && 
			            				rows[editIndex].overUnitPrice != undefined){
//			            			changeFalg = true;
			            			var newTotalPrice = (strToFloat(rows[editIndex].saleNum) * strToFloat(newValue) * strToFloat(rows[editIndex].baseUnitPrice)
			            					+ strToFloat(rows[editIndex].overWeight) * strToFloat(rows[editIndex].overUnitPrice)).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
//			            		if(changeFalg){
//			            			setTimeout(function () {
//					            		$('#listTable').datagrid('endEdit',editIndex);
//					            		$('#listTable').datagrid('updateRow',{
//					            			index:editIndex,
//					            			row:rows[editIndex]
//					            		});
//					            	 },100);
//			            		}
			              		
			              	}
		              	}),
		              	j_gridNumber({field:'baseUnitPrice',title:'底重单价',precision:2,increment:0.1,width:80,min:0,
		              		onChange:function(newValue,oldValue){
			              		if(editIndex == undefined){
			              			return;
			              		}
			              		if(newValue == ""){
			            			newValue = 0;
			            		}
//			              		var changeFalg = false;
			              		var rows = $('#listTable').datagrid('getRows');
			              		if(rows[editIndex].baseWeight != undefined && rows[editIndex].saleNum != undefined ){
			              			var baseWeight = strToFloat(rows[editIndex].baseWeight),saleNum = strToFloat(rows[editIndex].saleNum);
					            	rows[editIndex].unitPricePrice = (saleNum * baseWeight * strToFloat(newValue)).toFixed(2);
//					            	changeFalg = true;
					            	changeTableDisplayValue('listTable',editIndex,[{
					            		field:'unitPricePrice',
					            		value:rows[editIndex].unitPricePrice
					            	}]);
			              		}
			              		//头数、总重、底重、超重、超重单价都不为空，按公式1计算总金额
			            		if(rows[editIndex].saleNum != undefined && 
			            				rows[editIndex].totalWeight != undefined && 
			            				rows[editIndex].baseWeight != undefined &&
			            				rows[editIndex].overWeight != undefined && 
			            				rows[editIndex].overUnitPrice != undefined){
//			            			changeFalg = true;
			            			var newTotalPrice = (strToFloat(rows[editIndex].saleNum) * strToFloat(newValue) * strToFloat(rows[editIndex].baseWeight)
			            					+ strToFloat(rows[editIndex].overWeight) * strToFloat(rows[editIndex].overUnitPrice)).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
//			            		if(changeFalg){
//			            			setTimeout(function () {
//					            		$('#listTable').datagrid('endEdit',editIndex);
//					            		$('#listTable').datagrid('updateRow',{
//					            			index:editIndex,
//					            			row:rows[editIndex]
//					            		});
//					            	 },100);
//			            		}
		              		}
	              		}),
		              	j_gridText({field:'unitPricePrice',title:'底重金额',width:80,min:0}),
		              	j_gridText({field:'overWeight',title:'超重(KG)',width:80,min:0}),
		              	j_gridNumber({field:'overUnitPrice',title:'超重单价',precision:2,increment:0.1,width:80,min:0,
		              		onChange:function(newValue,oldValue){
			              		if(editIndex == undefined){
			              			return;
			              		}
			              		if(newValue == ""){
			            			newValue = 0;
			            		}
//			              		var changeFalg = false;
			              		var rows = $('#listTable').datagrid('getRows');
			              		if(rows[editIndex].overWeight != undefined ){
			              			var overWeight = strToFloat(rows[editIndex].overWeight);
					            	rows[editIndex].overPrice = (overWeight * strToFloat(newValue)).toFixed(2);
//					            	changeFalg = true;
					            	changeTableDisplayValue('listTable',editIndex,[{
					            		field:'overPrice',
					            		value:rows[editIndex].overPrice
					            	}]);
			              		}
			              		//头数、总重、底重、底重单价、超重单价都不为空，按公式1计算总金额
			            		if(rows[editIndex].saleNum != undefined && 
			            				rows[editIndex].totalWeight != undefined && 
			            				rows[editIndex].baseWeight != undefined &&
			            				rows[editIndex].overWeight != undefined && 
			            				rows[editIndex].baseUnitPrice != undefined){
//			            			changeFalg = true;
			            			var newTotalPrice = (strToFloat(rows[editIndex].saleNum) * strToFloat(rows[editIndex].baseUnitPrice) * strToFloat(rows[editIndex].baseWeight)
			            					+ strToFloat(rows[editIndex].overWeight) * strToFloat(newValue)).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
			            		//头单价、头数,超重都不为空，按公式2计算总金额
			            		if(rows[editIndex].saleNum != undefined && 
			            				rows[editIndex].preUnitPrice != undefined && rows[editIndex].preUnitPrice != ''){
//			            			changeFalg = true;
			            			var overWeight = strToFloat(rows[editIndex].overWeight == undefined? 0 :rows[editIndex].overWeight);
			            			var newTotalPrice = (strToFloat(rows[editIndex].preUnitPrice) * strToFloat(rows[editIndex].saleNum)
			            					+ overWeight * strToFloat(newValue)).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
//			            		if(changeFalg){
//			            			setTimeout(function () {
//					            		$('#listTable').datagrid('endEdit',editIndex);
//					            		$('#listTable').datagrid('updateRow',{
//					            			index:editIndex,
//					            			row:rows[editIndex]
//					            		});
//					            	 },100);
//			            		}
		              		}
		              	}),
		              	j_gridText({field:'overPrice',title:'超重金额',width:80,min:0}),
		              	j_gridNumber({field:'preUnitPrice',title:'头单价',precision:2,increment:0.1,width:80,min:0,
		              		onChange:function(newValue,oldValue){
			              		if(editIndex == undefined){
			              			return;
			              		}
			              		if(newValue == ""){
			            			newValue = 0;
			            		}
//			              		var changeFalg = false;
			              		var rows = $('#listTable').datagrid('getRows');
			              		//头数,超重,超重单价都不为空，按公式2计算总金额
			            		if(rows[editIndex].saleNum != undefined){
//			            			changeFalg = true;
			            			var overWeight = strToFloat(rows[editIndex].overWeight == undefined?0:rows[editIndex].overWeight);
			            			var overUnitPrice = strToFloat(rows[editIndex].overUnitPrice == undefined?0:rows[editIndex].overUnitPrice);
			            			var newTotalPrice = (strToFloat(newValue) * strToFloat(rows[editIndex].saleNum)
			            					+  overWeight * overUnitPrice).toFixed(2);
			            			rows[editIndex].totalPrice = newTotalPrice;
			            			changeTableDisplayValue('listTable',editIndex,[{
					            		field:'totalPrice',
					            		value:rows[editIndex].totalPrice
					            	}]);
			            			calculate(newTotalPrice,'totalPrice');
			            		}
//			            		if(changeFalg){
//			            			setTimeout(function () {
//					            		$('#listTable').datagrid('endEdit',editIndex);
//					            		$('#listTable').datagrid('updateRow',{
//					            			index:editIndex,
//					            			row:rows[editIndex]
//					            		});
//					            	 },100);
//			            		}
		              		}
		              	}),
		              	j_gridText({field:'totalPrice',title:'总金额',width:80,min:0}),
		              	//技术员
		              	j_gridText(workerGridComboGrid({width:80})),
		              	j_gridText({field:'notes',title:'备注',width:80,editor:'textbox'})
				    ]],
		    onEndEdit:function(index,row){
		    	var house = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'houseId'
	            });
	            if(house != null){
	            	if(row.saleDescribe == 1 || row.saleDescribe == 2 || row.saleDescribe == 6 || row.saleDescribe == 7 || row.saleDescribe == 8 || row.saleDescribe == 9 || row.saleDescribe == 10){
						$(house.target).combogrid('setText',row.houseIdName);
						$(house.target).combogrid('disable');
					}else{
		            	returnTarName({
		            		tarType:'grid',
		            		tarName:'houseName',
		            		tarFiled:'houseId',
		            		target:house.target
		            	},row)
					}
	            }
	            var swinery = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'swineryId'
	            });
	            if(swinery != null){
	            	if(row.saleDescribe == 1 || row.saleDescribe == 2 || row.saleDescribe == 6 || row.saleDescribe == 7 || row.saleDescribe == 8 || row.saleDescribe == 9 || row.saleDescribe == 10){
						$(swinery.target).combogrid('setText',row.swineryIdName);
						$(swinery.target).combogrid('disable');
					}else{
		            	returnTarName({
		            		tarType:'grid',
		            		tarName:'swineryName',
		            		tarFiled:'swineryId',
		            		target:swinery.target
		            	},row)
					}
	            }
	            var saleDescribe = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'saleDescribe'
	            });
		    	if(saleDescribe != null){
		    		row.saleDescribeName = $(saleDescribe.target).combobox('getText');
		    	}
		    	var sex = $('#listTable').datagrid('getEditor', {
	                index: index,
	                field: 'sex'
	            });
		    	if(sex != null){
		    		row.sexName = $(sex.target).combobox('getText');
		    	}
		    },
		    onBeginEdit:function(index,row){
		    	var swinery = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'swineryId'
			    });
				if(swinery != null){
					if(row.saleDescribe == 1 || row.saleDescribe == 2 || row.saleDescribe == 6 || row.saleDescribe == 7 || row.saleDescribe == 8 || row.saleDescribe == 9 || row.saleDescribe == 10){
						$(swinery.target).combogrid('setText',row.swineryIdName);
						$(swinery.target).combogrid('disable');
					}else{
						var swineryGrid = $(swinery.target).combogrid('grid');
						var houseId = row.houseId == null || row.houseId == undefined || row.houseId == ''? 0 : row.houseId;
						swineryGrid.datagrid('reload',basicUrl+'/production/searchPorkToList.do?lineId=0&pigType=3&houseId='+houseId+'&eventName='+eventName);
					}
				}
				var saleDescribe = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'saleDescribe'
			    });
				if(saleDescribe != null){
					if(row.saleDescribe == 1 || row.saleDescribe == 2 || row.saleDescribe == 6 || row.saleDescribe == 7 || row.saleDescribe == 8 || row.saleDescribe == 9 || row.saleDescribe == 10){
						$(saleDescribe.target).combobox('disable');
						var describe = null;
						if(row.saleDescribe == 1){
							describe = "生产公猪";
						}else if(row.saleDescribe == 2){
							describe = "生产母猪";
						}else if(row.saleDescribe == 6){
							describe = "后备公猪";
						}else if(row.saleDescribe == 7){
							describe = "后备母猪";
						}else if(row.saleDescribe == 8){
							describe = "留种公猪";
						}else if(row.saleDescribe == 9){
							describe = "留种母猪";
						}else if(row.saleDescribe == 10){
							describe = "留种猪";
						}
						$(saleDescribe.target).combobox('setText',describe);
					}
				}
				var saleNum = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'saleNum'
			    });
				if(saleNum != null){
					if(row.saleDescribe == 1 || row.saleDescribe == 2 || row.saleDescribe == 6 || row.saleDescribe == 7 || row.saleDescribe == 8 || row.saleDescribe == 9 || row.saleDescribe == 10){
						$(saleNum.target).numberspinner('disable');
					}
				}
				var houseId = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'houseId'
			    });
				var house = $('#listTable').datagrid('getEditor', {
				    index: index,
				    field: 'houseId'
				});
    			if(houseId != null){
    			    if(row.saleDescribe == 1 || row.saleDescribe == 2 || row.saleDescribe == 6 || row.saleDescribe == 7 || row.saleDescribe == 8 || row.saleDescribe == 9 || row.saleDescribe == 10){
    			    	$(houseId.target).combogrid('setText',row.houseIdName);
    			    	$(houseId.target).combogrid('disable');
    			    
    			    }
    			    if(row.saleDescribe == 3 || row.saleDescribe == 4 || row.saleDescribe == 5){
    				var houseGrid = $(house.target).combogrid('grid');
    				url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName;
    				houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName+ "&saleDescribe="+row.saleDescribe );
    			    }else{
    				var houseGrid = $(house.target).combogrid('grid');
    				url:'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName;
    				houseGrid.datagrid('reload',basicUrl+'/basicInfo/searchHouseToList.do?lineId=0&eventName='+eventName );
    			    }
    			}
    			var sex = $('#listTable').datagrid('getEditor', {
			        index: index,
			        field: 'sex'
			    });
				if(sex != null){
					var checkLeaveReason = $('#leaveReason').combogrid('getValue');
					if(checkLeaveReason != 2 || row.saleDescribe == 1 || row.saleDescribe == 2 || row.saleDescribe == 6 || row.saleDescribe == 7 || ((row.saleDescribe == 8 || row.saleDescribe == 9 || row.saleDescribe == 10) && row.sex != 3 )){
						$(sex.target).numberspinner('disable');
					}
					$(sex.target).combobox('setText',row.sexName);
				}
		    }
		})
	);
	
	$('#preSaveRecordTable').datagrid(
			j_grid_view({
				fitColumns:false,
				haveCheckBox:false,
				columnFields:'saleDescribeName,houseIdName,swineryIdName,pigInfo,pigQty,saleDate,saleNum,totalWeight,totalUnitPrice,baseWeight,baseUnitPrice,unitPricePrice,overWeight,overUnitPrice,overPrice,preUnitPrice,totalPrice,worker,notes',
				columnTitles:'销售品名,猪舍名称,批次名称,猪只信息,猪只数量,销售日期,销售数量,总重(KG),重量单价,底重(KG),底重单价,底重金额,超重(KG),超重单价,超重金额,头单价,总金额,技术员,备注',
				hiddenFields:'worker,notes,saleDate',
				columnWidths:'100,100,100,100,100,110,100,100,100,100,100,100,100,100,100,100,100,100,100',
				fit:false,
				width:'auto',
				height:'93%',
				pagination:false
			},'preSaveRecordTable')
	);
});
function selectType(){
	//清空明细
	$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
	$('#sellNumSum').html('0');
	$('#weightSum').html('0.00');
	$('#moneySum').html('0.00');
	$('#weightAvg').html('0.00');
	$('#moneyAvg').html('0.00');

	var select = $('#leaveReason').combobox("getValue");
	if( select == 1){
		$("#selectPig").attr({"disabled":"disabled"});
		$("#selectPig").css('background-color','rgb(220, 220, 220)');
		$("#addPig").removeAttr("disabled");
		$("#addPig").css('background-color','rgb(113, 175, 76)');
		rightSlipFun('selectBreedPigWin',780);
	}else if( select == 2 || select == 3){
		$("#addPig").attr({"disabled":"disabled"});
		$("#addPig").css('background-color','rgb(220, 220, 220)');
		$("#selectPig").removeAttr("disabled");
		$("#selectPig").css('background-color','rgb(113, 175, 76)');
		selectBreedPig();
	}else{
		$("#addPig").removeAttr("disabled");
		$("#addPig").css('background-color','rgb(113, 175, 76)');
		$("#selectPig").removeAttr("disabled");
		$("#selectPig").css('background-color','rgb(113, 175, 76)');
		rightSlipFun('selectBreedPigWin',780);
	}
	if(select == 4){
		$('#showCustomerId').combobox('disable');
		$('#showCustomerId').combobox('setValue',$('#farmId').val());
		$('#showCustomerId').combobox('setText',$('#farmName').val());
	}else{
		$('#showCustomerId').combogrid('enable');
		$('#showCustomerId').combobox('reset');
	}
}
/**
 * 猪舍改变方法
 */
function houseChangeFun(newValue,oldValue){
	if(oldValue == ''){
		return;
	}
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].swineryId = '';
	rows[editIndex].swineryIdName = '';
	changeTableDisplayValue('listTable',editIndex,[{
		field:'swineryId',
		value:''
	}]);
	/*setTimeout(function () {
		$('#listTable').datagrid('endEdit',editIndex);
		$('#listTable').datagrid('updateRow',{
			index:editIndex,
			row:rows[editIndex]
		});
	 },100);*/
}
/**
 * 猪舍选择方法
 */
function houseSelectFun(index,row){
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].pigQty = row.pigQty;
	rows[editIndex].minValidDate = row.minValidDate;
	rows[editIndex].maxValidDate = row.maxValidDate;
	rows[editIndex].pigInfo = row.pigInfo;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'pigQty',
		value:rows[editIndex].pigQty
	},{
		field:'minValidDate',
		value:rows[editIndex].minValidDate
	},{
		field:'maxValidDate',
		value:rows[editIndex].maxValidDate
	},{
		field:'pigInfo',
		value:rows[editIndex].pigInfo
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
 * 批次选择方法
 */
function swinerySelectFun(index,row){
	var rows = $('#listTable').datagrid('getRows');
	rows[editIndex].pigQty = row.pigQty;
	rows[editIndex].minValidDate = row.minValidDate;
	rows[editIndex].maxValidDate = row.maxValidDate;
	rows[editIndex].pigInfo = row.pigInfo;
	changeTableDisplayValue('listTable',editIndex,[{
		field:'pigQty',
		value:rows[editIndex].pigQty
	},{
		field:'minValidDate',
		value:rows[editIndex].minValidDate
	},{
		field:'maxValidDate',
		value:rows[editIndex].maxValidDate
	},{
		field:'pigInfo',
		value:rows[editIndex].pigInfo
	}]);
//	setTimeout(function () {
//		$('#listTable').datagrid('endEdit',editIndex);
//		$('#listTable').datagrid('updateRow',{
//			index:editIndex,
//			row:rows[editIndex]
//		});
//	 },100);
}
/**新增行
 * pms{
 * model:明细模块代码
 * }*/
function detailAdd(){
	var isValid = $('#addNum').numberspinner('isValid');
	if(isValid){
		var listTable = $('#listTable');
		var allRows = listTable.datagrid('getRows');
		var length = allRows.length;
		var num = strToInt($('#addNum').val(),10);
		if(length + num > 999){
			$.messager.alert('警告','明细表数据不能超过999行！');
		}else{
			if (endEditing()){
				var leaveReason = $('#leaveReason').combobox('getValue');
				if(leaveReason == "" || leaveReason == null){
					$.messager.alert('提示','请先选择销售类型！');
					return;
				}
				var billDate = $('#billDate').datebox('getValue');
				var data = {success:true,total: length + num};
				var rows = [];
				if(length != 0){
					rows = allRows;
				}
				for(var i = 0 ; i < num ; i++){
					var defaultValues = {
							status:'1',
							deletedFlag:'0',
							saleDate:billDate
						};
					rows.push(defaultValues);
				}
				data.rows = rows;
				listTable.datagrid('loadData',data);
				editIndex = length;
				var fistEditField = getFistEditField(listTable);
				listTable.datagrid('editCell', {index:editIndex,field:fistEditField});
				var editor = listTable.datagrid('getEditor', {index:editIndex,field:fistEditField});
				var eidtCellInput = $(editor.target[0].parentNode).find('.textbox-text.validatebox-text');
				if(eidtCellInput.length == 0){
					$(editor.target)[0].focus();
					$(editor.target).keydown(function(e){
						editCellTabDownFun(e,listTable,editIndex,fistEditField);
				    });
				}else{
					eidtCellInput[0].focus();
					eidtCellInput.keydown(function(e){
						editCellTabDownFun(e,listTable,editIndex,fistEditField);
				    });
				}
			}
		}
	}
}
/**
 * 获取明细表提交数据数据
 */
function collectDetailData(){
	var listTable = $('#listTable');
	var rows =  listTable.datagrid('getRows');
	for(var i = 0 ; i < rows.length ; i ++){
		listTable.datagrid('endEdit',i);
	}
	var queryParams;
	//获取新增行
	var insertRows = listTable.datagrid('getRows');
	//给提交数据加上行号
	var validFlag = true;
	$.each(insertRows,function(index,data){
		if(data.saleNum > data.pigQty){
			$.messager.alert('提醒','第'+(index+1)+'行销售数量大于猪只数量不能不能保存！');
			validFlag = false;
			return false;
		}else{
			insertRows[index].lineNumber = listTable.datagrid('getRowIndex',data)+1;
		}
	});
	if(validFlag){
		var jsonString = JSON.stringify(insertRows);
		queryParams = {
				status:1,
				deletedFlag:0,
				gridList:jsonString
			};
	}else{
		queryParams = false;
	}
	return queryParams;
}
/**
 * 保存
 * pms{
 * 	model:模块代码
 * }
 */
function onBtnSave(){
	var queryParams;
	var listTable = document.getElementById("listTable");
	//判断是否有明细表
	if(listTable!= null){
		queryParams = collectDetailData();
	}else{
		queryParams = {
			status:1,
			deletedFlag:0,
		};
	}
	if(queryParams){
		var leaveReason = $('#leaveReason').combobox('getValue');
		//判断是否是事件保存
		if(typeof(eventName) != "undefined"){
			queryParams.eventName = eventName;
			var saveValidFalg = true;
			var errorEarBrand = null;
			var gridList_eval = eval(queryParams.gridList);
			$.each(gridList_eval,function(i,item){
				if(item.status == -1){
					saveValidFalg = false;
					errorEarBrand = item.earBrand;
					return false;
				}
				/*if(leaveReason == 2 || leaveReason == 3){
					delete item.houseId;
					delete item.swineryId;
				}*/
			});
			queryParams.gridList = JSON.stringify(gridList_eval);
			if(!saveValidFalg){
				$.messager.alert('提示','前台提示--耳牌号：【'+errorEarBrand+'】没有对应的猪只数据，请重新选择！');
				return;
			}
		}
		if(leaveReason == 4){
			queryParams.customerId = $('#farmId').val();
		}else{
			queryParams.customerId = $('#showCustomerId').combobox('getValue');
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
					if(typeof(eventName) == "undefined"){
						$('#editWin').window('close');
						$.messager.alert('提示','保存成功！');
					}else{
						// 单据日期
						$('#headBillDate').html($('#billDate').combo('getText'));
						// 销售类型
						$('#headLeaveReason').html($('#leaveReason').combo('getText'));
						// 销售总重
						$('#headLeaveWeight').html($('#weightSum').html());
						// 客户
						$('#headCustomerName').html($('#showCustomerId').combogrid('getText'));
						//重置
						var preRecord = $('#listTable').datagrid('getData');
						$('#editForm').form('reset');
						leftSilpFun('preSaveRecord',true,9002);
						if(preRecord.success == undefined){
							preRecord.success = true;
						}
						$('#preSaveRecordTable').datagrid('loadData',preRecord);
						if(listTable!= null){
							$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
						}
						//合计清空
						$('#sellNumSum').html('0');
						$('#weightSum').html('0.00');
						$('#moneySum').html('0.00');
						$('#weightAvg').html('0.00');
						$('#moneyAvg').html('0.00');
					}
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
}
/**
 * 计算合计平均值
 */
function calculate(newValue,str){
	if(editIndex == undefined){
		return;
	}
	var listTable = $('#listTable');
	var rows = listTable.datagrid('getRows');
	if(rows.length > 0 ){
		if(str == 'saleNum'){
			var leaveQtySum = 0;
			if(editIndex != undefined && newValue !=0){
				leaveQtySum = strToInt(newValue);
			}
			var totalWeight = 0;
			$.each(rows,function(i,data){
				if(i != editIndex && data.saleNum != undefined){
					leaveQtySum += strToInt(data.saleNum == '' ? 0 : data.saleNum);
				}
				totalWeight += strToInt(data.totalWeight == '' || data.totalWeight == undefined ? 0 : data.totalWeight);
			});
			$('#sellNumSum').html(leaveQtySum);
			var totalWeightAvg = totalWeight == 0 || leaveQtySum == 0 ? 0 :totalWeight/leaveQtySum;
			$('#weightAvg').html((totalWeightAvg).toFixed(2));
		}
		if(str == 'totalWeight'){
			var totalWeightSum = 0;
			var totalWeightAvg = 0;
			var saleSum = 0;
			if(editIndex != undefined && newValue !=0){
				totalWeightSum = strToFloat(newValue);
			}
			$.each(rows,function(i,data){
				saleSum += strToInt(data.saleNum == undefined ? 0 : data.saleNum);
				if(i != editIndex && data.totalWeight != undefined){
					totalWeightSum += strToFloat(data.totalWeight == '' ? 0 : data.totalWeight);
				}
			});
			totalWeightAvg =saleSum == 0 || totalWeightSum == 0 ? 0 : totalWeightSum/saleSum;
			$('#weightSum').html(totalWeightSum.toFixed(2));
			$('#weightAvg').html(totalWeightAvg.toFixed(2));
		}
		if(str == 'totalPrice'){
			var totalPriceSum = 0;
			var totalPriceAvg = 0;
			var saleSum = 0;
			if(editIndex != undefined && newValue !=0){
				totalPriceSum = strToFloat(newValue);
			}
			$.each(rows,function(i,data){
				saleSum += strToInt(data.saleNum == undefined ? 0 : data.saleNum);
				if(i != editIndex && data.totalPrice != undefined){
					totalPriceSum += strToFloat(data.totalPrice == '' ? 0 : data.totalPrice);
				}
			});
			var otherFee = strToFloat($('#otherFee').numberspinner('getValue') == "" ? 0: $('#otherFee').numberspinner('getValue')),
			paymentDiff = strToFloat($('#paymentDiff').numberspinner('getValue') == "" ? 0 : $('#paymentDiff').numberspinner('getValue'));
			totalPriceSum = totalPriceSum - otherFee + paymentDiff;
			totalPriceAvg = totalPriceSum == 0 ? 0 : totalPriceSum/saleSum;
			$('#moneySum').html(totalPriceSum.toFixed(2));
			$('#moneyAvg').html(totalPriceAvg.toFixed(2));
		}
	}
}
function selectBreedPig(){
	var leaveReason = $('#leaveReason').combobox('getValue');
	if(leaveReason == "" || leaveReason == null){
		$.messager.alert('提示','请先选择销售类型！');
		return;
	}
	leftSilpFun('selectBreedPigWin');
	var pigIds = "";
	var rows = $('#listTable').datagrid('getRows');
	if(rows.length > 0){
		$.each(rows,function(i,row){
			if(row.pigIds != undefined){
				if(i == 0){
					pigIds += row.pigIds;
				}else{
					pigIds += ','+row.pigIds;
				}
			}
		});
	}
	if(pigIds == ""){
		pigIds = 0;
	}
	var pigClassGrid = $('#pigClassId').combogrid('grid');
	pigClassGrid.datagrid('reload',basicUrl+'/backEnd/searchPigClassToListByEvent.do?pigType='+oldPigType+'&eventName='+eventName);
	var columnFields = '';
	var columnTitles = '';
	var columnWidths = '';
	var url = '';
	if(leaveReason == '2'){
		$("#claseWinShowBy3").css("display","none");
		$("#searchPigShowBy2").css('display','inline');
		$("#selectPigSearchForm").css('display','inline');
		var earBrand =  $('#earBrand').textbox('getValue');
		var pigClassId = $('#pigClassId').combogrid('getValue');
		var sex = $('#sexName').combogrid('getValue');
		var breedIds = $('#breedIds').combogrid('getValue');
		var parity = $('#parity').textbox('getValue');
		var houseIds = $('#houseIds').combogrid('getValue');
		var swineryIds = $('#swineryIds').combogrid('getValue');
		var goodPigFlag = 1;
		if(!$('#goodPigFlag').is(':checked')){
			goodPigFlag = 0;
		}
		columnFields='pigIds,pigType,earBrand,pigInfo,birthDate,sexName,saleType';
		columnTitles='猪只ID,猪只类别,耳牌号,耳缺号-品种(胎次)-状态-日龄-猪舍-猪栏,出生日期,性别,销售品名';
		columnWidths='80,80,100,400,100,80,80';
		url = '/production/searchValidPigToPage.do?pigIds='+pigIds+"&eventName="+eventName+"&earBrand="+earBrand+"&pigClassIds="+pigClassId+"&goodPigFlag="+goodPigFlag+"&sex="+sex+"&breedIds="+breedIds+"&parity="+parity+"&houseIds="+houseIds+"&swineryIds="+swineryIds;
	}else{
		$("#searchPigShowBy2").css("display","none");
		$("#claseWinShowBy3").css('display','inline');
		$("#selectPigSearchForm").css('display','none');
		columnFields='pigIds,pigType,earBrand,houseName,breedName,pigClassName,lastPigClassName,auditDate,obsoleteReasonName,obsoleteReason,saleType';
		columnTitles='猪只ID,猪只类别,耳牌号,猪舍,品种,状态,之前状态,审批日期,淘汰原因,淘汰原因id,销售品名';
		columnWidths='100,100,100,100,100,100,100,100,100,100,100';
		url = '/production/searchBoarObsoletePass.do?pigIds='+pigIds;
	}
	$('#selectBreedPigTable').datagrid(
			j_grid({
			width:'auto',
			height:'auto',
			url:url,
			columnFields:columnFields,
			columnTitles:columnTitles,
			columnWidths:columnWidths,
			hiddenFields:'pigIds,pigType,saleType,obsoleteReason',
		},'selectBreedPigTable')
	);
}

/**
 * 搜索猪只
 */
function searchPigSearch(){
	var earBrand =  $('#earBrand').textbox('getValue');
	var pigClassId = $('#pigClassId').combogrid('getValue');
	var parity = $('#parity').numberspinner('getValue');
	var houseIds = $('#houseIds').combogrid('getValue');
	var swineryIds = $('#swineryIds').combogrid('getValue');
	var breedIds = $('#breedIds').combogrid('getValue');
	var sex = $('#sexName').combogrid('getValue');
	var goodPigFlag = 1;
	if(!$('#goodPigFlag').is(':checked')){
		goodPigFlag = 0;
	}
	var hasSelectRows = $('#listTable').datagrid('getData').rows;
	var pigIdsArray = [];
	$.each(hasSelectRows,function(i,data){
		if(data.pigIds != null && data.pigIds != ''){
			pigIdsArray.push(data.pigIds);
		}
	});
	var pigIds = '';
	$.each(pigIdsArray,function(i,data){
		if(i == pigIdsArray.length - 1){
			pigIds += data;
		}else{
			pigIds += data + ',';
		}
	});
	$('#selectBreedPigTable').datagrid('load',basicUrl+'/production/searchValidPigToPage.do?queryType='+2+'&choice='+1+'&eventName='+eventName+'&earBrand='+earBrand
			+'&pigClassIds='+pigClassId+'&pigIds='+pigIds+'&parity='+parity+'&houseIds='+houseIds+'&goodPigFlag='+goodPigFlag+"&breedIds="+breedIds+"&sex="+sex+"&swineryIds="+swineryIds
			/*{
    	queryType:2,
    	pigType:4,
    	specifyPigs:0,
    	choice:1,
    	eventName:eventName,
    	earBrand:earBrand,
    	pigClassIds:pigClassId,
    	pigIds:pigIds
    }*/);
}

function selectBreedPigSure(){
	var leaveReason = $('#leaveReason').combobox('getValue');
	var rows =  $('#selectBreedPigTable').datagrid('getSelections');
	if(rows.length < 1){
		$.messager.alert('提示',"请选择数据！");
	}else{
		var pigIds = "";
		var houseId = "";
		var houseIdName = "";
		var swineryId = "";
		var swineryIdName = "";
		var obsoleteReasons = "";
		var sex = "";
		var sexName = "";
		var breedId = "";
		var saleType = null;
		var breedId = null;
		var breedName = null;
		var errFlag = false;
		$.each(rows,function(i,row){
			if(i == 0){
				if(row.saleType == 1 || row.saleType == 2 || row.saleType == 6 || row.saleType == 7 || row.saleType == 8 || row.saleType == 9 || row.saleType == 10){
					pigIds += row.pigId;
					obsoleteReasons = '';
				}
				if(leaveReason == 3){
					obsoleteReasons += ',' + row.obsoleteReason
				}
				houseId = row.houseId;
				houseIdName = row.houseName;
				swineryId = row.swineryId;
				swineryIdName = row.swineryName;
				sex = row.sex;
				sexName = row.sexName;
				saleType = row.saleType;
				breedId = row.breedId;
				breedName = row.breedName;
			}else{
				if(leaveReason == 2 && (saleType != row.saleType || sex != row.sex || breedId != row.breedId || houseId != row.houseId || swineryId != row.swineryId)){
					errFlag = true;
				}else if(((leaveReason == 3 || leaveReason == 4) && toHana == "OFF" && (saleType != row.saleType || sex != row.sex)) || 
						((leaveReason == 3 || leaveReason == 4) && toHana == "ON" && (saleType != row.saleType || sex != row.sex || breedId != row.breedId || houseId != row.houseId || swineryId != row.swineryId))){
					errFlag = true;
				}else{
					if(row.saleType == 1 || row.saleType == 2 || row.saleType == 6 || row.saleType == 7 || row.saleType == 8 || row.saleType == 9 || row.saleType == 10){
						pigIds += ',' + row.pigId;
					}
					if(leaveReason == 3){
						obsoleteReasons += ',' + row.obsoleteReason
						if(toHana == "OFF"){
							houseIdName = "";
						}
					}
				}
			}
			breedId = row.breedId;
		});
		if(errFlag){
			if(toHana == "ON"){
				$.messager.alert('警告',"只能同时选择相同销售类型，相同性别，相同品名，相同猪舍/猪群的猪只！");
			}else{
				$.messager.alert('警告',"只能同时选择同一类型猪只！");
			}
		}else{
			if(houseIdName == 'null' || houseIdName == 'undefined'){
				houseIdName = "";
			}
			if(swineryIdName == 'null'|| swineryIdName == 'undefined'){
				swineryIdName = "";
			}
			if(houseId == 'null' || houseId == 'undefined'){
				houseId = "";
			}
			if(swineryId == 'null'|| swineryId == 'undefined'){
				swineryId = "";
			}
			var row = {
					status:'1',
					deletedFlag:'0',
					pigIds:pigIds,
					pigQty:rows.length,
					saleNum:rows.length,
					houseId:houseId,
					houseIdName:houseIdName,
					swineryId:swineryId,
					swineryIdName:swineryIdName,
					sex:sex,
					sexName:sexName,
					breedId:breedId,
					breedName:breedName,
					obsoleteReasons:obsoleteReasons,
					saleDate:$('#billDate').datebox('getValue')
			};
			if(saleType == 1){
				row.saleDescribe = 1;
				row.saleDescribeName = "生产公猪";
			}else if(saleType == 2){
				row.saleDescribe = 2;
				row.saleDescribeName = "生产母猪";
			}else if(saleType == 6){
				row.saleDescribe = 6;
				row.saleDescribeName = "后备公猪";
			}else if(saleType == 7){
				row.saleDescribe = 7;
				row.saleDescribeName = "后备母猪";
			}else if(saleType == 8){
				row.saleDescribe = 8;
				row.saleDescribeName = "留种公猪";
			}else if(saleType == 9){
				row.saleDescribe = 9;
				row.saleDescribeName = "留种母猪";
			}else if(saleType == 10){
				row.saleDescribe = 10;
				row.saleDescribeName = "留种猪";
			}
			$('#listTable').datagrid('appendRow',row);
			
			rightSlipFun('selectBreedPigWin',780);
			var sellNumSum = strToInt($('#sellNumSum').	html());
			$('#sellNumSum').html(sellNumSum+rows.length);
		}
	}
}
/**
 * 删除明细
 */
function detailDelete(){
	var listTable = $('#listTable');
	var record = listTable.datagrid('getSelections');
	var length = record.length;
	if(length < 1){
		$.messager.alert('警告','请选择一条数据！');
	}else{
		$.messager.confirm('提示', '确定要删除这'+record.length+'条记录吗？', function(r){
			if (r){
				if(endEditing()){
					$.each(record,function(index,data){
						var row = listTable.datagrid('getRowIndex',data);
						data.deletedFlag = "1";
						listTable.datagrid('deleteRow',row);
					});
					var listRows = listTable.datagrid('getRows');
					if(listRows.length <= 0){
						$('#sellNumSum').html('0');
						$('#weightSum').html('0.00');
						$('#moneySum').html('0.00');
						$('#weightAvg').html('0.00');
						$('#moneyAvg').html('0.00');
					}else{
						var sellNumSum = 0,weightSum = 0,moneySum = 0;
						$.each(listRows,function(i,row){
							sellNumSum += strToInt(row.saleNum == undefined ? 0 : row.saleNum);
							weightSum += strToFloat(row.totalWeight == undefined ? 0 : row.totalWeight);
							moneySum += strToFloat(row.totalPrice == undefined ? 0 : row.totalPrice);
						});
						var otherFee = strToFloat($('#otherFee').numberspinner('getValue') == "" ? 0 : $('#otherFee').numberspinner('getValue')),
						paymentDiff = strToFloat($('#paymentDiff').numberspinner('getValue') == "" ? 0 : $('#paymentDiff').numberspinner('getValue'));
						moneySum = moneySum - otherFee + paymentDiff;
						$('#sellNumSum').html(sellNumSum);
						$('#weightSum').html(weightSum.toFixed(2));
						$('#moneySum').html(moneySum.toFixed(2));
						$('#weightAvg').html((weightSum/sellNumSum).toFixed(2));
						$('#moneyAvg').html((moneySum/sellNumSum).toFixed(2));
					}
				}
			}
		});
	}
}
/**
 * 清空
 */
function detailClear(){
	var listTable = $('#listTable');
	$.messager.confirm('提示', '确定要清空吗？', function(r){
		if (r){
			$('#listTable').datagrid('loadData',{success:true,total: 0, rows: [] });
			$('#sellNumSum').html('0');
			$('#weightSum').html('0.00');
			$('#moneySum').html('0.00');
			$('#weightAvg').html('0.00');
			$('#moneyAvg').html('0.00');
		}
	});
}
/**
 * 费用改变方法
 * @param newValue
 * @param oldValue
 */
function otherFeeChange(newValue,oldValue){
	if(newValue == ''){
		newValue = 0;
	}
	var listRows = $('#listTable').datagrid('getRows');
	if(listRows.length <= 0){
		return;
	}
	var moneySum = 0,saleNum = 0;
	$.each(listRows,function(i,row){
		moneySum += strToFloat(row.totalPrice == undefined ? 0 : row.totalPrice);
		saleNum += strToInt(row.saleNum == undefined ? 0 : row.saleNum);
	});
	var paymentDiff = strToFloat($('#paymentDiff').numberspinner('getValue') == "" ? 0 : $('#paymentDiff').numberspinner('getValue'));
	moneySum = (moneySum - strToFloat(newValue) + paymentDiff).toFixed(2);
	var moneyAvg = (moneySum/saleNum).toFixed(2);
	$('#moneySum').html(moneySum);
	$('#moneyAvg').html(moneyAvg);
}
/**
 * 货款差异改变方法
 * @param newValue
 * @param oldValue
 */
function paymentDiffChange(newValue,oldValue){
	if(newValue == ''){
		newValue = 0;
	}
	var listRows = $('#listTable').datagrid('getRows');
	if(listRows.length <= 0){
		return;
	}
	var moneySum = 0,saleNum = 0;
	$.each(listRows,function(i,row){
		moneySum += strToFloat(row.totalPrice == undefined ? 0 : row.totalPrice);
		saleNum += strToInt(row.saleNum == undefined ? 0 : row.saleNum);
	});
	var otherFee = strToFloat($('#otherFee').numberspinner('getValue') == "" ? 0 : $('#otherFee').numberspinner('getValue'));
	moneySum = (moneySum - otherFee + strToFloat(newValue)).toFixed(2);
	var moneyAvg = (moneySum/saleNum).toFixed(2);
	$('#moneySum').html(moneySum);
	$('#moneyAvg').html(moneyAvg);
}
//种猪销售重置
function onBtnReSearch(){
	$('#selectPigSearchForm').form('reset');
}

function houseIdsChange(newValue, oldValue){
	$("#swineryIds").combogrid('setValue', null);
	$("#swineryIds").combogrid('grid').datagrid('reload',basicUrl+'/production/searchPorkToList.do?lineId=0&pigType=3&houseId='+newValue+'&eventName='+eventName)
}