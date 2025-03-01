package com.curso.ecommerce.services;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.model.Producto;

public interface ProductoService 
{
	public Producto save(Producto producto);//metodo para añadir producto
	public Optional <Producto> get(int id); //optional nos da la opcion de poder validar el objeto que mandamos a llamar de la base de datos existe o no.
	public void update (Producto producto);//metodo para modificar un producto
	public void delete (int id);//metodo para eliminar un producto
	public List <Producto> findAll();//metodo para mostrar lista de todos los productos
}
