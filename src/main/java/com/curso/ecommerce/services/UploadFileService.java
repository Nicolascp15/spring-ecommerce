package com.curso.ecommerce.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//clase de servicio que va a contener los metodos para subir y borrar imagenes de los productos
@Service
public class UploadFileService 
{
	//ubicacion de nuestro proyecto donde van a cargar las imagemes
	private String folder="images//";
	//añadir las imagenes del producto
	public String saveImage(MultipartFile file) throws IOException
	{
		if(!file.isEmpty())
		{
			//convertir imagen en bytes para que se pueda enviar del cliente al servidor
			byte [] bytes = file.getBytes();
			Path path =Paths.get(folder+file.getOriginalFilename());
			Files.write(path, bytes);
			return file.getOriginalFilename();//retorna el nombre de la imagen que se ha subido
		}
		return"default.jpg";
	}
	
	//borrar imagenes del producto,borrara la imagen cuando borremos el producto
	public void deleteImage(String nombre)
	{
		String ruta="images//";
		File file =new File(ruta+nombre);
		file.delete();
	}
	
}
