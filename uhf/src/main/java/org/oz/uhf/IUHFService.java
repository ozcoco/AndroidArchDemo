package org.oz.uhf;

import androidx.annotation.NonNull;

import org.oz.uhf.base.DataCallback;
import org.oz.uhf.base.ILifecycle;
import org.oz.uhf.emtity.Tag;

public interface IUHFService extends ILifecycle {

    /**
     * 扫描返回数据的存储区类型，用于标签扫描
     **/
    enum SCAN_TYPE {
        TID,    //扫描标签得到的数据为tid
        EPC     //扫描标签得到的数据为epc
    }

    /**
     * 盘存模式，用于标签扫描
     */
    enum SESSION_MODE {
        SINGLE, //单标签
        MULTI,  //多标签
        AUTO    //自动
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

    boolean singleScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<Tag> callback);

    boolean multiScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<Tag> callback);

    boolean autoScan(@NonNull SCAN_TYPE scanType, @NonNull SESSION_MODE sessionMode, @NonNull DataCallback<Tag> callback);

    boolean connect();

    boolean close();

}
