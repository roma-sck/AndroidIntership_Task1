package com.example.sck.androidintership_task1.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Performer extends RealmObject {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("person")
    @Expose
    private String person;
    @SerializedName("deadline")
    @Expose
    private int deadline;

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
     *     The organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     *     The organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * 
     * @return
     *     The person
     */
    public String getPerson() {
        return person;
    }

    /**
     *
     * @param person
     *     The person
     */
    public void setPerson(String person) {
        this.person = person;
    }

    /**
     * 
     * @return
     *     The deadline
     */
    public int getDeadline() {
        return deadline;
    }

    /**
     *
     * @param deadline
     *     The deadline
     */
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

}
