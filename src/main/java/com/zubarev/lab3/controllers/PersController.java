package com.zubarev.lab3.controllers;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import com.zubarev.lab3.modal.Personality;
import com.zubarev.lab3.modal.Place;
import com.zubarev.lab3.service.PersService;
import com.zubarev.lab3.service.PlaceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "personList";
    }

    @GetMapping("/placeList")
    public String placeList(Map<String, Object> model) {
        Iterable<Place> place = placeService.getAll();
        model.put("places", place);
        return "placeList";
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

    @GetMapping("/place/{placeId}/personList")
    public String getPersonsPlace(Model model, @PathVariable String placeId) {
        Place place = null;
        List<Personality> pers=persService.getAll();
        try {
            place = placeService.getPlace(Long.parseLong(placeId));
            model.addAttribute("allowDelete", false);
            for(int i=0;i<place.getChild();i++)
                if(pers.get(i).getPlaceName()==place.getPlaceName())
                    model.addAttribute("personalities",pers);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "personList";
    }
















    @GetMapping("/filter")
    public String filter(Map<String, Object> model) {
        Iterable<Personality> person = persService.getAll();
        model.put("personalities", person);
        return "filter";
    }







    @GetMapping("/editPerson")
    public String editPerson(Map<String, Object> model) {
        Iterable<Personality> person = persService.getAll();
        model.put("personalities", person);
        return "editPerson";
    }

    @GetMapping("/editPlace")
    public String editPlace(Map<String, Object> model) {
        Iterable<Place> place = placeService.getAll();
        model.put("places", place);
        return "editPlace";
    }

    @GetMapping(value = {"/personList/{persId}/edit"})
    public String editTask(Model model, @PathVariable long persId) {
        Personality person = null;
        try {
            person = persService.getPerson(persId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("personalities", person);
        return "editPerson";
    }

    @GetMapping("/personList/{persId}")
    public String getPersId(Model model, @PathVariable String persId) {
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

    @GetMapping("/placeList/{placeId}")
    public String getPlaceId(Model model, @PathVariable String placeId) {
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

    @GetMapping(value = {"/personList/{persId}/delete"})
    public String showDeleteTask(
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

    @GetMapping(value = {"/placeList/{placeId}/delete"})
    public String showDeletePlace(
            Model model, @PathVariable long placeId) {
        Place place = null;
        try {
            place = placeService.getPlace(placeId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("personalities", place);
        return "place";
    }

    @PostMapping("/personList")
    public String add(@RequestParam String name, @RequestParam String lastName, @RequestParam String gender, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth, Map<String, Object> model) {
        Personality person = new Personality(name, lastName, gender, dateOfBirth, null);
        persService.addPerson(person);
        Iterable<Personality> personalities = persService.getAll();
        model.put("personalities", personalities);
        return "personList";
    }

    @PostMapping("/placeList")
    public String addPlace(@RequestParam String name, Map<String, Object> model) {
        Place place = new Place(name);
        placeService.addPlace(place);
        Iterable<Place> places = placeService.getAll();
        model.put("places", places);
        return "placeList";
    }

    @PostMapping("/personList/{persId}/edit")
    public String edit(@RequestParam String name, @RequestParam String lastName, @RequestParam String gender, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth, Model model, @PathVariable long persId) {
        try {
            Personality person = new Personality(name, lastName, gender, dateOfBirth, null);
            person.setId(persId);
            persService.changePerson(person);
            return "redirect:/personList/" + person.getId();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "edit";
        }
    }

    @PostMapping("/placeList/{placeId}/edit")
    public String edit(@RequestParam String placeName, Model model, @PathVariable long placeId) {
        try {
            Place place = new Place(placeName);
            place.setPlaceId(placeId);
            placeService.changePlace(place);
            return "redirect:/placeList/" + place.getPlaceId();
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("add", false);
            return "edit";
        }
    }

    @PostMapping(value = {"/personList/{persId}/delete"})
    public String deleteTaskById(
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

    @PostMapping(value = {"/placeList/{placeId}/delete"})
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