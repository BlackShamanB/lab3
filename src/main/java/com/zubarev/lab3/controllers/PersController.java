package com.zubarev.lab3.controllers;

import com.zubarev.lab3.modal.City;
import com.zubarev.lab3.modal.Personality;
import com.zubarev.lab3.service.PersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class PersController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private PersService persService;

    public PersController(PersService persService) {
        this.persService = persService;
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

    @GetMapping("/edit")
    public String edit(Map<String, Object> model) {
        Iterable<Personality> person = persService.getAll();
        model.put("personalities", person);
        return "edit";
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
        return "edit";
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

    @PostMapping("/personList")
    public String add(@RequestParam String name, @RequestParam String lastName, @RequestParam String gender, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth, @RequestParam City city, Map<String, Object> model) {
        Personality person = new Personality(name, lastName, gender, dateOfBirth,city);
        persService.addPerson(person);
        Iterable<Personality> personalities = persService.getAll();
        model.put("personalities", personalities);
        return "personList";
    }

    @PostMapping("/personList/{persId}/edit")
    public String edit(@RequestParam String name, @RequestParam String lastName, @RequestParam String gender, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,@RequestParam City city, Model model, @PathVariable long persId) {
        try {
            Personality person=new Personality(name,lastName,gender,dateOfBirth,city);
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