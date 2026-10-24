<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Catálogo"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor">
  <h1>Catálogo</h1>
  <div class="chips" role="list">
    <a role="listitem" class="chip ${empty catActual ? 'activo' : ''}" href="${ctx}/catalogo">Todo</a>
    <c:forEach var="cat" items="${categorias}">
      <a role="listitem" class="chip ${cat == catActual ? 'activo' : ''}" href="${ctx}/catalogo?cat=${cat}">${cat}</a>
    </c:forEach>
  </div>

  <c:choose>
    <c:when test="${empty productos}">
      <div class="vacio">
        <p>No encontramos productos con esa búsqueda.</p>
        <a class="btn" href="${ctx}/catalogo">Ver todo el catálogo</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="grilla">
        <c:forEach var="p" items="${productos}">
          <%@ include file="/WEB-INF/views/layout/tarjeta-producto.jspf" %>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
