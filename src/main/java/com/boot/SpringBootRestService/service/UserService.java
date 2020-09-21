package com.boot.SpringBootRestService.service;



import com.boot.SpringBootRestService.AuthorizedUser;
import com.boot.SpringBootRestService.model.User;
import com.boot.SpringBootRestService.repository.JPA.UserRepo;
import com.boot.SpringBootRestService.to.UserTo;
import com.boot.SpringBootRestService.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.boot.SpringBootRestService.util.UserUtil.prepareToSave;
import static com.boot.SpringBootRestService.util.ValidationUtil.checkNotFound;
import static com.boot.SpringBootRestService.util.ValidationUtil.checkNotFoundWithId;


@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    protected final Logger log= LoggerFactory.getLogger(getClass());
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    private User prepareAndSave(User user) {
        return userRepo.save(prepareToSave(user, passwordEncoder));
    }


    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    public List<User> getAll(){
        log.info("getAll");
        return userRepo.getAll();

    }

    public User get(Integer id){
        log.info("GET");
        return checkNotFoundWithId(userRepo.get(id),id);
    }

    public User create(User user){
        log.info("create user");
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
    }

    public void update(User user){
        log.info("update user");
        Assert.notNull(user,"restaurant must not be null");
        //checkNotFoundWithId(userRepo.save(user),user.id());
        prepareAndSave(user);
        log.info(user.getClass().getSimpleName()+":name:"+user.getName());
    }

    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));   // !! need only for JDBC implementation
    }

    public void delete( int id){
        log.info("delete user");
        checkNotFoundWithId(userRepo.delete(id),id);
    }
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepo.getByEmail(email), "email=" + email);
    }
}
