# LoopViewPager
循环ViewPager

在XML中 添加方式

<com.liu.learning.library.LoopViewPager
    android:id="@+id/loop_pager"
    android:layout_width="match_parent"
    android:layout_height="200dip"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:gravity="left"
    app:indicatorSpacing="0.5dip">
</com.liu.learning.library.LoopViewPager>
app:gravity="left"
是指示器的位置
app:indicatorSpacing="0.5dip"
是指示器圆点之间的距离

在代码中初始化方式如下


pager.setCycle(true); //是否循环
pager.setWheel(true); //是否轮播
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

代码地址：https://github.com/liu8021203/LoopViewPager

gradle引用方式：

compile 'com.github.jcdream.loopviewpager:library:1.2'
