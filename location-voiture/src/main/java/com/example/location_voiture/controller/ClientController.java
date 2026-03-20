package com.example.location_voiture.controller;



import jakarta.validation.Valid;
import com.example.location_voiture.entities.Client;
import com.example.location_voiture.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @GetMapping("/clients")
    public String listClients(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "index-client";
    }
    @GetMapping("/clients/new")
    public String showClientForm(Client client) { return "add-client"; }

    @PostMapping("/clients/add")
    public String addClient(@Valid Client client, BindingResult result, Model model) {
        if(result.hasErrors()) return "add-client";
        clientRepository.save(client);
        model.addAttribute("clients", clientRepository.findAll());
        return "index-client";
    }

    @GetMapping("/clients/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Client Id:" + id));
        model.addAttribute("client", client);
        return "update-client";
    }

    @PostMapping("/clients/update/{id}")
    public String updateClient(@PathVariable("id") long id, @Valid Client client, BindingResult result, Model model) {
        if(result.hasErrors()) {
            client.setId(id);
            return "update-client";
        }
        clientRepository.save(client);
        model.addAttribute("clients", clientRepository.findAll());
        return "index-client";
    }

    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable("id") long id, Model model) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Client Id:" + id));
        clientRepository.delete(client);
        model.addAttribute("clients", clientRepository.findAll());
        return "index-client";
    }
}
