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

public class CanvasView2 extends View {

    Paint paint,paintBody;
    Context context;
    Path path,pathBody;
    Bitmap bitmapUp,bitmapDown;
    Boolean drawPolygonMode = true;
    Boolean first = true;
    public void setDrawPolygonMode(Boolean drawPolygonMode) {
        this.drawPolygonMode = drawPolygonMode;
    }

    public CanvasView2(Context context, @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        path = new Path();
        pathBody = new Path();
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
        canvas.drawPath(pathBody,paintBody);
        canvas.drawPath(path,paint);
        this.setDrawingCacheEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(),y=event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(drawPolygonMode){
                    if(first){
                        pathBody.moveTo(x,y);
                        first = false;
                    }
                    pathBody.lineTo(x,y);
                }
                else {
                    path.reset();
                    path.moveTo(x, y);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!drawPolygonMode) path.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default: break;
        }
        invalidate();
        return true;
    }

    public List<Bitmap> getBitmaps() {
        this.buildDrawingCache();
        Boolean flip;
        bitmapUp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmapDown = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap temp = Bitmap.createBitmap(this.getDrawingCache(true));
        for (int y = 0; y < getHeight(); y++) {
            flip=true;
            for (int x = 0; x < getWidth()-1; x++) {
                if(temp.getPixel(x,y)==paint.getColor()) {
                    if(temp.getPixel(x+1,y)!=paint.getColor()) flip=!flip;
                    continue;
                }
                if(flip) bitmapUp.setPixel(x,y,temp.getPixel(x,y));
                else bitmapDown.setPixel(x,y,temp.getPixel(x,y));
            }
        }

        List<Bitmap> list = new ArrayList<>();
        list.add(bitmapUp);
        list.add(bitmapDown);
        return list;
    }
}
