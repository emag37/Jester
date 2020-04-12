package com.emag.jesterTester;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;

import com.emag.jester.GestureTransformer;
import com.emag.jester.TouchDetector;
import com.emag.jester.Transformation;

public class MainActivity extends AppCompatActivity {

    private TouchDetector detector = new TouchDetector();

    private TestView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testView = findViewById(R.id.testView);
        findViewById(R.id.resetButton).setOnClickListener(v -> {
            testView.resetRect();
        });
        testView.setOnTouchListener(detector);
        detector.addListener(pointers -> {
            if (pointers.stream().anyMatch(p -> !testView.getRect().contains(p.currentPoint.x, p.currentPoint.y))) {
                return;
            }

            PointF centroid = new PointF(testView.getRect().centerX(), testView.getRect().centerY());

            Transformation t = GestureTransformer.transformFromGesture(pointers, centroid);
            testView.transformRect(t);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
