package com.example.cutter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class CanvasView extends View {

    Paint paint,paintBody;
    Context context;
    Path path;
    float Xi,Yi,Xf,Yf;
    Bitmap bitmapUp,bitmapDown;
    Canvas canvas;
    public CanvasView(Context context, @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        path = new Path();
        paintBody = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8f);
        paintBody.setAntiAlias(true);
        paintBody.setColor(Color.RED);
        paintBody.setStyle(Paint.Style.FILL);
        paintBody.setStrokeWidth(10f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,400,paintBody);
        this.canvas = canvas;
        canvas.drawPath(path,paint);
        this.setDrawingCacheEnabled(true);
    }

    private Boolean isAbove(float x,float y){
        return ((Yf-Yi)/(Xf-Xi))*(x-Xf) -  (y-Yf)>0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(),y=event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(x,y);
                Xi=x;
                Yi=y;
                break;
            case MotionEvent.ACTION_MOVE: break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x,y);
                Xf=x;
                Yf=y;
                break;
            default: break;
        }
        invalidate();
        return true;
    }

    public List<Bitmap> getBitmaps() {
        this.buildDrawingCache();
        bitmapUp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++)
                if (isAbove(x, y))
                    bitmapUp.setPixel(x, y, this.getDrawingCache(true).getPixel(x, y));
        bitmapDown = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        for (int x = 0; x < getWidth(); x++)
            for (int y = 0; y < getHeight(); y++)
                if (!isAbove(x, y))
                    bitmapDown.setPixel(x, y, this.getDrawingCache(true).getPixel(x, y));
        List<Bitmap> list = new ArrayList<>();
        list.add(bitmapUp);
        list.add(bitmapDown);
        return list;
    }
}
