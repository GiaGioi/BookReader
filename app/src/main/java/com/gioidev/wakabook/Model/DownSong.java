package com.gioidev.wakabook.Model;

public class DownSong {
    String Name;
    String Link;
    String Time;
    String Talker;
    String Description;
    String AuthorAudio;
    String Chapter;

    public DownSong(){}
    public DownSong(String name, String link, String time, String talker, String description, String authorAudio,String chapter) {
        Name = name;
        Link = link;
        Time = time;
        Talker = talker;
        Description = description;
        AuthorAudio = authorAudio;
        Chapter = chapter;
    }

    public String getChapter() {
        return Chapter;
    }

    public void setChapter(String chapter) {
        Chapter = chapter;
    }

    public String getName() {
        return Name;
    }

    public String getTalker() {
        return Talker;
    }

    public void setTalker(String talker) {
        Talker = talker;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthorAudio() {
        return AuthorAudio;
    }

    public void setAuthorAudio(String authorAudio) {
        AuthorAudio = authorAudio;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
