package com.htec.fa_api.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class City {
    private Integer id;
    private String name;
    private Country country;
    private String description;
    private String postalCode;
    private Byte active;

    public City() {
    }

    public City(String name, Country country, String description, String postalCode) {
        this.name = name;
        this.country = country;
        this.description = description;
        this.postalCode = postalCode;
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
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @ManyToOne
    @JoinColumn(name = "country", nullable = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Basic
    @Column(name = "description", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "postal_code", nullable = true)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(id, city.id) &&
                Objects.equals(name, city.name) &&
                Objects.equals(country, city.country) &&
                Objects.equals(description, city.description) &&
                Objects.equals(postalCode, city.postalCode) &&
                Objects.equals(active, city.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, description, postalCode, active);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", description='" + description + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", active=" + active +
                '}';
    }
}
