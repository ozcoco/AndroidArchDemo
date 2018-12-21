package org.oz.demo.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.oz.demo.R;
import org.oz.demo.ui.rfid.RfidFragment;

public class RFIDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rfid_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RfidFragment.newInstance())
                    .commitNow();
        }
    }
}
