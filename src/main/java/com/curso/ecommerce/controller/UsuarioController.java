package com.curso.ecommerce.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.services.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController
{
	@Autowired
	private IUsuarioService usuarioService;
	
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
	public String acceder(Usuario usuario,HttpSession session)
	{
		logger.info("Accesos: {}",usuario); 
		
		Optional <Usuario> user = usuarioService.findByEmail(usuario.getEmail());//obetener email del ususario
		//logger.info("Usuario de db: {}",user.get());//visualizar usuario
		
		if(user.isPresent())//si hay un registro con ese email
		{
			session.setAttribute("idusuario", user.get().getId());//le pasaremos el id del usuario para que este presente toda la session
			if(user.get().getTipo().equals("ADMIN"))//validacion para entrar como ADMIN O USER
			{
				return "redirect:/administrador";//si el usuario es de tipo ADMIN  que nos devuelva al administrador
			}
			else// en caso de que no sea ADMIN que nos mande hacia la pagina de la raiz 
			{
				return "redirect:/"; 
			}
		}else//si no existe que lance mensaje
		{
			logger.info("Usuario no existe");
		}
		
		return "redirect:/";
	}
	//metodo que mostrara en la vista todas las compras que hemos hecho con nuestro usuario 
	@GetMapping("/compras")	
	public String obtenerCompras(Model model ,HttpSession session) 
	{
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuario/compras";
	}
	
}
