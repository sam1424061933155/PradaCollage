package lab.prada.collage.component;


import com.thuytrinh.multitouchlistener.MultiTouchListener;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
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
		getView().setTranslationZ(PhotoView.initalZ);
		PhotoView.initalZ++;
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
			//getView().bringToFront();
			Log.i(TAG, "Single Tap Up" );
			ViewCompat.animate(getView())
					.translationZ(PhotoView.initalZ)
					.setDuration(0)
					.start();
			PhotoView.initalZ++;

			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			//pushToBottom();
			Log.i(TAG, "Long Press" );
			ViewCompat.animate(getView())
					.translationZ(PhotoView.bottomZ)
					.setDuration(0)
					.start();
			PhotoView.bottomZ--;
		}
	}
	
	public  void pushToBottom(){
		int index = MainActivity.allViews.indexOfChild(getView());
		for(int i = 0; i<index; i++) {
			MainActivity.allViews.bringChildToFront(MainActivity.allViews.getChildAt(i));
		}
	}
}
