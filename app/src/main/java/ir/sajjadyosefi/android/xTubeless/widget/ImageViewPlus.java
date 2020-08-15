package ir.sajjadyosefi.android.xTubeless.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import ir.sajjadyosefi.android.xTubeless.R;


/**
 * Created by sajjad on 06/23/2016.
 */
public class ImageViewPlus extends ImageView {

    float widthAndHeightRatio = -1;

    public ImageViewPlus(Context context) {
        super(context);
    }

    public ImageViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,  R.styleable.CustomImageview);
        widthAndHeightRatio = a.getFloat(a.getIndex(0), 1);

    }

    public ImageViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomImageview);
        widthAndHeightRatio = a.getFloat(a.getIndex(0), 1);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (widthAndHeightRatio != -1) {
            int width = getMeasuredWidth();

            setMeasuredDimension(width, (int) (width / widthAndHeightRatio));
        }

    }
    public void setWidthAndHeightRatio(float rate){
        widthAndHeightRatio=rate;
        requestLayout();
    }

}
