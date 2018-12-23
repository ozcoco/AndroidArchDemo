package org.oz.demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import org.oz.demo.R;
import org.oz.demo.ui.rfid.RfidFragment;

public class RFIDActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rfid_activity);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, RfidFragment.newInstance()).commitNow();
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event)
    {

        Log.e("test key event>>>>>>>", String.format("onKeyLongPress keyCode: %d, event: %d , event number: %c", keyCode, event.getAction(), event.getNumber()));

        return super.onKeyLongPress(keyCode, event);
    }


    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
    {

        Log.e("test key event>>>>>>>", String.format("onKeyMultiple keyCode: %d, event: %d , event number: %c", keyCode, event.getAction(), event.getNumber()));

        return super.onKeyMultiple(keyCode, repeatCount, event);
    }


    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event)
    {

        Log.e("test key event>>>>>>>", String.format("onKeyShortcut keyCode: %d, event: %d , event number: %c", keyCode, event.getAction(), event.getNumber()));

        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {

        Log.e("test key event>>>>>>>", String.format("onKeyUp keyCode: %d, event: %d , event number: %c", keyCode, event.getAction(), event.getNumber()));

        return super.onKeyUp(keyCode, event);
    }
}
