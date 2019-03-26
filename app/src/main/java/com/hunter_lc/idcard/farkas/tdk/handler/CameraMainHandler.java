package com.hunter_lc.idcard.farkas.tdk.handler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.hunter_lc.idcard.farkas.tdk.handler.base.BaseHandler;
import com.hunter_lc.idcard.farksa.tdk.ocr.CameraMainActivity;

/**
 * author：tangdk
 * time：2016/8/16.14:55
 * 接收MainActivity的ui更新消息
 */
public class CameraMainHandler extends BaseHandler {

//    public final int SAVESTATE = 0;
//    public final int RESTORESTATE = 1;
    public final int AUTOFOCUS = 2;
    public final int NEXTBTM = 3;

    public CameraMainHandler(CameraMainActivity activity) throws Exception {
        super(activity);
    }

    @Override
    public void handleStandardMessage(Activity activity, int what, Bundle bundle) {
        CameraMainActivity theActivity = (CameraMainActivity) activity;
        switch (what) {
//            case SAVESTATE:
//                theActivity.saveState(bundle);
//                break;
//            case RESTORESTATE:
//                theActivity.restoreState(bundle);
//                break;
            case AUTOFOCUS:
                theActivity.autoFocus();
                break;
            case NEXTBTM:
                theActivity.nextActivity();
                break;
        }
    }

    @Override
    public void handleStandardMessage(Activity activity, int what, Object obj) {
        CameraMainActivity theActivity = (CameraMainActivity) activity;
        switch (what) {
            case 5:
                break;
        }
    }

    @Override
    public void error(String e) {
        Log.e(TAG, e);
    }
} 