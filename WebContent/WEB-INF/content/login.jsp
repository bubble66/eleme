<%@ page contentType="text/html; charset=gb2312" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��¼ϵͳ</title>
<body>
<s:form action="processLogin">
	<s:textfield name="manager.name" label="�û���"/>
	<s:textfield name="manager.pass" label="����"/>
	<s:textfield name="vercode" label="��֤��"/>
	<tr><td colspan="2">
	<s:submit value="��¼" theme="simple"/><s:reset  theme="simple" value="����"/>
	</td></tr>
</s:form>
</body>
</html>
