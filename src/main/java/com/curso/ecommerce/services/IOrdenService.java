package com.curso.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;

public interface IOrdenService 
{
	Orden save(Orden orden);
	List<Orden>findAll(); //obtener un loistado de ordenes
	String generarNumeroOrden();
	List <Orden> findByUsuario(Usuario usuario); 
	Optional<Orden> findById(Integer id);

	
}
