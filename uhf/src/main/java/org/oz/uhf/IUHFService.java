package org.oz.uhf;

import org.oz.uhf.base.ILifecycle;

public interface IUHFService extends ILifecycle {

    /**
     * 扫描返回数据的存储区类型，用于标签扫描
     **/
    enum SCAN_TYPE {
        TID, EPC
    }

    /**
     * 盘存模式，用于标签扫描
     * */
    enum SESSION_MODE {

    }

    /**
     * @Name setPower
     * @Params [power]
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/4 16:00
     * @Description 返回调用是否成功，true成功，反之失败
     */
    boolean setPower(int power);

    int getPower();

    boolean singleScan();

    boolean multiScan();

    boolean connect();

    boolean close();

}
