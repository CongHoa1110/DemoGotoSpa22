package com.hoathan.hoa.demogotospa.data.model;

/**
 * Created by Tungnguyenbk54 on 11/18/2017.
 */

public class Language {
    private int mId;
    private String mName;
    private String mCode;

    public Language(int id, String name, String code) {
        mId = id;
        mName = name;
        mCode = code;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getCode() {
        return mCode;
    }
}
