package com.curso.ecommerce.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.DetalleOrden;
@Repository
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer>
{
	
}
