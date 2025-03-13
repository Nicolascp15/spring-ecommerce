package com.curso.ecommerce.services;

import java.util.List;

import com.curso.ecommerce.model.Orden;

public interface IOrdenService 
{
	Orden save(Orden orden);
	List<Orden>findAll(); //obtener un loistado de ordenes
	
}
