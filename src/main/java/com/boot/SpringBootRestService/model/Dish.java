package com.boot.SpringBootRestService.model;


import com.boot.SpringBootRestService.View;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

@Entity
@Table(name = "dish")
public class Dish extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2,max = 100)
    @Column(name = "name",nullable = false)
    @SafeHtml(groups = {View.Web.class}, whitelistType = NONE)
    private String name;

    @Column(name = "price")
    @NotNull
    @DecimalMin(value = "0.0",inclusive = false)
    @DecimalMax(value = "99999.99",inclusive = true)
    @Digits(integer = 5,fraction = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId",nullable = false,referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "dishList")
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, BigDecimal price) {
        super(id);
        this.name = name;
        this.price = price;

    }

    public Dish(String name, BigDecimal price) {
        this(null, name, price);
    }



    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return super.toString()+" price:"+price+"name:"+name;
    }
}
