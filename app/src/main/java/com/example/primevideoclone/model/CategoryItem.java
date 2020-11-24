package com.example.primevideoclone.model;

public class  CategoryItem {
    Integer Id;
    String movieName;
    String ImageUrl;
    String fileUrl;

    public CategoryItem(Integer id, String movieName, String imageUrl, String fileUrl) {
        Id = id;
        this.movieName = movieName;
        ImageUrl = imageUrl;
        this.fileUrl = fileUrl;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}