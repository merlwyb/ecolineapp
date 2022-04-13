package com.ecoline.application.data.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RECIPE")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String recipeStringIdentifier = "Null";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "RECIPE_RECIPEPART",
            joinColumns = @JoinColumn(name = "RECIPE_ID"),
            inverseJoinColumns = @JoinColumn(name = "RECIPEPART_ID")
    )
    private Set<RecipePart> recipeParts = new HashSet<>();


    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeStringIdentifier() {
        return recipeStringIdentifier;
    }

    public void setRecipeStringIdentifier(String recipeStringIdentifier) {
        this.recipeStringIdentifier = recipeStringIdentifier;
    }

    public Set<RecipePart> getRecipeParts() {
        return recipeParts;
    }

    public void setRecipeParts(Set<RecipePart> recipeParts) {
        this.recipeParts = recipeParts;
    }
}
