package com.example.bohay.model;

public class Url {

    public static String domain = "http://192.168.43.204/doancoso3/tintuc/";


    public static String getUrlListCategory(){
        String url = domain+"list_category";
        return url;
    }
    public static String getUrlAllByCate(String idDM){
        String url = domain+"alltintuc/"+idDM;
        return url;
    }
    public static String getUrlDetailNew(){
        String url = domain+"chitiettin/";
        return url;
    }

}
