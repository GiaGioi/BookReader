package com.gioidev.wakabook.Model;

public class User {
    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public User(){}

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs;
    }

    public void User() {
    }
    public User(String price, String image, String gs, String nameBook, String nameAuthor, String description, String url,String category,String uid) {
        this.price = price;
        this.image = image;
        this.gs = gs;
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.description = description;
        this.url = url;
        this.category = category;
        this.uid = uid;
    }
    public String price;
    public String image;
    public String gs;
    public String nameBook;
    public String nameAuthor;
    public String description;
    public String url;
    public String category;
    public String uid;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
