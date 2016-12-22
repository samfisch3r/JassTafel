package ch.zhaw.jasstafel;

import ch.zhaw.jasstafel.R;
import ch.zhaw.jasstafel.AddPointsDialog.PointsDialogListener;
import ch.zhaw.jasstafel.ChooseLimitDialog.LimitDialogListener;
import ch.zhaw.jasstafel.WonDialog.WonDialogListener;
import ch.zhaw.jasstafel.util.SystemUiHider;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity implements LimitDialogListener, PointsDialogListener, WonDialogListener {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	private ChooseLimitDialog dialog1;
	private String stringLimit = "";
	private int intLimit;
	private DrawPoints drawPoints;
	private TextView textView;
	private PointManager pManager = PointManager.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fullscreen);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// Disable back button
	}
	
	protected void onStart() {
		super.onStart();
		if (stringLimit == "" && dialog1 == null) {
			dialog1 = new ChooseLimitDialog();
			dialog1.show(getFragmentManager(), "limit");
		}
	}
	
	@Override
	public void onLimitOkButtonClick(int value) {
		setContentView(R.layout.activity_fullscreen);
		textView = new TextView(this);
		textView = (TextView)findViewById(R.id.text_limit);
		Limit limit = new Limit();
		stringLimit = limit.getLimits(value);
		intLimit = limit.getLimitsValue(value);
		textView.setText(stringLimit);
	}
	
	@Override
	public void onAddPointsButtonClick(int pTeam1, int pTeam2) {
		setContentView(R.layout.activity_fullscreen);
		pManager.addPoints(pTeam1, pTeam2);
		int pointsTeam1 = pManager.getPoints(1);
		int pointsTeam2 = pManager.getPoints(2);
		textView = new TextView(this);
		textView = (TextView)findViewById(R.id.text_limit);
		textView.setText(stringLimit);
		textView = (TextView)findViewById(R.id.points_team1);
		textView.setText(String.valueOf(pointsTeam1));
		textView = (TextView)findViewById(R.id.points_team2);
		textView.setText(String.valueOf(pointsTeam2));
		if (pointsTeam1 >= intLimit) {
			WonDialog dialog = new WonDialog(1, pointsTeam1, pointsTeam2);
			dialog.show(getFragmentManager(), "won");
		} else if (pointsTeam2 >= intLimit) {
			WonDialog dialog = new WonDialog(2, pointsTeam1, pointsTeam2);
			dialog.show(getFragmentManager(), "won");
		}
		drawPoints = new DrawPoints(this);
		drawPoints.drawSetPoints(pManager);
		addContentView(drawPoints, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		drawPoints.bringToFront();
		textView = (TextView)findViewById(R.id.text_single_points1);
		textView.setText(String.valueOf(pManager.getSinglePoints(1)));
		textView = (TextView)findViewById(R.id.text_single_points2);
		textView.setText(String.valueOf(pManager.getSinglePoints(2)));
	}
	
	@Override
	public void onWonOkButtonClick() {
		textView = new TextView(this);
		textView = (TextView)findViewById(R.id.points_team1);
		textView.setText("0");
		textView = (TextView)findViewById(R.id.points_team2);
		textView.setText("0");
		textView = (TextView)findViewById(R.id.text_single_points1);
		textView.setText("0");
		textView = (TextView)findViewById(R.id.text_single_points2);
		textView.setText("0");
		pManager.resetPoints();
		drawPoints.invalidate();
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (x > 130 && x < 1060) {
					if (y > 170 && y < 930) {
						AddPointsDialog dialog = new AddPointsDialog(2);
						dialog.show(getFragmentManager(), "points");
					}
					if (y > 950 && y < 1700) {
						AddPointsDialog dialog = new AddPointsDialog(1);
						dialog.show(getFragmentManager(), "points");
					}
				}
				break;
			default:
				break;
		}
		return true;
	}
	
	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
