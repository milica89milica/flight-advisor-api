package com.htec.fa_api.model;


import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AircraftType {
    private Integer id;
    private String icaoCode;
    private Byte active;

    public AircraftType() {
    }

    public AircraftType(String icaoCode) {
        this.icaoCode = icaoCode;
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
    @Column(name = "icaoCode", nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AircraftType that = (AircraftType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(icaoCode, that.icaoCode) &&
                Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, icaoCode, active);
    }

    @Override
    public String toString() {
        return "AircraftType{" +
                "id=" + id +
                ", icaoCode='" + icaoCode + '\'' +
                ", active=" + active +
                '}';
    }
}

