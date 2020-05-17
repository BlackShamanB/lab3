package com.zubarev.lab3.repos;

import com.zubarev.lab3.modal.Personality;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PersRepos extends CrudRepository<Personality, Long> {
    List<Personality> findByName(String name);
    List<Personality> findByNameContains(String name);
}
