package com.boot.SpringBootRestService.repository.JPA;



import com.boot.SpringBootRestService.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepo {
    List<Vote> getAll(int restaurantId);

    Vote save(Vote vote,int userId,int restaurantId);

    Vote get(int id,int restaurantId,int userId);

    boolean delete(int id,int restaurantId,int userId);

    List<Vote> getByTimeExist(int userId, LocalDateTime startDate, LocalDateTime endTime);
    void update(int restaurantId, int id, int userId );

    Vote getByTimeToDay(int userId);
}
