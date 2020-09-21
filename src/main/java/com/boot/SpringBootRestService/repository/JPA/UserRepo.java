package com.boot.SpringBootRestService.repository.JPA;



import com.boot.SpringBootRestService.model.User;

import java.util.List;

public interface UserRepo {
    List<User> getAll();

    User save(User user);

    User get(Integer id);

    boolean delete(Integer id);

    User getByEmail(String email);


}
