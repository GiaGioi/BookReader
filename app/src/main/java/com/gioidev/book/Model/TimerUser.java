package com.gioidev.book.Model;

public class TimerUser {
    String userid;
    int timer;

    public TimerUser(String userid, int timer) {
        this.userid = userid;
        this.timer = (int) timer;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
    
}
