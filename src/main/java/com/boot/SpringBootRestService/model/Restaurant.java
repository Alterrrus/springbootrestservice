package com.boot.SpringBootRestService.model;

import com.boot.SpringBootRestService.View;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractBaseEntity{
    @NotBlank
    @Size(min = 2,max = 100)
    @Column(name = "name",nullable = false)
    @SafeHtml(groups = {View.Web.class}, whitelistType = NONE)
    private String name;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.LAZY)
    @JsonManagedReference(value = "dishList")
    @OrderBy("name ASC")
    private List<Dish> dishList;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.LAZY)
    @JsonManagedReference(value = "voteList")
    @OrderBy("timeExist desc")
    private List<Vote> voteList;



    public Restaurant(){}

    public Restaurant(String name){
        this.name=name;
    }



    public Restaurant(String name, List<Vote> voteList, List<Dish> dishList) {
        this.name = name;
        this.voteList = voteList;
        this.dishList = dishList;
    }

    public Restaurant(Integer id, List<Vote> voteList, List<Dish> dishList,String name) {
        super(id);
        this.voteList = voteList;
        this.dishList = dishList;
        this.name=name;
    }
    public Restaurant(Restaurant r){
        this(r.getId(),r.getVoteList(),r.getDishList(),r.getName());
    }

    public String getName() {
        return name;
    }

    public List<Vote> getVoteList() {
        return voteList;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }


}
