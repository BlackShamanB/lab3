package com.zubarev.lab3.service;

import com.zubarev.lab3.modal.Place;

import java.util.List;

public interface PlaceService {
   public Place addPlace(Place place);
   public void deletePlace(Place place);
   public Place changePlace(Place place);
   public List<Place> getAll();
   public Place getPlace(Long id);
   public void deletePlaceId(Long id);
   public Long count();
   public List<Place> findByName(String name);

}