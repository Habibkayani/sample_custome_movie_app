package com.example.primevideoclone.model;

import java.util.List;

public class AllCategory {
    Integer CatagoryId;
    String CatagoryTitle;
    private List<CategoryItem> categoryItemList = null;

    public AllCategory(Integer catagoryId, String catagoryTitle, List<CategoryItem> categoryItemList) {
        this.CatagoryId = catagoryId;
        this.CatagoryTitle = catagoryTitle;
        this.categoryItemList = categoryItemList;
    }

    public Integer getCatagoryId() {
        return CatagoryId;
    }

    public void setCatagoryId(Integer catagoryId) {
        CatagoryId = catagoryId;
    }

    public String getCatagoryTitle() {
        return CatagoryTitle;
    }

    public void setCatagoryTitle(String catagoryTitle) {
        CatagoryTitle = catagoryTitle;
    }

    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }
}