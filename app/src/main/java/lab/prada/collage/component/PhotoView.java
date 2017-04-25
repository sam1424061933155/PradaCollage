package lab.prada.collage.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thuytrinh.multitouchlistener.MultiTouchListener;

import lab.prada.collage.MainActivity;
import lab.prada.collage.R;

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
		getView().setBackgroundResource(R.drawable.outline);

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
			// Up motion completing a single tap occurred.
			//getView().bringToFront();
			Log.i(TAG, "Single Tap Up" );

			final float x = getScaleX();
			final float y = getScaleY();

			ViewCompat.animate(getView())
					.scaleX(x+0.1f)
					.scaleY(y+0.1f)
					.setDuration(200)
					.withEndAction(new Runnable() {
						@Override
						public void run() {
							ViewCompat.setScaleX(getView(),x);
							ViewCompat.setScaleY(getView(),y);
							getView().bringToFront();

						}
					})
					.start();


			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// Touch has been long enough to indicate a long press.
			// Does not indicate motion is complete yet (no up event necessarily)
			Log.i(TAG, "Long Press" );
			final float x = getScaleX();
			final float y = getScaleY();


			ViewCompat.animate(getView())
						.alpha(0.5f)
						.setDuration(300)
						.withEndAction(new Runnable() {
							@Override
							public void run() {
								//ViewCompat.setAlpha(getView(),1);
								ViewCompat.animate(getView())
										.alpha(1f)
										.setDuration(300)
										.start();
								ViewCompat.setScaleX(getView(),x);
								ViewCompat.setScaleY(getView(),y);
								MainActivity.photoPanel.removeView(getView());
								MainActivity.photoPanel.addView(getView(),0);

							}
						})
						.start();
		}
	}




	/*public boolean Distance(View view){

		double x_delta =Math.pow(ViewCompat.getX(view)-ViewCompat.getX(getView()),2);
		double y_delta =Math.pow(ViewCompat.getY(view)-ViewCompat.getY(getView()),2);
		double dis = Math.sqrt(x_delta+y_delta);
		Log.i(TAG, "dis = " +dis );
		if(dis < 500){
			return true;
		}
		return false;
	}*/


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
