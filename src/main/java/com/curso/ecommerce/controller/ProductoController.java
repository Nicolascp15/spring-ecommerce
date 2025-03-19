package com.curso.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.services.ProductoService;
import com.curso.ecommerce.services.UploadFileService;
import com.curso.ecommerce.services.UsuarioServiceImpl;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/productos")
public class ProductoController {
	//variable tipo Logger para hacer pruebas en la parte del backend por consola 
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	//declarar la variable service para implementar el guardado  en la base de datos 
	@Autowired
	private ProductoService productoService;
	@Autowired
	private UsuarioServiceImpl usuarioService;
	@Autowired
	private UploadFileService upload;
	
	@GetMapping("")
	//el emtodo model lleva informacion del backend hacia la vista de la lista de productos
	public String show(Model model)
	{
		model.addAttribute("productos",productoService.findAll());
		return "productos/show";
	}
	//metodo que sirve para crear nuestro producto en la base de datos
	@GetMapping("/create")
	public String create()
	{
		return "productos/create";
	}
	
	@PostMapping("/save")
	public String save(Producto producto ,@RequestParam("img") MultipartFile file,HttpSession session) throws IOException
	{
		//prueba
		//importante que la clase que queremos vizualizar tenga el metodo ToString() porque si no no se vizualizara
		LOGGER.info("Este es el objeto producto{}",producto);
		
		Usuario u = usuarioService.findbyId(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		producto.setUsuario(u);
		
		//imagen
		if(producto.getId()==null)//esta imagen se añadira a la base de datos siempre cuando el producto no exista
		{
			String nombreimagen = upload.saveImage(file);
			producto.setImagen(nombreimagen);
		}else//para modificar producto
		{
			if(file.isEmpty())//cuando editamos el producto pero no cambiamos la imagen
			{
				Producto p = new Producto();
				p =productoService.get(producto.getId()).get();
				producto.setImagen(p.getImagen());
			}
			else
			{
				String nombreimagen = upload.saveImage(file);
				producto.setImagen(nombreimagen);
			}
		}
		//llamada al service para implementar el objeto en la base de datos
		productoService.save(producto);
		
		
		//peticion directa al controlador de productos
		return "redirect:/productos";
	}
	
	//cuando demos a la opcion de "editar" producto esta accion nos redirigiera a este metodo 
	//para buscar el registro del id que pasemos
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id,Model model)
	{
		Producto p = new Producto();
		Optional <Producto> optionalProducto =productoService.get(id);
		p = optionalProducto.get();
		
		LOGGER.info("Producto buscado: {}",p);
		model.addAttribute("producto", p);
		return "productos/edit";
	}
	//metodo actualizar el objeto
	@PostMapping("/update")
	public String update(Producto producto, @RequestParam("img") MultipartFile file ) throws IOException
	{ 
		Producto p = new Producto();
		p =productoService.get(producto.getId()).get();
		if(file.isEmpty())//cuando editamos el producto pero no cambiamos la imagen
		{
			
			producto.setImagen(p.getImagen());
		}
		else
		{//cuando se edita tmbn la imagen
			p = productoService.get(producto.getId()).get();
			//eliminar cuando no sea la imagen por defecto
			if(!p.getImagen().equals("default.jpg"))
			{
				upload.deleteImage(p.getImagen());
			}
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		producto.setUsuario(p.getUsuario());//obtener usuario para concatenarlo con el producto

		productoService.update(producto);
		return "redirect:/productos";
	}
	
	//metodo para eliminar objeto
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id)
	{
		Producto p = new Producto ();
		p = productoService.get(id).get();
		//eliminar cuando no sea la imagen por defecto
		if(!p.getImagen().equals("default.jpg"))
		{
			upload.deleteImage(p.getImagen());
		}
		productoService.delete(id);
		return "redirect:/productos";
	}
	
	

}
