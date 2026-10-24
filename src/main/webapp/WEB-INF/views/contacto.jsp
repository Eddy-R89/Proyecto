<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Contacto"/>
<%@ include file="/WEB-INF/views/layout/head.jspf" %>
<%@ include file="/WEB-INF/views/layout/header.jspf" %>
<main id="contenido" class="contenedor angosto">
  <h1>Contacto</h1>
  <p class="suave">Escríbenos y te respondemos en un día hábil.</p>
  <form class="formulario" method="post" action="${ctx}/contacto">
    <label>Nombre <input name="nombre" required></label>
    <label>Correo <input name="email" type="email" required></label>
    <label>Mensaje <textarea name="mensaje" rows="5" required></textarea></label>
    <button class="btn ancho" type="submit">Enviar mensaje</button>
  </form>
  <%-- TODO: agregar doPost en PaginasServlet (o ContactoServlet) para recibir el mensaje --%>
</main>
<%@ include file="/WEB-INF/views/layout/footer.jspf" %>
