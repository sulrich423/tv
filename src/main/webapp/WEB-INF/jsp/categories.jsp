<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
<link rel="stylesheet" href="/css/styles.css">
<script src="/webjars/jquery/jquery.min.js" ></script>
<script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js" ></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="/js/app.js"></script>
</head>
<body>
<div class="overlay"></div>

    <svg viewBox="0 0 36 36" class="circular-chart spinning">
      <path class="circle-bg"
          d="M18 2.0845
            a 15.9155 15.9155 0 0 1 0 31.831
            a 15.9155 15.9155 0 0 1 0 -31.831"
        />
      <path class="circle"
        stroke-dasharray="75, 100"
        d="M18 2.0845
          a 15.9155 15.9155 0 0 1 0 31.831
          a 15.9155 15.9155 0 0 1 0 -31.831"
      />
      <text x="18" y="20.35" class="percentage"></text>
    </svg>

  <div class="container mt-4">

    <nav aria-label="Page navigation example">
      <ul class="pagination">
        <li class="page-item"><a class="page-link" href="/?date=${yesterday}">Previous</a></li>
        <li class="page-item active" data-date="${today}"><span class="page-link" href="#">${todayFormatted}</span></li>
        <li class="page-item"><a class="page-link" href="/?date=${tomorrow}">Next</a></li>
      </ul>
    </nav>

    <button id="update" class="btn btn-primary">Update</button>
<%--     <a href="/update/?date=${today}" target="_blank" class="btn btn-primary">Update</a> --%>
    <c:forEach var="category" items="${categories}">
      <c:if test="${not empty category.movies}">
        <h2 class="box mb-3">${category.categoryName}</h2>
        <div class="row">
          <%@include file="movies.jsp" %>
        </div>
        <hr>
        <hr>
        <hr>
      </c:if>
    </c:forEach>
  </div>


</body>
</html>
