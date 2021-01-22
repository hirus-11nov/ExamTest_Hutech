package com.a1711060794.buitrunghieu;

public class Hirus {
    private String cName="";
    private String cID="";
    private String rType="";
    private String rBike="";

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }


    @Override
    public String toString(){
        return (getcName()); //Trả về tên KH
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getrBike() {
        return rBike;
    }

    public void setrBike(String rBike) {
        this.rBike = rBike;
    }
}
