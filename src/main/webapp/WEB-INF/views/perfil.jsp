<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Mi perfil"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor angosto">
  <h1>Mi perfil</h1>
  <p><strong><c:out value="${sessionScope.usuarioNombre}"/></strong><br><span class="suave"><c:out value="${sessionScope.usuarioEmail}"/></span></p>
  <h2>Historial de pedidos</h2>
  <div class="vacio"><p>Aún no tienes pedidos.</p><a class="btn" href="${ctx}/catalogo">Hacer mi primer pedido</a></div>
  <form method="post" action="${ctx}/logout"><button class="btn-sec" type="submit">Cerrar sesión</button></form>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
