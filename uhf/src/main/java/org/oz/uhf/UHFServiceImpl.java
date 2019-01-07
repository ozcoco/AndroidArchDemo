package org.oz.uhf;

import android.content.Context;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uhf.scanlable.UHfData;

import org.oz.uhf.base.DataCallback;
import org.oz.uhf.base.ScanControl;
import org.oz.uhf.emtity.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UHFServiceImpl implements IUHFService {

    /**
     * UHF功率，默认值为30dBm
     **/
    private int power = 30;

    /**
     * 扫描结果为tid数据的标签集合
     */
    private ArrayMap<String, UHfData.InventoryTagMap> tidTagMap = new ArrayMap<>();

    /**
     * 扫描结果为epc数据的标签集合
     */
    private ArrayMap<String, UHfData.InventoryTagMap> epcTagMap = new ArrayMap<>();


    private UHFServiceImpl() {
    }

    public synchronized static IUHFService newInstance() {

        return new UHFServiceImpl();
    }

    @Override
    public void init(@Nullable Context context) {

    }

    @Override
    public void onCleared() {

        tidTagMap.clear();

        tidTagMap = null;

        epcTagMap.clear();

        epcTagMap = null;
    }


    /**
     * @Name setPower
     * @Params [power]
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/4 16:31
     * @Description 返回调用是否成功，true成功，反之失败
     */
    @Override
    public boolean setPower(int power) {

        return 0 == UHfData.UHfGetData.SetRfPower(Integer.valueOf(this.power = power).byteValue());
    }


    /**
     * @Name clearTagCache
     * @Params []
     * @Return void
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/7 15:21
     * @Description 清除Tag缓存
     */
    @Override
    public void clearTagCache() {

        tidTagMap.clear();

        epcTagMap.clear();
    }


    /**
     * @Name getPower
     * @Params []
     * @Return int
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/7 15:20
     * @Description 获取功率
     */
    @Override
    public int getPower() {

        return power;
    }


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
    @Override
    public synchronized void singleScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<Tag> callback) {

        //清除累计的数据
        UHfData.lsTagList.clear();

        UHfData.dtIndexMap.clear();

        int session = 0;    //单标签

        int tidFlag = scanType == SCAN_TYPE.TID ? 1 : 0;

        //检查有效范围内是否有符合协议的电子标签存在
        UHfData.Inventory_6c(session, tidFlag);

        final List<UHfData.InventoryTagMap> list = new ArrayList<>(UHfData.lsTagList);

        if (list.size() == 1) {

            final Tag tag = new Tag(list.get(0).strEPC);

            callback.callback(tag);

            if (scanType == SCAN_TYPE.TID)
                tidTagMap.put(list.get(0).strEPC, list.get(0));
            else
                epcTagMap.put(list.get(0).strEPC, list.get(0));

        } else if (list.size() > 1) {

            if (scanType == SCAN_TYPE.TID) { //结果集为tid

                if (tidTagMap.size() > 0) { // tidTagMap集合有元素时

                    for (UHfData.InventoryTagMap bean : list) {

                        if (!tidTagMap.containsKey(bean.strEPC)) {

                            final Tag tag = new Tag(bean.strEPC);

                            callback.callback(tag);

                            tidTagMap.put(bean.strEPC, bean);

                            return;
                        }
                    }

                    final Tag tag = new Tag(list.get(0).strEPC);

                    callback.callback(tag);

                } else {    // tidTagMap集合无元素时

                    final Tag tag = new Tag(list.get(0).strEPC);

                    callback.callback(tag);

                    tidTagMap.put(list.get(0).strEPC, list.get(0));
                }

            } else { //结果集为epc

                if (epcTagMap.size() > 0) {  // epcTagMap集合有元素时

                    for (UHfData.InventoryTagMap bean : list) {

                        if (!epcTagMap.containsKey(bean.strEPC)) {

                            final Tag tag = new Tag(bean.strEPC);

                            callback.callback(tag);

                            epcTagMap.put(bean.strEPC, bean);

                            return;
                        }
                    }

                    final Tag tag = new Tag(list.get(0).strEPC);

                    callback.callback(tag);

                } else {    // epcTagMap集合无元素时

                    final Tag tag = new Tag(list.get(0).strEPC);

                    callback.callback(tag);

                    epcTagMap.put(list.get(0).strEPC, list.get(0));
                }

            }
        }

    }


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
    @Override
    public synchronized ScanControl multiScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<List<Tag>> callback) {

        final AtomicBoolean isScan = new AtomicBoolean(true);

        for (; isScan.get(); ) {

            //清除累计的数据
            UHfData.lsTagList.clear();

            UHfData.dtIndexMap.clear();

            int session = 1;    //多标签

            int tidFlag = scanType == SCAN_TYPE.TID ? 1 : 0;

            //检查有效范围内是否有符合协议的电子标签存在
            UHfData.Inventory_6c(session, tidFlag);

            final List<UHfData.InventoryTagMap> list = new ArrayList<>(UHfData.lsTagList);

            if (list.size() == 0) continue;

            if (scanType == SCAN_TYPE.TID) {    //结果集为tid

                if (tidTagMap.size() > 0) { // tidTagMap集合有元素时

                    final List<Tag> tags = new ArrayList<>();

                    for (UHfData.InventoryTagMap bean : list) {

                        if (!tidTagMap.containsKey(bean.strEPC)) {

                            tags.add(new Tag(bean.strEPC));

                            tidTagMap.put(bean.strEPC, bean);
                        }
                    }

                    if (tags.size() > 0)
                        callback.callback(tags);

                } else {    // tidTagMap集合无元素时

                    final List<Tag> tags = new ArrayList<>();

                    for (UHfData.InventoryTagMap bean : list) {

                        tags.add(new Tag(bean.strEPC));

                        tidTagMap.put(bean.strEPC, bean);
                    }

                    callback.callback(tags);
                }

            } else {    //结果集为epc

                if (epcTagMap.size() > 0) { // epcTagMap集合有元素时

                    final List<Tag> tags = new ArrayList<>();

                    for (UHfData.InventoryTagMap bean : list) {

                        if (!epcTagMap.containsKey(bean.strEPC)) {

                            tags.add(new Tag(bean.strEPC));

                            epcTagMap.put(bean.strEPC, bean);
                        }
                    }

                    if (tags.size() > 0)
                        callback.callback(tags);

                } else {    // epcTagMap集合无元素时

                    final List<Tag> tags = new ArrayList<>();

                    for (UHfData.InventoryTagMap bean : list) {

                        tags.add(new Tag(bean.strEPC));

                        epcTagMap.put(bean.strEPC, bean);
                    }

                    callback.callback(tags);
                }

            }


        }

        return new ScanControl() {
            @Override
            public void stop() {

                isScan.set(false);
            }

            @Override
            public boolean isScan() {
                return isScan.get();
            }
        };
    }


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
    @Override
    public synchronized ScanControl autoScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<List<Tag>> callback) {

        final AtomicBoolean isScan = new AtomicBoolean(true);

        for (; isScan.get(); ) {

            //清除累计的数据
            UHfData.lsTagList.clear();

            UHfData.dtIndexMap.clear();

            int session = 2;    //自动

            int tidFlag = scanType == SCAN_TYPE.TID ? 1 : 0;

            //检查有效范围内是否有符合协议的电子标签存在
            UHfData.Inventory_6c(session, tidFlag);

            final List<UHfData.InventoryTagMap> list = new ArrayList<>(UHfData.lsTagList);

            if (list.size() == 0) continue;

            if (scanType == SCAN_TYPE.TID) {    //结果集为tid

                if (tidTagMap.size() > 0) { // tidTagMap集合有元素时

                    final List<Tag> tags = new ArrayList<>();

                    for (UHfData.InventoryTagMap bean : list) {

                        if (!tidTagMap.containsKey(bean.strEPC)) {

                            tags.add(new Tag(bean.strEPC));

                            tidTagMap.put(bean.strEPC, bean);
                        }
                    }

                    if (tags.size() > 0) { //有新标签

                        callback.callback(tags);

                        break;
                    }

                } else {    // tidTagMap集合无元素时

                    final List<Tag> tags = new ArrayList<>();

                    for (UHfData.InventoryTagMap bean : list) {

                        tags.add(new Tag(bean.strEPC));

                        tidTagMap.put(bean.strEPC, bean);
                    }

                    callback.callback(tags);

                    break;
                }

            } else {//结果集为epc

                if (epcTagMap.size() > 0) { // epcTagMap集合有元素时

                    final List<Tag> tags = new ArrayList<>();

                    for (UHfData.InventoryTagMap bean : list) {

                        if (!epcTagMap.containsKey(bean.strEPC)) {

                            tags.add(new Tag(bean.strEPC));

                            epcTagMap.put(bean.strEPC, bean);
                        }
                    }

                    if (tags.size() > 0) { //有新标签

                        callback.callback(tags);

                        break;
                    }

                } else {    // epcTagMap集合无元素时

                    final List<Tag> tags = new ArrayList<>();

                    for (UHfData.InventoryTagMap bean : list) {

                        tags.add(new Tag(bean.strEPC));

                        epcTagMap.put(bean.strEPC, bean);
                    }

                    callback.callback(tags);

                    break;
                }

            }

        }

        return new ScanControl() {
            @Override
            public void stop() {

                isScan.set(false);
            }

            @Override
            public boolean isScan() {
                return isScan.get();
            }
        };
    }

    /**
     * @Name connect
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/20 14:17
     * @Description 通过串口，连接UHF,  return ,true connected, unable connected
     */
    @Override
    public boolean connect() {

        final int state = UHfData.UHfGetData.OpenUHf("/dev/ttyMT1", 57600);

        return state == 0 && setPower(power);
    }


    /**
     * @Name close
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/20 14:23
     * @Description 释放串口资源，断开UHF， return, true 成功，false失败
     */
    @Override
    public boolean close() {

        UHfData.lsTagList.clear();

        UHfData.dtIndexMap.clear();

        return UHfData.UHfGetData.CloseUHf() == 0;
    }

}
