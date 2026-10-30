package com.example.laptop_management_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "brands")
    @JsonIgnore
    private List<Custodian> custodians = new ArrayList<>();

    public Brand() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Custodian> getCustodians() {
        return custodians;
    }

    public void setCustodians(List<Custodian> custodians) {
        this.custodians = custodians;
    }
}