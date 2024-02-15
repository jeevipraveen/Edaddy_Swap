package com.mauto.myapplication.utils;




public class AppInstance {
    private static MAutoApplication context;

    public void init(MAutoApplication appContext) { // only call from App!
        context = appContext;
    }

    public MAutoApplication getContext() {
        return context;
    }

    private static AppInstance mInstance = new AppInstance();
    public static AppInstance getInstance() {
        synchronized (AppInstance.class) {
            if (mInstance == null) {
                mInstance = new AppInstance();
            }
        }
        return mInstance;
    }
}
