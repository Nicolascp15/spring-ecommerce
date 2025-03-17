package com.curso.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repositoy.IUsuarioRepository;

//clase que implemente la interfaz IUsuarioService
@Service
public class UsuarioServiceImpl implements IUsuarioService
{
	@Autowired
	private IUsuarioRepository usuariorepository;

	@Override
	public Optional<Usuario> findbyId(Integer id) 
	{
		// TODO Auto-generated method stub
		return usuariorepository.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		
		return usuariorepository.save(usuario);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuariorepository.findByEmail(email) ;
	}

}
