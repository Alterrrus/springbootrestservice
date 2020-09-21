package com.boot.SpringBootRestService.model;

import com.boot.SpringBootRestService.View;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.BatchSize;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

import static org.hibernate.validator.constraints.SafeHtml.WhiteListType.NONE;

//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "users")
public class User extends AbstractBaseEntity{

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name",nullable = false)
    @SafeHtml(groups = {View.Web.class}, whitelistType = NONE)
    private String name;

    @Column(name = "email",unique = true,nullable = false)
    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml(groups = {View.Web.class}, whitelistType = NONE)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonManagedReference(value = "voteList1")
    private List<Vote> voteList;

    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }

    public List<Vote> getVoteList() {
        return voteList;
    }

    public User() {
    }
    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.isEnabled(), u.getRegistered(), u.getRoles());
    }



    public User(Integer id,String name, String email, String password, boolean enabled, Date registered,Collection<Role> roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }
    public User(String name, String email, String password, boolean enabled, Date registered,Collection<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }
    public User(Integer id,String name,String email,String password,Role role,Role... roles){
        this(id,name,email,password,true,new Date(),EnumSet.of(role,roles));
    }
    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registered=" + registered +
                ", roles=" + roles +
                '}';
    }
}
