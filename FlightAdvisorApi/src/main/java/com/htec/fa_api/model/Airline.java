package com.htec.fa_api.model;


import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Airline {
    private Integer id;
    private Integer openFlightId;  //from file
    private String name;
    private String alias;
    private String iataCode;
    private String icaoCode;
    private Country country;
    private String description;
    //todo add still operating
    private Byte active;

    public Airline() {
    }

    public Airline(Integer openFlightId, String name, String alias, String iataCode, String icaoCode, Country country, String description) {
        this.openFlightId = openFlightId;
        this.name = name;
        this.alias = alias;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.country = country;
        this.description = description;
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
    @Column(name = "open_flight_id", nullable = false)
    public Integer getOpenFlightId() {
        return openFlightId;
    }

    public void setOpenFlightId(Integer openFlightId) {
        this.openFlightId = openFlightId;
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
    @Column(name = "alias", nullable = true)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "iata_code", nullable = true)
    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    @Basic
    @Column(name = "icao_code", nullable = true) //example N/A
    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    @ManyToOne
    @JoinColumn(name = "country", nullable = true)
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
        Airline airline = (Airline) o;
        return Objects.equals(id, airline.id) &&
                Objects.equals(openFlightId, airline.openFlightId) &&
                Objects.equals(name, airline.name) &&
                Objects.equals(alias, airline.alias) &&
                Objects.equals(iataCode, airline.iataCode) &&
                Objects.equals(icaoCode, airline.icaoCode) &&
                Objects.equals(country, airline.country) &&
                Objects.equals(description, airline.description) &&
                Objects.equals(active, airline.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openFlightId, name, alias, iataCode, icaoCode, country, description, active);
    }

    @Override
    public String toString() {
        return "Airline{" +
                "id=" + id +
                ", openFlightId=" + openFlightId +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", iataCode='" + iataCode + '\'' +
                ", icaoCode='" + icaoCode + '\'' +
                ", country=" + country +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
}
