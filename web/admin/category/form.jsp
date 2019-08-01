<%--
  Created by IntelliJ IDEA.
  User: ngoly
  Date: 30/07/2019
  Time: 9:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Category form</title>
</head>
<body>
<h1>Category form</h1>
<form action="/controller/category" method="post">
    <div>
        Name <input type="text" name="name">
    </div>
    <div>
        Description <input type="text" name="description">
    </div>
    <div>
        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </div>
</form>

</body>
</html>
