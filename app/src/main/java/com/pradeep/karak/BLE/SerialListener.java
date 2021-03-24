package com.pradeep.karak.BLE;

public interface SerialListener {
    void onSerialConnect();

    void onSerialConnectError(Exception e);

    void onSerialRead(byte[] data);

    void onSerialIoError(Exception e);

    void onDisconnected();
}
