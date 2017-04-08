package com.gsww.www.loginview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Author : luweicheng on 2017/4/7 0007 11:25
 * E-mail ：1769005961@qq.com
 * GitHub : https://github.com/luweicheng24
 * function: 自定义圆形图片
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    Paint paint;//画笔

    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
    /**
     * 绘制圆形图片
     * @author caizhiming
     */
    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap b = getCircleBitmap(bitmap);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            Log.e("Circle", "onDraw: b.getWidth="+b.getWidth()+"b.getHeight="+b.getHeight()+"getWidth="+getWidth()+"getHeight()="+getHeight() );
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, paint);


        } else {
            super.onDraw(canvas);
        }
    }
    /**
     * 获取圆形图片方法
     * @param bitmap
     * @return Bitmap
     * @author caizhiming
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {

        int length = bitmap.getHeight()>bitmap.getWidth()?bitmap.getWidth():bitmap.getHeight();
        Log.e("circle", "getCircleBitmap: length=="+length );
        Bitmap output = Bitmap.createBitmap(length,
                length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);//创建一个图片画布
        paint.setAntiAlias(true);//去除锯齿
        canvas.drawCircle(length/2 ,length/2 , length/2 , paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置重叠显示模式
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return output;


    }

}
