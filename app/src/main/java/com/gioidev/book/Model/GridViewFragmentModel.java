package com.gioidev.book.Model;

public class GridViewFragmentModel {
    private String nameBook, description,url,gs,image,price,nameAuthor,category,uid;

    public GridViewFragmentModel(){
    }
    public GridViewFragmentModel(String nameBook, String description, String url, String gs, String image, String price, String nameAuthor, String category, String uid) {
        this.nameBook = nameBook;
        this.description = description;
        this.url = url;
        this.gs = gs;
        this.image = image;
        this.price = price;
        this.nameAuthor = nameAuthor;
        this.category = category;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
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

    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }
}
