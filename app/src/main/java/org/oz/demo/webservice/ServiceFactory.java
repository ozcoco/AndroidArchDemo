package org.oz.demo.webservice;

public class ServiceFactory {

    public static <T extends IService> T getService(Class<? extends T> serviceClass) {

        try {
            return serviceClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T extends IApiService> T getWebService(Class<T> serviceClass) {



        return null;
    }

}
