package com.example.fragrancecollection;

public class Fragrance {
    private int fragranceId;
    private int userId;
    private String name;
    private String perfumer;
    private int releaseYear;
    private String notes;
    private byte[] image;

    public Fragrance(){

    }

    public Fragrance(int userId, String name, String perfumer, int releaseYear, String notes, byte[] image) {
        this.userId = userId;
        this.name = name;
        this.perfumer = perfumer;
        this.releaseYear = releaseYear;
        this.notes = notes;
        this.image = image;
    }

    public Fragrance(int fragranceId, int userId, String name, String perfumer, int releaseYear, String notes, byte[] image) {
        this.fragranceId = fragranceId;
        this.userId = userId;
        this.name = name;
        this.perfumer = perfumer;
        this.releaseYear = releaseYear;
        this.notes = notes;
        this.image = image;
    }

    public int getFragranceId() {
        return fragranceId;
    }

    public void setFragranceId(int fragranceId) {
        this.fragranceId = fragranceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerfumer() {
        return perfumer;
    }

    public void setPerfumer(String perfumer) {
        this.perfumer = perfumer;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
