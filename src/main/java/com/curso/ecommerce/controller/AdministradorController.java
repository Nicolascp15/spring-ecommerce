package com.curso.ecommerce.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.services.IOrdenService;
import com.curso.ecommerce.services.IUsuarioService;
import com.curso.ecommerce.services.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	@Autowired
	private ProductoService productoService;//para acceder a los productos
	@Autowired
	private IUsuarioService usuarioService;//variable para acceddr a la informacion de los usuarios desde el ADMIN
	@Autowired
	private IOrdenService ordenService;//variable para acceder a los metodos CRUD de las ordenes 
	
	private Logger logg = LoggerFactory.getLogger(AdministradorController.class);
	
	//metodo que nos redirecciona hacia el archivo html home
	//modificamos este metodo para que muestre los productos en la home
	@GetMapping("")
	public String home(Model model)//añadimos objeto Model para mandar las imagenes a la vista home
	{
		List<Producto>  productos =productoService.findAll();//importa todos los productos de la base de datos 
		model.addAttribute("productos",productos);//este metodo lo pasa a la vista home
		return "administrador/home";
	}
	//metodo para que nos redireccione a a la vista /administrador/usuarios
	@GetMapping("/usuarios")
	public String usuarios(Model model)
	{
		model.addAttribute("usuarios", usuarioService.findAll());
		return "administrador/usuarios";
	}
	
	@GetMapping("/ordenes")
	public String ordenes(Model model)
	{
		model.addAttribute("ordenes", ordenService.findAll());
		return "administrador/ordenes";
	}
	
	//metodo para mapear la peticion cuando estemos en ver detalle
	@GetMapping("/detalle/{id}")
	public String detalle(Model model , @PathVariable Integer id)
	{
		logg.info("ID de la orden {}",id);
		Orden orden = ordenService.findById(id).get();//obtener id de la lista 
		
		model.addAttribute("detalles", orden.getDetalle());//una vez obtenida la orden mandar las ordenes a la vista  
		return "administrador/detalleorden";
	}
	
}
