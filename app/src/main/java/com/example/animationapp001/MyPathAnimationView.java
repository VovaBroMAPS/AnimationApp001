package com.example.animationapp001;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyPathAnimationView extends View {
    private Path drawingPath;
    private Paint drawingPaint;
    private List<Keyframe> keyframes;
    private ObjectAnimator pathAnimator;
    private int targetID;

    public MyPathAnimationView(Context context,int targetID) {
        super(context);
        setupDrawing();
        keyframes = new ArrayList<>();
    }

    private void setupDrawing() {
        drawingPath = new Path();
        drawingPaint = new Paint();
        drawingPaint.setColor(Color.BLACK);
        drawingPaint.setStyle(Paint.Style.STROKE);
        drawingPaint.setStrokeJoin(Paint.Join.ROUND);
        drawingPaint.setStrokeCap(Paint.Cap.ROUND);
        drawingPaint.setStrokeWidth(5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(drawingPath, drawingPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawingPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawingPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                // Create a keyframe for the current state
                Keyframe keyframe = Keyframe.ofFloat((float) keyframes.size() / 3f, touchX);
                keyframes.add(keyframe);
                // Update the drawing path

                drawingPath.reset();
                for (Keyframe kf : keyframes) {
                    drawingPath.lineTo((Float) kf.getValue(), touchY);
                }
                break;
            default:
                return false;
        }

        invalidate(); // Invalidate the view to trigger onDraw and update the display
        return true;
    }

    public void startAnimation() {
        if (keyframes.size() < 2) {
            // Animation requires at least two keyframes
            return;
        }

        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframes.toArray(new Keyframe[0]));

        pathAnimator = new ObjectAnimator();
        pathAnimator.setTarget(targetID);
        pathAnimator = ObjectAnimator.ofPropertyValuesHolder(this, valuesHolder);
        pathAnimator.setDuration(keyframes.size() * 1000); // Total duration in milliseconds
        pathAnimator.start();
    }

    // Setter method for translationX property used in the animation
    //public void setTranslationX(float value) {
//        setTranslationX(value);
    //   invalidate();
    //}
}

