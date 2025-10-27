// /frontend/js/productos.js
(async function () {
  const fmtBs = (n) => `Bs ${Number(n || 0).toFixed(2)}`;
  const tbody = document.querySelector('[data-productos-tbody]');
  if (!tbody) return;

  try {
    const res = await fetch('/api/productos'); // TU endpoint real
    const items = await res.json();
    tbody.innerHTML = '';

    items.forEach(p => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${p.nombre ?? '-'}</td>
        <td>${p.brandOrDescription ?? p.descripcion ?? '-'}</td>
        <td>${p.categoria ?? p.tipoProducto ?? '-'}</td>
        <td>${fmtBs(p.price ?? p.precio ?? 0)}</td>
        <td>${p.stock ?? 0}</td>
      `;
      tbody.appendChild(tr);
    });
  } catch (e) {
    console.error('Error cargando productos', e);
  }
})();
