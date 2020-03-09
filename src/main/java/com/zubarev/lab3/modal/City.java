package com.zubarev.lab3.modal;

import javax.persistence.*;
import java.util.Set;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column (name = "cityId")
    private Long idCity;
    private String nameCity;
    @OneToMany (mappedBy = "cityId")
    private Set<Personality> peronId;

    public City() {
    }

    public City(String nameCity,Set<Personality> personId) {
        this.nameCity = nameCity;
        this.peronId=personId;
    }

    public Long getIdCity() {
        return idCity;
    }

    public void setIdCity(Long idCity) {
        this.idCity = idCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public Set<Personality> getPeronId() {
        return peronId;
    }

    public void setPeronId(Set<Personality> peronId) {
        this.peronId = peronId;
    }
}
