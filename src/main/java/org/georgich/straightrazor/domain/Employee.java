package org.georgich.straightrazor.domain;

import javax.persistence.*;

@Entity
@Table(name="staff")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private String position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Employee(){    }

    public Employee(String name, String surname, String position, User author) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.author = author;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    //getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPosition() {
        return position;
    }

    public User getAuthor() {
        return author;
    }

    public String getAuthorName() {
        return author.getName();
    }
}
