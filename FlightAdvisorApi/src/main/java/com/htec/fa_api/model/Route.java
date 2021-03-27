package com.htec.fa_api.model;


import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
public class Route {
    private Integer id;
    private Airline airline;
    private Airport sourceAirport;
    private Airport destinationAirport;
    private Byte codeshare; //"yes" or "no"
    private Integer stops;
    private AircraftType equipment; //todo
    private Double price; //instead of BigDecimal
    private Byte active;

    public Route() {
    }

    public Route(Airline airline, Airport sourceAirport, Airport destinationAirport, Byte codeshare, Integer stops, AircraftType equipment, Double price) {
        this.airline = airline;
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.codeshare = codeshare;
        this.stops = stops;
        this.equipment = equipment;
        this.price = price;
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

    @ManyToOne
    @JoinColumn(name = "airline", nullable = false)
    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    @ManyToOne
    @JoinColumn(name = "source_airport", nullable = false)
    public Airport getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(Airport sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

    @ManyToOne
    @JoinColumn(name = "destination_airport", nullable = false)
    public Airport getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Airport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    @Basic
    @Column(name = "codeshare", nullable = false)
    @ColumnDefault(value = "1")
    public Byte getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(Byte codeshare) {
        this.codeshare = codeshare;
    }

    @Basic
    @Column(name = "stops", nullable = false)
    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    @ManyToOne
    @JoinColumn(name = "equipment", nullable = false)
    public AircraftType getEquipment() {
        return equipment;
    }

    public void setEquipment(AircraftType equipment) {
        this.equipment = equipment;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
