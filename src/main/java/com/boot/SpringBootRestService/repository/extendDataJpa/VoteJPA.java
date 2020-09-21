package com.boot.SpringBootRestService.repository.extendDataJpa;


import com.boot.SpringBootRestService.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteJPA extends JpaRepository<Vote,Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE  Vote v SET v.restaurant.id=:restaurantId WHERE v.id=:id AND v.user.id=:userId")
    void update(@Param("restaurantId") int restaurantId,@Param("id") int id, @Param("userId") int userId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.restaurant.id=:restaurantId AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId")
    List<Vote> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.timeExist>=:startDate AND v.timeExist<:endTime")
    List<Vote> getByTimeExist(@Param("userId") int userId, @Param("startDate")LocalDateTime startDate,@Param("endTime") LocalDateTime endTime);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId")
    List<Vote> getByTimeToDay(@Param("userId") int userId);

}
