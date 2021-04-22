package com.example.eclothes.Models;

import androidx.room.Embedded;

public class District {
    private String _id;
    private String district;
    @Embedded
    private Region region;

    public District(String district, Region region) {
        this.district = district;
        this.region = region;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
