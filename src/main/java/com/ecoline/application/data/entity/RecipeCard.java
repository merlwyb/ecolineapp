package com.ecoline.application.data.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Рецептурная карта аналогична рецепту

@Entity
@Table(name = "RECIPE_CARD")
public class RecipeCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String recipeStringIdentifier = "Null";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "RECIPECARD_RECIPECARDPART",
            joinColumns = @JoinColumn(name = "RECIPECARD_ID"),
            inverseJoinColumns = @JoinColumn(name = "RECIPECARDPART_ID")
    )
    private Set<RecipeCardPart> recipeCardParts = new HashSet<>();


    public RecipeCard() {
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

    public Set<RecipeCardPart> getRecipeCardParts() {
        return recipeCardParts;
    }

    public void setRecipeCardParts(Set<RecipeCardPart> recipeCardParts) {
        this.recipeCardParts = recipeCardParts;
    }
}
