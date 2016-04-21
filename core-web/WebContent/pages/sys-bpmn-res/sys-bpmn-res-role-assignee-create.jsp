<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="gs" uri="http://www.gsweb.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!doctype html>
<html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <base href="<%=basePath%>">
    
    <title>bambooCORE</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="bambooCORE">
	<meta http-equiv="description" content="bambooCORE">
	
<style type="text/css">

</style>

<script type="text/javascript">

var CORE_PROG003D0005A_fieldsId = new Object();
CORE_PROG003D0005A_fieldsId['resourceOid'] 	= 'CORE_PROG003D0005A_resourceOid';
CORE_PROG003D0005A_fieldsId['roleOid'] 		= 'CORE_PROG003D0005A_roleOid';
CORE_PROG003D0005A_fieldsId['taskName'] 	= 'CORE_PROG003D0005A_taskName';

function CORE_PROG003D0005A_saveSuccess(data) { // data 是 json 資料
	setFieldsBackgroundDefault(CORE_PROG003D0005A_fieldsId);
	alertDialog(_getApplicationProgramNameById('${programId}'), data.message, function(){}, data.success);	
	if ('Y' != data.success) {						
		setFieldsBackgroundAlert(data.fieldsId, CORE_PROG003D0005A_fieldsId);		
		return;
	}	
	CORE_PROG003D0005A_clear();
}

function CORE_PROG003D0005A_clear() {
	setFieldsBackgroundDefault(CORE_PROG003D0005A_fieldsId);
	dijit.byId('CORE_PROG003D0005A_resourceOid').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG003D0005A_roleOid').set("value", _gscore_please_select_id);
	dijit.byId('CORE_PROG003D0005A_taskName').set("value", "");
}

//------------------------------------------------------------------------------
function ${programId}_page_message() {
	var pageMessage='<s:property value="pageMessage" escapeJavaScript="true"/>';
	if (null!=pageMessage && ''!=pageMessage && ' '!=pageMessage) {
		alert(pageMessage);
	}	
}
//------------------------------------------------------------------------------

</script>

</head>

<body class="flat" bgcolor="#EEEEEE" >

	<gs:toolBar
		id="${programId}" 
		cancelEnable="Y" 
		cancelJsMethod="${programId}_TabClose();" 
		createNewEnable="N"
		createNewJsMethod=""		 
		saveEnabel="Y" 
		saveJsMethod="CORE_PROG003D0005A_save();"
		refreshEnable="Y" 		 
		refreshJsMethod="${programId}_TabRefresh();" 		
		></gs:toolBar>
	<jsp:include page="../header.jsp"></jsp:include>
	
	<table border="0" width="100%" height="200px" cellpadding="1" cellspacing="0" >
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Resource</b>:
    			<br/>
    			<gs:select name="CORE_PROG003D0005A_resourceOid" dataSource="resourceMap" id="CORE_PROG003D0005A_resourceOid" width="250"></gs:select>
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Role</b>:
    			<br/>
    			<gs:select name="CORE_PROG003D0005A_roleOid" dataSource="roleMap" id="CORE_PROG003D0005A_roleOid"></gs:select>
    		</td>    		
    	</tr>
		<tr>
    		<td height="50px" width="100%"  align="left">
    			<font color='RED'>*</font><b>Task name</b>:
    			<br/>
    			<gs:textBox name="CORE_PROG003D0005A_taskName" id="CORE_PROG003D0005A_taskName" maxlength="100" width="200"></gs:textBox>
    		</td>    		
    	</tr>
    	<tr>
    		<td height="50px" width="100%"  align="left">
    			<gs:button name="CORE_PROG003D0005A_save" id="CORE_PROG003D0005A_save" onClick="CORE_PROG003D0005A_save();"
    				handleAs="json"
    				sync="N"
    				xhrUrl="core.systemBpmnResourceRoleAssigneeSaveAction.action"
    				parameterType="postData"
    				xhrParameter=" 
    					{ 
    						'fields.resourceOid'	: dijit.byId('CORE_PROG003D0005A_resourceOid').get('value'),
    						'fields.roleOid'		: dijit.byId('CORE_PROG003D0005A_roleOid').get('value'),
    						'fields.taskName'		: dijit.byId('CORE_PROG003D0005A_taskName').get('value')
    					} 
    				"
    				errorFn=""
    				loadFn="CORE_PROG003D0005A_saveSuccess(data);" 
    				programId="${programId}"
    				label="Save" 
    				iconClass="dijitIconSave"
    				cssClass="alt-primary"></gs:button>    			
    			<gs:button name="CORE_PROG003D0005A_clear" id="CORE_PROG003D0005A_clear" onClick="CORE_PROG003D0005A_clear();" 
    				label="Clear" 
    				iconClass="dijitIconClear"
    				cssClass="alt-primary"></gs:button>    			
    		
    		</td>
    	</tr>     	 	  	    	
	</table>	

<script type="text/javascript">${programId}_page_message();</script>
</body>
</html>
