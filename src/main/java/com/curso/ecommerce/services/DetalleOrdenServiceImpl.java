package com.curso.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.repositoy.IDetalleOrdenRepository;

@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService
{
	@Autowired
	private IDetalleOrdenRepository detalleordenreposirtory;
	
	@Override
	public DetalleOrden save(DetalleOrden detalleorden) {
		// TODO Auto-generated method stub
		return detalleordenreposirtory.save(detalleorden);
	}

}
