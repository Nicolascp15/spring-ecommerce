package com.curso.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Usuario;

//interfaz con los metos crud de los usuarios
public interface IUsuarioService 
{
	//metodo para obtener los usuarios de la BBDD
	Optional<Usuario> findbyId(Integer id);
	//metodo para guardar usuarios
	Usuario save (Usuario usuario);
	//metodo para validacion por correo
	Optional <Usuario> findByEmail (String email);
	//metodo para ver todos los usuarios desde el ADMIN
	List <Usuario> findAll();
}
