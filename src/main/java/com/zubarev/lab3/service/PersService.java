package com.zubarev.lab3.service;

import com.zubarev.lab3.modal.Personality;

import java.util.List;

public interface PersService {
    public Personality addPerson(Personality person);
    public void deletePerson(Personality person);
    public Personality changePerson(Personality person);
    public List<Personality> getAll();
    public Personality getPerson(Long id);
    public void deletePersonId(Long id);
    public Long count();
    public List<Personality> findByName(String name);
}
