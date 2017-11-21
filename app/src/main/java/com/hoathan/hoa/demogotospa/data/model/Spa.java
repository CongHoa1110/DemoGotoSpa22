package com.hoathan.hoa.demogotospa.data.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Spa implements Serializable {
    private String spaID;
    private int spaViews;
    private ArrayList<String> spaImage;
    private String spaName;
    private String spaAddress;
    private String spaLocation;
    private String spaDescription;
    private String spaYear;
    private String spaOpen;
    private String spaClose;
    private String spaPhone;
    private String spaEmail;
    public String tyte;
    private String spaTime;
    private boolean spaLike;

    public boolean getspaLike() {
        return spaLike;
    }

    public void setSpaLike(boolean spaColor) {
        this.spaLike = spaColor;
    }

    public String getSpaTime() {
        return spaTime;
    }

    public void setSpaTime(String spaTime) {
        this.spaTime = spaTime;
    }

    public Spa() {
    }

    public Spa(String tyte) {
        this.tyte = tyte;
    }

    public Spa(String spaID, int spaViews, ArrayList<String> spaImage,
               String spaName, String spaAddress, String spaLocation,
               String spaDescription, String spaYear, String spaOpen,
               String spaClose, String spaPhone, String spaEmail,String spaTime,boolean spaLike) {
        this.spaID = spaID;
        this.spaViews = spaViews;
        this.spaImage = spaImage;
        this.spaName = spaName;
        this.spaAddress = spaAddress;
        this.spaLocation = spaLocation;
        this.spaDescription = spaDescription;
        this.spaYear = spaYear;
        this.spaOpen = spaOpen;
        this.spaClose = spaClose;
        this.spaPhone = spaPhone;
        this.spaEmail = spaEmail;
        this.spaTime = spaTime;
        this.spaLike = spaLike;
    }

    public String getSpaID() {
        return spaID;
    }

    public void setSpaID(String spaID) {
        this.spaID = spaID;
    }

    public int getSpaViews() {
        return spaViews;
    }

    public void setSpaViews(int spaViews) {
        this.spaViews = spaViews;
    }

    public ArrayList<String> getSpaImage() {
        return spaImage;
    }

    public void setSpaImage(ArrayList<String> spaImage) {
        this.spaImage = spaImage;
    }

    public String getSpaName() {
        return spaName;
    }

    public void setSpaName(String spaName) {
        this.spaName = spaName;
    }

    public String getSpaAddress() {
        return spaAddress;
    }

    public void setSpaAddress(String spaAddress) {
        this.spaAddress = spaAddress;
    }

    public String getSpaLocation() {
        return spaLocation;
    }

    public void setSpaLocation(String spaLocation) {
        this.spaLocation = spaLocation;
    }

    public String getSpaDescription() {
        return spaDescription;
    }

    public void setSpaDescription(String spaDescription) {
        this.spaDescription = spaDescription;
    }

    public String getSpaYear() {
        return spaYear;
    }

    public void setSpaYear(String spaYear) {
        this.spaYear = spaYear;
    }

    public String getSpaOpen() {
        return spaOpen;
    }

    public void setSpaOpen(String spaOpen) {
        this.spaOpen = spaOpen;
    }

    public String getSpaClose() {
        return spaClose;
    }

    public void setSpaClose(String spaClose) {
        this.spaClose = spaClose;
    }

    public String getSpaPhone() {
        return spaPhone;
    }

    public void setSpaPhone(String spaPhone) {
        this.spaPhone = spaPhone;
    }

    public String getSpaEmail() {
        return spaEmail;
    }

    public void setSpaEmail(String spaEmail) {
        this.spaEmail = spaEmail;
    }
}
