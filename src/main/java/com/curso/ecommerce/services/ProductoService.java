package com.curso.ecommerce.services;

import java.util.Optional;

import com.curso.ecommerce.model.Producto;

public interface ProductoService 
{
	public Producto save(Producto producto);
	public Optional <Producto> get(int id); //optional nos da la opcion de poder validar el objeto que mandamos a llamar de la base de datos existe o no.
	public void update (Producto producto);
	public void delete (int id);
}
