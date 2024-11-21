package com.project.capstone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false ,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>(); // Initialize the Set here

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;  // One User can have many bookings

    public void setRole(Role role) {
        this.roles.add(role);  // This will work now
    }

    public Set<Role> getRole() {
        return roles;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + Id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", roles=" + (roles != null ? roles.size() : 0) + // Avoid printing roles list directly
                '}';
    }

}

