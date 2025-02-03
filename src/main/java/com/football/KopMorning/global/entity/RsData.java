package com.football.KopMorning.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsData<T> {
    private String code;
    private String msg;
    private T Data;

    public RsData(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonIgnore
    public int getStatusCode() {
        String statusCodeStr = code.split("-")[0];
        return Integer.parseInt(statusCodeStr);
    }
}
