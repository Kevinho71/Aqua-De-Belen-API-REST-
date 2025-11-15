package com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel;

public class ListVentaViewModel {

    String ventaId;
    String cliente;
    String totalNeto;
    String conFactura;
    String fecha;



    public ListVentaViewModel(){

    }



    public ListVentaViewModel(String ventaId, String cliente, String totalNeto, String conFactura,
            String fecha) {
        this.ventaId = ventaId;
        this.cliente = cliente;
        this.totalNeto = totalNeto;
        this.conFactura = conFactura;
        this.fecha = fecha;
    }



    public String getVentaId() {
        return ventaId;
    }



    public void setVentaId(String ventaId) {
        this.ventaId = ventaId;
    }



    public String getCliente() {
        return cliente;
    }



    public void setCliente(String cliente) {
        this.cliente = cliente;
    }



    public String getTotalNeto() {
        return totalNeto;
    }



    public void setTotalNeto(String totalNeto) {
        this.totalNeto = totalNeto;
    }



    public String getConFactura() {
        return conFactura;
    }



    public void setConFactura(String conFactura) {
        this.conFactura = conFactura;
    }



    public String getFecha() {
        return fecha;
    }



    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    
    
}
