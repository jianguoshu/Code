package com.douzi.dd.demo.inflate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;
import com.douzi.dd.utils.Logger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class InflateTestActivity extends BaseActivity {

    public static void startAct(Context context) {
        Intent intent = new Intent(context, InflateTestActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflate_test);
        ViewGroup container = this.findViewById(R.id.container);
        XmlResourceParser xmlParser = getResources().getLayout(R.layout.inflate_test);
        try {
            int event = xmlParser.getEventType();   //先获取当前解析器光标在哪
            while (event != XmlPullParser.END_DOCUMENT){    //如果还没到文档的结束标志，那么就继续往下处理
                switch (event){
                    case XmlPullParser.START_DOCUMENT:
                        Logger.i(InflateTestActivity.class.getSimpleName(),"xml解析开始");
                        break;
                    case XmlPullParser.START_TAG:
                        //一般都是获取标签的属性值，所以在这里数据你需要的数据
                        Logger.i(InflateTestActivity.class.getSimpleName(),"当前标签是："+xmlParser.getName());
                        for (int i = 0; i < xmlParser.getAttributeCount(); i++) {
                            Logger.i(InflateTestActivity.class.getSimpleName(), xmlParser.getAttributeName(i)
                                    + ": " + xmlParser.getAttributeValue(i));
                        }
                        break;
                    case XmlPullParser.TEXT:
                        Logger.i(InflateTestActivity.class.getSimpleName(),"Text:" + xmlParser.getText());
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = xmlParser.next();   //将当前解析器光标往下一步移
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        AdaptXmlResourceParser parser = new AdaptXmlResourceParser(xmlParser, 2.0f);
        LayoutInflater.from(this).inflate(getResources().getLayout(R.layout.inflate_test), container, true);
    }
}