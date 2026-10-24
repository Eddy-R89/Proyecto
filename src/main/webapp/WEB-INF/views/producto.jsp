<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="${p.nombre}"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor">
  <p class="migas"><a href="${ctx}/catalogo">Catálogo</a> / <a href="${ctx}/catalogo?cat=${p.categoria}">${p.categoria}</a></p>
  <section class="detalle">
    <div class="detalle-media">
      <c:choose>
        <c:when test="${fn:contains(p.emoji, '.')}">
          <img class="img-producto" src="${ctx}/assets/img/${p.emoji}" alt="${p.nombre}">
        </c:when>
        <c:otherwise><span class="emoji grande" aria-hidden="true">${p.emoji}</span></c:otherwise>
      </c:choose>
    </div>
    <div>
      <h1>${p.nombre}</h1>
      <p class="suave">${p.descripcion}</p>
      <p class="precio grande-precio"><fmt:formatNumber value="${p.precio}" pattern="Q#,##0.00"/></p>
      <form class="form-agregar fila" method="post" action="${ctx}/carrito">
        <input type="hidden" name="accion" value="agregar">
        <input type="hidden" name="id" value="${p.id}">
        <label class="cantidad">Cantidad
          <input type="number" name="cantidad" value="1" min="1" max="99">
        </label>
        <button class="btn" type="submit">Agregar al carrito</button>
      </form>
    </div>
  </section>

  <c:if test="${not empty relacionados}">
    <h2>También en ${p.categoria}</h2>
    <div class="grilla">
      <c:forEach var="p" items="${relacionados}">
        <%@ include file="/WEB-INF/views/layout/tarjeta-producto.jspf" %>
      </c:forEach>
    </div>
  </c:if>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
