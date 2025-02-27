package com.curso.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.repositoy.ProductoRepository;
@Service
public class ProductoServiceImpl implements ProductoService
{
	
	//implementamos un objeto de tipo repositorio para poder trabajar con los metodos CRUD
	//@Autowired sirve para llamar a un objeto e la clase
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) 
	{
		return productoRepository.save(producto);
	}

	@Override
	public Optional<Producto> get(int id) 
	{
		return productoRepository.findById(id);
	}

	@Override
	public void update(Producto producto) 
	{
		//el save en jpa se comporta de manera diferente,si no encuentra el id creara el objeto si no lo actualiza
		
		productoRepository.save(producto);
	}

	@Override
	public void delete(int id) 
	{
		
		productoRepository.deleteById(id);
	}

}
