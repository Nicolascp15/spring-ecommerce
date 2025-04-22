package com.curso.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repositoy.IDetalleOrdenRepository;
import com.curso.ecommerce.services.IDetalleOrdenService;
import com.curso.ecommerce.services.IOrdenService;
import com.curso.ecommerce.services.IUsuarioService;
import com.curso.ecommerce.services.ProductoService;

import jakarta.servlet.http.HttpSession;

//esta clase tendra la logica para mostrar todos los productos al usuario
@Controller
@RequestMapping("/")
public class HomeController 
{
	private final Logger log = LoggerFactory.getLogger(HomeController.class);//variable log para hacer pruebas por consola
	@Autowired
	private ProductoService productoService;//variable para obtener los productos de la BBDD y mostrarlos al usuario
	@Autowired
	private IDetalleOrdenRepository detalleOrdenRepository;
	@Autowired
	private IUsuarioService usuarioService;//para obtener usuarios
	List<DetalleOrden>  detalles = new ArrayList <DetalleOrden> (); //para almacenar los detalles de la orden en el carrito
	@Autowired
	private IOrdenService ordenService;//para obtener el metodo que genera el codigo de la ordem 00000000010
	@Autowired 
	private IDetalleOrdenService detalleOrdenService;//para guardar las detalles de las ordenes
	Orden orden = new Orden();//variable que va a almacenar los datos de la orden
	@GetMapping("/")
	public String home(Model model, HttpSession session) {
	    Object idUsuarioObj = session.getAttribute("idusuario");
	    if (idUsuarioObj == null) {
	        log.info("No hay sesión activa, redirigiendo a login.");
	        return "redirect:/usuario/login";
	    }

	    int idusuario = (int) idUsuarioObj;
	    log.info("ID del usuario en sesión: " + idusuario);
	    
	    Usuario usuario = usuarioService.findbyId(idusuario)
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	    model.addAttribute("usuario", usuario);
	    model.addAttribute("productos", productoService.findAll());
	    model.addAttribute("sesion", idusuario);

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
	public String getCart(Model model, HttpSession session) {
	    model.addAttribute("cart", detalles);
	    model.addAttribute("orden", orden);
	    model.addAttribute("sesion", session.getAttribute("idusuario"));
	    return "usuario/carrito";
	}

	
	@GetMapping("/order")
	public String order(Model model, HttpSession session) {
	    // Verificar sesión
	    Object idUsuarioObj = session.getAttribute("idusuario");
	    if (idUsuarioObj == null) {
	        return "redirect:/login";
	    }

	    int idUsuario = Integer.parseInt(idUsuarioObj.toString());
	    
	    // Obtener usuario y manejar el Optional correctamente
	    Usuario usuario = usuarioService.findbyId(idUsuario)
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	    
	    model.addAttribute("cart", detalles);
	    model.addAttribute("orden", orden);
	    model.addAttribute("usuario", usuario);
	    
	    return "usuario/resumenorden";
	}
	//guardar orden
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session) {
	    try {
	        if (session.getAttribute("idusuario") == null) {
	            return "redirect:/login";
	        }

	        int idUsuario = Integer.parseInt(session.getAttribute("idusuario").toString());
	        Usuario usuario = usuarioService.findbyId(idUsuario)
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        Orden orden = (Orden) session.getAttribute("orden");
	        List<DetalleOrden> detalles = (List<DetalleOrden>) session.getAttribute("detalles");

	        if (orden == null || detalles == null || detalles.isEmpty()) {
	            return "redirect:/usuario/compras"; // O mostrar un mensaje de "carrito vacío"
	        }

	        orden.setUsuario(usuario);
	        orden.setFechaCreacion(new Date());
	        orden.setNumero(ordenService.generarNumeroOrden());
	        orden.setTotal(detalles.stream().mapToDouble(DetalleOrden::getTotal).sum());

	        ordenService.save(orden);

	        for (DetalleOrden dt : detalles) {
	            dt.setOrden(orden);
	            detalleOrdenRepository.save(dt);
	        }

	        // Limpiar sesión
	        session.removeAttribute("orden");
	        session.removeAttribute("detalles");

	        return "redirect:/";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/error";
	    }
	}

	//buscar productos en el buscador
	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre,Model model)
	{
		log.info("Nombre del producto: {}",nombre);
		List <Producto> productos = productoService.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
		
		model.addAttribute("productos", productos);
		return "usuario/home";
	}
	
	
	
}
