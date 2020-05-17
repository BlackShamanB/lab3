package com.zubarev.lab3.service;

import com.zubarev.lab3.modal.Personality;
import com.zubarev.lab3.modal.Place;
import com.zubarev.lab3.repos.PersRepos;
import com.zubarev.lab3.repos.PlaceRepos;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepos placeRepos;

    public PlaceServiceImpl(PlaceRepos placeRepos) {
        this.placeRepos = placeRepos;
    }

    @Override
    public Place addPlace(Place place) {
        return placeRepos.save(place);
    }

    @Override
    public void deletePlace(Place place) {
        placeRepos.delete(place);
    }

    @Override
    public Place changePlace(Place place) {
        return placeRepos.save(place);
    }

    @Override
    public List<Place> getAll() {
        return Lists.newArrayList(placeRepos.findAll());
    }

    @Override
    public Place getPlace(Long id) {
        Optional<Place> place = placeRepos.findById(id);
        return place.orElse(null);
    }

    @Override
    public void deletePlaceId(Long id) {
        placeRepos.deleteById(id);
    }

    @Override
    public Long count() {
        return placeRepos.count();
    }

    @Override
    public List<Place> findByName(String name) {
        return placeRepos.findByPlaceNameContains(name);
   }
}
