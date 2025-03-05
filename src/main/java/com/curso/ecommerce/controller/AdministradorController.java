package com.curso.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.services.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	@Autowired
	private ProductoService productoService;//para acceder a los productos
	
	//metodo que nos redirecciona hacia el archivo html home
	//modificamos este metodo para que muestre los productos en la home
	@GetMapping("")
	public String home(Model model)//añadimos objeto Model para mandar las imagenes a la vista home
	{
		List<Producto>  productos =productoService.findAll();//importa todos los productos de la base de datos 
		model.addAttribute("productos",productos);//este metodo lo pasa a la vista home
		return "administrador/home";
	}
	
}
