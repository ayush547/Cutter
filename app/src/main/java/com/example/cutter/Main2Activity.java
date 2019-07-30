package com.example.cutter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {

    CanvasView2 canvasView;
    ImageView one,two;
    Button generate,drawCurve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        canvasView = findViewById(R.id.canvas);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        generate = findViewById(R.id.button);
        drawCurve = findViewById(R.id.drawCurve);
    }

    public void Generate(View view) {
        canvasView.setVisibility(View.GONE);
        one.setVisibility(View.VISIBLE);
        two.setVisibility(View.VISIBLE);
        one.setImageBitmap(canvasView.getBitmaps().get(0));
        two.setImageBitmap(canvasView.getBitmaps().get(1));
        generate.setVisibility(View.INVISIBLE);
        drawCurve.setVisibility(View.INVISIBLE);
    }

    public void DrawCurve(View view) {
        canvasView.setDrawPolygonMode(false);
    }
}
