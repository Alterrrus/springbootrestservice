package com.boot.SpringBootRestService.web;



import com.boot.SpringBootRestService.AuthorizedUser;
import com.boot.SpringBootRestService.model.User;
import com.boot.SpringBootRestService.service.UserService;
import com.boot.SpringBootRestService.to.UserTo;
import com.boot.SpringBootRestService.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.boot.SpringBootRestService.util.ValidationUtil.assureIdConsistent;
import static com.boot.SpringBootRestService.util.ValidationUtil.checkNew;


@RestController
@RequestMapping("/rest")
public class ProfileRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final UserService service;

    public ProfileRestController(UserService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/user/profile")
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        return service.get(authUser.getId());
    }

    @DeleteMapping(value = "/user/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        service.delete(authUser.getId());
    }

    public User create(UserTo userTo) {
        log.info("create from to {}", userTo);
        return create(UserUtil.createNewFromTo(userTo));
    }
    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/user/profile").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/user/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        assureIdConsistent(userTo, authUser.getId());
        service.update(userTo);
    }
}
