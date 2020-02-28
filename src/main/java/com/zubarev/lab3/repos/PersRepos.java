package com.zubarev.lab3.repos;

import com.zubarev.lab3.modal.Personality;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersRepos extends CrudRepository<Personality, Long> {
    List<Personality> findByName(String name);
}
