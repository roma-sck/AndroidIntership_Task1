package com.example.sck.androidintership_task1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Address extends RealmObject {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("street")
    @Expose
    private Street street;
    @SerializedName("house")
    @Expose
    private House house;
    @SerializedName("flat")
    @Expose
    private String flat;

    /**
     *
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The district
     */
    public District getDistrict() {
        return district;
    }

    /**
     *
     * @param district
     *     The district
     */
    public void setDistrict(District district) {
        this.district = district;
    }

    /**
     *
     * @return
     *     The city
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     *     The city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     *
     * @return
     *     The street
     */
    public Street getStreet() {
        return street;
    }

    /**
     *
     * @param street
     *     The street
     */
    public void setStreet(Street street) {
        this.street = street;
    }

    /**
     *
     * @return
     *     The house
     */
    public House getHouse() {
        return house;
    }

    /**
     *
     * @param house
     *     The house
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     *
     * @return
     *     The flat
     */
    public String getFlat() {
        return flat;
    }

    /**
     *
     * @param flat
     *     The flat
     */
    public void setFlat(String flat) {
        this.flat = flat;
    }

}
