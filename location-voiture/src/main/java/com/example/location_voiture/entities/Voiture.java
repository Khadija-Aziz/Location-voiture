package com.example.location_voiture.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Immatriculation obligatoire")
    private String immatriculation;

    @NotBlank(message = "Marque obligatoire")
    private String marque;

    @NotBlank(message = "Segment obligatoire")
    private String segment;

    private boolean disponible = true;

    @NotNull(message = "Prix par jour obligatoire")
    @Min(value = 0, message = "Prix doit être >=0")
    private Double prixJour;

    public Voiture() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getImmatriculation() { return immatriculation; }
    public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }
    public String getSegment() { return segment; }
    public void setSegment(String segment) { this.segment = segment; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public Double getPrixJour() { return prixJour; }
    public void setPrixJour(Double prixJour) { this.prixJour = prixJour; }
}