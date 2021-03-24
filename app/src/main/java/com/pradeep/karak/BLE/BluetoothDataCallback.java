package com.pradeep.karak.BLE;

public interface BluetoothDataCallback {
    public void OnDataReceived(String data);

    public void OnDataReceivedError(Exception e);
}
