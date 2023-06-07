package com.example.two_51_64.User;

import com.google.gson.annotations.SerializedName;

public class RZCX {

    private Integer temperature;
    private Integer humidity;
    private Integer illumination;
    private Integer co2;
    private Integer pm25;
    @SerializedName("RESULT")
    private String rESULT;

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getIllumination() {
        return illumination;
    }

    public void setIllumination(Integer illumination) {
        this.illumination = illumination;
    }

    public Integer getCo2() {
        return co2;
    }

    public void setCo2(Integer co2) {
        this.co2 = co2;
    }

    public Integer getPm25() {
        return pm25;
    }

    public void setPm25(Integer pm25) {
        this.pm25 = pm25;
    }

    public String getRESULT() {
        return rESULT;
    }

    public void setRESULT(String rESULT) {
        this.rESULT = rESULT;
    }

    private String time;

    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return time;
    }

}
