package com.boot.SpringBootRestService.repository.JpaImpl;


import com.boot.SpringBootRestService.model.Vote;
import com.boot.SpringBootRestService.repository.JPA.VoteRepo;
import com.boot.SpringBootRestService.repository.extendDataJpa.RestaurantJPA;
import com.boot.SpringBootRestService.repository.extendDataJpa.UserJPA;
import com.boot.SpringBootRestService.repository.extendDataJpa.VoteJPA;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VoteJpaImpl implements VoteRepo {
    private final VoteJPA voteJPA;
    private final UserJPA userJPA;
    private final RestaurantJPA restaurantJPA;

    public VoteJpaImpl(VoteJPA voteJPA, UserJPA userJPA, RestaurantJPA restaurantJPA) {
        this.voteJPA = voteJPA;
        this.userJPA = userJPA;
        this.restaurantJPA = restaurantJPA;
    }

    @Override
    public List<Vote> getAll(int restaurantId) {
        return voteJPA.getAll(restaurantId);
    }

    @Override
    @Transactional
    public Vote save(Vote vote,int userId,int restaurantId) {
/*      if (!vote.isNew()&&get(vote.getId(),restaurantId,userId)==null ){
           return null;}*/
        vote.setRestaurant(restaurantJPA.getOne(restaurantId));
        vote.setUser(userJPA.getOne(userId));


        return voteJPA.save(vote);
    }

    @Override
    public Vote get(int id, int restaurantId, int userId) {
        return voteJPA.findById(id)
                .filter(vote -> vote.getRestaurant().getId()==restaurantId&&vote.getUser().getId()==userId)
                .orElse(null);
    }
    @Override
    public List<Vote> getByTimeExist(int userId, LocalDateTime startDate,LocalDateTime endTime){
        return voteJPA.getByTimeExist(userId,startDate,endTime);
    }

    @Override
    public void update(int restaurantId, int id, int userId) {
        voteJPA.update(restaurantId,id,userId);
    }

    @Override
    public Vote getByTimeToDay(int userId) {
        List<Vote>l=voteJPA.getByTimeToDay(userId);
        return l.stream()
                .filter(v-> v.getTimeExist().toLocalDate().equals(LocalDate.now()))
                .findFirst().orElse(null);

    }

    @Override
    public boolean delete(int id,int restaurantId,int userId) {
        return voteJPA.delete(id,restaurantId,userId)!=0;
    }


}
