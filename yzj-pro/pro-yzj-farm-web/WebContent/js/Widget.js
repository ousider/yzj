/**
 * 控件封装
 */
/**
 * ComboGrid表格下拉框
 * pms{
 * id:div 的Id ，必填项
 * width:宽度，
 * iDField:实际值,必填项
 * textField:显示值,必填项
 * url:数据获取路径,必填项
 * columns:表格显示列,必填项
 * required:是否必填，默认为false
 * }
 */
function xnComboGrid(pms){
	var editable = pms.editable == null ? false : pms.editable;
	$('#'+pms.id).combogrid({
		panelWidth:pms.width == null ? 450 : pms.width,
	    idField:pms.idField,
	    textField:pms.textField,
	    url:pms.url == null ? null : basicUrl+pms.url,
	    method: 'get',
	    columns:pms.columns,
	    fitColumns: true,
	    rownumbers:true,
	    multiple:pms.multiple == null ? false : pms.multiple,
		editable:editable,
		readonly:pms.readonly == null ? false : pms.readonly,
		disabled:pms.disabled == null ? false : pms.disabled,
	    required:pms.required == null ? false : pms.required,
		rowStyler: function(index,row){
			if ((index+1) % 2 == 0){
				return 'background-color:#f7f7f7;';
			}
		},
		validType:editable == false ? null :'combogrid_validateExist["'+pms.id+'","'+pms.textField+'"]',
	    filter: function(q, row){
			var opts = $(this).combogrid('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		onChange:pms.onChange == null ? function(newValue,oldValue){
			return;
		}:pms.onChange,
		onSelect:pms.onSelect == null ? function(index,row){
			return;
		}:pms.onSelect,
		onBeforeSelect:pms.onBeforeSelect == null ? function(index,row){
			return;
		}:pms.onBeforeSelect,
		prompt:pms.prompt
	});
}
/**
 * combobox通过Url获取数据下拉框
 *pms{
 * id:div 的Id ，必填项
 * valueField:实际值,必填项
 * textField:显示值,必填项
 * url:数据获取路径,必填项
 * required:是否必填，默认为false
 * } 
 */
function xnCombobox(pms){
	var editable = pms.editable==null?true:pms.editable ;
	var data = [];
	if(pms.url){
        data = jAjax.submit(pms.url);
    	var includeValue = pms.includeValue;
    	var excludeValue = pms.excludeValue;
    	
		if(includeValue != null){
			var newData = [];
			$.each(data,function(index,row){
				var isExist = false;
				$.each(includeValue,function(i,ev){
					if(row[pms.valueField] == ev){
						isExist = true;
					}
				});
				if(isExist){
					newData.push(row);
				}
	    	});
			data = newData;
		}
		
		if(excludeValue != null){
			var newData = [];
			$.each(data,function(index,row){
				var isExist = false;
				$.each(excludeValue,function(i,ev){
					if(row[pms.valueField] == ev){
						isExist = true;
					}
				});
				if(!isExist){
					newData.push(row);
				}
	    	});
			data = newData;
		}
	}
	
    var firstDataIsDefault = pms.firstDataIsDefault==null?false:pms.firstDataIsDefault ;
    var hasAll = pms.hasAll == null ? false : pms.hasAll;
	$('#'+pms.id).combobox({
	    //url:basicUrl+pms.url,
	    data:data,
	    valueField:pms.valueField,
	    textField:pms.textField,
	    required:pms.required == null ? false : pms.required,
		panelHeight:pms.panelHeight == null ? 'auto' : pms.panelHeight,
		panelMaxHeight:300,
	    value:firstDataIsDefault == true ? data[0][pms.valueField]:
	    	pms.value == null ? pms.multiple ? [] : pms.value : pms.value,
	    readonly:pms.readonly == null ? false : pms.readonly,
	    editable:editable,
		multiple:pms.multiple == null ? false : pms.multiple,
		validType:editable== false ? null : 'combobox_validateExist["'+pms.id+'","'+pms.textField+'"]',
	    filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		loadFilter:function(data){
			if(hasAll){
				var allData = new Object(); 
				allData[pms.textField] = "全部";
				allData[pms.valueField] = "";
				data.unshift(allData);
			}
			return data;
		},
		onSelect:pms.onSelect == null ? function(row){return;}:pms.onSelect,
		onChange:pms.onChange == null ? function(newValue,oldValue){return;}: pms.onChange,
		prompt:pms.prompt
	});
}



/**************************************************下拉框封装BEGIN**********************************************************/
/**
 * 获取cdCode的数据 
 * j_typeCode
 * j_linkValue
 */
function jGetCdCode(j_typeCode,j_linkValue){

	return jAjax.submit_dir('/param/searchByTypeCode.do', {typeCode:j_typeCode,linkValue:j_linkValue});
}
/**
 * 组建下拉框
 * @param pms
 */
function jCdCombobox(pms,j_data,j_defaultValue){
	var editable = pms.editable==null?true:pms.editable ;
	var hasAll = pms.hasAll == null ? false : pms.hasAll;
	$('#'+pms.id).combobox({
		data:j_data,
	    valueField:pms.sameField == true ? 'codeName':'codeValue',
	    textField:'codeName',
	    panelHeight:pms.panelHeight==null?'auto':pms.panelHeight,
	    editable:editable,
	    required:pms.required == null ? false : pms.required,
	    value:pms.value == null ? j_defaultValue : hasAll ? "" : pms.value,
		readonly:pms.readonly == null ? false : pms.readonly,
	    panelMaxHeight:300,
	    multiple:pms.multiple == null ? false : pms.multiple,
	    filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		loadFilter:function(data){
			if(hasAll){
				data.unshift({
					codeName:"全部",
					codeValue:""
				});
			}
			return data;
		},
		validType:editable == false ? null : 'combobox_validateExist["'+pms.id+'","codeName"]',
		onChange:pms.onChange,
		onSelect:pms.onSelect == null ? function(newValue,oldValue){
			return;
		}:pms.onSelect,
		prompt:pms.prompt
	});
}

/**
 *pms{
 * CdComboBox根据CdCode获取下拉框值
 * id:div 的Id ，必填项
 * typeCode:查询的CdCodeType的typeCode值，必填项 ,
 * linkField:下级下拉框id，如有级联必填
 * linkCode:下级下拉框typeCode值，如有级联必填
 * required:是否必填，默认为false
 * } 
 */
function xnCdCombobox(pms,j_linkValue){
	var data=jGetCdCode(pms.typeCode,j_linkValue);
	var defaultValue = pms.defaultValue;
	var includeValue = pms.includeValue;
	var excludeValue = pms.excludeValue;
	if(defaultValue == null){
		if(pms.multiple){
			defaultValue = [];
		}
		$.each(data,function(index,row){
			if(row.isDefault == 'Y'){
				if(pms.multiple){
					defaultValue.push(row.codeValue);
				}else{
					defaultValue = row.codeValue;
				}
			}
		});
	}
	
	if(includeValue != null){
		var newData = [];
		$.each(data,function(index,row){
			var isExist = false;
			$.each(includeValue,function(i,ev){
				if(row.codeValue == ev){
					isExist = true;
				}
			});
			if(isExist){
				newData.push(row);
			}
    	});
		data = newData;
	}
	
	if(excludeValue != null){
		var newData = [];
		$.each(data,function(index,row){
			var isExist = false;
			$.each(excludeValue,function(i,ev){
				if(row.codeValue == ev){
					isExist = true;
				}
			});
			if(!isExist){
				newData.push(row);
			}
    	});
		data = newData;
	}
	
	// 如果既有级联又有onChange方法，补全onChange方法
	var onChangeFunction = pms.onChange != null ? pms.onChange:function(newValue,oldValue){};
	
	pms.onChange = function(newValue,oldValue){
    	//如果有级联
		if(pms.linkCode != null && pms.linkField != null){ 
			var linkValue;
			if(!newValue){
				linkValue = -1;
			}else{
				linkValue = newValue;
			}
			var linkData = jGetCdCode(pms.linkCode,linkValue);
			var linkDefaultValue = '';
			$.each(linkData,function(index,row){
				if(row.isDefault == 'Y'){
					linkDefaultValue = row.codeValue;
				}
			});
			if(oldValue != '' && oldValue != null){
				$('#'+pms.linkField).combobox('setValue', linkDefaultValue);
			}
			
			$('#'+pms.linkField).combobox('loadData', linkData);
		}
		
		onChangeFunction(newValue,oldValue);
	};
	
	/*pms.onSelect = pms.onSelect != null ?pms.onSelect:function(rec){
		//如果有级联
		if(pms.linkCode != null && pms.linkField != null){ 
			 var linkData =	jGetCdCode(pms.linkCode,rec.codeValue);
			 var linkDefaultValue = null;
			 $.each(linkData,function(index,row){
					if(row.isDefault == 'Y'){
						linkDefaultValue = row.codeValue;
					}
				});
			 $('#'+pms.linkField).combobox('setValue', linkDefaultValue);
			 $('#'+pms.linkField).combobox('loadData', linkData);
		}
	}*/
	
	jCdCombobox(pms,data,defaultValue);
}
/**
 * 根据cdCode获取radiobox
 * pms{
 * id:页面元素ID
 * name:页面元素name
 * typeCode:cdCode中TYPE_CODE
 * onChange：改变事件
 * }
 */
function xnRadioBox(pms){
    var excludeValue = pms.excludeValue;
	var height = pms.height == null ? 32 : pms.height,
		width = pms.width == null ? '100%' : pms.height;
	var html = '';
	var codeList = jGetCdCode(pms.typeCode);
	if(excludeValue != null){
		var newData = [];
		$.each(codeList,function(i,data){
			var isExist = false;
			$.each(excludeValue,function(i,ev){
				if(data.codeValue == ev){
					isExist = true;
				}
			});
			if(!isExist){
				newData.push(data);
			}
		});
		codeList = newData;
	}
	$.each(codeList,function(index,data){
		html += '<input type="radio" name="'+pms.name+'" value="'+data.codeValue+'"';
		if(pms.onChange){
			html += 'onclick="on'+UpperFirstLetter(pms.id)+'Change(this)"';
		}
		if(data.isDefault == 'Y' || pms.defaultValue == data.codeValue ){
			html += 'checked="checked" />';
		}else{
			html += '/>';
		}
		html += data.codeName;
	});
	document.getElementById(pms.id).innerHTML = html;
	
}
/**
 * 根据cdCode获取checkBox
 * pms{
 * id:页面元素ID
 * name:页面元素name
 * typeCode:cdCode中TYPE_CODE
 * onChange：改变事件
 * }
 */
function xnCheckBox(pms){
    var excludeValue = pms.excludeValue;
	var height = pms.height == null ? 32 : pms.height,
		width = pms.width == null ? '100%' : pms.height;
	var html = '';
	var codeList = jGetCdCode(pms.typeCode);
	if(excludeValue != null){
		var newData = [];
		$.each(codeList,function(i,data){
			var isExist = false;
			$.each(excludeValue,function(i,ev){
				if(data.codeValue == ev){
					isExist = true;
				}
			});
			if(!isExist){
				newData.push(data);
			}
		});
		codeList = newData;
	}
	var num = pms.flex == null?Math.floor(24/codeList.length):Math.floor(24/pms.flex);
	$.each(codeList,function(index,data){
		html += '<div class="wd-com wd-'+num+'"><input type="checkbox" name="'+pms.name+'" value="'+data.codeValue+'"';
		if(pms.onChange){
			html += 'onclick="on'+UpperFirstLetter(pms.id)+'Change()"';
		}
		if(data.isDefault == 'Y' || pms.defaultValue == data.codeValue ){
			html += 'checked="checked" />';
		}else{
			html += '/>';
		}
		html += data.codeName+'</div>';
	});
	document.getElementById(pms.id).innerHTML = html;
}

/**************************************************下拉框封装END**********************************************************/

/*************************************gird编辑框*******************************************************************/
/**
 * gird编辑下拉框
 *pms{
 * valueField:实际值,必填项
 * textField:显示值,必填项
 * url:数据获取路径,必填项
 * required:是否必填，默认为false
 * } 
 * } 
 */
function xnGridComboBox(pms){
	var editable = pms.editable == null ? false : pms.editable;
	var data = [];
	if(pms.url){
		data = jAjax.submit(pms.url);
	
		if(data == undefined){
			data = jAjax.submit_dir(pms.url);
		}
	}
	var result = {};
	var listTableId = pms.listTableId == null ? 'listTable' : pms.listTableId;
	result.type = 'combobox';
	result.options = {
			valueField:pms.valueField,
			textField:pms.textField,
			required:pms.required == null ? false : pms.required,
			panelMaxHeight:300,		
			panelHeight:pms.panelHeight == null ? 'auto' : pms.panelHeight,
			method:'get',
			data:data,
			//url:basicUrl+pms.url,
			editable:pms.editable == null ? false : pms.editable,
			validType:editable == false ? null : 'grid_combobox_validateExist["'+pms.field+'","'+pms.textField+'","'+listTableId+'"]',
			filter: function(q, row){
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) == 0;
			},
			onChange:pms.onChange ==null ?function(newValue,oldValue){return false}:pms.onChange
	};
	return result;
}
/**
 * gird编辑获取CdCode下拉框
 *pms{
 * typeCode:查询的CdCodeType的typeCode值，必填项 ,
 * linkField:下级下拉框id，如有级联必填
 * linkCode:下级下拉框typeCode值，如有级联必填
 * required:是否必填，默认为false
 * } 
 */
function xnGridCdComboBox(pms){
	var editable = pms.editable == null ? true : pms.editable;
	var excludeValue = pms.excludeValue;
	var data = [];
	var defaultValue = '';
	if(pms.data == null){
		$.ajax({  
	        url : basicUrl+'/param/searchByTypeCode.do?typeCode='+pms.typeCode,
	        async : false, 
	        type : "POST",  
	        dataType : "json",  
	        success : function(response) { 
	        	data = response;
	        	$.each(data,function(index,row){
	        		if(row.isDefault == 'Y'){
	        			defaultValue = row.codeValue;
	        		}
	        	});
	        }  
	    });
	}else{
		data = pms.data;
	}
	if(excludeValue != null){
		var newData = [];
		$.each(data,function(index,row){
			var isExist = false;
			$.each(excludeValue,function(i,ev){
				if(row.codeValue == ev){
					isExist = true;
				}
			});
			if(!isExist){
				newData.push(row);
			}
    	});
		data = newData;
	}
	var result = {};
	var listTableId = pms.listTableId == null ? 'listTable' : pms.listTableId;
	result.type = 'combobox';
	result.options = {
		data:data,
		value:defaultValue,
	    valueField:'codeValue',
	    textField:'codeName',
	    panelHeight:'auto',
	    editable:editable,
	    multiple:pms.multiple,
	    required:pms.required == null ? false : pms.required,
		validType:editable == false ? null : 'grid_combobox_validateExist["'+pms.field+'","codeName","'+listTableId+'"]',
		panelMaxHeight:300,	
	    filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		onChange:pms.onChange == null ? function(newValue,oldValue){
			return;
		}:pms.onChange,
		onSelect:pms.onSelect != null ?pms.onSelect:function(rec){
			focusEditCell(listTableId,editIndex,pms.field);
			if(pms.linkCode != null && pms.linkField != null){ //如果有级联
				var url = basicUrl+'/param/searchByTypeCode.do?typeCode='+pms.linkCode+'&linkValue='+rec.codeValue;
				 $('#'+pms.linkField).combobox('setValue', null);
				 $('#'+pms.linkField).combobox('reload', url);
			}
		}
	};
	return result;
}
function xnGridComboGrid(pms){
	var editable = pms.editable == null ? false : pms.editable;
	var result = {};
	result.type = 'combogrid';
	var listTableId = pms.listTableId == null ? 'listTable' : pms.listTableId;
	result.options = {
			panelWidth:pms.width == null ? 450 : pms.width,
			idField:pms.idField,
			textField:pms.textField,
			required:pms.required == null ? false : pms.required,
			method:'get',
			url:pms.url == null ? null :basicUrl+pms.url,
			editable:editable,
			disabled:pms.disabled == null?false:pms.disabled,
			columns:pms.columns,
		    fitColumns: true,
		    rownumbers:true,
		    loadMsg:'数据加载中……',
		    multiple:pms.multiple == null ? false : pms.multiple,
		    validType:editable == false ? null : 'grid_combogrid_validateExist["'+pms.field+'","'+pms.textField+'"]',
		    rowStyler: function(index,row){
				if ((index+1) % 2 == 0){
					return 'background-color:#f7f7f7;';
				}
			},
		    onChange:pms.onChange == null ? function(newValue,oldValue){
				return;
			}:pms.onChange,
			onSelect:pms.onSelect == null ? function(index,row){
				focusEditCell(listTableId,editIndex,pms.field);
			}:function(index,row){
				focusEditCell(listTableId,editIndex,pms.field);
				pms.onSelect(index,row);
			},
			filter: function(q, row){
				var opts = $(this).combogrid('options');
				return row[opts.textField].indexOf(q) == 0;
			},
			keyHandler:{
				up: function(e){},
				down: function(e){},
				left: function(e){},
				right: function(e){},
				enter: function(e){
					var selection = $(this).combogrid('grid').datagrid('getSelected');
					if(selection){
						focusEditCell(listTableId,editIndex,pms.field);
						$(this).combogrid('hidePanel');
						$(this).combogrid('setValue',selection.rowId);
					}
				},
				query: pms.query == null ? function(q,e){
					var rows = $(this).combogrid('grid').datagrid('getRows');
					var targetGrid = $(this).combogrid('grid');
					var opts = $(this).combogrid('options');
					for(var i = 0 ; i < rows.length; i ++ ){
						var index = targetGrid.datagrid('getRowIndex',rows[i]);
						if(rows[i][opts.textField] == q){
							targetGrid.datagrid('selectRow',index);
							break;
						}else if(rows[i][opts.textField].indexOf(q) != -1){
							targetGrid.datagrid('highlightRow',index);
							break;
						}
					}
					if(q == ''){
						var selectRow = targetGrid.datagrid('getSelected');
						var selectRowIndex = targetGrid.datagrid('getRowIndex',selectRow);
						if(selectRowIndex != -1){
							targetGrid.datagrid('unselectRow',selectRowIndex);
						}
					}
				}:pms.query
			}
	};
	return result;
}
function xnMonthSelect(pms){
	  var db = $('#'+pms.id);
      db.datebox({
          onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
              span.trigger('click'); //触发click事件弹出月份层
              if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                  tds = p.find('div.calendar-menu-month-inner td');
                  tds.click(function (e) {
                      e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                      var year = /\d{4}/.exec(span.html())[0]//得到年份
                      , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                      db.datebox('hidePanel')//隐藏日期对象
                      .datebox('setValue', year + '-' + month); //设置日期的值
                  });
              }, 0);
              yearIpt.unbind();//解绑年份输入框中任何事件
          },
          parser: function (s) {
              if (!s) return new Date();
              var arr = s.split('-');
              return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
          },
          formatter: function (d) { 
        	  var month = d.getMonth() + 1;
              if(month < 9){
            	  month = '0'+ month 
              }
              return d.getFullYear() + '-' + month;
    	  }
      });
      var p = db.datebox('panel'), //日期选择对象
          tds = false, //日期选择对象中月份
          aToday = p.find('a.datebox-current'),
          yearIpt = p.find('input.calendar-menu-year'),//年份输入框
          //显示月份层的触发控件
          span = aToday.length ? p.find('div.calendar-title span') ://1.3.x版本
          p.find('span.calendar-text'); //1.4.x版本
      if (aToday.length) {//1.3.x版本，取消Today按钮的click事件，重新绑定新事件设置日期框为今天，防止弹出日期选择面板
         
          aToday.unbind('click').click(function () {
              var now=new Date();
              var month = now.getMonth() + 1;
              if(month < 9){
            	  month = '0'+ month 
              }
              db.datebox('hidePanel').datebox('setValue', now.getFullYear() + '-' + month);
          });
      }
}
function xnCombotree(pms){
	$('#'+pms.id).combotree({
		textField:pms.textField
	});
	if(pms.url){
        jAjax.submit(pms.url,null,function(result){
        	$('#'+pms.id).combotree('loadData',result);
        },null,null,true);
	}
}