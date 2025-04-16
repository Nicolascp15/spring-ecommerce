package com.curso.ecommerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repositoy.IOrdenRepository;

@Service
public class OrdenServiceImpl implements IOrdenService
{
	@Autowired
	private IOrdenRepository ordenRepository;
	@Override
	public Orden save(Orden orden) 
	{
		// TODO Auto-generated method stub
		return ordenRepository.save(orden);
	}
	@Override
	public List<Orden> findAll() 
	{
		// TODO Auto-generated method stub
		return ordenRepository.findAll();
	}
	//metodo que generara una cadena secuencial 
	
	public String generarNumeroOrden()
	{
		int numero=0;//variable de incremento para la orden
		String numeroConcatenado = "";
		
		List<Orden> ordenes =findAll() ;
		
		List <Integer> numeros = new ArrayList <Integer>();
		
		// Convertir los números de orden a enteros, ignorando valores vacíos o no numéricos
	    for (Orden o : ordenes) {
	        if (o.getNumero() != null && !o.getNumero().trim().isEmpty()) {
	            try {
	                numeros.add(Integer.parseInt(o.getNumero().trim()));
	            } catch (NumberFormatException e) {
	                System.out.println("Número inválido en la base de datos: " + o.getNumero());
	            }
	        }
	    }

	    // Determinar el próximo número de orden
	    if (numeros.isEmpty()) {
	        numero = 1;
	    } else {
	        numero = numeros.stream().max(Integer::compare).orElse(0) + 1;
	    }

	    // Formatear el número con ceros a la izquierda (10 dígitos)
	    numeroConcatenado = String.format("%010d", numero);

	    return numeroConcatenado;
		
	}
	@Override
	public List<Orden> findByUsuario(Usuario usuario) 
	{
		// TODO Auto-generated method stub
		return ordenRepository.findByUsuario(usuario);
	}
	@Override
	public Optional<Orden> findById(Integer id) {
		// TODO Auto-generated method stub
		return ordenRepository.findById(id);
	}
	
	

}
