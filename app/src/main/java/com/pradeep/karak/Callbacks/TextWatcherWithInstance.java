package com.pradeep.karak.Callbacks;

import android.widget.EditText;

public interface TextWatcherWithInstance {
    void onTextChanged(EditText editText, CharSequence s, int start, int before, int count);
}
