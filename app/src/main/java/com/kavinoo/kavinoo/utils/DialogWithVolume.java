package com.kavinoo.kavinoo.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.kavinoo.kavinoo.activity.PlaceActivity;

public class DialogWithVolume extends Dialog {
    Context context;
    SimpleExoPlayer simpleExoPlayer;

    public DialogWithVolume(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public DialogWithVolume(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogWithVolume(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN){
            Float currentVol = simpleExoPlayer.getVolume();
            currentVol = currentVol - 0.05f;
            float number = currentVol;
            number= (short)(100*number);
            number=(float)(number/100);
            if (number >= 0.0f){
                simpleExoPlayer.setVolume(number);
            }

        }else if(event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP){
            Float currentVol = simpleExoPlayer.getVolume();
            currentVol = currentVol + 0.05f;
            float number = currentVol;
            number= (short)(100*number);
            number=(float)(number/100);
            if (number <= 1.0f){
                simpleExoPlayer.setVolume(number);
            }
        }
        return super.dispatchKeyEvent(event);
    }
    public void setPlayer (SimpleExoPlayer simpleExoPlayer){
        this.simpleExoPlayer = simpleExoPlayer;
    }
}
