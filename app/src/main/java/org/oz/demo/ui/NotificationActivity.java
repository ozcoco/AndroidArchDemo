package org.oz.demo.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.oz.demo.R;
import org.oz.demo.ui.notification.NotificationFragment;

public class NotificationActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, NotificationFragment.newInstance()).commitNow();
        }
    }
}
