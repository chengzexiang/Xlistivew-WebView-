package com.bwei.czx.chengzexiang1507d0918;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**解析
 * Created by czx on 2017/9/18.
 */

public class StreamTools {
    public static String getstr(InputStream is){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len = is.read(arr)) != -1){
                bos.write(arr,0,len);
            }
            return bos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static DisplayImageOptions getOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_error)
                .showImageOnLoading(R.mipmap.loading)
                .displayer(new CircleBitmapDisplayer())
                .build();
        return options;
    }
}
