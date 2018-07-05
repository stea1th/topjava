
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
        .button {text-align: center;
            margin: 0 auto;
            padding: 5px;
            }

    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<form method="post" action="meals?action=edit">
    <button type="submit" name>Insert</button>
</form>
<table>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan="2">Действие</th>
    </tr>
        <c:forEach var="meal" items="${list}">
                    <tr style="color:${meal.isExceed()? 'red' : 'green'}">
                        <td><javatime:format value="${meal.getDateTime()}" pattern="yyyy-MM-dd HH:mm" /></td>
                        <td><c:out value="${meal.getDescription()}"/></td>
                        <td><c:out value="${meal.getCalories()}"/></td>
                        <td>
                            <form method="post" class="button" action="meals?action=delete&mealId=<c:out value="${meal.getId()}"/> ">
                                <button type="submit" >Delete</button>
                            </form>
                        </td>
                        <td>
                            <form method="post" class="button" action="meals?action=edit&mealId=<c:out value="${meal.getId()}"/> ">
                                <button type="submit" >Edit</button>
                            </form>
                        </td>
                    </tr>
        </c:forEach>
</table>
</body>
</html>
