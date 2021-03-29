package com.htec.fa_api.model;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Airport {
    private Integer id;
    private Integer openFlightId;
    private String name;
    private City city;
    private String iataCode;
    private String icaoCode;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double utcTimeOffset;
    private String type;
    private String dst;
    private String dbTimezone;
    private Byte active;
    //todo: source data not relevant?

    public Airport() {
    }

    public Airport(Integer openFlightId, String name, City city, String iataCode, String icaoCode, Double latitude, Double longitude, Double altitude, Double utcTimeOffset, String type, String dst, String dbTimezone) {
        this.openFlightId = openFlightId;
        this.name = name;
        this.city = city;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.utcTimeOffset = utcTimeOffset;
        this.type = type;
        this.dst = dst;
        this.dbTimezone = dbTimezone;
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
    @Column(name = "openFlightId", nullable = false)
    @Unique
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

    @ManyToOne
    @JoinColumn(name = "city", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
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
    @Column(name = "icao_code", nullable = false)
    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
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

    @Basic
    @Column(name = "latitude", nullable = false)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = false)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "altitude", nullable = true)
    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "dst", nullable = false)
    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    @Basic
    @Column(name = "dbTimezone", nullable = false)
    @ColumnDefault(value = "")
    public String getDbTimezone() {
        return dbTimezone;
    }

    public void setDbTimezone(String dbTimezone) {
        this.dbTimezone = dbTimezone;
    }

    @Basic
    @Column(name = "utcTimeOffset", nullable = true) //some of data are allowed to be nullable because their value is not supplied
    public Double getUtcTimeOffset() {
        return utcTimeOffset;
    }

    public void setUtcTimeOffset(Double utcTimeOffset) {
        this.utcTimeOffset = utcTimeOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(id, airport.id) &&
                Objects.equals(openFlightId, airport.openFlightId) &&
                Objects.equals(name, airport.name) &&
                Objects.equals(city, airport.city) &&
                Objects.equals(iataCode, airport.iataCode) &&
                Objects.equals(icaoCode, airport.icaoCode) &&
                Objects.equals(latitude, airport.latitude) &&
                Objects.equals(longitude, airport.longitude) &&
                Objects.equals(altitude, airport.altitude) &&
                Objects.equals(utcTimeOffset, airport.utcTimeOffset) &&
                Objects.equals(type, airport.type) &&
                Objects.equals(dst, airport.dst) &&
                Objects.equals(dbTimezone, airport.dbTimezone) &&
                Objects.equals(active, airport.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openFlightId, name, city, iataCode, icaoCode, latitude, longitude, altitude, utcTimeOffset, type, dst, dbTimezone, active);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", openFlightId=" + openFlightId +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", iataCode='" + iataCode + '\'' +
                ", icaoCode='" + icaoCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", utcTimeOffset=" + utcTimeOffset +
                ", type='" + type + '\'' +
                ", dst='" + dst + '\'' +
                ", dbTimezone='" + dbTimezone + '\'' +
                ", active=" + active +
                '}';
    }
}
