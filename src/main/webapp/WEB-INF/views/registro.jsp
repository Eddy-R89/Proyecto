<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Crear cuenta"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor angosto">
  <h1>Crear cuenta</h1>
  <c:if test="${not empty error}"><p class="error" role="alert">${error}</p></c:if>
  <form class="formulario" method="post" action="${ctx}/registro" novalidate>
    <label>Nombre <input name="nombre" value="<c:out value='${nombre}'/>" autocomplete="name" required></label>
    <label>Correo <input name="email" type="email" value="<c:out value='${email}'/>" autocomplete="email" required></label>
    <label>Contraseña (mínimo 8 caracteres) <input name="clave" type="password" autocomplete="new-password" required></label>
    <label>Repite la contraseña <input name="clave2" type="password" autocomplete="new-password" required></label>
    <button class="btn ancho" type="submit">Crear cuenta</button>
  </form>
  <p class="suave">¿Ya tienes cuenta? <a href="${ctx}/login">Entrar</a></p>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
