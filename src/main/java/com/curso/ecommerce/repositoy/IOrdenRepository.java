package com.curso.ecommerce.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>
{
	//metodo para obtener todas las ordenes atraves del usuario
	List <Orden> findByUsuario(Usuario usuario); 
}
