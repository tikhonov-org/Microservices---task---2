package ru.itmentor.spring.boot_security.demo.model;

import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name can not be empty")
    @Size(max = 50, message = "Name can be less than 50 characters")
    private String name;

    @Column(name = "age")
    @NotNull(message = "Age can not be empty")
    @Min(value=0, message="Age can not be less than zero")
    private int age;

    @Column(name = "email")
    @NotEmpty(message = "Email can not be empty")
    @Email(message="Email must be valid")
    private String email;

    @Column(name="login", unique=true)
    @NotEmpty(message = "Enter login")
    private String login;

    @Column(name="password")
    @NotEmpty(message = "Enter password")
    private String password;

    @ManyToMany
    @NotEmpty(message = "Select role")
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public User(int id, String name, int age, String email, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
