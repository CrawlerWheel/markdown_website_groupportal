package com.sun.groupportal.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostersDto implements Serializable {

    private String name;

    private String url;

    public PostersDto(String name , String url){
        this.name = name;
        this.url = url;
    }

}
