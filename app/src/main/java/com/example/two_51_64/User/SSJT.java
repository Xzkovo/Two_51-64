package com.example.two_51_64.User;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SSJT {


    @SerializedName("ROWS_DETAIL")
    private List<ROWSDETAILBean> rOWS_DETAIL;
    @SerializedName("RESULT")
    private String rESULT;

    public List<ROWSDETAILBean> getROWS_DETAIL() {
        return rOWS_DETAIL;
    }

    public void setROWS_DETAIL(List<ROWSDETAILBean> rOWS_DETAIL) {
        this.rOWS_DETAIL = rOWS_DETAIL;
    }

    public String getRESULT() {
        return rESULT;
    }

    public void setRESULT(String rESULT) {
        this.rESULT = rESULT;
    }

    public static class ROWSDETAILBean {
        private Integer id;
        private String start;
        private String end;
        private String mileage;
        private String price;
        private List<String> site;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public List<String> getSite() {
            return site;
        }

        public void setSite(List<String> site) {
            this.site = site;
        }
    }
}
