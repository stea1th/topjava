
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals</title>
    <style>
        table {width: 100%; border-spacing: 3px;}
        tr:nth-child(2n) {
            text-align: left;
        }
        tr:nth-child(2n+1) {
            background: #e8e8e8;
            text-align: left;
        }
        tr:nth-child(1) {
            background: #666666;
            color: white;
            text-align: left;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="meal" items="${list}">
        <c:choose>
        <c:when test="${meal.isExceed()}">
            <tr style="color: red">
                <td><javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm" /></td>
                <td><c:out value="${meal.getDescription()}"/></td>
                <td><c:out value="${meal.getCalories()}"/></td>
            </tr>
        </c:when>
            <c:otherwise>
                <tr style="color: green">
                    <td><javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm" /></td>
                    <td><c:out value="${meal.getDescription()}"/></td>
                    <td><c:out value="${meal.getCalories()}"/></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>
</body>
</html>
