package com.example.dell.learning10;

/**
 * Created by dell on 2018/9/19.
 */
import java.io.BufferedReader;
import java.io.InputStream;

public interface HttpCallBackListener {
    /**
     * 当Http访问完成时回调onFinish方法
     */
    void onFinish(InputStream inputStream);
    /**
     * 当Http访问失败时回调onError方法
     */
    void onError();
}
