package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@Builder
@Entity
public class UserInformation  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String mail;
    private String firstName;
    private String lastName;
    private String middleName;
    private Boolean isActive;

    public UserInformation(Long id, String mail, String firstName, String lastName, String middleName, Boolean isActive) {
        this.id = id;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.isActive = isActive;
    }

    public UserInformation(Long id,String mail, String firstName, String lastName, String middleName,boolean isActive) {
    }

    public UserInformation(String mail, String firstName, String lastName, String middleName, boolean isActive) {
    }
    public UserInformation(Long id,String mail, String firstName, String lastName, String middleName) {
    }

    public UserInformation(long l, String testMail, String john, String doe, String o, boolean b) {
    }



//    public Boolean getActive() {
//        return isActive;
//    }
}


