package com.example.switchbuttonlib;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SwitchView extends LinearLayout {
    private View mView;
    private Context mContext;

    private LinearLayout llSwitchButton;
    private TextView tvSwitchOn, tvSwitchOff;
    private View vOnFlap, vOffFlap;
    private FrameLayout frameOn, frameOff;

    /**
     * 开关当前状态
     */
    private volatile boolean isPowerOn = false;

    /**
     * 开关背景颜色
     */
    private int switchBgColor;
    /**
     * 开文字颜色
     */
    private int switchOnTextColor;
    /**
     * 关文字颜色
     */
    private int switchOffTextColor;
    /**
     * 开所显示指定文字
     */
    private String switchOnText;
    /**
     * 关所显示指定文字
     */
    private String switchOffText;
    /**
     * 开关内文字大小
     */
    private int switchTextSize;
    /**
     * 开关开启挡板颜色
     */
    private int switchOnFlapColor;
    /**
     * 开关关闭挡板颜色
     */
    private int switchOffFlapColor;
    /**
     * 控件圆角大小
     */
    private int switchCorners;
    /**
     * 控件边框颜色
     */
    private int switchStrokeColor;
    /**
     * 控件边框宽度
     */
    private int switchStrokeWidth;
    /**
     * 控件显示模式
     */
    private int switchMode;

    /**
     * 控件是否显示文字
     */
    private boolean isShowText;


    public SwitchView(Context context) {
        super(context);
        this.mContext = context;
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.switch_view, this);

        llSwitchButton = mView.findViewById(R.id.ll_switch_button);
        tvSwitchOn = mView.findViewById(R.id.tv_switch_on);
        tvSwitchOff = mView.findViewById(R.id.tv_switch_off);
        vOnFlap = mView.findViewById(R.id.v_on_falp);
        vOffFlap = mView.findViewById(R.id.v_off_flap);
        frameOn = mView.findViewById(R.id.frame_on);
        frameOff = mView.findViewById(R.id.frame_off);
        llSwitchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isPowerOn = !isPowerOn;
                exchangeSwitchStatus();
                if (mListener != null) {
                    mListener.switchStatus(isPowerOn);
                }
            }
        });

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchView);
        isPowerOn = typedArray.getBoolean(R.styleable.SwitchView_initStatus, false);
        switchBgColor = typedArray.getColor(R.styleable.SwitchView_switchBgColor, Color.WHITE);
        switchOnTextColor = typedArray.getColor(R.styleable.SwitchView_switchOnTextColor, Color.BLACK);
        switchOffTextColor = typedArray.getColor(R.styleable.SwitchView_switchOffTextColor, Color.BLACK);
        switchOnText = typedArray.getString(R.styleable.SwitchView_switchOnText);
        switchOffText = typedArray.getString(R.styleable.SwitchView_switchOffText);
        switchTextSize = typedArray.getDimensionPixelSize(R.styleable.SwitchView_switchTextSize, sp2px(6));
        switchOnFlapColor = typedArray.getColor(R.styleable.SwitchView_switchOnFlapColor, Color.argb(255, 255, 155, 175));
        switchOffFlapColor = typedArray.getColor(R.styleable.SwitchView_switchOffFlapColor, Color.argb(255, 200, 235, 23));
        switchCorners = typedArray.getDimensionPixelOffset(R.styleable.SwitchView_switchCorners, dp2px(2));
        switchStrokeColor = typedArray.getColor(R.styleable.SwitchView_switchStrokeColor, Color.argb(255, 222, 222, 222));
        switchStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.SwitchView_switchStrokeWidth, dp2px(0.5f));
        switchMode = typedArray.getInt(R.styleable.SwitchView_switchMode, 2);
        isShowText = typedArray.getBoolean(R.styleable.SwitchView_isShowText, false);
        typedArray.recycle();

        //设置switch背景，圆角，边框
        GradientDrawable switchDrawable = new GradientDrawable();
        switchDrawable.setShape(GradientDrawable.RECTANGLE);
        switchDrawable.setCornerRadius(switchCorners);
        switchDrawable.setStroke(switchStrokeWidth, switchStrokeColor);
        switchDrawable.setColor(switchBgColor);

        float innerCorners = switchCorners - switchStrokeWidth / 2;
        //设置左/上挡板的颜色
        GradientDrawable onFlapDrawable = new GradientDrawable();
        onFlapDrawable.setShape(GradientDrawable.RECTANGLE);
        onFlapDrawable.setColor(switchOnFlapColor);

        //设置右/下挡板的颜色
        GradientDrawable offFlapDrawable = new GradientDrawable();
        offFlapDrawable.setShape(GradientDrawable.RECTANGLE);
        offFlapDrawable.setColor(switchOffFlapColor);

        LayoutParams lpOn = (LayoutParams) frameOn.getLayoutParams();
        LayoutParams lpOff = (LayoutParams) frameOff.getLayoutParams();

        //设置switch方向
        if (switchMode == 1) {//横
            llSwitchButton.setOrientation(HORIZONTAL);
            onFlapDrawable.setCornerRadii(new float[]{innerCorners, innerCorners, 0, 0, 0, 0, innerCorners, innerCorners});
            offFlapDrawable.setCornerRadii(new float[]{0, 0, innerCorners, innerCorners, innerCorners, innerCorners, 0, 0});
            lpOn.setMargins(switchStrokeWidth, switchStrokeWidth, 0, switchStrokeWidth);
            lpOff.setMargins(0, switchStrokeWidth, switchStrokeWidth, switchStrokeWidth);
        } else if (switchMode == 2) {//纵
            llSwitchButton.setOrientation(VERTICAL);
            onFlapDrawable.setCornerRadii(new float[]{innerCorners, innerCorners, innerCorners, innerCorners, 0, 0, 0, 0});
            offFlapDrawable.setCornerRadii(new float[]{0, 0, 0, 0, innerCorners, innerCorners, innerCorners, innerCorners});
            lpOn.setMargins(switchStrokeWidth, switchStrokeWidth, switchStrokeWidth, 0);
            lpOff.setMargins(switchStrokeWidth, 0, switchStrokeWidth, switchStrokeWidth);
        }

        llSwitchButton.setBackground(switchDrawable);
        vOnFlap.setBackground(onFlapDrawable);
        vOffFlap.setBackground(offFlapDrawable);
        frameOn.setLayoutParams(lpOn);
        frameOff.setLayoutParams(lpOff);

        if (isShowText) {
            tvSwitchOn.setTextColor(switchOnTextColor);
            tvSwitchOff.setTextColor(switchOffTextColor);
            tvSwitchOn.setTextSize(switchTextSize);
            tvSwitchOff.setTextSize(switchTextSize);

            if (switchOnText == null || switchOnText.equals("")) {
                tvSwitchOn.setText("开启");
            } else {
                tvSwitchOn.setText(switchOnText);
            }
            if (switchOffText == null || switchOffText.equals("")) {
                tvSwitchOff.setText("关闭");
            } else {
                tvSwitchOff.setText(switchOffText);
            }
        }

        exchangeSwitchStatus();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = getRectSize()[0];
            widthMode = MeasureSpec.EXACTLY;
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = getRectSize()[1];
            heightMode = MeasureSpec.EXACTLY;
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int[] getRectSize() {
        int[] rect = new int[2];//1.width 2.height
        if (isShowText) {
            if (switchMode == 1) {
                rect[0] = dp2px(100);
                rect[1] = dp2px(40);
            } else {
                rect[0] = dp2px(50);
                rect[1] = dp2px(70);
            }
        } else {
            if (switchMode == 1) {
                rect[0] = dp2px(50);
                rect[1] = dp2px(25);
            } else {
                rect[0] = dp2px(40);
                rect[1] = dp2px(40);
            }
        }
        return rect;
    }

    private void exchangeSwitchStatus() {
        tvSwitchOff.setVisibility(isPowerOn ? VISIBLE : INVISIBLE);
        tvSwitchOn.setVisibility(isPowerOn ? INVISIBLE : VISIBLE);
        vOffFlap.setVisibility(!isPowerOn ? VISIBLE : INVISIBLE);
        vOnFlap.setVisibility(!isPowerOn ? INVISIBLE : VISIBLE);
    }

    public void setSwitchAble(boolean enable) {
        llSwitchButton.setEnabled(enable);
    }

    public void setSwitchStatus(boolean isOn) {
        isPowerOn = isOn;
        exchangeSwitchStatus();
    }

    public boolean getSwitchStatus() {
        return isPowerOn;
    }


    private SwitchStatusStatusListener mListener;

    public void setSwitchStatusStatusListener(SwitchStatusStatusListener listener) {
        this.mListener = listener;
    }


    public interface SwitchStatusStatusListener {
        void switchStatus(boolean status);
    }

    private int sp2px(float spValue) {
        float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int dp2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
