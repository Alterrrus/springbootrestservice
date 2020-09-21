package com.boot.SpringBootRestService.repository.extendDataJpa;


import com.boot.SpringBootRestService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserJPA extends JpaRepository<User,Integer> {



    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);
    @Query("select u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=:email")
    User getByEmail(@Param("email") String email);


}
