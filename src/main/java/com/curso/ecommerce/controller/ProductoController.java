package com.curso.ecommerce.controller;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	//el emtodo model lleva informacion del backend hacia la vista de la lista de productos
	public String show(Model model)
	{
		model.addAttribute("productos",productoService.findAll());
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
	
	//cuando demos a la opcion de "editar" producto esta accion nos redirigiera a este metodo 
	//para buscar el registro del id que pasemos
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id,Model model)
	{
		Producto p = new Producto();
		Optional <Producto> optionalProducto =productoService.get(id);
		p = optionalProducto.get();
		
		LOGGER.info("Producto buscado: {}",p);
		model.addAttribute("producto", p);
		return "productos/edit";
	}
	//metodo actualizar el objeto
	@PostMapping("/update")
	public String update(Producto producto)
	{ 
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	

}
