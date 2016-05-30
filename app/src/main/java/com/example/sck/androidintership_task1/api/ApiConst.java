package com.example.sck.androidintership_task1.api;

public class ApiConst {
    public static final String ENDPOINT = "http://dev-contact.yalantis.com/rest/v1/";

    public static final String STATE_IN_PROGRESS = "0,9,5,7,8";
    public static final String STATE_IN_DONE = "10,6";
    public static final String STATE_IN_PENDING = "1,3,4";

    public static final int TICKETS_AMOUNT = 10;
    public static final int TICKETS_WITHOUT_OFFSET = -1;

    public static final String STATE_FIELD_NAME = "state.name";
    public static final String STATE_IN_PROGRESS_VALUE = "В роботі";
    public static final String STATE_IN_DONE_VALUE = "Виконано";
    public static final String STATE_IN_PENDING_VALUES = "На розгляді,Прийнято модератором";
    public static final String STATE_IN_PENDING_VALUE_1 = "На";
    public static final String STATE_IN_PENDING_VALUE_2 = "При";

    public static final String TICKETS_IMGS_URL = "http://dev-contact.yalantis.com/files/ticket/";
}
