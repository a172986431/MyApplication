package com.mvp.rxandroid.activity.imageshow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elang on 16/6/3.
 */
public class ImageshowModel {

    public List<String> imageUrls;

    /**
     * 初始化activity需要的数据
     * @return 返回数据
     */
    public List<String> initData(){
        if (imageUrls == null) {
            imageUrls = new ArrayList<>();
        }
        imageUrls.clear();
        imageUrls.add("http://d.hiphotos.baidu.com/image/h%3D360/sign=9f6e24765adf8db1a32e7a623922dddb/0ff41bd5ad6eddc492d491153ddbb6fd52663328.jpg");
        imageUrls.add("http://a.hiphotos.baidu.com/image/h%3D360/sign=4f6888e673c6a7efa626ae20cdfaafe9/f9dcd100baa1cd11daf25f19bc12c8fcc3ce2d46.jpg");
        imageUrls.add("http://h.hiphotos.baidu.com/image/h%3D360/sign=0d00fbf7d2ca7bcb627bc1298e096b3f/a2cc7cd98d1001e9460fd63bbd0e7bec54e797d7.jpg");
        imageUrls.add("http://f.hiphotos.baidu.com/image/h%3D360/sign=0ec20d71f01fbe09035ec5125b600c30/00e93901213fb80e0ee553d034d12f2eb9389484.jpg");
        imageUrls.add("http://img4.imgtn.bdimg.com/it/u=3704122693,1924714915&fm=21&gp=0.jpg");
        imageUrls.add("http://img4.imgtn.bdimg.com/it/u=2718132976,3419769724&fm=21&gp=0.jpg");
        imageUrls.add("http://img3.imgtn.bdimg.com/it/u=1584000174,2156456511&fm=21&gp=0.jpg");
        imageUrls.add("http://img0.imgtn.bdimg.com/it/u=2520669762,4163608816&fm=21&gp=0.jpg");
        imageUrls.add("http://img1.imgtn.bdimg.com/it/u=3048638830,762675327&fm=21&gp=0.jpg");
        imageUrls.add("http://img0.imgtn.bdimg.com/it/u=1185995358,925786236&fm=21&gp=0.jpg");
        imageUrls.add("http://img3.imgtn.bdimg.com/it/u=3538432714,789627981&fm=21&gp=0.jpg");
        return imageUrls;
    }

    /**
     * 从数据库中取出一张图片
     * @param urls 取出的图片保存的列表
     * @return
     */
    public List<String> addImage(List<String> urls){
        if (urls == null){
            urls = new ArrayList<>();
        }
        if (urls.size() >= imageUrls.size()){
            return urls;
        }
        int index = urls.size();
        urls.add(index,imageUrls.get(index));
        return urls;
    }

}
