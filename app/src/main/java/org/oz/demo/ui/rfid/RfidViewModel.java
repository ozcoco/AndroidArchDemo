package org.oz.demo.ui.rfid;

import android.app.Application;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableByte;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.uhf.scanlable.UHfData;

import org.oz.demo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RfidViewModel extends AndroidViewModel {

    public final MutableLiveData<Boolean> isConnected = new MutableLiveData<>();

    public final ObservableBoolean isWrite6c = new ObservableBoolean();

    public final ObservableInt read6cTypePosition = new ObservableInt();

    //read6c mem
    public final ObservableByte read6cType = new ObservableByte();

    //read6c mem type
    public final ObservableField<String> type = new ObservableField<>();

    public final MutableLiveData<Integer> itemClickPosition = new MutableLiveData<>();

    public final MutableLiveData<Integer> power = new MutableLiveData<>();

    public final MutableLiveData<Integer> mode = new MutableLiveData<>();

    public final MutableLiveData<Boolean> isScan = new MutableLiveData<>();

    public final ObservableInt itemPosition = new ObservableInt();

    public final MutableLiveData<List<UHfData.InventoryTagMap>> inventoryTagMap = new MutableLiveData<>();

    public final LiveData<ArrayMap<String, UHfData.InventoryTagMap>> arrayMapTags = Transformations.map(inventoryTagMap, input ->
    {

        final ArrayMap<String, UHfData.InventoryTagMap> map = new ArrayMap<>();

        for (UHfData.InventoryTagMap bean : input)
            map.put(bean.strEPC, bean);

        return map;
    });

    public final LiveData<List<UHfData.InventoryTagMap>> itemData = Transformations.map(arrayMapTags, input -> new ArrayList<>(input.values()));

    public final LiveData<String> info = Transformations.map(inventoryTagMap, Object::toString);

    public final ObservableField<String> epcData = new ObservableField<>();

    public RfidViewModel(@NonNull Application application) {
        super(application);

        power.setValue(10);

        mode.setValue(0);

        isScan.setValue(false);

        itemClickPosition.observeForever(position -> epcData.set(itemData.getValue().get(position).strEPC));

        final String[] read6cTypes = application.getResources().getStringArray(R.array.read6c_mem);

        type.set(read6cTypes[0]);

        read6cTypePosition.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                final int position = read6cTypePosition.get();

                byte typeIndex = (byte) position;

                String typeName = read6cTypes[position];

                type.set(typeName);

                read6cType.set(typeIndex);
            }
        });

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
    public boolean connectUHF() {

        final int state = UHfData.UHfGetData.OpenUHf("/dev/ttyMT1", 57600);

        isConnected.setValue(state == 0);

        return Objects.requireNonNull(isConnected.getValue());
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
    public boolean disconnectUHF() {
        UHfData.lsTagList.clear();
        UHfData.dtIndexMap.clear();
        return UHfData.UHfGetData.CloseUHf() == 0;
    }


}