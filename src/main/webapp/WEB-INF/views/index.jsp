<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Inicio"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido">
  <section class="hero">
    <div class="contenedor hero-fila">
      <div>
        <h1>Tu parada de camino: café, snacks y comida lista.</h1>
        <p class="hero-sub">Pide en línea y recoge en tienda o recíbelo en casa.</p>
        <a class="btn" href="${ctx}/catalogo">Ver catálogo</a>
      </div>
      <div class="hero-pila" aria-hidden="true">
        <span>☕</span><span>🥐</span><span>🌭</span><span>🥤</span>
      </div>
    </div>
  </section>

  <section class="contenedor">
    <h2>Categorías</h2>
    <div class="cats">
      <c:forEach var="cat" items="${categorias}">
        <a class="cat" href="${ctx}/catalogo?cat=${cat}">${cat}</a>
      </c:forEach>
    </div>
  </section>

  <section class="contenedor">
    <h2>Lo más pedido</h2>
    <div class="grilla">
      <c:forEach var="p" items="${destacados}">
        <%@ include file="/WEB-INF/views/layout/tarjeta-producto.jspf" %>
      </c:forEach>
    </div>
  </section>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
