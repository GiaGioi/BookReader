package com.gioidev.wakabook.Model;

public class Books {
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
    public Books(){}

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

    public Books(String price, String image, String gs, String nameBook, String nameAuthor, String description, String url) {
        this.price = price;
        this.image = image;
        this.gs = gs;
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.description = description;
        this.url = url;
    }

    public Books(String nameBook, String nameAuthor, String description, String url) {
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.description = description;
        this.url = url;
    }

    public String price;
    public String image;
    public String gs;
    public String nameBook;
    public String nameAuthor;
    public String description;
    public String url;


}
