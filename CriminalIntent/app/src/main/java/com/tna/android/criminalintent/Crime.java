package com.tna.android.criminalintent;

import java.util.UUID;

/**
 * Created by tasing on 7/22/2016.
 */
public class Crime {
    private UUID mID;
    private String mText;

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public UUID getmID() {
        return mID;
    }

    public Crime(){
        mID = UUID.randomUUID();
    }
}
