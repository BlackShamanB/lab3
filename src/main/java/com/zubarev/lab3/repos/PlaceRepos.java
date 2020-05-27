package com.zubarev.lab3.repos;

import com.zubarev.lab3.modal.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PlaceRepos extends CrudRepository<Place, Long> {
 // List<Place> findByName(String name);
  List<Place> findByPlaceNameContains(String name);
  Place findByPlaceName(String name);
}
