package com.example.crm.settings.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@ApiModel
@Data
public class ResultData {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "返回信息")
    private String msg;
    @ApiModelProperty(value = "true/false,成功/失败")
    private boolean success;
    @ApiModelProperty(value = "其他可能的数据")
    private Map<String,Object> data;

    public int getCode() {
        return code;
    }

    public ResultData setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultData setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;

    }

    public ResultData setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public ResultData setData(String key,String value) {
        this.data = data;
        return this;
    }
}
