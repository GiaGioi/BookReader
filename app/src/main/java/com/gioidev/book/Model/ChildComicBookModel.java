package com.gioidev.book.Model;

public class ChildComicBookModel {

    private String Url;

    public ChildComicBookModel(){}
    public ChildComicBookModel(String url) {
        Url = url;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
