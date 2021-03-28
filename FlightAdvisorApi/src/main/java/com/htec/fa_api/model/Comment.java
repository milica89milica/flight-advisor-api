package com.htec.fa_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public class Comment {
    private Integer id;
    private String title;
    private String details;
    private Timestamp created;
    private Timestamp updated;
    private City city;
    private User user;
    private Byte active;

    public Comment() {
    }

    public Comment(String title, String details, Timestamp created, Timestamp updated, City city, User user) {
        this.title = title;
        this.details = details;
        this.created = created;
        this.updated = created; //simplicity
        this.city = city;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true)
    // @NotNull(message = "{NotNull.name}") //todo?
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "details", nullable = false)
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @CreationTimestamp
    @JsonFormat(pattern = "dd.MM.yyyy. HH:mm")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @UpdateTimestamp
    @JsonFormat(pattern = "dd.MM.yyyy. HH:mm")
    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    @JsonBackReference(value = "citys-comments")
    @ManyToOne
    @JoinColumn(name = "city", nullable = false)
    @NotNull(message = "{notNull.city}")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @JsonBackReference(value = "users-comments")
    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    @NotNull(message = "{notNull.user}")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "active", nullable = false, insertable = false)
    @ColumnDefault(value = "1")
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

}
