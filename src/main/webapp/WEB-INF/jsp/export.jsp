<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

</head>
<body>
<h1>Task</h1>
<p>You have selected to export routes from ${zsjfrom}</p>

<!--
<p>FROM</p>
<c:forEach items="${zsjfrom}" var="entry">
     <p>${entry}</p>
</c:forEach>

<p>TO</p>
<c:forEach items="${zsjto}" var="entry">
     <p>${entry}</p>
</c:forEach>
-->

<p>Status of your job is <a href="/status?sessionid=${sessionid}">HERE</a>.</p>
<a href="/">Back home</a>

<br/>

</body>
</html>


