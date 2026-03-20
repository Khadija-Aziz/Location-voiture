package com.example.location_voiture.controller;



import com.example.location_voiture.entities.Client;
import com.example.location_voiture.entities.Voiture;
import jakarta.validation.Valid;
import com.example.location_voiture.entities.Location;
import com.example.location_voiture.repositories.LocationRepository;
import com.example.location_voiture.repositories.VoitureRepository;
import com.example.location_voiture.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.temporal.ChronoUnit;

@Controller
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ClientRepository clientRepository;
    @GetMapping("/locations")
    public String listLocations(Model model) {
        model.addAttribute("locations", locationRepository.findAll());
        return "index-location";
    }
    @GetMapping("/locations/new")
    public String showLocationForm(Location location, Model model) {
        model.addAttribute("voitures", voitureRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());
        return "add-location";
    }

    @PostMapping("/locations/add")
    public String addLocation(@Valid Location location, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("voitures", voitureRepository.findAll());
            model.addAttribute("clients", clientRepository.findAll());
            return "add-location";
        }
        long jours = ChronoUnit.DAYS.between(location.getDateDebut(), location.getDateFin()) + 1;
        location.setMontantTotal(jours * location.getVoiture().getPrixJour());
        location.setStatut("En cours");
        location.getVoiture().setDisponible(false);
        voitureRepository.save(location.getVoiture());
        locationRepository.save(location);
        model.addAttribute("locations", locationRepository.findAll());
        return "index-location";
    }

    @GetMapping("/locations/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Location Id:" + id));

        if (location.getClient() == null) {
            location.setClient(new Client());
        }
        if (location.getVoiture() == null) {
            location.setVoiture(new Voiture());
        }

        model.addAttribute("location", location);
        model.addAttribute("voitures", voitureRepository.findAll());
        model.addAttribute("clients", clientRepository.findAll());

        return "update-location";
    }

    @PostMapping("/locations/update/{id}")
    public String updateLocation(@PathVariable("id") long id,
                                 @Valid Location location,
                                 BindingResult result,
                                 Model model) {

        if(result.hasErrors()) {
            location.setId(id);
            model.addAttribute("voitures", voitureRepository.findAll());
            model.addAttribute("clients", clientRepository.findAll());
            return "update-location";
        }
        Location existing = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Location Id:" + id));
        existing.setClient(clientRepository.findById(location.getClient().getId()).orElse(null));
        existing.setVoiture(voitureRepository.findById(location.getVoiture().getId()).orElse(null));
        existing.setDateDebut(location.getDateDebut());
        existing.setDateFin(location.getDateFin());
        existing.setStatut(location.getStatut());

        locationRepository.save(existing);

        return "redirect:/locations";
    }
    @GetMapping("/locations/delete/{id}")
    public String deleteLocation(@PathVariable("id") long id, Model model) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Location Id:" + id));
        location.getVoiture().setDisponible(true);
        voitureRepository.save(location.getVoiture());
        locationRepository.delete(location);
        model.addAttribute("locations", locationRepository.findAll());
        return "index-location";
    }
}