<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="row bg-light align-items-center" style="margin-bottom: 20px;">
  <ul class="pagination" style="margin: 0; padding: 10px;">
    <li class="page-item"><a class="page-link" style="color: inherit;" href="/?date=${yesterday}"><span aria-hidden="true">&laquo;</span></a></li>
    <li class="page-item" data-date="${today}"><span style="color: inherit;" class="page-link" href="#">${todayFormatted}</span></li>
    <li class="page-item"><a class="page-link" style="color: inherit;" href="/?date=${tomorrow}"><span aria-hidden="true">&raquo;</span></a></li>
  </ul>
  <div class="list-group list-group-horizontal">
    <c:forEach var="category" items="${categories}" varStatus="status">
      <c:if test="${not empty category.movies}">
        <a class="list-group-item list-group-item-light list-group-item-action <c:if test="${status.first}">active</c:if>" style="width: initial; line-height: 10px;" data-toggle="list" href="#category${status.index}" role="tab">
          ${category.categoryName}
        </a>
      </c:if>
    </c:forEach>
  </div>
  <c:if test="${errors > 0}">
    <div>Errors: ${errors}</div>
    <button class="retry-errors btn btn-secondary ml-auto" style="margin-right: 10px;">Retry errors</button>
  </c:if>
  <button class="update btn btn-secondary ml-auto" style="margin-right: 10px;">Update</button>
</nav>