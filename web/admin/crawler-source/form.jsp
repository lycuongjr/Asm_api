<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ngoly
  Date: 25/07/2019
  Time: 10:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Form</title>
</head>
<body>
<form action="/admin/crawler-source" method="post">
<div>
    Main url <input type="text" name="url">
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
    Link selector <input type="text" name="linkSelector">
</div>
<div>
    Title selector <input type="text" name="titleSelector">
</div>
<div>
    Description selector <input type="text" name="descriptionSelector">
</div>
<div>
    Content selector <input type="text" name="contentSelector">
</div>
<div>
    <input type="submit" value="Save">
    <input type="reset" value="Reset">
</div>
</form>
</body>
</html>
