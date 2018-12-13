package org.oz.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.rfid.GlideApp;
import com.rfid.GlideRequests;

public final class ImageLoader {

    public static GlideRequests with(@NonNull Context context) {
        return GlideApp.with(context);
    }

    public static GlideRequests with(@NonNull Fragment fragment) {
        return GlideApp.with(fragment);
    }

    public static GlideRequests with(@NonNull Activity activity) {
        return GlideApp.with(activity);
    }

    public static GlideRequests with(@NonNull View view) {
        return GlideApp.with(view);
    }

    public static GlideRequests with(@NonNull FragmentActivity activity) {
        return GlideApp.with(activity);
    }


}
