package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Bebida;
import com.example.demo.entity.Categoria;
import com.example.demo.repository.BebidaRepository;
import com.example.demo.repository.CategoriaRepository;

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
	public String registrarBebida(@ModelAttribute Bebida bebida, Model model) {
	    bebidaRepository.save(bebida);
	    return "redirect:/listarBebida";
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
	public String guardarEdicionBebida(@ModelAttribute Bebida bebida) {
	    bebidaRepository.save(bebida);
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
