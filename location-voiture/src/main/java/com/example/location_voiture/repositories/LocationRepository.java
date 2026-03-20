package com.example.location_voiture.repositories;



import com.example.location_voiture.entities.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findByStatut(String statut);

    List<Location> findByDateDebutBetween(LocalDate d1, LocalDate d2);
}
