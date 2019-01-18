package org.oz.uhf;

import androidx.annotation.NonNull;

import org.oz.uhf.base.DataCallback;
import org.oz.uhf.base.ILifecycle;
import org.oz.uhf.base.ScanControl;
import org.oz.uhf.emtity.Tag;

import java.util.List;

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

    /**
     * @Name clearTagCache
     * @Params []
     * @Return void
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/7 15:19
     * @Description 清除Tag缓存
     */
    void clearTagCache();


    /**
     * @Name getPower
     * @Params []
     * @Return int
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/7 15:20
     * @Description 获取功率
     */
    int getPower();


    /**
     * @Name singleScan
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/4 16:31
     * @Description 单个Tag扫描，一次扫描一个Tag 。注：并非每次都能扫到有效的Tag，若扫描器使用不
     * 当，则扫不到Tag；例如：功率太低、Tag距离太远。
     */
    void singleScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<Tag> callback);


    /**
     * @Name multiScan
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/4 16:32
     * @Description 多Tag扫描，持续扫描标签，返回扫描到有效标签的集合，需要控制扫描的结
     * 束。注：若扫描器使用不当，则扫不到Tag，例如：功率太低、Tag距离太远。
     */
    ScanControl multiScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<List<Tag>> callback);

    /**
     * @Name autoScan
     * @Params [scanType, sessionMode, callback]
     * @Return org.oz.uhf.base.ScanControl
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/7 15:13
     * @Description 自动Tag扫描，持续扫描标签，直到扫描到有效标签会自动结束扫描，也可控制结束扫描，返回扫描到有效标签的集
     * 合，需要控制扫描的结束。注：若扫描器使用不当，则扫不到Tag，例如：功率太低、Tag距离太远。
     */
    ScanControl autoScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<List<Tag>> callback);


    /**
     * @Name connect
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/20 14:17
     * @Description 通过串口，连接UHF,  return ,true connected, unable connected
     */
    boolean connect();

    /**
     * @Name close
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/20 14:23
     * @Description 释放串口资源，断开UHF， return, true 成功，false失败
     */
    boolean close();

}
