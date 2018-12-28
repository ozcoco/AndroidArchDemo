package org.oz.demo.ui.rfid;

import com.uhf.scanlable.UHfData;

public enum EPCNumberType {

//    byte ENum, byte[] EPC, byte Mem, byte[] WordPtr, byte Num, byte[] Password

    RESERVED("保留区", (byte) 0x00, (byte) 4),
    EPC("EPC区", (byte) 0x01, (byte) 4),
    TID("TID区", (byte) 0x02, (byte) 4),
    USER("用户区", (byte) 0x03, (byte) 4),
    ;

    //len = 4
    public final static byte[] passwd = UHfData.UHfGetData.hexStringToBytes("00000000");

    private String name;

    private byte type;

    private byte len;

    EPCNumberType(String name, byte type, byte len) {
        this.name = name;
        this.type = type;
        this.len = len;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getLen() {
        return len;
    }

    public void setLen(byte len) {
        this.len = len;
    }
}
