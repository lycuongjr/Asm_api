<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.asm.entity.Category" %>

<%
    Category category = new Category();
%>
<html>
<head>
    <title>Article form</title>
</head>
<body>
<h1>Article </h1>
<form action="/controller/article" method="post">
    <div>
        Url <input type="text" name="url">
    </div>
    <div>
        Category
        <select name="categoryId">
            <c:forEach var="cate" items="${categories}">
                <option value="${cate.id}"><c:out value = "${cate.name}"/></option>
            </c:forEach>
        </select>
    </div>
    <div>
        Title <input type="text" name="title">
    </div>
    <div>
        Description <input type="text" name="description">
    </div>
    <div>
        Content
        <textarea rows="10" cols="50"></textarea>
    </div>
    <div>
        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </div>
</form>

</body>
</html>