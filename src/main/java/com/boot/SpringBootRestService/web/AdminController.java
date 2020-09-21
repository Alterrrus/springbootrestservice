package com.boot.SpringBootRestService.web;



import com.boot.SpringBootRestService.View;
import com.boot.SpringBootRestService.model.User;
import com.boot.SpringBootRestService.service.UserService;
import com.boot.SpringBootRestService.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.boot.SpringBootRestService.util.ValidationUtil.assureIdConsistent;


@RestController
@RequestMapping(value = "/rest/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final UserService service;


    public AdminController(UserService service) {
        this.service = service;
    }
    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Validated(View.Web.class) @RequestBody User user) {
        ValidationUtil.checkNew(user);
        User created =service.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/admin/users" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody User user, @PathVariable int id) {
        assureIdConsistent(user, id);
        service.update(user);
    }
    @GetMapping("/by")
    public User getByMail(@RequestParam String email) {
        return service.getByEmail(email);
    }


}
