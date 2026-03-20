package com.example.location_voiture.controller;



import jakarta.validation.Valid;
import com.example.location_voiture.entities.Voiture;
import com.example.location_voiture.repositories.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;
    @GetMapping("/voitures")
    public String listVoitures(Model model) {
        model.addAttribute("voitures", voitureRepository.findAll());
        return "index-voiture";
    }
    @GetMapping("/voitures/new")
    public String showVoitureForm(Voiture voiture) { return "add-voiture"; }

    @PostMapping("/voitures/add")
    public String addVoiture(@Valid Voiture voiture, BindingResult result, Model model) {
        if(result.hasErrors()) return "add-voiture";
        voitureRepository.save(voiture);
        model.addAttribute("voitures", voitureRepository.findAll());
        return "index-voiture";
    }

    @GetMapping("/voitures/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Voiture Id:" + id));
        model.addAttribute("voiture", voiture);
        return "update-voiture";
    }

    @PostMapping("/voitures/update/{id}")
    public String updateVoiture(@PathVariable("id") long id, @Valid Voiture voiture, BindingResult result, Model model) {
        if(result.hasErrors()) {
            voiture.setId(id);
            return "update-voiture";
        }
        voitureRepository.save(voiture);
        model.addAttribute("voitures", voitureRepository.findAll());
        return "index-voiture";
    }
    @GetMapping("/voitures/disponibles")
    public String voituresDisponibles(Model model) {
        model.addAttribute("voitures", voitureRepository.findByDisponibleTrue());
        return "index-voiture";
    }

    @PostMapping("/voitures/segment")
    public String voituresParSegment(@RequestParam String segment, Model model) {
        model.addAttribute("voitures",
                voitureRepository.findByDisponibleTrueAndSegment(segment));
        return "index-voiture";
    }
    @GetMapping("/voitures/delete/{id}")
    public String deleteVoiture(@PathVariable("id") long id, Model model) {
        Voiture voiture = voitureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Voiture Id:" + id));
        voitureRepository.delete(voiture);
        model.addAttribute("voitures", voitureRepository.findAll());
        return "index-voiture";
    }
}