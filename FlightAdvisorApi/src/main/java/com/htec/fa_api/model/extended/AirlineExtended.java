package com.htec.fa_api.model.extended;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true) //todo validation
public class AirlineExtended {
    private Integer openFlightId;
    private String name;
    private String alias;
    private String iataCode;
    private String icaoCode;
    private String callsign;
    private String countryName;
    private String stillOperating;

    public AirlineExtended() {
    }

    public AirlineExtended(Integer openFlightId, String name, String alias, String iataCode, String icaoCode, String callsign, String countryName, String stillOperating) {
        this.openFlightId = openFlightId;
        this.name = name;
        this.alias = alias;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.callsign = callsign;
        this.countryName = countryName;
        this.stillOperating = stillOperating;
    }
    @JsonProperty("openFlightId")
    public Integer getOpenFlightId() {
        return openFlightId;
    }

    public void setOpenFlightId(Integer openFlightId) {
        this.openFlightId = openFlightId;
    }
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
    @JsonProperty("callsign")
    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
    @JsonProperty("countryName")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    @JsonProperty("stillOperating")
    public String getStillOperating() {
        return stillOperating;
    }

    public void setStillOperating(String stillOperating) {
        this.stillOperating = stillOperating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirlineExtended that = (AirlineExtended) o;
        return Objects.equals(openFlightId, that.openFlightId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(alias, that.alias) &&
                Objects.equals(iataCode, that.iataCode) &&
                Objects.equals(icaoCode, that.icaoCode) &&
                Objects.equals(callsign, that.callsign) &&
                Objects.equals(countryName, that.countryName) &&
                Objects.equals(stillOperating, that.stillOperating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openFlightId, name, alias, iataCode, icaoCode, callsign, countryName, stillOperating);
    }

    @Override
    public String toString() {
        return "AirlineExtended{" +
                "openFlightId=" + openFlightId +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", iataCode='" + iataCode + '\'' +
                ", icaoCode='" + icaoCode + '\'' +
                ", callsign='" + callsign + '\'' +
                ", countryName='" + countryName + '\'' +
                ", stillOperating='" + stillOperating + '\'' +
                '}';
    }
}
