package com.pradeep.karak.Callbacks;

import android.view.View;

public interface ItemClickListener {
    void OnItemClick(int pos,String name);

    void onSaveClicked(String mac);

    void onUnSave(String mac);
}
