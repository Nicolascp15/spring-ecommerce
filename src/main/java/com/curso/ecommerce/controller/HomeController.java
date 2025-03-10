package com.curso.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.services.ProductoService;

//esta clase tendra la logica para mostrar todos los productos al usuario
@Controller
@RequestMapping("/")
public class HomeController 
{
	private final Logger log = LoggerFactory.getLogger(HomeController.class);//variable log para hacer pruebas por consola
	@Autowired
	private ProductoService productoService;//variable para obtener los productos de la BBDD y mostrarlos al usuario
	@GetMapping("/")
	public String home (Model model)
	{

		model.addAttribute("productos", productoService.findAll());
		return "usuario/home";
	}
	
	//metodo que nos va a llevar desde el boton " ver producto" a la vista producto home 
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id,Model model)//variable Model para llevar informacion a la vista
	{
		log.info("Id Producto Enviado Como Parametro {}",id);
		Producto producto =new Producto();//declarar variable de objeto Producto para buscarlo en la BBDD
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();//para obterner el producto de la base de datos
		
		model.addAttribute("producto",producto);//mandar el producto a la vista
		
		return "usuario/productohome";
	}
	
	
}
