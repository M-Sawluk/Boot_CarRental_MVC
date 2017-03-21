package com.michal.springboot.domain;

import com.michal.springboot.forms.ReCaptchaForm;
import com.michal.springboot.forms.UserStatus;
import com.michal.springboot.validators.PasswordRepetition;
import com.michal.springboot.validators.UserEmail;
import com.michal.springboot.validators.UserName;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Mike on 2017-03-14.
 */
@Entity
@PasswordRepetition
public class User extends ReCaptchaForm implements Serializable {

    private static final long serialVersionUID = -5991809008372848478L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "[a-zA-Z ]{5,25}", message = "{pattern.user.username.validation}")
    @UserName
    private String username;

    @Pattern(regexp = "[a-zA-Z ]{3,25}", message = "{pattern.user.name.validation}")
    private String firstName;

    @Pattern(regexp = "[a-zA-Z ]{3,25}", message = "{pattern.user.name.validation}")
    private String lastName;

    @Size(min = 5,message = "{pattern.user.password.validation}")
    private String password;

    @Size(min = 5, message = "{pattern.user.password.validation}")
    @Transient
    private String password1;

    @Email
    @UserEmail
    @NotNull
    private String email;

    private Date dateCreated;

    @Pattern(regexp = "[a-zA-Z0-9./ ]{4,60}", message = "{pattern.user.address.validation}")
    private String address;

    @Pattern(regexp = "[0-9-+ ]{7,14}", message = "{pattern.user.tele.validation}")
    private String telephone;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private String birth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UsersAndRoles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @OneToMany(mappedBy="user",orphanRemoval=true,fetch = FetchType.EAGER)
    private Set<Order> order = new HashSet<Order>();

    public User() {
        this.status= UserStatus.INACTIVE;
        this.setRoles(Arrays.asList(new Role("USER_ROLE")));
        this.setDateCreated(new Date());
    }

    public User(String username, String password,
                List<Role> roles, UserStatus status,
                String email, String birth,
                String telephone, String address) {

        this.username = username;
        this.password = password;
        this.roles = roles;
        this.status = status;
        this.email = email;
        this.birth = birth;
        this.telephone = telephone;
        this.address = address;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {

        this.roles = roles;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!username.equals(user.username)) return false;
        if (!lastName.equals(user.lastName)) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + username.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

}
