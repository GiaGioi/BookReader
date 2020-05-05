package com.gioidev.wakabook.Model;

public class TimerUser {
    String userid;
    int timer;
    boolean vip;

    public TimerUser(String userid, int timer, boolean vip) {
        this.userid = userid;
        this.timer = timer;
        this.vip = vip;
    }

    public TimerUser(String userid, int timer) {
        this.userid = userid;
        this.timer = (int) timer;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
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
