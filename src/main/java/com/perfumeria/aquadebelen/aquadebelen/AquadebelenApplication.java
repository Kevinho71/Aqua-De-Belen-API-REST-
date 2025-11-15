package com.perfumeria.aquadebelen.aquadebelen;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.ClienteDAO;
import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.NivelFidelidadDAO;
import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.UbicacionDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.TipoProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Venta;
import com.perfumeria.aquadebelen.aquadebelen.ventas.repository.MetodoDePagoDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.repository.VentaDAO;

@SpringBootApplication
public class AquadebelenApplication {

	public static void main(String[] args) {
		SpringApplication.run(AquadebelenApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(VentaDAO transaccionDAO, ProductoDAO productoDAO, TipoProductoDAO tipoProductoDAO, ClienteDAO clienteDAO, NivelFidelidadDAO nivelFidelidadDAO, UbicacionDAO ubicacionDAO, MetodoDePagoDAO metodoDePagoDAO){
		return runner -> {
			

			/*Cliente cliente = new Cliente("Kevin", "Guzman", "60259580","12594527",
											"Calle General Trigo entre 15 de Agosto y 14 de Julio" ,
											nivelFidelidadDAO.findById(1), ubicacionDAO.findById(2));
			clienteDAO.save(cliente);			

			Producto producto = new Producto(200, "Perfume olor a fresas", "Luis Buitom France", tipoProductoDAO.findById(1));
			Producto producto1 = new Producto(50, "Bloqueado Centella", "Centella Aqua", tipoProductoDAO.findById(2));
			Producto producto2 = new Producto(80, "Crema hidratante", "Ponds Aclarante", tipoProductoDAO.findById(3));
			Producto producto3 = new Producto(20, "Desodorante con olor a agua", "Rexona Maxmen", tipoProductoDAO.findById(4));

			productoDAO.save(producto);
			productoDAO.save(producto1);
			productoDAO.save(producto2);
			productoDAO.save(producto3);*/

			/* 
			LocalDateTime fecha = LocalDateTime.now();
			System.out.println(fecha);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = fecha.format(formatter);
			System.out.println(formattedDateTime);*/

			//Transaccion transaccion = transaccionDAO.findById(32);
			//System.out.println(transaccion);

		};
	}

}
