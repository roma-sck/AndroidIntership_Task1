package com.example.sck.androidintership_task1.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class IssueDataModel extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private User user;
    @SerializedName("category")
    private Category category;
    @SerializedName("type")
    private Type type;
    @SerializedName("title")
    private String title;
    @SerializedName("body")
    private String body;
    @SerializedName("created_date")
    private int createdDate;
    @SerializedName("start_date")
    private int startDate;
    @SerializedName("state")
    private State state;
    @SerializedName("ticket_id")
    private String ticketId;
    @SerializedName("files")
    private RealmList<File> files = new RealmList<File>();
    @SerializedName("performers")
    private RealmList<Performer> performers = new RealmList<Performer>();
    @SerializedName("deadline")
    private int deadline;
    @SerializedName("likes_counter")
    private int likesCounter;
    @SerializedName("address")
    private Address address;
    @SerializedName("geo_address")
    private GeoAddress geoAddress;
    @SerializedName("completed_date")
    private int completedDate;

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
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     *
     * @param category
     *     The category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The type
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     *     The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 
     * @return
     *     The createdDate
     */
    public int getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     *     The created_date
     */
    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 
     * @return
     *     The startDate
     */
    public int getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     *     The start_date
     */
    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return
     *     The state
     */
    public State getState() {
        return state;
    }

    /**
     *
     * @param state
     *     The state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The ticketId
     */
    public String getTicketId() {
        return ticketId;
    }

    /**
     *
     * @param ticketId
     *     The ticket_id
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * 
     * @return
     *     The files
     */
    public RealmList<File> getFiles() {
        return files;
    }

    /**
     *
     * @param files
     *     The files
     */
    public void setFiles(RealmList<File> files) {
        this.files = files;
    }

    /**
     * 
     * @return
     *     The performers
     */
    public RealmList<Performer> getPerformers() {
        return performers;
    }

    /**
     *
     * @param performers
     *     The performers
     */
    public void setPerformers(RealmList<Performer> performers) {
        this.performers = performers;
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

    /**
     * 
     * @return
     *     The likesCounter
     */
    public int getLikesCounter() {
        return likesCounter;
    }

    /**
     *
     * @param likesCounter
     *     The likes_counter
     */
    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }

    /**
     * 
     * @return
     *     The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address
     *     The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The geoAddress
     */
    public GeoAddress getGeoAddress() {
        return geoAddress;
    }

    /**
     *
     * @param geoAddress
     *     The geo_address
     */
    public void setGeoAddress(GeoAddress geoAddress) {
        this.geoAddress = geoAddress;
    }

    /**
     * 
     * @return
     *     The completedDate
     */
    public int getCompletedDate() {
        return completedDate;
    }

    /**
     *
     * @param completedDate
     *     The completed_date
     */
    public void setCompletedDate(int completedDate) {
        this.completedDate = completedDate;
    }

}
