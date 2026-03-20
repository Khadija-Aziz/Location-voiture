package com.example.location_voiture.entities;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Date début obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "Date fin obligatoire")
    private LocalDate dateFin;

    private Double montantTotal;

    private String statut; // En cours, Terminée, Annulée

    @ManyToOne
    @NotNull(message = "Voiture obligatoire")
    private Voiture voiture;

    @ManyToOne
    @NotNull(message = "Client obligatoire")
    private Client client;

    public Location() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public Double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Double montantTotal) { this.montantTotal = montantTotal; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public Voiture getVoiture() { return voiture; }
    public void setVoiture(Voiture voiture) { this.voiture = voiture; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}
