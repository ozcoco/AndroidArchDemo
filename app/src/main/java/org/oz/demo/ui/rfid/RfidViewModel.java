package org.oz.demo.ui.rfid;

import android.app.Application;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.uhf.scanlable.UHfData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RfidViewModel extends AndroidViewModel
{

    public final MutableLiveData<Boolean> isWrite6c = new MutableLiveData<>();

    public final MutableLiveData<Integer> read6cTypePosition = new MutableLiveData<>();

    //read6c mem
    public final LiveData<Byte> read6cType = Transformations.map(read6cTypePosition, position ->
    {
        byte type = 0x00;

        switch (position)
        {
            case 0: //0x00 – 保留区
                break;
            case 1:  //0x01 – EPC存储区
                type = 0x01;
                break;
            case 2: //0x02 – TID存储区
                type = 0x02;
                break;
            case 3: //0x03 –用户存储区
                type = 0x03;
                break;

        }

        return type;
    });


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

    public final LiveData<String> epcData = Transformations.map(itemClickPosition, position -> Objects.requireNonNull(itemData.getValue()).get(position).strEPC);

    public RfidViewModel(@NonNull Application application)
    {
        super(application);

        power.setValue(10);

        mode.setValue(0);

        isScan.setValue(false);
    }

}