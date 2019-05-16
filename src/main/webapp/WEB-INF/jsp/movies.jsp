<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:forEach items="${category.movies}" var="movie" varStatus="rowStatus">
  <div class="col-12 col-sm-6 col-md-4 col-lg-3">
    <div class="box shadow p-3 mb-5 bg-white rounded">
      <h3>
        <c:if test="${not empty movie.imdbUrl}">
          <a href="${movie.imdbUrl}" target="_blank">${movie.title}</a>
        </c:if>
        <c:if test="${empty movie.imdbUrl}">
          ${movie.title}
        </c:if>
      </h3>
      <div class="mb-4">
        ${movie.originalTitle}
      </div>
      <div class="critic-data">
        <span class="mr-2 editorial-rating small${movie.tvSpielfilmRating}">
          <c:if test="${movie.tipp}">
            <span class="icon-tip">TIPP</span>
          </c:if>
        </span>

        <c:if test="${not empty movie.imdbRating}">
          <span class="imdbRating">
            <strong><span>${movie.imdbRating}</span></strong><span class="grey">/</span><span class="grey">10</span>
          </span>
        </c:if>
        <span class="metascore_w larger tvshow ${movie.metacriticClass}">${movie.metacriticRating}</span>
      </div>
      <div class="mt-4">
        <span>${movie.genre}</span>
        <span>${movie.country}</span>
        <span>${movie.year}</span>
      </div>
      <c:if test="${not empty movie.stars}">
        <div>
          Darsteller: ${movie.stars}
        </div>
      </c:if>
      <div>
        Regie: ${movie.director.name}
      </div>
      <c:forEach var="airingData" items="${movie.airingDatas}">
        <div class="airing-data">
          <span class="chl_bg_m c-${airingData.channel.toLowerCase()}" title="${airingData.channel}"></span>
          <span class="mr-1">${airingData.date}</span>
          <span>${airingData.time}</span>
        </div>
      </c:forEach>

    <div id="carouselExampleIndicators${movie.id}" class="carousel slide mt-4" data-ride="carousel" data-interval="false" data-interval="10">
      <ol class="carousel-indicators">
      <c:forEach items="${movie.images}" varStatus="status">
        <li data-target="#carouselExampleIndicators${movie.id}" data-slide-to="${status.index}" <c:if test="${status.first}">class="active"</c:if>></li>
      </c:forEach>
      </ol>
      <div class="carousel-inner">
        <c:forEach var="image" items="${movie.images}" varStatus="status">
        <div class="carousel-item <c:if test="${status.first}">active</c:if>">
          <img class="d-block" src="${image}" alt="First slide">
        </div>
        </c:forEach>
      </div>
      <a class="carousel-control-prev" href="#carouselExampleIndicators${movie.id}" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="carousel-control-next" href="#carouselExampleIndicators${movie.id}" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>

    <p class="mt-4" style="position: relative">
      <a class="stretched-link collapsed" data-toggle="collapse" href="#collapseExample${movie.id}" role="button" aria-expanded="false" aria-controls="collapseExample${movie.id}">
        <i class="fas fa-chevron-down"></i>
        <i class="fas fa-chevron-up"></i>
      </a>
    </p>
    <div class="collapse" id="collapseExample${movie.id}">
      <div class="description mt-4">
        ${movie.description}
      </div>
    </div>

    </div>
  </div>
</c:forEach>
