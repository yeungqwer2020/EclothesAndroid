package com.example.eclothes.Models;



import java.util.List;

public class Location {
    private List<Double> coordinates;
    private String address;
    private District district;

    public Location(List<Double> coordinates, String address, District district) {
        this.coordinates = coordinates;
        this.address = address;
        this.district = district;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }
}
