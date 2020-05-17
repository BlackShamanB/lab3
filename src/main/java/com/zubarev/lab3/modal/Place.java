package com.zubarev.lab3.modal;

import javax.persistence.*;

@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long placeId;
    private String placeName;
    private Long parent;

    public Place() {
    }

    public Place(String placeName) {
        this.placeName=placeName;
    }

    public Place(String placeName, Long parent ) {
        this.placeName=placeName;
        this.parent=parent;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Long getChild() {
        return parent;
    }

    public void setChild(Long parent) {
        this.parent = parent;
    }
}