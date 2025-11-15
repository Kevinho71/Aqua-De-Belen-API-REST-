package com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel;

public class DetalleVentaViewModel {
        String idDetalle;
        String producto;
        String costoUnitario;
        String cantidad;
        String descuento;
        String subtotal;

        public DetalleVentaViewModel(){

        }

        public DetalleVentaViewModel(String idDetalle, String producto, String costoUnitario, String cantidad, String descuento, String subtotal) {
                this.idDetalle = idDetalle;
                this.producto = producto;
                this.costoUnitario = costoUnitario;
                this.cantidad = cantidad;
                this.descuento = descuento;
                this.subtotal = subtotal;
        }

        public String getIdDetalle() {
                return idDetalle;
        }

        public void setIdDetalle(String idDetalle) {
                this.idDetalle = idDetalle;
        }

        public String getProducto() {
                return producto;
        }

        public void setProducto(String producto) {
                this.producto = producto;
        }

        public String getCantidad() {
                return cantidad;
        }

        public void setCantidad(String cantidad) {
                this.cantidad = cantidad;
        }

        public String getSubtotal() {
                return subtotal;
        }

        public void setSubtotal(String subtotal) {
                this.subtotal = subtotal;
        }

        public String getDescuento() {
                return descuento;
        }

        public void setDescuento(String descuento) {
                this.descuento = descuento;
        }

        public String getCostoUnitario() {
                return costoUnitario;
        }

        public void setCostoUnitario(String costoUnitario) {
                this.costoUnitario = costoUnitario;
        }

        
        
}
