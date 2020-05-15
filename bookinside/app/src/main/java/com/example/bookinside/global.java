package com.example.bookinside;

public class global {
    private String ip ="http://192.168.1.10:3000";
    private static global instance;
    // Restrict the constructor from being instantiated
    private global(){}

    public static synchronized global getInstance(){
        if(instance==null){
            instance=new global();
        }
        return instance;
    }
    public String getIp() {
        return ip;
    }

}
