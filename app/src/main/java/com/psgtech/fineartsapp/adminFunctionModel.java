package com.psgtech.fineartsapp;

public class adminFunctionModel {
    private String functionName;
    private int imgId;

    public adminFunctionModel(String functionName, int imgId){
        this.functionName = functionName;
        this.imgId = imgId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
