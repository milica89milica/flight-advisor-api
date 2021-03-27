package com.htec.fa_api.model.extended;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportExtended implements Serializable {
    private Integer id;
    private String name;
    private String cityName;
    private String countryName;
    private String iataCode;
    private String icaoCode;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Integer utcTimeOffset;
    private String dst;
    private String timeZone;
    private String type;

    //not using source, it's not relevant


    public AirportExtended() {
    }

    public AirportExtended(Integer id, String name, String cityName, String countryName, String iataCode, String icaoCode, Double latitude, Double longitude, Double altitude, String dst, String type, Integer utcTimeOffset, String timeZone) {
        this.id = id;
        this.name = name;
        this.cityName = cityName;
        this.countryName = countryName;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.dst = dst;
        this.type = type;
        this.utcTimeOffset = utcTimeOffset;
        this.timeZone = timeZone;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("cityName")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @JsonProperty("countryName")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("iataCode")
    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    @JsonProperty("icaoCode")
    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("altitude")
    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    @JsonProperty("utcTimeOffset")
    public Integer getUtcTimeOffset() {
        return utcTimeOffset;
    }

    public void setUtcTimeOffset(Integer utcTimeOffset) {
        this.utcTimeOffset = utcTimeOffset;
    }

    @JsonProperty("dst")
    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }


    @JsonProperty("timeZone")
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirportExtended that = (AirportExtended) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(cityName, that.cityName) &&
                Objects.equals(countryName, that.countryName) &&
                Objects.equals(iataCode, that.iataCode) &&
                Objects.equals(icaoCode, that.icaoCode) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(altitude, that.altitude) &&
                Objects.equals(utcTimeOffset, that.utcTimeOffset) &&
                Objects.equals(dst, that.dst) &&
                Objects.equals(type, that.type) &&
                Objects.equals(timeZone, that.timeZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cityName, countryName, iataCode, icaoCode, latitude, longitude, altitude, utcTimeOffset, dst, type, timeZone);
    }

    @Override
    public String toString() {
        return "AirportExtended{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", iataCode='" + iataCode + '\'' +
                ", icaoCode='" + icaoCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", utcTimeOffset=" + utcTimeOffset +
                ", dst='" + dst + '\'' +
                ", type='" + type + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}

