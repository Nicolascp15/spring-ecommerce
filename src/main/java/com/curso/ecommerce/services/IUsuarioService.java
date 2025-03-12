package com.curso.ecommerce.services;

import java.util.Optional;

import com.curso.ecommerce.model.Usuario;

//interfaz con los metos crud de los usuarios
public interface IUsuarioService 
{
	//metodo para obtener los usuarios de la BBDD
	Optional<Usuario> findbyId(Integer id);
}
