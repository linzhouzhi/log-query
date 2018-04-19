package com.lzz.model;

import net.sf.json.JSONObject;

/**
 * Created by lzz on 2018/4/19.
 */
public class HostParam {
    private String service;
    private String ip;
    private String username;
    private String password;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String series(){
        JSONObject jsonObject = JSONObject.fromObject( this );
        return  jsonObject.toString();
    }

    public static HostParam unSeries(String obj){
        JSONObject jsonObject = JSONObject.fromObject( obj );
        HostParam hostParam = (HostParam) JSONObject.toBean( jsonObject, HostParam.class );
        return hostParam;
    }
}
