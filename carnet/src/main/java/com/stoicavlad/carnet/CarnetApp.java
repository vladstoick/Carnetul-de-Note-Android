package com.stoicavlad.carnet;

import android.app.Application;
import android.content.Context;

import com.stoicavlad.carnet.DaggerModules.AndroidModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Vlad on 1/26/14.
 */
public class CarnetApp extends Application {
    private ObjectGraph applicationGraph;

    @Override public void onCreate() {
        super.onCreate();
        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AndroidModule(this));
    }

    ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
    public void inject(Object o) {
        applicationGraph.inject(o);
    }

    public static CarnetApp get(Context context) {
        return (CarnetApp) context.getApplicationContext();
    }
}
