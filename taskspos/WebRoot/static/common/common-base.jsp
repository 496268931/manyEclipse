<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--taglib--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="q" uri="/WEB-INF/tld/qyh.tld" %>

<%--env variable--%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<c:set var="staticPath" value="http://zrbcxz.chinazrbc.com/console"/>
<c:set var="ftpPath" value="http://192.168.1.231:8002/image"/>

<%--css--%>
<link rel="stylesheet" href="${staticPath}/static/css/common_table.css">
<%--script--%>
<script type="text/javascript" src="${staticPath}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/jsTools.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/common_table.js"></script>
<%-- <script type="text/javascript" src="${basePath}/static/js/common/utils.js"></script> --%>
<script type="text/javascript" src="${basePath}/static/js/common/common_oper.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/object_factory.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/Namespace.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${staticPath}/static/plugins/DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
    var basePath = '${pageContext.request.contextPath}';
    var staticPath = '${staticPath}';
    var __resourcecode__ = '${param.__resourcecode__}';
    var ftpPath = '${ftpPath}';

    var randomPara = new Date().getTime();
</script>
 
