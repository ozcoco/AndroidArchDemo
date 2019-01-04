package org.oz.uhf.base;

import com.uhf.scanlable.UHfData;

import cn.pda.serialport.Tools;

public enum MEM {

//    byte ENum, byte[] EPC, byte Mem, byte[] WordPtr, byte Num, byte[] Password

    RESERVED("保留区", (byte) 0x00, (byte) 4),
    EPC("EPC区", (byte) 0x01, (byte) 8),
    TID("TID区", (byte) 0x02, (byte) 12),
    USER("用户区", (byte) 0x03, (byte) 32),
    ;

    //len = 4
    public final static byte[] passwd = UHfData.UHfGetData.hexStringToBytes("00000000");

    private String name;

    private byte type;

    private final byte[] wordPtr = Tools.intToByte(0);

    private byte len;

    MEM(String name, byte type, byte len) {
        this.name = name;
        this.type = type;
        this.len = len;
    }

    public byte[] getWordPtr() {
        return wordPtr;
    }

    public String getName() {
        return name;
    }

    public byte getType() {
        return type;
    }

    public byte getLen() {
        return len;
    }

}
