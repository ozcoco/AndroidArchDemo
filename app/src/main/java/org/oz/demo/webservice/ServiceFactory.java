package org.oz.demo.webservice;

import androidx.annotation.Nullable;

import org.oz.demo.network.HttpUtils;

public class ServiceFactory
{

    public static <T extends IService> T getService(Class<? extends T> serviceClass)
    {
        try
        {
            return serviceClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    @Nullable
    public static <T> T getWebService(Class<T> serviceClass)
    {
        return HttpUtils.INSTANCE.getRetrofit().create(serviceClass);
    }

}
