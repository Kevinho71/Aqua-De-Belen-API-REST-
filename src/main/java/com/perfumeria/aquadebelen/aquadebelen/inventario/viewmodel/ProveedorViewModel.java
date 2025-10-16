package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class ProveedorViewModel {
        private String nombre;
        private String correo;
        private String telefono;
        private String nit;
        private String ubicacion;


        public ProveedorViewModel(){}
        public ProveedorViewModel(String nombre, String correo, String telefono, String nit, String ubicacion) {
            this.nombre = nombre;
            this.correo = correo;
            this.telefono = telefono;
            this.nit = nit;
            this.ubicacion = ubicacion;
        }
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public String getCorreo() {
            return correo;
        }
        public void setCorreo(String correo) {
            this.correo = correo;
        }
        public String getTelefono() {
            return telefono;
        }
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
        public String getNit() {
            return nit;
        }
        public void setNit(String nit) {
            this.nit = nit;
        }
        public String getUbicacion() {
            return ubicacion;
        }
        public void setUbicacion(String ubicacion) {
            this.ubicacion = ubicacion;
        }

        
        
}
