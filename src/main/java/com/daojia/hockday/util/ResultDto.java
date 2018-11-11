package com.daojia.hockday.util;


import com.daojia.hockday.enums.ErrorEnum;

/**
 * @author by Dawei on 2018/8/22.
 * 新的返回实体Dto
 */
public class ResultDto<T> {

    private Integer code;

    private String codeMsg;

    private T data;

    public ResultDto() {
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultDto(Integer code) {
        this.code = code;
        this.codeMsg = ErrorEnum.getDescByCode(code);
    }

    public ResultDto(Integer code, T data) {
        this.code = code;
        this.codeMsg = ErrorEnum.getDescByCode(code);
        this.data = data;
    }

    public ResultDto(Integer code, String codeMsg, T data) {
        this.code = code;
        this.codeMsg = codeMsg;
        this.data = data;
    }

    public void setSuccess() {
        this.setCodeMsg(ErrorEnum.SUCCESS.getDesc());
        this.setCode(ErrorEnum.SUCCESS.getCode());
    }

    public void setError(){
        this.setCodeMsg(ErrorEnum.ERROR.getDesc());
        this.setCode(ErrorEnum.ERROR.getCode());
    }

    public void setParamError(){
        this.setCodeMsg(ErrorEnum.ERROR_PARAM.getDesc());
        this.setCode(ErrorEnum.ERROR_PARAM.getCode());
    }

}
