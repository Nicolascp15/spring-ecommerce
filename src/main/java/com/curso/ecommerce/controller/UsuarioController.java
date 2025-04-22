package com.curso.ecommerce.controller;

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

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.services.IOrdenService;
import com.curso.ecommerce.services.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController
{
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IOrdenService ordenservice;
	
	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	
	
	//metodo para redireccionarnos al registro
	@GetMapping("/registro")
	public String create()
	{
		
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario)
	{
		logger.info("Usuario Registro: {}",usuario);
		usuario.setTipo("USER");
		usuarioService.save(usuario);
		
		return "redirect:/";
	}
	//metodo para mostrar al ususario la parte del login
	@GetMapping ("/login")
	public String login()
	{
		
		return "usuario/login";
	}
	@PostMapping("/acceder")//primero va a ser tipo post para hacer pruebas
	public String acceder(Usuario usuario,HttpSession session,Model model)
	{
		
		
		Optional <Usuario> user= usuarioService.findByEmail(usuario.getEmail());//obetener email del ususario
		//logger.info("Usuario de db: {}",user.get());//visualizar usuario
		
		logger.info("Accesos: {}", usuario);
		
		//logger.info("Usuario de db: {}", user.get());
		if (user.isPresent()) 
		{
			session.setAttribute("idusuario", user.get().getId());
		
		if (user.get().getTipo ().equals("ADMIN")) 
		{
			return "redirect:/administrador";
		}else
		{
			return "redirect:/";
		}
		}else 
		{
		logger.info("Usuario no existe");
		}
			return "redirect:/";
		}
		
	
	//metodo que mostrara en la vista todas las compras que hemos hecho con nuestro usuario 
	@GetMapping("/compras")	
	public String obtenerCompras(Model model ,HttpSession session) 
	{
		 Integer idUsuario = (Integer) session.getAttribute("idusuario");

		    if (idUsuario != null) {
		        Usuario usuario = usuarioService.findbyId(idUsuario).get(); // o con Optional
		        List<Orden> ordenes = ordenservice.findByUsuario(usuario);
		        model.addAttribute("ordenes", ordenes);
		    }

		    model.addAttribute("sesion", idUsuario);
		return "usuario/compras";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id,HttpSession session,Model model)
	{
		logger.info("Id de la orden: {}", id);

	    // Buscar la orden por ID
	    Orden orden = ordenservice.findById(id).orElse(null);

	    if (orden != null) {
	        model.addAttribute("orden", orden); // Enviamos la orden completa
	        model.addAttribute("detalles", orden.getDetalle()); // Enviamos los detalles de la orden
	    }

	    model.addAttribute("sesion", session.getAttribute("idusuario"));//para que no nos de error al validar si la sesion no existe
	    
		return "usuario/detallecompra";
	}
	//metodo para cerrar sesion 
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session)
	{
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}
