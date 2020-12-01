package org.georgich.straightrazor.domain;

import javax.persistence.*;

@Entity
@Table(name="note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String date;
    private String time;
    private String comment;

    // relationships to tables
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;


    public Note(){    }

    public Note(String date, String time, String comment, Employee employee, Client client, User author) {
        this.date=date;
        this.time = time;
        this.comment = comment;
        this.employee = employee;
        this.client = client;
        this.author = author;
    }


    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    // getters
    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Client getClient() {
        return client;
    }

    public User getAuthor() {
        return author;
    }

    public String getClientName(){
        return client.getName()+" "+client.getSurname();
    }

    public String getClientPhone(){
        return client.getCellNumber();
    }

    public String getEmployeeName(){
        return employee.getName()+" "+employee.getSurname();
    }

    public Long getId() {
        return id;
    }

}
