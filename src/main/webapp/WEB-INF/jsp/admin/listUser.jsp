<%--
  User: wangxh
  Date: 17-3-9
  Time: 下午5:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="./include/adminHeader.jsp"%>
<%@include file="./include/adminNavigator.jsp"%>

<script>
</script>

<title>用户管理</title>

<div class="workingArea">
  <h1 class="label label-info" >用户管理</h1>
  <br>
  <br>
  
  <div class="listDataTableDiv">
    <table class="table table-striped table-bordered table-hover  table-condensed">
      <thead>
      <tr class="success">
        <th>ID</th>
        <th>用户名称</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${requestScope.us}" var="u">
        <tr>
          <td>${u.id}</td>
          <td>${u.name}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
  
  <div class="pageDiv">
    <%@include file="./include/adminPage.jsp" %>
  </div>
</div>

<%@include file="./include/adminFooter.jsp"%>

