package com.example.primevideoclone.model;

import com.google.gson.annotations.SerializedName;

public class BannerMovies {
Integer bannerCategoryId;
    Integer Id;
    String movieName;

    @SerializedName("imageUrl")
    String ImageUrl;
    String fileUrl;

    public Integer getBannerCategoryId() {
        return bannerCategoryId;
    }

    public void setBannerCategoryId(Integer bannerCategoryId) {
        this.bannerCategoryId = bannerCategoryId;
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
