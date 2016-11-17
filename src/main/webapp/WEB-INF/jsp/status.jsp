<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

</head>
<body>
<h1>Status of your request id: ${sessionid}</h1>

<c:choose>
    <c:when test="${status==100}">
        <p>The result is available <a href="/download?sessionid=${sessionid}">HERE</a>.</p>
    </c:when>
    <c:otherwise>
        The result is available <a href="/download?sessionid=${sessionid}">HERE</a> in a few minutes.
    </c:otherwise>
</c:choose>

<a href="/">Back home</a>

<br/>

</body>
</html>


