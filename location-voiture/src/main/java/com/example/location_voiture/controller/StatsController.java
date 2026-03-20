package com.example.location_voiture.controller;

import com.example.location_voiture.entities.Location;
import com.example.location_voiture.entities.Voiture;
import com.example.location_voiture.repositories.LocationRepository;
import com.example.location_voiture.repositories.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatsController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/stats")
    public String showStats(
            @RequestParam(required = false) String segment,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Model model) {

        List<Voiture> voitures = (List<Voiture>) voitureRepository.findAll();
        List<Location> locations = (List<Location>) locationRepository.findAll();
        long nbLouees = locations.stream().filter(l -> "En cours".equals(l.getStatut()) || "Terminée".equals(l.getStatut())).count();
        double tauxOccupation = voitures.isEmpty() ? 0 : ((double) nbLouees / voitures.size()) * 100;
        model.addAttribute("tauxOccupation", tauxOccupation);

        Map<String, Double> revenusParModele = new HashMap<>();
        for (Location l : locations) {
            String marque = l.getVoiture().getMarque();
            revenusParModele.put(marque, revenusParModele.getOrDefault(marque, 0.0) + (l.getMontantTotal() != null ? l.getMontantTotal() : 0));
        }
        model.addAttribute("revenusParModele", revenusParModele);


        Map<String, Double> revenusParMois = new TreeMap<>();
        for (Location l : locations) {
            if (l.getDateDebut() != null) {
                YearMonth ym = YearMonth.from(l.getDateDebut());
                String key = ym.toString(); // format YYYY-MM
                revenusParMois.put(key, revenusParMois.getOrDefault(key, 0.0) + (l.getMontantTotal() != null ? l.getMontantTotal() : 0));
            }
        }
        model.addAttribute("revenusParMois", revenusParMois);

        if (segment != null && !segment.isEmpty()) {
            voitures = voitures.stream().filter(v -> segment.equalsIgnoreCase(v.getSegment()) && v.isDisponible()).collect(Collectors.toList());
        }
        model.addAttribute("voitures", voitures);
        if (statut != null && !statut.isEmpty()) {
            locations = locations.stream().filter(l -> statut.equalsIgnoreCase(l.getStatut())).collect(Collectors.toList());
        }
        if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            locations = locations.stream().filter(l -> !l.getDateDebut().isAfter(end) && !l.getDateFin().isBefore(start)).collect(Collectors.toList());
        }
        model.addAttribute("locations", locations);

        return "stats";
    }
}