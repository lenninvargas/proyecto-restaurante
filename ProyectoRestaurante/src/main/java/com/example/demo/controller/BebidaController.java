package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Bebida;
import com.example.demo.entity.Categoria;
import com.example.demo.repository.BebidaRepository;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.utils.Utilitarios;

@Controller
public class BebidaController {
	
	@Autowired
	private BebidaRepository bebidaRepository;
	
    @Autowired
    private CategoriaRepository categoriaRepository;
	
	@GetMapping("/listarBebida")
	public String listBebida(Model model) {
		List<Bebida> listaBebida = bebidaRepository.findAll();
		model.addAttribute("listaBebida",listaBebida);
		return "listarBebida";
	}
	
	@GetMapping("/registrarBebida")
	public String createBebida(Model model) {
		List<Categoria> categorias = categoriaRepository.findAll();
		model.addAttribute("categorias", categorias);
		model.addAttribute("bebida", new Bebida());
		return "registrarBebida";
	}
	
	@PostMapping("/registrarBebida")
	public String registrarBebida(@ModelAttribute Bebida bebida, @RequestParam("imagen") MultipartFile file, Model model) {  
	    bebida.setUrlImagen(file.getOriginalFilename());
	    Bebida bebidas= bebidaRepository.save(bebida);
	    if(bebidas!= null) {
	    	try {
	    		File saveFile = new ClassPathResource("static/bebida_imagen").getFile();
	            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				// TODO: handle exception
			}
	    } 
	    model.addAttribute("successMessage", "El registro fue guardado exitosamente.");
	    List<Categoria> categorias = categoriaRepository.findAll();
	    model.addAttribute("categorias", categorias);
	    model.addAttribute("bebida", new Bebida());
	    return "registrarBebida";
	}

	@GetMapping("/editarBebida/{idBebida}")
	public String editarBebida(Model model, @PathVariable("idBebida") Integer idBebida) {
	    Bebida bebida = bebidaRepository.findById(idBebida).orElse(null);
	    List<Categoria> categorias = categoriaRepository.findAll();
	    model.addAttribute("bebida", bebida);
	    model.addAttribute("categorias", categorias);	    
	    return "editarBebida";
	}

	@PostMapping("/guardarEdicionBebida")
	@Transactional
	public String guardarEdicionBebida(@ModelAttribute Bebida bebida, @RequestParam("imagen") MultipartFile file) throws IOException {
	    try {
	        if (!file.isEmpty()) {
	            Bebida bebidaExistente = bebidaRepository.findById(bebida.getIdBebida()).orElse(null);
	            if (bebidaExistente != null && bebidaExistente.getUrlImagen() != null) {
	                Utilitarios.eliminarImagen("bebida_imagen", bebidaExistente.getUrlImagen());
	            }
	            File saveFile = new ClassPathResource("static/bebida_imagen").getFile();
	            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	            bebida.setUrlImagen(file.getOriginalFilename());     
	        } else {
	            Bebida bebidaExistente = bebidaRepository.findById(bebida.getIdBebida()).orElse(null);
	            if (bebidaExistente != null) {
	                bebida.setUrlImagen(bebidaExistente.getUrlImagen());
	            }
	        }
	        bebidaRepository.save(bebida);
	    } catch (IOException e) {
	    	// TODO: handle exception 
	    }
	    return "redirect:/listarBebida";
	}
	
	
	
	@GetMapping("/detalleBebida/{idBebida}")
	public String verBebida(Model model, @PathVariable("idBebida")Integer idBebida) {
		Bebida bebidaencontrado = bebidaRepository.findById(idBebida).get();
		model.addAttribute("bebida", bebidaencontrado);
		return "detalleBebida";
	}
	
	@GetMapping("/eliminarBebida/{idBebida}")
	public String eliminarBebida(Model model, @PathVariable("idBebida")Integer idBebida) {
		bebidaRepository.deleteById(idBebida);
		return "redirect:/listarBebida";
	}
	

}
