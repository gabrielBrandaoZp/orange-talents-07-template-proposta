package br.com.zupacademy.msproposta.utils.handler;

public class ApiErrorResponse {

    private String code;
    private String msg;

    public ApiErrorResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
