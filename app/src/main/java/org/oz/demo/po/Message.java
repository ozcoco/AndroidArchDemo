package org.oz.demo.po;

public class Message<T> implements Cloneable {

    public final static int OK = 0;

    public final static int FAIL_PARAM_NULL = 1;

    public final static int FAIL_SERVICE = 2;


    private int code;

    private String msg;

    private T data;


    public Message() {
    }

    public Message(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        System.out.println(" --------> finalize!!!!!! ");

    }

}
