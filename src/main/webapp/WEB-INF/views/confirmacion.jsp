<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Pedido confirmado"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor centro">
  <div class="confirma">
    <span class="emoji grande" aria-hidden="true">✅</span>
    <h1>Pedido confirmado</h1>
    <p>Número <strong>${sessionScope.pedidoNumero}</strong> · ${sessionScope.pedidoEntrega} · ${sessionScope.pedidoTotal}</p>
    <a class="btn" href="${ctx}/catalogo">Seguir comprando</a>
  </div>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
