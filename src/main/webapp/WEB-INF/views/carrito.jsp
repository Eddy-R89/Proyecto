<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Carrito"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor">
  <h1>Tu carrito</h1>
  <c:choose>
    <c:when test="${sessionScope.carrito.vacio}">
      <div class="vacio">
        <p>Tu carrito está vacío.</p>
        <a class="btn" href="${ctx}/catalogo">Elegir productos</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="dos-col">
        <ul class="lista-carrito">
          <c:forEach var="it" items="${sessionScope.carrito.items}">
            <li class="linea">
              <c:choose>
                <c:when test="${fn:contains(it.producto.emoji, '.')}">
                  <img class="img-mini" src="${ctx}/assets/img/${it.producto.emoji}" alt="">
                </c:when>
                <c:otherwise><span class="emoji mini" aria-hidden="true">${it.producto.emoji}</span></c:otherwise>
              </c:choose>
              <div class="linea-info">
                <a href="${ctx}/producto?id=${it.producto.id}">${it.producto.nombre}</a>
                <span class="suave"><fmt:formatNumber value="${it.producto.precio}" pattern="Q#,##0.00"/> c/u</span>
              </div>
              <form method="post" action="${ctx}/carrito" class="linea-cant">
                <input type="hidden" name="accion" value="cambiar">
                <input type="hidden" name="id" value="${it.producto.id}">
                <input type="number" name="cantidad" value="${it.cantidad}" min="0" max="99" aria-label="Cantidad de ${it.producto.nombre}">
                <button class="btn-sec" type="submit">Actualizar</button>
              </form>
              <strong class="linea-sub"><fmt:formatNumber value="${it.subtotal}" pattern="Q#,##0.00"/></strong>
              <form method="post" action="${ctx}/carrito">
                <input type="hidden" name="accion" value="quitar">
                <input type="hidden" name="id" value="${it.producto.id}">
                <button class="btn-texto" type="submit">Quitar</button>
              </form>
            </li>
          </c:forEach>
        </ul>

        <aside class="resumen" aria-label="Resumen del pedido">
          <h2>Resumen</h2>
          <p class="resumen-fila"><span>Productos</span><span>${sessionScope.carrito.cantidadTotal}</span></p>
          <p class="resumen-fila total"><span>Total</span><span><fmt:formatNumber value="${sessionScope.carrito.total}" pattern="Q#,##0.00"/></span></p>
          <a class="btn ancho" href="${ctx}/checkout">Continuar al pago</a>
          <form method="post" action="${ctx}/carrito">
            <input type="hidden" name="accion" value="vaciar">
            <button class="btn-texto" type="submit">Vaciar carrito</button>
          </form>
        </aside>
      </div>
    </c:otherwise>
  </c:choose>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
