package com.emag.jesterTester;

import android.graphics.PointF;

import com.emag.jester.ScaleTransformer;

import org.junit.Test;

import java.util.Arrays;

public class AnchorPointTest {
    @Test
    public void WhenAnchorPointIsFirstPoint_AnchorPointIsFound() {
        Pointer p1 = new Pointer(1);
        p1.currentPoint = new FakePointF(1f,1f);
        p1.previousPoint = new FakePointF(1f,1f);

        Pointer p2 = new Pointer(2);
        p2.currentPoint = new FakePointF(3f,3f);
        p2.previousPoint = new FakePointF(2f,2f);
        PointF anchor = ScaleTransformer.getAnchorPoint(Arrays.asList(p1, p2));
    }

    @Test
    public void WhenAnchorPointIsMidpointBetweenFirstAndSecond_AnchorPointIsFound() {
        Pointer p1 = new Pointer(1);
        p1.currentPoint = new FakePointF(0f,0f);
        p1.previousPoint = new FakePointF(1f,1f);

        Pointer p2 = new Pointer(2);
        p2.currentPoint = new FakePointF(3f,3f);
        p2.previousPoint = new FakePointF(2f,2f);
        PointF anchor = ScaleTransformer.getAnchorPoint(Arrays.asList(p1, p2));
    }

    @Test
    public void WhenAnchorPointIsOneThirdBetweenFirstAndSecond_AnchorPointIsFound() {
        Pointer p1 = new Pointer(1);
        p1.currentPoint = new FakePointF(0.5f,0.5f);
        p1.previousPoint = new FakePointF(1f,1f);

        Pointer p2 = new Pointer(2);
        p2.currentPoint = new FakePointF(3f,3f);
        p2.previousPoint = new FakePointF(2f,2f);
        PointF anchor = ScaleTransformer.getAnchorPoint(Arrays.asList(p1, p2));
    }
}
