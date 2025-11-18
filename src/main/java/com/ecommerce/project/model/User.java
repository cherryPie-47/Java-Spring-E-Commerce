package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 20, min = 4)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120, min = 4)
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE},
               orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    // Here User is the owning side, so we only want to cascade persist and merge operations
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Cart cart;

    public User() {
    }

    public User(String username, String email, String password, Set<Role> roles,Set<Product> products, Cart cart) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.products = products;
        this.cart = cart;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRole(Role role) {
        if (role == null) {
            return;
        }
        this.roles.add(role);
        if (!role.getUsers().contains(this)) {
            role.getUsers().add(this);
        }
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setProduct(Product product) {
        if (product == null) {
            return;
        }
        this.products.add(product);
        if (!this.equals(product.getUser())) {
            product.setUser(this);
        }
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setAddress(Address address) {
        if (address == null) {
            return;
        }
        this.addresses.add(address);
        if (!this.equals(address.getUser())) {
            address.setUser(this);
        }
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        if (cart == null) {
            return;
        }
        this.cart = cart;
        if (this.equals(cart.getUser())) {
            cart.setUser(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "addresses=" + addresses +
                ", roles=" + roles +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                '}';
    }
}
