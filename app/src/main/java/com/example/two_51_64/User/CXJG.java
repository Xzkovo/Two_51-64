package com.example.two_51_64.User;

public class CXJG {

    String name;
    String body;
    int wz = 0;
    int kf = 0;
    int fk = 0;

    public void setTemp(int wz,int kf,int fk){
        this.wz += wz;
        this.kf += kf;
        this.fk += fk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getWz() {
        return wz;
    }

    public void setWz(int wz) {
        this.wz = wz;
    }

    public int getKf() {
        return kf;
    }

    public void setKf(int kf) {
        this.kf = kf;
    }

    public int getFk() {
        return fk;
    }

    public void setFk(int fk) {
        this.fk = fk;
    }


}
