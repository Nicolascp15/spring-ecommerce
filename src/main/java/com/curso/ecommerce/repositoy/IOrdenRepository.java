package com.curso.ecommerce.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.Orden;
@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>
{

}
