package org.oz.demo.po;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Message implements Serializable, Cloneable, Parcelable
{

    public final static int OK = 0;

    public final static int FAIL_PARAM_NULL = 1;

    public final static int FAIL_SERVICE = 2;


    private int code;

    private String msg;

    private byte[] data;


    public Message()
    {
    }

    public Message(int code, String msg, byte[] data)
    {
        this.code = code;
        this.msg = msg;
        this.data = data;

    }

    protected Message(Parcel in)
    {
        code = in.readInt();
        msg = in.readString();
        data = in.createByteArray();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>()
    {
        @Override
        public Message createFromParcel(Parcel in)
        {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size)
        {
            return new Message[size];
        }
    };

    public static Message newInstance()
    {

        return new Message();
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();

        System.out.println(" --------> finalize!!!!!! ");

    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeByteArray(data);
    }
}
