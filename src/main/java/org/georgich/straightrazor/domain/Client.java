package org.georgich.straightrazor.domain;

import javax.persistence.*;


@Entity
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private String cellNumber;
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public Client(){
    }

    public Client(String name, String surname, String cellNumber, User author) {
        this.name = name;
        this.surname = surname;
        this.cellNumber = cellNumber;
        this.author = author;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public void setComment(String comment){
        this.comment = comment;
    }


    //getters
    public String getAuthorName() {
        return author.getName();
    }

    public String getName() {
        return name;
    }

    public String getComment(){
        return comment;
    }

    public String getSurname() {
        return surname;
    }

    public String getCellNumber() {
        return cellNumber;
    }
}
