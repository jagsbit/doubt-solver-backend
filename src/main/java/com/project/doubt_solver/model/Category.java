package com.project.doubt_solver.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int catId;

    @Column(unique = true)
    private String catName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doubt> doubts;

    // Getters and Setters

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<Doubt> getDoubts() {
        return doubts;
    }

    public void setDoubts(List<Doubt> doubts) {
        this.doubts = doubts;
    }
}
