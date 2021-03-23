package com.htec.fa_api.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Country implements Comparable<Country> {
    private Integer id;
    private String name;
    private String alphaCode;
    private Byte active;

    public Country() {
    }


    public Country(String name, String alphaCode) {
        this.name = name;
        this.alphaCode = alphaCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Basic
    @Column(name = "alpha_code", nullable = false)
    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
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
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name) &&
                Objects.equals(alphaCode, country.alphaCode) &&
                Objects.equals(active, country.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, alphaCode, active);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alphaCode='" + alphaCode + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public int compareTo(Country c) {
        return this.name.compareTo(c.name);
    }
}
