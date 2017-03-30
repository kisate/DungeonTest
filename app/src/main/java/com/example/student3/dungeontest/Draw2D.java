package com.example.student3.dungeontest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.Random;
/**
 * Created by student3 on 30.03.17.
 */
public class Draw2D extends View {

    private int windowHeight, windowWidth;
    private LinkedList<Edge> edges = new LinkedList<Edge>();
    public Draw2D(Context context) {
        super(context);
    }

    public Draw2D(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        windowHeight = h;
        windowWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LinkedList<Point2D> points = generate();

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);

        for (Point2D point :
                points) {
            canvas.drawCircle(point.x*50 + windowWidth/2, point.y*50 + windowHeight/2, 10, paint);
        }

        for (Edge edge :
                edges) {
            canvas.drawLine(edge.either.x*50 + windowWidth/2, edge.either.y*50 + windowHeight/2, edge.other.x*50 + windowWidth/2, edge.other.y*50 + windowHeight/2, paint);
        }
    }

    public LinkedList<Point2D> generate(){
        edges = new LinkedList<Edge>();
        int count = 10;
        LinkedList<Point2D> points = new LinkedList<Point2D>();
        points.add(new Point2D(0, 0));
        while (points.size() < count){
            points.add(nextPoint(points));
        }


        return points;
    }

    public Point2D nextPoint(LinkedList<Point2D> points) {
        int x = 0;
        if (Math.random() >= 0.5) x = 4;
        if (Math.random() >= 0.5) x *= -1f;

        int y = (int) Math.sqrt(16 - x*x);
        if (Math.random() >= 0.5) y *= -1f;

        Point2D rand = points.get((int)(Math.random() * points.size()));
        Point2D res = new Point2D(rand.x + x, rand.y + y);

        boolean t = false;

        for (Point2D point :
                points) {
            if((res.x == point.x) && (res.y == point.y)) t = true;

        }
        if (t) return nextPoint(points);
        edges.add(new Edge(rand, res));
        return res;
    }

    public void onClick(View v) {
        this.invalidate();
    }
}
