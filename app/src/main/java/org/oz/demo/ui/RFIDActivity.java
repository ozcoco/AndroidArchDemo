package org.oz.demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import org.oz.demo.R;
import org.oz.demo.ui.rfid.RfidFragment;

public class RFIDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rfid_activity);
    }

}
