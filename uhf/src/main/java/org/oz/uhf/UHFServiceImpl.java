package org.oz.uhf;

import android.content.Context;

import androidx.annotation.Nullable;

import com.uhf.scanlable.UHfData;

public class UHFServiceImpl implements IUHFService {

    /**
     * UHF功率，默认值为30dBm
     **/
    private int power = 30;


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

    }


    /**
     * @Name setPower
     * @Params [power]
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/4 16:31
     * @Description todo
     */
    @Override
    public boolean setPower(int power) {

        return 0 == UHfData.UHfGetData.SetRfPower(Integer.valueOf(this.power = power).byteValue());
    }


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
     * @Description todo
     */
    @Override
    public boolean singleScan() {

        //清除累计的数据
        UHfData.lsTagList.clear();

        UHfData.dtIndexMap.clear();

        //检查有效范围内是否有符合协议的电子标签存在
        UHfData.Inventory_6c(0, 0);


        return false;
    }


    /**
     * @Name multiScan
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2019/1/4 16:32
     * @Description todo
     */
    @Override
    public boolean multiScan() {


        return false;
    }

    /**
     * @Name connectUHF
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/20 14:17
     * @Description 通过串口，连接RFID读写器,  return ,true connected, unable connected
     */
    @Override
    public boolean connect() {

        final int state = UHfData.UHfGetData.OpenUHf("/dev/ttyMT1", 57600);

        return state == 0 && setPower(power);
    }


    /**
     * @Name disconnectUHF
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/20 14:23
     * @Description 断开RFID读写器， return, true 成功，false失败
     */
    @Override
    public boolean close() {

        UHfData.lsTagList.clear();

        UHfData.dtIndexMap.clear();

        return UHfData.UHfGetData.CloseUHf() == 0;
    }

}
