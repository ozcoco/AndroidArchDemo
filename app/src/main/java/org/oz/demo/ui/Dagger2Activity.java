package org.oz.demo.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.oz.demo.R;
import org.oz.demo.ui.dagger2.Dagger2Fragment;

public class Dagger2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dagger2_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Dagger2Fragment.newInstance())
                    .commitNow();
        }
    }
}
