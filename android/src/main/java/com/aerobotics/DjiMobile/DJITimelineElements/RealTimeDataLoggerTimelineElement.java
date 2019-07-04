package com.aerobotics.DjiMobile.DJITimelineElements;

import com.aerobotics.DjiMobile.DJIRealTimeDataLogger;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

import dji.common.error.DJIError;
import dji.sdk.mission.timeline.actions.MissionAction;
import dji.sdk.sdkmanager.DJISDKManager;

public class RealTimeDataLoggerTimelineElement extends MissionAction {
    ReactApplicationContext reactApplicationContext;
    DJIRealTimeDataLogger djiRealTimeDataLogger;
    private RealTimeDataLoggerTimelineElement self;

    boolean stopRecordingFlightData = false;
    String fileName;

    public RealTimeDataLoggerTimelineElement(ReactApplicationContext reactApplicationContext, DJIRealTimeDataLogger djiRealTimeDataLogger, ReadableMap parameters) {
        this.self = this;
        this.reactApplicationContext = reactApplicationContext;
        this.fileName = parameters.getString("fileName");
        this.stopRecordingFlightData = parameters.getBoolean("stopRecordFlightData");
        this.djiRealTimeDataLogger = djiRealTimeDataLogger;
    }

    @Override
    protected void startListen() {

    }

    @Override
    protected void stopListen() {

    }

    @Override
    public void run() {
        if (this.stopRecordingFlightData) {
            djiRealTimeDataLogger.stopLogging();
            DJISDKManager.getInstance().getMissionControl().onStopWithError(self, null);
        } else {
            if (djiRealTimeDataLogger.isLogging()) {
                djiRealTimeDataLogger.stopLogging();
                DJISDKManager.getInstance().getMissionControl().onStopWithError(self, null);
            }
            djiRealTimeDataLogger.startLogging(fileName);
            DJISDKManager.getInstance().getMissionControl().onStart(self);
        }
    }

    @Override
    public boolean isPausable() {
        return false;
    }

    @Override
    public void stop() {
        djiRealTimeDataLogger.stopLogging();
        DJISDKManager.getInstance().getMissionControl().onStopWithError(self, null);
    }

    @Override
    public DJIError checkValidity() {
        return null;
    }
}
