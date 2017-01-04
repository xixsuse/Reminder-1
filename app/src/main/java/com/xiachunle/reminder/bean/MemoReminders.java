package com.xiachunle.reminder.bean;

/**
 * Created by xiachunle on 2016/12/1.
 */

import android.text.Editable;

import java.io.Serializable;

/**
 * 备忘录属性
 */
public class MemoReminders implements Serializable {
    private int mId; //编号
    private String mContent;//文本内容
    private int mFlag; //是否重要
    private String createTime;//创建时间
    private byte[] imageDatas;//所有图片数据
    private boolean  hasImage;//所有图片地址拼接称的字符串


    public MemoReminders(int mId, String mContent, int mFlag, String createTime,byte []imageDatas,boolean hasImage) {
        this.mId = mId;
        this.mContent = mContent;
        this.mFlag = mFlag;
        this.createTime = createTime;
        this.imageDatas = imageDatas;
       this.hasImage=hasImage;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getmFlag() {
        return mFlag;
    }

    public void setmFlag(int mFlag) {
        this.mFlag = mFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public byte[] getImageDatas() {
        return imageDatas;
    }

    public void setImageDatas(byte[] imageDatas) {
        this.imageDatas = imageDatas;
    }

    public MemoReminders(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
}