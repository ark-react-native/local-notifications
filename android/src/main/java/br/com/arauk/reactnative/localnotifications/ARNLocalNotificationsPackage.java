package br.com.arauk.reactnative.localnotifications;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ARNLocalNotificationsPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new ARNLocalNotificationsModule(reactContext));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

    /**
     * Deprecated on React Native 0.47.*
     *
     * @return empty List of JavaScriptModule objects
     */
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

}
