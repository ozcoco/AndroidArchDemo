package org.oz.demo.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import es.dmoral.toasty.Toasty;


public class ToastUtils {

    public static Toast error(@NonNull Context context, @NonNull @StringRes int resId, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.error(context, context.getString(resId), duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }

    public static Toast error(@NonNull Context context, @NonNull String msg, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.error(context, msg, duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }


    public static Toast info(@NonNull Context context, @NonNull @StringRes int resId, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.info(context, context.getString(resId), duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }

    public static Toast info(@NonNull Context context, @NonNull String msg, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.info(context, msg, duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }


    public static Toast normal(@NonNull Context context, @NonNull @StringRes int resId, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.normal(context, context.getString(resId), duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }

    public static Toast normal(@NonNull Context context, @NonNull String msg, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.normal(context, msg, duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }

    public static Toast success(@NonNull Context context, @NonNull @StringRes int resId, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.success(context, context.getString(resId), duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }

    public static Toast success(@NonNull Context context, @NonNull String msg, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.success(context, msg, duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }


    public static Toast warning(@NonNull Context context, @NonNull @StringRes int resId, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.warning(context, context.getString(resId), duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }

    public static Toast warning(@NonNull Context context, @NonNull String msg, @NonNull int gravity, @NonNull int duration) {

        final Toast toast = Toasty.warning(context, msg, duration);

        toast.setGravity(gravity, 0, 0);

        return toast;
    }


}
