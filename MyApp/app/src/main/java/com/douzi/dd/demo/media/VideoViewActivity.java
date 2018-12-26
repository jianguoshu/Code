package com.douzi.dd.demo.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.douzi.dd.BaseActivity;
import com.douzi.dd.R;

public class VideoViewActivity extends BaseActivity {

    private FitHeightVideoView mVideoView;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, VideoViewActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        mVideoView = this.findViewById(R.id.video_view);

        mVideoView.setVideoPath("http://sofa.resource.shida.sogoucdn.com/a6cbee9f-052d-42c3-859f-6bbbfab8d6582_1_0.mp4");
//        mVideoView.setVideoPath("https://biziflow.sogoucdn.com/201811/1127cc6f-8675-498b-8927-1f49e316eb8f.mp4?1545731239");
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.setVideoWidth(mp.getVideoWidth());
                mVideoView.setVideoHeight(mp.getVideoHeight());
                mVideoView.requestLayout();
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            // video 视屏播放的时候把背景设置为透明
                            mVideoView.setBackgroundColor(Color.TRANSPARENT);
                            return true;
                        }
                        return false;
                    }
                });
                mp.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
    }
}
