<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Entrar"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor angosto">
  <h1>Entrar</h1>
  <c:if test="${param.registrado == '1'}"><p class="ok" role="status">Cuenta creada. Ya puedes entrar.</p></c:if>
  <c:if test="${not empty error}"><p class="error" role="alert">${error}</p></c:if>
  <form class="formulario" method="post" action="${ctx}/login" novalidate>
    <label>Correo <input name="email" type="email" value="<c:out value='${email}'/>" autocomplete="email" required></label>
    <label>Contraseña <input name="clave" type="password" autocomplete="current-password" required></label>
    <button class="btn ancho" type="submit">Entrar</button>
  </form>
  <p class="suave">¿No tienes cuenta? <a href="${ctx}/registro">Crear cuenta</a></p>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
