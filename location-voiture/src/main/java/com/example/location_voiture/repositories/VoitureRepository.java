package com.example.location_voiture.repositories;



import com.example.location_voiture.entities.Voiture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoitureRepository extends CrudRepository<Voiture, Long> {

    List<Voiture> findByDisponibleTrue();

    List<Voiture> findByDisponibleTrueAndSegment(String segment);
}