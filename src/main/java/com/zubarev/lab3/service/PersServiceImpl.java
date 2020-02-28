package com.zubarev.lab3.service;

import com.zubarev.lab3.modal.Personality;
import com.zubarev.lab3.repos.PersRepos;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersServiceImpl implements PersService {
    private final PersRepos persRepos;

    public PersServiceImpl(PersRepos persRepos) {
        this.persRepos = persRepos;
    }

    @Override
    public Personality addPerson(Personality person) {
        return persRepos.save(person);
    }

    @Override
    public void deletePerson(Personality person) {
        persRepos.delete(person);
    }

    @Override
    public Personality changePerson(Personality person) {
        return persRepos.save(person);
    }

    @Override
    public List<Personality> getAll() {
        return Lists.newArrayList(persRepos.findAll());
    }

    @Override
    public Personality getPerson(Long id) {
        Optional<Personality> person = persRepos.findById(id);
        return person.orElse(null);
    }

    @Override
    public void deletePersonId(Long id) {
        persRepos.deleteById(id);
    }

    @Override
    public Long count() {
        return persRepos.count();
    }

    @Override
    public List<Personality> findByName(String name) {
        return persRepos.findByName(name);
    }

}
