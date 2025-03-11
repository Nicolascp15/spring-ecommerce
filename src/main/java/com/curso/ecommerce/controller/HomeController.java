package com.curso.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
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
	List<DetalleOrden>  detalles = new ArrayList <DetalleOrden> (); //para almacenar los detalles de la orden en el carrito
	Orden orden = new Orden();//variable que va a almacenar los datos de la orden
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
	//peticion tipo postmapping para redireccionar al carrito
	
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad,Model model) 
	{
		
	    DetalleOrden detalleOrden = new DetalleOrden();
	    double sumaTotal = 0;//esta variable es para contar todos los productos que hay en total en el carrito
	    Producto producto = new Producto();
	    
	    Optional<Producto> optionalProducto = productoService.get(id);
	    log.info("Producto añadido : {}", optionalProducto.get());
	    log.info("Cantidad : {}",cantidad);
	    producto = optionalProducto.get();
	    
	    detalleOrden.setCantidad(cantidad);
	    detalleOrden.setPrecio(producto.getPrecio());
	    detalleOrden.setNombre(producto.getNombre()); 
	    detalleOrden.setTotal(producto.getPrecio()*cantidad);
	    detalleOrden.setProducto(producto);//clave foranea que tenemos para detalleOrden
	    
	    //validar que el producto no se añada mas de una vez
	    Integer idProducto = producto.getId();
	    boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId().equals(idProducto));
	    
	    if (!ingresado)//si no es true que añada la orden 
	    {
	    	 detalles.add(detalleOrden);//enviamos los detalles a la lista para hacerlas visibles luego en el carrito
	    }
	    
	   
	    
	    sumaTotal= detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
	    orden.setTotal(sumaTotal);
	    model.addAttribute("cart", detalles);
	    model.addAttribute("orden", orden);
	    
	    
	    return "usuario/carrito"; 
	    
	 
	 
	}
	
	
	   //quitar un producto del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model)
	{
		//lista nueva de productos
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();
		
		for (DetalleOrden detalleOrden :detalles)
		{
			if(detalleOrden.getProducto().getId()!=id)
			{
				ordenesNueva.add(detalleOrden);
			}
		}
		
		//poner la nueva lisata con los productos restantes
		detalles=ordenesNueva;
		
		
		double sumaTotal =detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
	    orden.setTotal(sumaTotal);
	    model.addAttribute("cart", detalles);
	    model.addAttribute("orden", orden);
	    
		return "usuario/carrito";
	}
	//metodo que nos redirecciona al carrito desde el home
	@GetMapping("/getCart")
	public String getCart(Model model)
	{
		
		
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		return "/usuario/carrito";
	}
	
	@GetMapping("/order")
	public String order()
	{
		return "usuario/resumenorden";
		
	}
	
	
	
}
