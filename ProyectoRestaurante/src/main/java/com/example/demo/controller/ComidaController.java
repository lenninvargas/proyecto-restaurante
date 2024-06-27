package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Categoria;
import com.example.demo.entity.Comida;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ComidaRepository;

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
    public String registrarComida(@ModelAttribute Comida comida, Model model) {
        comidaRepository.save(comida);
        return "redirect:/listarComida";
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
    public String guardarEdicionComida(@ModelAttribute Comida comida) {
        comidaRepository.save(comida);
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
