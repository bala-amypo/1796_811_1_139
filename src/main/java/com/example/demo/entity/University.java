package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class University {

    @Id @GeneratedValue 
    @Column 
    private Long id;
    private String name;
    private String accreditationLevel;
    private String country; 
    private boolean active = true;

    public University(Long id, String name, )

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
    public boolean isActive() {
        return active; 
    }
    public void setActive(boolean active) {
        this.active = active; 
    }
}
