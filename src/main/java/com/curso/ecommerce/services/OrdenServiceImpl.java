package com.curso.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.repositoy.IOrdenRepository;

@Service
public class OrdenServiceImpl implements IOrdenService
{
	@Autowired
	private IOrdenRepository ordenRepository;
	@Override
	public Orden save(Orden orden) {
		// TODO Auto-generated method stub
		return ordenRepository.save(orden);
	}

}
