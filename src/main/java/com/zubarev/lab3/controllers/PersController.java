package com.zubarev.lab3.controllers;

import com.zubarev.lab3.modal.Personality;
import com.zubarev.lab3.modal.Place;
import com.zubarev.lab3.service.PersService;
import com.zubarev.lab3.service.PlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class PersController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PersService persService;
    private PlaceService placeService;
    public PersController(PersService persService, PlaceService placeService) {
        this.persService = persService;
        this.placeService=placeService;
    }

    @GetMapping
    public String greeting() {
        return "index";
    }

    @GetMapping("/personList")
    public String personList(Map<String, Object> model) {
        Iterable<Personality> person = persService.getAll();
        model.put("personalities", person);
        Iterable<Place> place = placeService.getAll();
        model.put("places", place);
        return "personList";
    }

    @GetMapping("/placeList")
    public String placeList(Map<String, Object> model) {
        Iterable<Place> place = placeService.getAll();
        model.put("places", place);
        return "placeList";
    }

    @GetMapping("/place/{placeId}")
    public String place(Model model, @PathVariable String placeId) {
        Place place = null;
        try {
            place = placeService.getPlace(Long.parseLong(placeId));
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("places", place);
        return "place";
    }



    @GetMapping("/person/{persId}")
    public String person(Model model, @PathVariable String persId) {
        Personality person = null;
        try {
            person = persService.getPerson(Long.parseLong(persId));
            model.addAttribute("allowDelete", false);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("personalities", person);
        return "person";
    }

















    @GetMapping("/filter")
    public String filter(Map<String, Object> model) {
        Iterable<Personality> person = persService.getAll();
        model.put("personalities", person);
        return "filter";
    }






//
//    @GetMapping("/editPerson")
//    public String editPerson(Map<String, Object> model) {
//        Iterable<Personality> person = persService.getAll();
//        model.put("personalities", person);
//        Iterable<Place> place = placeService.getAll();
//        model.put("places", place);
//        return "personList";
//    }

//    @GetMapping("/editPlace")
//    public String editPlace(Map<String, Object> model) {
//        Iterable<Place> place = placeService.getAll();
//        model.put("places", place);
//        return "editPlace";
//    }

    @GetMapping(value = {"/person/{persId}/edit"})
    public String editPerson(Model model, @PathVariable long persId) {
        Personality person = null;
        try {
            person = persService.getPerson(persId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("personalities", person);
        Iterable<Place> place = placeService.getAll();
        model.addAttribute("places", place);
        return "editPerson";
    }

    @GetMapping(value = {"/place/{placeId}/edit"})
    public String editPlace(Model model, @PathVariable long placeId) {
        Place place = null;
        try {
            place = placeService.getPlace(placeId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("places", place);
        Iterable<Place> places = placeService.getAll();
        model.addAttribute("parents", places);
        return "editPlace";
    }

    @GetMapping(value = {"/person/{persId}/delete"})
    public String showDeletePerson(
            Model model, @PathVariable long persId) {
        Personality person = null;
        try {
            person = persService.getPerson(persId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("personalities", person);
        return "person";
    }

    @GetMapping(value = {"/place/{placeId}/delete"})
    public String showDeletePlace(
            Model model, @PathVariable long placeId) {
        Place place = null;
        try {
            place = placeService.getPlace(placeId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("places", place);
        return "place";
    }







    @PostMapping("/personList")
    public String add(@RequestParam String name, @RequestParam String lastName, @RequestParam String gender, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,@RequestParam String placeName, Map<String, Object> model) {
        Place place= placeService.getPlace(placeName);
        Personality person = new Personality(name, lastName, gender, dateOfBirth, place);
        persService.addPerson(person);
        Iterable<Personality> personalities = persService.getAll();
        model.put("personalities", personalities);
        return "personList";
    }

    @PostMapping("/placeList")
    public String addPlace(@RequestParam String name,@RequestParam String parent, Map<String, Object> model) {
        Place place;
        if(!placeService.check(parent))
            place = new Place(name);
        else{
            Place placeParent=placeService.getPlace(parent);
            place=new Place(name,placeParent.getPlaceId());
        }

        placeService.addPlace(place);
        Iterable<Place> places = placeService.getAll();
        model.put("places", places);
        return "placeList";
    }

    @PostMapping("/person/{persId}/edit")
    public String edit(@RequestParam String name, @RequestParam String lastName, @RequestParam String gender, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth, Model model,@RequestParam String placeName, @PathVariable long persId) {
        try {
            Place place= placeService.getPlace(placeName);
            Personality person = new Personality(name, lastName, gender, dateOfBirth, place);
            person.setId(persId);
            persService.changePerson(person);
            return "redirect:/person/" + person.getId();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "editPerson";
        }
    }

    @PostMapping("/place/{placeId}/edit")
    public String edit(@RequestParam String placeName,@RequestParam String parent, Model model, @PathVariable long placeId) {
        try {
            Place place;
            if(!placeService.check(parent))
                place = new Place(placeName);
            else{
                Place placeParent=placeService.getPlace(parent);
                place=new Place(placeName,placeParent.getPlaceId());
            }
            place.setPlaceId(placeId);
            placeService.changePlace(place);
            return "redirect:/place/" + place.getPlaceId();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "editPlace";
        }
    }

    @PostMapping(value = {"/person/{persId}/delete"})
    public String deletePersonById(
            Model model, @PathVariable long persId) {
        try {
            persService.deletePersonId(persId);
            return "redirect:/personList";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "person";
        }
    }

    @PostMapping(value = {"/place/{placeId}/delete"})
    public String deletePlaceById(
            Model model, @PathVariable long placeId) {
        try {
            placeService.deletePlaceId(placeId);
            return "redirect:/placeList";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "place";
        }
    }

    @PostMapping("filter")
    public String filter(@RequestParam String nameFilter, Map<String, Object> model) {
        Iterable<Personality> personalities;
        if (nameFilter != null && !nameFilter.isEmpty()) {
            personalities = persService.findByName(nameFilter);
        } else {
            personalities = persService.getAll();
        }
        model.put("personalities", personalities);
        return "filter";
    }

}