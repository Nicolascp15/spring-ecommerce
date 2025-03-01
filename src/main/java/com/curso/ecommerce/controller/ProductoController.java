package com.curso.ecommerce.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.services.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	//variable tipo Logger para hacer pruebas en la parte del backend por consola 
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	//declarar la variable service para implementar el guardado  en la base de datos 
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("")
	public String show()
	{
		return "productos/show";
	}
	//metodo que sirve para crear nuestro producto en la base de datos
	@GetMapping("/create")
	public String create()
	{
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto)
	{
		//prueba
		//importante que la clase que queremos vizualizar tenga el metodo ToString() porque si no no se vizualizara
		LOGGER.info("Este es el objeto producto{}",producto);
		
		Usuario u = new Usuario(1,"","","","","","","");
		producto.setUsuario(u);
		//llamada al service para implementar el objeto en la base de datos
		productoService.save(producto);
		//peticion directa al controlador de productos
		return "redirect:/productos";
	}

}
