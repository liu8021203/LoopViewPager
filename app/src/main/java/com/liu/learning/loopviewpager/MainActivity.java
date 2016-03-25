package com.liu.learning.loopviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liu.learning.library.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LoopViewPager<String> pager;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (LoopViewPager) findViewById(R.id.loop_pager);
        button = (Button) findViewById(R.id.btn_click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> data = new ArrayList<>();
                data.add("http://a.hiphotos.baidu.com/image/h%3D300/sign=c7024667783e6709a10043ff0bc69fb8/faedab64034f78f097b220b37e310a55b3191c30.jpg");
                data.add("http://e.hiphotos.baidu.com/image/h%3D300/sign=0d25eb3f8c1363270aedc433a18da056/11385343fbf2b2118fe9f2adcc8065380dd78e09.jpg");
                data.add("http://b.hiphotos.baidu.com/image/h%3D360/sign=a9a74c192ff5e0fef1188f076c6134e5/d788d43f8794a4c224a6a42b0cf41bd5ad6e392c.jpg");
                data.add("http://g.hiphotos.baidu.com/image/h%3D360/sign=218406bb85d6277ff612343e18391f63/1b4c510fd9f9d72ac6dc821dd62a2834349bbb72.jpg");
                data.add("http://a.hiphotos.baidu.com/image/h%3D300/sign=c7024667783e6709a10043ff0bc69fb8/faedab64034f78f097b220b37e310a55b3191c30.jpg");
                data.add("http://e.hiphotos.baidu.com/image/h%3D300/sign=0d25eb3f8c1363270aedc433a18da056/11385343fbf2b2118fe9f2adcc8065380dd78e09.jpg");
                data.add("http://b.hiphotos.baidu.com/image/h%3D360/sign=a9a74c192ff5e0fef1188f076c6134e5/d788d43f8794a4c224a6a42b0cf41bd5ad6e392c.jpg");
                data.add("http://g.hiphotos.baidu.com/image/h%3D360/sign=218406bb85d6277ff612343e18391f63/1b4c510fd9f9d72ac6dc821dd62a2834349bbb72.jpg");
                pager.setData(data, new LoopViewPager.ImageListener<String>() {
                    @Override
                    public void onImageClick(String data) {
                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void initData(ImageView view, String o) {
                        Glide
                                .with(MainActivity.this)
                                .load(o)
                                .placeholder(R.mipmap.ic_launcher)
                                .crossFade()
                                .into(view);
                    }
                });
            }
        });
//        pager.setIndicatorRes(R.mipmap.abc_btn_radio_to_on_mtrl_000, R.mipmap.abc_btn_radio_to_on_mtrl_015);
        pager.setCycle(true);
        pager.setWheel(true);
        List<String> data = new ArrayList<>();
        data.add("http://a.hiphotos.baidu.com/image/h%3D300/sign=c7024667783e6709a10043ff0bc69fb8/faedab64034f78f097b220b37e310a55b3191c30.jpg");
        data.add("http://e.hiphotos.baidu.com/image/h%3D300/sign=0d25eb3f8c1363270aedc433a18da056/11385343fbf2b2118fe9f2adcc8065380dd78e09.jpg");
        data.add("http://b.hiphotos.baidu.com/image/h%3D360/sign=a9a74c192ff5e0fef1188f076c6134e5/d788d43f8794a4c224a6a42b0cf41bd5ad6e392c.jpg");
        data.add("http://g.hiphotos.baidu.com/image/h%3D360/sign=218406bb85d6277ff612343e18391f63/1b4c510fd9f9d72ac6dc821dd62a2834349bbb72.jpg");
        pager.setData(data, new LoopViewPager.ImageListener<String>() {
            @Override
            public void onImageClick(String data) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void initData(ImageView view, String o) {
                Glide
                        .with(MainActivity.this)
                        .load(o)
                        .placeholder(R.mipmap.ic_launcher)
                        .crossFade()
                        .into(view);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        pager.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pager.start();
    }
}
