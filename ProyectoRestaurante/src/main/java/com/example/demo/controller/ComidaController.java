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

import com.example.demo.entity.Categoria;
import com.example.demo.entity.Comida;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ComidaRepository;
import com.example.demo.utils.Utilitarios;

@Controller
public class ComidaController {

	@Autowired
	private ComidaRepository comidaRepository;
	
    @Autowired
    private CategoriaRepository categoriaRepository;
    

    @GetMapping("/listarComida")
    public String listComida(Model model) {
        List<Comida> listaComida = comidaRepository.findAll();
        model.addAttribute("listaComida", listaComida);
        return "listarComida";
    }
    
    @GetMapping("/registrarComida")
    public String createComida(Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("comida", new Comida());
        return "registrarComida";
    }
    
    @PostMapping("/registrarComida")
    public String registrarComida(@ModelAttribute Comida comida, @RequestParam("imagen") MultipartFile file, Model model) {
	    comida.setUrlImagen(file.getOriginalFilename());
	    Comida comidas = comidaRepository.save(comida);
        if (comidas!= null) {
        	try {
        		File saveFile = new ClassPathResource("static/comida_imagen").getFile();
        		Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
        		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
				// TODO: handle exception
            }
        }
        model.addAttribute("successMessage", "El registro fue guardado exitosamente.");
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("comida", new Comida());
        return "registrarComida";
    }

    @GetMapping("/editarComida/{idComida}")
    public String editarComida(Model model, @PathVariable("idComida") Integer idComida) {
        Comida comida = comidaRepository.findById(idComida).orElse(null);
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("comida", comida);
        model.addAttribute("categorias", categorias);
        return "editarComida";
    }

    @PostMapping("/guardarEdicionComida")
    @Transactional
    public String guardarEdicionComida(@ModelAttribute Comida comida, @RequestParam("imagen") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                Comida comidaExistente = comidaRepository.findById(comida.getIdComida()).orElse(null);
                if (comidaExistente != null && comidaExistente.getUrlImagen() != null) {
                    Utilitarios.eliminarImagen("comida_imagen", comidaExistente.getUrlImagen());
                }
                File saveFile = new ClassPathResource("static/comida_imagen").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                comida.setUrlImagen(file.getOriginalFilename());     
            } else {
                Comida comidaExistente = comidaRepository.findById(comida.getIdComida()).orElse(null);
                if (comidaExistente != null) {
                    comida.setUrlImagen(comidaExistente.getUrlImagen());
                }
            }
            comidaRepository.save(comida);
        } catch (IOException e) {
        	// TODO: handle exception 
        }
        return "redirect:/listarComida";
    }
    
    @GetMapping("/detalleComida/{idComida}")
    public String verComida(Model model, @PathVariable("idComida") Integer idComida) {
        Comida comidaEncontrada = comidaRepository.findById(idComida).get();
        model.addAttribute("comida", comidaEncontrada);
        return "detalleComida";
    }
    
    @GetMapping("/eliminarComida/{idComida}")
    public String eliminarComida(Model model, @PathVariable("idComida") Integer idComida) {
        comidaRepository.deleteById(idComida);
        return "redirect:/listarComida";
    }
}
