package com.theweflex.react;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.utils.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by beansoft on 2018/1/31.
 */

public class Util {

    //宽高等比缩放压缩
    public static byte[] comBitmapOption(Bitmap thumb) {
        int size = 1;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.PNG, 100, baos);

        ByteArrayInputStream isBm = null;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();

        byte[] result = bmpToByteArray(thumb, true);
        while (result.length > 32 * 1024) {
            size = size + 1;

            isBm = new ByteArrayInputStream(baos.toByteArray());

            newOpts.inSampleSize = size;

            thumb = BitmapFactory.decodeStream(isBm, null, newOpts);
            thumb.compress(Bitmap.CompressFormat.PNG, 100, baos);
            result = bmpToByteArray(thumb, true);
        }

        try {
            baos.close();
            if (null != isBm) {
                isBm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
