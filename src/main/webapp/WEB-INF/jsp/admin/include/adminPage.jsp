<%--
  User: wangxh
  Date: 17-3-5
  Time: 下午4:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:out value="${requestScope.page}"/>
<nav>
  <ul class="pagination">
    <%-- 回到首页 --%>
    <li>
      <a href="?page.start=0${requestScope.page.param}" aria-label="Previous">
        <span aria-hidden="true">«</span>
      </a>
    </li>
    <%-- 上一页 --%>
      <li <c:if test="${!requestScope.page.hasPrevious}">class="disabled"</c:if>>
      <a href="?page.start=${requestScope.page.start - requestScope.page.count}${requestScope.page.param}" aria-label="Previous">
        <span aria-hidden="true">‹</span>
      </a>
    </li>
    
    <%-- 中间的循环页码 --%>
    <c:forEach begin="0" end="${requestScope.page.totalPage <= 0 ? 0 :requestScope.page.totalPage - 1}" varStatus="status">
      <li><a href="?page.start=${status.index * requestScope.page.count}${requestScope.page.param}" class="current">${status.count}</a></li>
    </c:forEach>
    
    <%-- 下一页 --%>
      <li <c:if test="${requestScope.page.last == requestScope.page.start}">class="disabled"</c:if>>
      <a href="?page.start=${requestScope.page.start + requestScope.page.count}${requestScope.page.param}" aria-label="Next">
        <span aria-hidden="true">›</span>
      </a>
    </li>
    
    <%-- 最后一页 --%>
    <li>
      <a href="?page.start=${requestScope.page.last}${requestScope.page.param}" aria-label="Next">
        <span aria-hidden="true">»</span>
      </a>
    </li>
  
  </ul>
</nav>

