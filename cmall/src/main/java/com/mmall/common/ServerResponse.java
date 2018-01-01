package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by CJ on 2017/11/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable{
    private int status;
    private T data;
    private String msg;

    public ServerResponse(int status) {
        this.status = status;
    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        try {
            this.msg = msg;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.status ==ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }
    public String getMsg() {
        return msg;
    }
    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public  static  <T>ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public  static  <T>ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public  static  <T>ServerResponse<T> createBySuccess(String msg, T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public  static  <T>ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public  static  <T>ServerResponse<T> createByErrorMessage(String ErrorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ErrorMessage);
    }

    public  static  <T>ServerResponse<T> createByErrorCodeMessage(int ErrorCode,String ErrorMessage){
        return new ServerResponse<T>(ErrorCode,ErrorMessage);
    }
}
