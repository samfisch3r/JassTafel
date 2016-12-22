package ch.zhaw.jasstafel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawPoints extends View {
	private Paint paint = new Paint();
	private Canvas canvas;
	private int xOffset;
	private int yOffset;
	private int count;
	private PointManager pManager;
	
	public DrawPoints(Context context) {
		super(context);
	}
	
	public void drawSetPoints(PointManager pManager) {
		this.pManager = pManager;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		this.canvas = canvas;
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2);
		drawT1Hundred();
		drawT1Fifty();
		drawT1Twenty();
		drawT2Hundred();
		drawT2Fifty();
		drawT2Twenty();
	}

	private void drawT1Hundred() {
		xOffset = 300;
		yOffset = 1000;
		count = pManager.getHundredLineCount(1);
		for (int i = 1; i <= count; i++) {
			if (i % 5 > 0) {
				canvas.drawLine(xOffset, yOffset ,xOffset ,yOffset+80 , paint);
				xOffset += 10;
			} else {
				canvas.drawLine(xOffset-50, yOffset ,xOffset ,yOffset+80 , paint);
				xOffset += 20;
			}
		}
	}
	
	private void drawT1Fifty() {
		xOffset = 350;
		yOffset = 1425;
		count = pManager.getFiftyLineCount(1);
		for (int i = 1; i <= count; i++) {
			if (i % 2 > 0) {
				canvas.drawLine(xOffset, yOffset ,xOffset+80 ,yOffset+45 , paint);
			} else {
				canvas.drawLine(xOffset+30, yOffset-20 ,xOffset+45 ,yOffset+65 , paint);
				xOffset += 50;
				yOffset -= 36;
			}
		}
	}
	
	private void drawT1Twenty() {
		xOffset = 380;
		yOffset = 1510;
		count = pManager.getTwentyLineCount(1);
		for (int i = 1; i <= count; i++) {
			if (i % 5 > 0) {
				canvas.drawLine(xOffset, yOffset ,xOffset ,yOffset+80 , paint);
				xOffset += 10;
			} else {
				canvas.drawLine(xOffset-50, yOffset ,xOffset ,yOffset+80 , paint);
				xOffset += 20;
			}
		}
	}
	
	private void drawT2Hundred() {
		xOffset = 900;
		yOffset = 700;
		count = pManager.getHundredLineCount(2);
		for (int i = 1; i <= count; i++) {
			if (i % 5 > 0) {
				canvas.drawLine(xOffset, yOffset ,xOffset ,yOffset+80 , paint);
				xOffset -= 10;
			} else {
				canvas.drawLine(xOffset+50, yOffset ,xOffset ,yOffset+80 , paint);
				xOffset -= 20;
			}
		}
	}
	
	private void drawT2Fifty() {
		xOffset = 770;
		yOffset = 315;
		count = pManager.getFiftyLineCount(2);
		for (int i = 1; i <= count; i++) {
			if (i % 2 > 0) {
				canvas.drawLine(xOffset, yOffset ,xOffset+80 ,yOffset+45 , paint);
			} else {
				canvas.drawLine(xOffset+30, yOffset-20 ,xOffset+45 ,yOffset+65 , paint);
				xOffset -= 50;
				yOffset += 36;
			}
		}
	}
	
	private void drawT2Twenty() {
		xOffset = 820;
		yOffset = 190;
		count = pManager.getTwentyLineCount(2);
		for (int i = 1; i <= count; i++) {
			if (i % 5 > 0) {
				canvas.drawLine(xOffset, yOffset ,xOffset ,yOffset+80 , paint);
				xOffset -= 10;
			} else {
				canvas.drawLine(xOffset+50, yOffset ,xOffset ,yOffset+80 , paint);
				xOffset -= 20;
			}
		}
	}
}
