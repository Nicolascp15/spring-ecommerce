package com.curso.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.services.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController
{
	@Autowired
	private IUsuarioService usuarioService;
	
	//metodo para redireccionarnos al registro
	@GetMapping("/registro")
	public String create()
	{
		
		return "usuario/registro";
	}
	
}
