package lab.prada.collage.component;


import com.thuytrinh.multitouchlistener.MultiTouchListener;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import lab.prada.collage.MainActivity;

public abstract class BaseLabelView extends View implements BaseComponent {

	protected OnLabelListener listener;
	
	public final static int DEFAULT_FONT_SIZE = 100;
	protected String mText;
	protected int mAscent;
	protected boolean hasStroke = false;

	//test
	private String TAG="Animation Test";

	public BaseLabelView(Context context) {
		super(context);
	}

	public interface OnLabelListener {
		public void onModifyLabel(BaseLabelView view, String text, int color,
				boolean hasStroke);
	}

	public abstract void setText(String text, int color, boolean hasStroke);
	public abstract int getTextColor();

	public void setListener(OnLabelListener listener) {
		this.listener = listener;
		this.setOnTouchListener(new MultiTouchListener(new GestureListener()));

	}

	private class GestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			Log.i(TAG, "On Down" );

			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (listener != null)
				listener.onModifyLabel(BaseLabelView.this, mText, getTextColor(), hasStroke);
			return true;
		}
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// Up motion completing a single tap occurred.
			Log.i(TAG, "Single Tap Up" );
			//getView().bringToFront();


			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// Touch has been long enough to indicate a long press.
			// Does not indicate motion is complete yet (no up event necessarily)
			Log.i(TAG, "Long Press" );
			//pushToBottom();

		}
	}

}
