// /frontend/js/categorias.js
(async function () {
  const cont = document.querySelector('[data-cards-categorias]');
  if (!cont) return;

  try {
    const res = await fetch('/api/productos/categorias'); // TU endpoint real
    const cats = await res.json();
    cont.innerHTML = '';

    cats.forEach(c => {
      const card = document.createElement('div');
      card.className = 'card-categoria';
      card.innerHTML = `
        <h3>${c.nombre}</h3>
        <div class="kpis">
          <div><span>Productos</span><strong>-</strong></div>
          <div><span>Stock Total</span><strong>-</strong></div>
          <div><span>Bajo stock</span><strong>-</strong></div>
          <div><span>Valor Inventario</span><strong>Bs 0.00</strong></div>
          <div><span>Ingresos</span><strong>Bs 0.00</strong></div>
        </div>`;
      cont.appendChild(card);
    });
  } catch (e) {
    console.error('Error cargando categorías', e);
  }
})();
