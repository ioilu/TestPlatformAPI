package com.huilu.testplatform.util;

import com.huilu.testplatform.config.ErrorCode;
import com.huilu.testplatform.config.StatusCode;
import lombok.Data;

@Data
public class JsonResponse {
    private StatusCode status;
    private Object content;
    private String msg;

    public JsonResponse(){
        this.status = StatusCode.OK;
        this.msg = ErrorCode.SUCCESS.getMsg();
    }
    public JsonResponse setContent(Object content){
        this.content=content;
        return this;
    }
    public JsonResponse setStatus(StatusCode status){
        this.status=status;
        return this;
    }

    public JsonResponse setMsg(String msg){
        this.msg=msg;
        return this;
    }

    public String toString(JsonResponse jsonResponse){
        return "{\"status\":\""+ jsonResponse.getStatus()+
                "\",\"message\":\""+ jsonResponse.getMsg()+
                "\",\"content\":\""+jsonResponse.getContent()+"\"}";
    }

    public JsonResponse setError(){
        this.setStatus(StatusCode.ERROR).setMsg(ErrorCode.FAIL.getMsg());
        return this;
    }
}
