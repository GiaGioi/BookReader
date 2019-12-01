package com.gioidev.book.Model;

public class ChildComicBookModel {

    private String Name, Url;

    public ChildComicBookModel(){}
    public ChildComicBookModel(String name, String url) {
        Name = name;
        Url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
