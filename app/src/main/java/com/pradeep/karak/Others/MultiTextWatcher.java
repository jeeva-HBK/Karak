package com.pradeep.karak.Others;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pradeep.karak.Callbacks.TextWatcherWithInstance;

public class MultiTextWatcher {
    private TextWatcherWithInstance callback;

    public MultiTextWatcher setCallback(TextWatcherWithInstance callback) {
        this.callback = callback;
        return this;
    }

    public MultiTextWatcher registerEditText(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callback.onTextChanged(editText, s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        return this;
    }
}
