<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello JSP App!</title>
</head>
<body>
    <h2>made change4</h2>
    <form method="post">
        <label for="username">Enter your name:</label>
        <input type="text" id="username" name="username" />
        <input type="submit" value="Say Hello" />
    </form>

    <%
        String name = request.getParameter("username");
        if (name != null && !name.trim().isEmpty()) {
    %>
        <p>Hello, <strong><%= name %></strong>test17</p>
    <%
        }
    %>
</body>
</html>
