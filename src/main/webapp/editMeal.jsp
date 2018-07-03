<%--
  Created by IntelliJ IDEA.
  User: stea1
  Date: 03.07.2018
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="border: 1px solid #13cc1d;padding:5px;margin-bottom:20px;">
    <form method="post" name="frmAddMeal">
        <table align="center">
            <tr >
                <th width="100" align="right">Дата:</th>
                <td><input type="text" name="date" placeholder="Измените дату"/></td>
            </tr>
            <tr >
                <th width="100" align="right">Описание:</th>
                <td><input type="text" name="description" placeholder="Измените описание"/></td>
            </tr>
            <tr >
                <th width="100" align="right">Калории:</th>
                <td><input type="text" name="calories" placeholder="Измените калории"/></td>
            </tr>
            <tr >
                <td colspan="2" align="right"><button type="submit">Update</button></td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
