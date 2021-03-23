package com.htec.fa_api.model.extended;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteExtended implements Serializable {
    private String airlineCode;
    private Integer airlineId;
    private String sourceAirportCode;
    private Integer sourceAirportId;
    private String destinationAirportCode;
    private Integer destinationAirportId;
    private String codeshare;
    private Integer stops;
    private String equipmentCode;
    private Double price;

    public RouteExtended() {
    }

    public RouteExtended(String airlineCode, Integer airlineId, String sourceAirportCode, Integer sourceAirportId, String destinationAirportCode, Integer destinationAirportId, String codeshare, Integer stops, String equipmentCode, Double price) {
        this.airlineCode = airlineCode;
        this.airlineId = airlineId;
        this.sourceAirportCode = sourceAirportCode;
        this.sourceAirportId = sourceAirportId;
        this.destinationAirportCode = destinationAirportCode;
        this.destinationAirportId = destinationAirportId;
        this.codeshare = codeshare;
        this.stops = stops;
        this.equipmentCode = equipmentCode;
        this.price = price;
    }

    @JsonProperty("airlineCode")
    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    @JsonProperty("airlineId")
    public Integer getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Integer airlineId) {
        this.airlineId = airlineId;
    }

    @JsonProperty("sourceAirportCode")
    public String getSourceAirportCode() {
        return sourceAirportCode;
    }

    public void setSourceAirportCode(String sourceAirportCode) {
        this.sourceAirportCode = sourceAirportCode;
    }

    @JsonProperty("sourceAirportId")
    public Integer getSourceAirportId() {
        return sourceAirportId;
    }

    public void setSourceAirportId(Integer sourceAirportId) {
        this.sourceAirportId = sourceAirportId;
    }

    @JsonProperty("destinationAirportCode")
    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }

    @JsonProperty("destinationAirportId")
    public Integer getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(Integer destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    @JsonProperty("codeshare")
    public String getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(String codeshare) {
        this.codeshare = codeshare;
    }

    @JsonProperty("stops")
    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    @JsonProperty("equipmentCode")
    public String getEquipment() {
        return equipmentCode;
    }

    public void setEquipment(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteExtended that = (RouteExtended) o;
        return Objects.equals(airlineCode, that.airlineCode) &&
                Objects.equals(airlineId, that.airlineId) &&
                Objects.equals(sourceAirportCode, that.sourceAirportCode) &&
                Objects.equals(sourceAirportId, that.sourceAirportId) &&
                Objects.equals(destinationAirportCode, that.destinationAirportCode) &&
                Objects.equals(destinationAirportId, that.destinationAirportId) &&
                Objects.equals(codeshare, that.codeshare) &&
                Objects.equals(stops, that.stops) &&
                Objects.equals(equipmentCode, that.equipmentCode) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airlineCode, airlineId, sourceAirportCode, sourceAirportId, destinationAirportCode, destinationAirportId, codeshare, stops, equipmentCode, price);
    }

    @Override
    public String toString() {
        return "RouteExtended{" +
                "airlineCode='" + airlineCode + '\'' +
                ", airlineId=" + airlineId +
                ", sourceAirportCode='" + sourceAirportCode + '\'' +
                ", sourceAirportId=" + sourceAirportId +
                ", destinationAirportCode='" + destinationAirportCode + '\'' +
                ", destinationAirportId=" + destinationAirportId +
                ", codeshare='" + codeshare + '\'' +
                ", stops=" + stops +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", price=" + price +
                '}';
    }
}
