package com.curso.ecommerce.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.Usuario;
@Repository
public interface IUsuarioRepository  extends JpaRepository<Usuario, Integer>
{

}
