package com.boot.SpringBootRestService.model;

import com.boot.SpringBootRestService.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NamedQuery(name = "getByTimeExist",query = "SELECT v FROM Vote v WHERE v.user.id=:userId AND v.timeExist>=: startDate AND v.timeExist<: endTime")
@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity{
    @Column(name = "restaurantId",nullable = false,insertable = false,updatable = false)
    private Integer restaurantId;
    @Column(name = "userId", nullable = false,insertable = false,updatable = false)
    private Integer userId;

    @Column(name = "timeExist",nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime timeExist=LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId",nullable = false, referencedColumnName = "id")
    @JsonBackReference(value = "voteList")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",nullable = false,referencedColumnName = "id")
    @JsonBackReference(value = "voteList1")
    private User user;

    public Vote(){}

    public Vote(Integer userId,Integer restaurantId) {
        this.restaurantId=restaurantId;
        this.userId = userId;
        this.timeExist=LocalDateTime.now();
    }

    public Vote(Integer id, Integer userId,Integer restaurantId, LocalDateTime timeExist) {
        super(id);
        this.restaurantId=restaurantId;
        this.userId = userId;
        this.timeExist=timeExist;
    }
    public Vote(Integer userId,Integer restaurantId, LocalDateTime timeExist) {
        this.restaurantId=restaurantId;
        this.userId = userId;
        this.timeExist=timeExist;
    }



    public Integer getRestaurantId() {
        return restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public LocalDateTime getTimeExist() {
        return timeExist;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTimeExist(LocalDateTime timeExist) {
        this.timeExist = timeExist;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
