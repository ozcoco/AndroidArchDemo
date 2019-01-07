package org.oz.uhf;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.oz.uhf.base.DataCallback;
import org.oz.uhf.base.ScanControl;
import org.oz.uhf.emtity.Tag;

import java.util.List;

public class UHFService implements IUHFService {

    private IUHFService uhfService;

    private UHFService() {

        uhfService = UHFServiceImpl.newInstance();

    }

    @Override
    public boolean setPower(int power) {
        return uhfService.setPower(power);
    }

    @Override
    public void clearTagCache() {
        uhfService.clearTagCache();
    }

    @Override
    public int getPower() {
        return uhfService.getPower();
    }

    @Override
    public void singleScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<Tag> callback) {

        uhfService.singleScan(scanType, callback);
    }

    @Override
    public ScanControl multiScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<List<Tag>> callback) {
        return uhfService.multiScan(scanType, callback);
    }

    @Override
    public ScanControl autoScan(@NonNull SCAN_TYPE scanType, @NonNull DataCallback<List<Tag>> callback) {
        return uhfService.autoScan(scanType, callback);
    }

    @Override
    public boolean connect() {
        return uhfService.connect();
    }

    @Override
    public boolean close() {
        return uhfService.close();
    }

    private static class Singleton {
        static UHFService INSTANCE = new UHFService();
    }

    public static IUHFService getInstance() {
        return Singleton.INSTANCE;
    }


    @Override
    public void init(@Nullable Context context) {
        uhfService.init(context);
    }

    @Override
    public void onCleared() {

        if (uhfService != null)
            uhfService.onCleared();

        uhfService = null;
    }
}
