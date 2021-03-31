package com.pradeep.karak.BLE;

public interface BluetoothConnectCallback {
    public void OnConnectSuccess();
    public void OnConnectFailed(Exception e);
}
