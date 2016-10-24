package went_gine.applicationpassword;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 *describe: 单独一个自定义的支付密码框(是否显示密码点)
 *author: Went_Gone
 *create on: 2016/10/24
 */

public class PasswordFrameView extends View{
    private static final String TAG = "passwordframeView";
    private int width;//设置高
    private int height;//设置高
    private int radios ;
    private int radiosStart;
    private int radiosZero;
    private boolean isPassword;
    private Paint mPathPaint;
    private Paint mPaintCircle;



    public PasswordFrameView(Context context) {
        this(context,null);
    }

    public PasswordFrameView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PasswordFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化绘制路径的画笔
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setColor(getResources().getColor(R.color.passwordFrame));
        mPathPaint.setStyle(Paint.Style.STROKE);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(Color.BLACK);
        mPaintCircle.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);//设置宽和高

        radiosZero = 0;
        radiosStart = ((height>width)? height:width)/10;
        radios = radiosZero;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isPassword){
            radios = radiosStart;
        }else {
            radios = radiosZero;
        }
        canvas.drawRect(0,0,width,height,mPathPaint);
        canvas.drawCircle(width/2,height/2,radios,mPaintCircle);
    }

    private void invalidateView(boolean isPassword){
        this.isPassword = isPassword;
        invalidate();
    }

    /**
     * 改变小点的状态
     * @param isPassword
     */
    public void invalidatePassword(boolean isPassword){
        invalidateView(isPassword);
    }
}
