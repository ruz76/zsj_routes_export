<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

</head>
<body>
<h1>String array sample</h1>

    <c:forEach items="${s}" var="entry">
        <p>${entry}</p>
    </c:forEach>

</body>
</html>


