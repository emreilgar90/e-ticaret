package com.emreilgar.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String mail;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean isActive;

    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<UserDetails> userDetailsSet= new HashSet<>(); //ilk oluşturulan user'ın userDetails'i null döndüğü için yaptım.


    public Users(Long id, String mail, String firstName, String lastName, String middleName, boolean b) {
    }

//    public User(Long id, String mail, String firstName, String lastName, String middleName, Boolean isActive, Set<UserDetails> userDetailsSet) {
//        this.id = id;
//        this.mail = mail;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.middleName = middleName;
//        this.isActive = isActive;
//        this.userDetailsSet = userDetailsSet;
//    }
//
//    public User(Long id, String mail, String firstName, String lastName, String middleName) {
//        this.id = id;
//        this.mail = mail;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.middleName = middleName;
//    }
//
//    public User(String mail, String firstName, String lastName, String middleName, Boolean isActive, Set<UserDetails> userDetailsSet) {
//        this.mail = mail;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.middleName = middleName;
//        this.isActive = isActive;
//        this.userDetailsSet = userDetailsSet;
//    }
//
//    public User(String mail, String firstName, String lastName, String middleName, Boolean isActive) {
//        this.mail = mail;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.middleName = middleName;
//        this.isActive = isActive;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users that = (Users) o;
        return Objects.equals(id, that.id) && Objects.equals(mail, that.mail) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(middleName, that.middleName) && Objects.equals(isActive, that.isActive);
    }
    public Set<UserDetails> getUserDetailsSet(){
        return userDetailsSet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mail, firstName, lastName, middleName, isActive);
    }
}


