<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Pago"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor">
  <h1>Finalizar pedido</h1>
  <div class="dos-col">
    <form class="formulario" method="post" action="${ctx}/checkout" novalidate>
      <c:if test="${not empty error}"><p class="error" role="alert">${error}</p></c:if>

      <fieldset>
        <legend>Tus datos</legend>
        <label>Nombre <input name="nombre" value="<c:out value='${param.nombre}'/>" autocomplete="name" required></label>
        <label>Teléfono <input name="telefono" type="tel" value="<c:out value='${param.telefono}'/>" autocomplete="tel" required></label>
      </fieldset>

      <fieldset>
        <legend>Entrega</legend>
        <label class="opcion"><input type="radio" name="entrega" value="recoger" ${param.entrega != 'domicilio' ? 'checked' : ''}> Recoger en tienda</label>
        <label class="opcion"><input type="radio" name="entrega" value="domicilio" ${param.entrega == 'domicilio' ? 'checked' : ''}> A domicilio</label>
        <label>Dirección (solo a domicilio) <input name="direccion" value="<c:out value='${param.direccion}'/>" autocomplete="street-address"></label>
      </fieldset>

      <fieldset>
        <legend>Pago</legend>
        <label class="opcion"><input type="radio" name="pago" value="efectivo" ${param.pago != 'tarjeta' ? 'checked' : ''}> Efectivo al recibir</label>
        <label class="opcion"><input type="radio" name="pago" value="tarjeta" ${param.pago == 'tarjeta' ? 'checked' : ''}> Tarjeta (pasarela de pago)</label>
      </fieldset>

      <button class="btn ancho" type="submit">Confirmar pedido</button>
    </form>

    <aside class="resumen" aria-label="Resumen del pedido">
      <h2>Tu pedido</h2>
      <c:forEach var="it" items="${sessionScope.carrito.items}">
        <p class="resumen-fila"><span>${it.cantidad} × ${it.producto.nombre}</span><span><fmt:formatNumber value="${it.subtotal}" pattern="Q#,##0.00"/></span></p>
      </c:forEach>
      <p class="resumen-fila total"><span>Total</span><span><fmt:formatNumber value="${sessionScope.carrito.total}" pattern="Q#,##0.00"/></span></p>
    </aside>
  </div>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
