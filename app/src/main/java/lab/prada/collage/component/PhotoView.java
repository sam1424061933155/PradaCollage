package lab.prada.collage.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.thuytrinh.multitouchlistener.MultiTouchListener;

import lab.prada.collage.MainActivity;

public class PhotoView extends ImageView implements BaseComponent {

	public interface OnPhotoListener {
		void onModifyPhoto(PhotoView view);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		android.util.Log.d("DEBUG", "onAttachedToWindow : " + toString());
	}

	private OnPhotoListener listener;
	//test
	private String TAG="Animation Test";
	public static float initalZ=0;
	public static float bottomZ=0;
	private ViewPropertyAnimatorCompat animator;


	public PhotoView(Context context) {
		super(context);
		setOnTouchListener(new MultiTouchListener());
	}

	public PhotoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnTouchListener(new MultiTouchListener());
	}

	public PhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOnTouchListener(new MultiTouchListener());
	}

	public void setImage(Bitmap bitmap){
		setImageBitmap(bitmap);
	}
	
	public void setListener(OnPhotoListener listener){
		this.listener = listener;
		this.setOnTouchListener(new MultiTouchListener(
				new GestureListener()));
		getView().setTranslationZ(initalZ);
		initalZ++;
	}

	private class GestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (listener != null)
				listener.onModifyPhoto(PhotoView.this);
			return true;
		}
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			//getView().bringToFront();
			Log.i(TAG, "Single Tap Up" );
			ViewCompat.animate(getView())
					.translationZ(initalZ)
					.setDuration(0)
					.start();
			initalZ++;
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			//pushToBottom();
			Log.i(TAG, "Long Press" );
			ViewCompat.animate(getView())
					.translationZ(bottomZ)
					.setDuration(0)
					.start();
			bottomZ--;


		}
	}


	public  void pushToBottom(){
		int index = MainActivity.allViews.indexOfChild(getView());
		for(int i = 0; i<index; i++) {
			MainActivity.allViews.bringChildToFront(MainActivity.allViews.getChildAt(i));
		}
	}


	@Override
	public View getView() {
		return this;
	}

	@Override
	public void setXY(int x, int y) {
		ViewCompat.setX(this, x);
		ViewCompat.setY(this, y);
	}
}
