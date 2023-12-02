package ir.sajjadyosefi.android.xTubeless.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import ir.sajjadyosefi.android.xTubeless.R;


/**
 * Set the default text attribute  :given font , language  ,mode  , size.
 * <p>
 * mode :  0 text , 1 number , 2 icon .
 * <p>
 * customSize : get Scale Method per scale pixel.
 * <p>
 * language : 0 fa , 1 en.
 */
public class CustomEditText extends AppCompatEditText {
    public static final String FONT_IRANSANS_MOBILE_NORMAL_TTF = "fonts/IRANSansMobile_Normal.ttf";
//    public static final String FONT_IRANSANS_MOBILE_NORMAL_TTF = "fonts/byekan.ttf";
    static int flag;
    //    SqlEngine sqlEngine;
    Context context;
    InputFilter[] filterArray;
    TextWatcher amountWatcher = new TextWatcher() {
        private String current = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String regex = "\\s*\\bریال\\b\\s*";
            String cleanStr = "";
            if (!s.toString().equals(current)) {
                removeTextChangedListener(this);

                cleanStr = s.toString().replaceAll(regex, "");
                cleanStr = cleanStr.replaceAll("[,]", "");
                StringBuilder stringBuilder = new StringBuilder(cleanStr);
                int strLenght = cleanStr.length();
                for (int i = 1; i <= strLenght / 3; i++) {
                    if (strLenght - (i * 3) > 0) {
                        stringBuilder = stringBuilder.insert(strLenght - (i * 3), ',');
                    }
                }
                cleanStr = stringBuilder.toString();
                setText(cleanStr + " ریال ");
                setSelection(cleanStr.length());
            }
            addTextChangedListener(this);
        }


        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher ShebaWatcher = new TextWatcher() {
        boolean mEditing = false;
        private String current = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }


        @Override
        public synchronized void afterTextChanged(Editable s) {
            String regexSheba = "\\s*\\bIR\\b\\s*";
            String cleanS = "";
            if (s.length() > 33) {
                ;
            }
//            else if (!s.toString().equals(current)) {
//                removeTextChangedListener(this);
            else if (!mEditing) {
//                removeTextChangedListener(this);
                mEditing = true;

                cleanS = s.toString().replaceAll(regexSheba, "");
                cleanS = cleanS.replaceAll("[-]", "");
                StringBuilder stringBuilder = new StringBuilder(cleanS);
                cleanS = stringBuilder.toString();
                setText("IR " + cleanS);
                setSelection(cleanS.length() + 3);
            }
//            addTextChangedListener(this);
            mEditing = false;

        }
    };
    TextWatcher CardWatcher = new TextWatcher() {
        boolean mEditing = false;
        private String current = "";

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public synchronized void afterTextChanged(Editable s) {
            String cleanS = "";
//            if (!s.toString().equals(current)) {
            if (!mEditing) {
                mEditing = true;
//                removeTextChangedListener(this);
                cleanS = s.toString();
                cleanS = cleanS.replaceAll("[-]", "");
                StringBuilder stringBuilder = new StringBuilder(cleanS);
                int strLenght = cleanS.length();
                for (int i = 1; i <= strLenght / 4; i++) {
                    if (strLenght - (i * 4) > 0) {
                        stringBuilder = stringBuilder.insert((strLenght - (i * 4)), '-');
                    }
                }
                cleanS = stringBuilder.toString();
                setText(cleanS);
                setSelection(cleanS.length());
            }
//            addTextChangedListener(this);
            mEditing = false;
        }
    };

    public CustomEditText(Context context) {
        super(context);
        this.context = context;
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setCustomFacility(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setCustomFacility(context, attrs);
    }

    private void setCustomFacility(Context context, AttributeSet attrs) {
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomInputText);
//        int mode = a.getInteger(R.styleable.CustomInputText_modeI, 0);
//        int lengthInput = a.getInteger(R.styleable.CustomInputText_lengthInput, 0);
//        filterArray = new InputFilter[1];
//        filterArray[0] = new InputFilter.LengthFilter(lengthInput);
//        flag = mode;
//        switch (flag) {
        switch (0) {
            case 0:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
//                setWithdraw(filterArray);
                break;
            case 1:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
                setInputType(InputType.TYPE_CLASS_NUMBER);
//                setWithdraw(filterArray);
                break;
            case 2:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
                String dateFormat = String.format("%s/%s/%s", getText().subSequence(0, 4),
                        getText().subSequence(4, 6), getText().subSequence(6, 8));
                setText(dateFormat);
                setInputType(InputType.TYPE_CLASS_NUMBER);
                setClickable(false);
                setCursorVisible(false);
                setFocusable(false);
//                setWithdraw(filterArray);
                break;
            case 3:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
                addTextChangedListener(amountWatcher);
                setInputType(InputType.TYPE_CLASS_NUMBER);
//                setWithdraw(filterArray);
                break;
            case 4:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
//                setWithdraw(filterArray);
                setInputType(
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
            case 5:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
//                setWithdraw(filterArray);
                setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 6:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
//                setWithdraw(filterArray);
                setInputType(InputType.TYPE_CLASS_NUMBER);
                addTextChangedListener(ShebaWatcher);
                break;
            case 7:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
//                setWithdraw(filterArray);
                setInputType(InputType.TYPE_CLASS_NUMBER);
                addTextChangedListener(CardWatcher);
                break;
            case 8:
                setTypeface(Typeface.createFromAsset(context.getAssets(),
                        FONT_IRANSANS_MOBILE_NORMAL_TTF));
//                setWithdraw(filterArray);
                setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
                break;
        }
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager systemService = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        systemService.getDefaultDisplay().getMetrics(displayMetrics);
//        setTextSize(Utility.getScaledFontSize(getTextSize(), displayMetrics));
//        a.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        try {
            Typeface font2 = ResourcesCompat.getFont(getContext(), R.font.iransans);
            setTypeface(font2);
        }catch (Exception ex){

        }
    }
}
