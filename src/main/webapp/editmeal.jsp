
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div style="border: 1px solid #13cc1d;padding:5px;margin-bottom:20px;">
    <form method="post" action="meals?action=update">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <table align="center">
            <tr >
                <th width="100" hidden align="right">ID:</th>
                <td><input type="text" hidden readonly="readonly" name="mealId"
                       value="<c:out value="${meal.id}" />" /></td>
            </tr>
            <tr >
                <th width="100" align="right">Дата:</th>
                <td><input type="datetime-local"  name="date"
                           placeholder="<javatime:format value="${meal.dateTime}" pattern="yyyy-MM-dd HH:mm" />" /></td>
            </tr>
            <tr >
                <th width="100" align="right">Описание:</th>
                <td><input type="text" name="description" placeholder="<c:out value="${meal.description}" />" required/></td>
            </tr>
            <tr >
                <th width="100" align="right">Калории:</th>
                <td><input type="number" name="calories" placeholder="<c:out value="${meal.calories}"/>"/></td>
            </tr>
            <tr >
                <td colspan="2"  align="right" ><button type="submit">Update</button>
                    <button onclick="window.history.back()" type="button">Cancel</button>
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
