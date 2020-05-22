package com.emag.jesterTester;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.emag.jester.GestureTransformer;
import com.emag.jester.Pointer;
import com.emag.jester.TouchDetector;
import com.emag.jester.Transformation;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TouchDetector detector = new TouchDetector();

    private GestureTransformer currentTransformer;
    private TestView testView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testView = findViewById(R.id.testView);
        findViewById(R.id.resetButton).setOnClickListener(v -> {
            testView.resetRect();
        });
        testView.setOnTouchListener((v, event) -> {
            detector.updateWithTouch(event);
            List<Pointer> pointers = detector.getActivePointers();
            if (pointers.isEmpty() && currentTransformer != null) {
                currentTransformer = null;
                testView.commitPendingTransform();
            } else if (!pointers.isEmpty() && currentTransformer == null) {
                currentTransformer = new GestureTransformer(pointers, new PointF(testView.getRect().centerX(), testView.getRect().centerY()));
            } else if (currentTransformer != null) {
                currentTransformer.updateGesture(pointers);
                Transformation t = currentTransformer.getTransformation();
                testView.setPendingRectTransform(t);
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
