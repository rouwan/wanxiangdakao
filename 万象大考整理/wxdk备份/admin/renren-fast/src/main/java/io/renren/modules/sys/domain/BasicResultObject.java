package io.renren.modules.sys.domain;

public class BasicResultObject {
    /*1:返回成功 , -1:失败*/
    public int Tag;
    public String SessionId;
    public Object Result;
    public Object Result2;
    public String Message;
    public int TotalCount;


    public BasicResultObject() {
        this.Tag = 1;
        this.SessionId = "";
        this.Result = "";
        this.Message = "";
        this.TotalCount = 0;
    }
}
