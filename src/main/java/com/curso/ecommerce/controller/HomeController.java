package com.curso.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.services.ProductoService;

//esta clase tendra la logica para mostrar todos los productos al usuario
@Controller
@RequestMapping("/")
public class HomeController 
{
	@Autowired
	private ProductoService productoService;//variable para obtener los productos de la BBDD y mostrarlos al usuario
	@GetMapping("/")
	public String home (Model model)
	{

		model.addAttribute("productos", productoService.findAll());
		return "usuario/home";
	}
}
