package com.example.common.domain;

public class BasicResultObject {
    /*1:返回成功 , -1:失败*/
    public int Tag;
    public int Type;
    public String SessionId;
    public Object Result;
    public Object Result2;
    public String Message;
    public int TotalCount;


    public BasicResultObject() {
        this.Tag = 1;
        this.Type = 0;
        this.SessionId = "";
        this.Result = "";
        this.Message = "";
        this.TotalCount = 0;
    }

    public BasicResultObject(int tag,int type,Object result,String message,int totalCount) {
        this.Tag = tag;
        this.Type = type;
        this.Result = result;
        this.Message = message;
        this.TotalCount = totalCount;
    }
}
