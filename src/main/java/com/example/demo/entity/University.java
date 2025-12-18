package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class University {

    @Id @GeneratedValue 
    private Long id;
    @Column(unique=true)
    private String name;
    private String accreditationLevel;
    private String country; 
    private boolean active;

    public University(Long id, String name, String accreditationLevel, String country, boolean active = true;){
        this.id = id;
        this.name = name;
        this.accreditationLevel = accreditationLevel;
        this.country = country;
        this.active 
    }

    public Long getId() { 
        return id;
    }
    public void setId(Long id) {
        this.id = id; 
    }
    public String getName() {
        return name; 
    }
    public void setName(String name) {
        this.name = name; 
    }
    public String getAccreditationLevel(){
        return accreditationLevel;
    }
    public void setAccreditationLevel(String accreditationLevel){
        this.accreditationLevel = accreditationLevel;
    }
    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public boolean isActive() {
        return active; 
    }
    public void setActive(boolean active) {
        this.active = active; 
    }
}
