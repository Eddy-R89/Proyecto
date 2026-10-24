(function () {
  var toast = document.getElementById('toast');
  var badge = document.getElementById('cart-count');
  var timer;

  function aviso(msg) {
    toast.textContent = msg;
    toast.classList.add('visible');
    clearTimeout(timer);
    timer = setTimeout(function () { toast.classList.remove('visible'); }, 1800);
  }

  // Agregar al carrito sin recargar; si falla, se envía el formulario normal.
  document.addEventListener('submit', function (e) {
    var f = e.target.closest('.form-agregar');
    if (!f) return;
    e.preventDefault();
    fetch(f.action, {
      method: 'POST',
      body: new URLSearchParams(new FormData(f)),
      headers: { 'X-Requested-With': 'fetch' }
    })
      .then(function (r) { if (!r.ok) throw 0; return r.json(); })
      .then(function (d) {
        badge.textContent = d.count;
        badge.classList.remove('salto'); void badge.offsetWidth; badge.classList.add('salto');
        aviso('Agregado al carrito');
      })
      .catch(function () { f.submit(); });
  });
})();
