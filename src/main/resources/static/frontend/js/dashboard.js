// /frontend/js/dashboard.js
(function () {
  const set = (sel, val) => {
    const el = document.querySelector(sel);
    if (el) el.textContent = val;
  };
  const fmtBs = (n) => `Bs ${Number(n || 0).toFixed(2)}`;

  // Si todavía no tienes endpoint de dashboard, mostramos ceros limpios.
  set('[data-total-productos]', '0');
  set('[data-stock-bajo]', '0');
  set('[data-ventas-totales]', fmtBs(0)); // "Bs", no "$"
  set('[data-ventas-hoy]', '0');

  // Si luego expones /api/dashboard con tus nombres, aquí lo consumimos:
  fetch('/api/dashboard')
    .then(r => r.ok ? r.json() : null)
    .then(d => {
      if (!d) return;
      set('[data-total-productos]', d.totalProductos ?? '0');
      set('[data-stock-bajo]', d.stockBajo ?? '0');
      const ventas = Math.max(0, d.ventasTotalesBs ?? d.ventasTotales ?? 0); // sin negativos
      set('[data-ventas-totales]', fmtBs(ventas));
      set('[data-ventas-hoy]', d.ventasHoy ?? '0');

      // listas si existen en tu HTML
      const bajos = document.querySelector('[data-lista-stock-bajo]');
      if (bajos && Array.isArray(d.productosStockBajo)) {
        bajos.innerHTML = '';
        d.productosStockBajo.forEach(it => {
          const row = document.createElement('div');
          row.className = 'item';
          row.innerHTML = `
            <div class="nombre">${it.nombre ?? '-'}</div>
            <div class="detalle">${it.marca ?? it.descripcion ?? '-'}</div>
            <span class="badge">${it.unidades ?? 0} unidades</span>`;
          bajos.appendChild(row);
        });
      }

      const rec = document.querySelector('[data-ventas-recientes]');
      if (rec && Array.isArray(d.ventasRecientes)) {
        rec.innerHTML = '';
        d.ventasRecientes.forEach(v => {
          const row = document.createElement('div');
          row.className = 'row';
          row.innerHTML = `
            <div>${v.producto ?? '-'}</div>
            <div>${v.cantidad ?? 0}</div>
            <div>${fmtBs(v.precioUnitario ?? 0)}</div>
            <div>${fmtBs(v.total ?? 0)}</div>
            <div>${v.fechaIso ? new Date(v.fechaIso).toLocaleDateString() : '-'}</div>`;
          rec.appendChild(row);
        });
      }
    })
    .catch(() => {});
})();
