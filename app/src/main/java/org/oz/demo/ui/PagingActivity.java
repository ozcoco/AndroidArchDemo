package org.oz.demo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.oz.demo.R;
import org.oz.demo.ui.paging.PagingFragment;

public class PagingActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paging_activity);
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, PagingFragment.newInstance()).commitNow();
        }
    }
}
