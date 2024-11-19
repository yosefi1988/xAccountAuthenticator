package ir.sajjadyosefi.android.xTubeless.activity.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

//import com.orm.SugarContext;
import com.readystatesoftware.systembartint.SystemBarTintManager;
//import com.vansuita.gaussianblur.GaussianBlur;


import ir.sajjadyosefi.android.xTubeless.Global;
import ir.sajjadyosefi.android.xTubeless.activity.TubelessActivity;
import ir.sajjadyosefi.android.xTubeless.activity.TubelessTransparentStatusBarActivity;
import ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException;
import ir.sajjadyosefi.android.xTubeless.classes.model.network.request.ContactUsRequest;
import ir.sajjadyosefi.android.xTubeless.classes.model.network.response.ServerResponseBase;
import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.networkLayout.retrofit.TubelessRetrofitCallbackss;
import ir.sajjadyosefi.android.xTubeless.utility.xUtility.Validation;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import retrofit2.Call;

import static ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException.TUBELESS_CHECK_INPUT_VALUES;
import static ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException.TUBELESS_CONTENT_IS_COPIED;
import static ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException.TUBELESS_OPERATION_COMPLETE;
import static ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException.TUBELESS_OPERATION_NOT_COMPLETE;
import static ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException.TUBELESS_TRY_AGAIN;


public class ContactUsActivity extends TubelessTransparentStatusBarActivity {

    //Bundle String list
    public static final String Type = "TYPE";
    public static final String Title = "Title";
    public static final String Text = "Text";

    //Create New Activity List
    public static final int SUGGESTION              = 1;
    public static final int ORDER_APP               = 2;
    public static final int CONTACT_US              = 3;
    public static int messageType = 0;


    Context context;
    RadioButton radioButton1,radioButton2,radioButton3;

    EditText editTextTitle , editTextText , editTextPhone;
    Button buttonReg , buttonBack;
    ContactUsRequest message = new ContactUsRequest();


    public synchronized static Intent getIntent(Context context) {
        return getIntent(context,new Bundle());
    }

    public synchronized static Intent getIntent(Context context, Bundle bundle) {
        bundle.putString("item1","value1");
        Intent intent = new Intent(context, ContactUsActivity.class);
        intent.putExtras(bundle);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init
        context = this;
        setContentView(R.layout._activity_contact_us);


        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextText = (EditText) findViewById(R.id.editTextText);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        buttonReg = (Button) findViewById(R.id.buttonReg);
        buttonBack = (Button) findViewById(R.id.buttonBack);
        if(Global.user != null){
            editTextPhone.setVisibility(View.GONE);
//            etField1.setText(Global.user.getMobileNumber());
//            etField1.setEnabled(false);
        }else {
            editTextPhone.setVisibility(View.VISIBLE);
        }



        //getType
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (intent.hasExtra((Type))) {
            switch (bundle.getInt(Type)) {
                case CONTACT_US: {
                    messageType = CONTACT_US;
                    radioButton1.setChecked(false);
                    radioButton2.setChecked(false);
                    radioButton3.setChecked(true);
                    break;
                }
                case ORDER_APP: {
                    messageType = ORDER_APP;
                    radioButton1.setChecked(false);
                    radioButton2.setChecked(true);
                    radioButton3.setChecked(false);
                    break;
                }
                case SUGGESTION: {
                    messageType = SUGGESTION;
                    radioButton1.setChecked(true);
                    radioButton2.setChecked(false);
                    radioButton3.setChecked(false);
                    break;
                }
            }
        }

        //get Checked Radio
        ((RadioGroup)findViewById(R.id.rgRadios)).setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        switch (checkedId) {
//                            case R.id.radioButton1:
//                                radioButton1.setChecked(true);
//                                radioButton2.setChecked(false);
//                                radioButton3.setChecked(false);
//                                messageType = SUGGESTION;
//                                break;
//                            case R.id.radioButton2:
//                                radioButton1.setChecked(false);
//                                radioButton2.setChecked(true);
//                                radioButton3.setChecked(false);
//                                messageType = ORDER_APP;
//                                break;
//                            case R.id.radioButton3:
//                                radioButton1.setChecked(false);
//                                radioButton2.setChecked(false);
//                                radioButton3.setChecked(true);
//                                messageType = CONTACT_US;
//                                break;
//
//
//                        }
                    }
                });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if (Global.user == null){
                    if (Validation.validatePhoneNumber(editTextPhone.getText().toString())){
                        message.setPhoneNumber(editTextPhone.getText().toString());
                    }else {
                        isValid = false;
                    }
                } else {
                    if (Validation.validatePhoneNumber(Global.user.getUserName())){
                        message.setPhoneNumber(Global.user.getUserName());
                    }else {
                        message.setPhoneNumber("");
                    }
                }


                if (intent.hasExtra((Title))) {
                    if (bundle.getString(Title).contains("محتوی نامناسب")){


                    }else {
                        if (editTextTitle.getText().toString().length() < 5) {
                            isValid = false;
                        }
                    }
                }else {
                    if (editTextTitle.getText().toString().length() < 5) {
                        isValid = false;

                    }
                }

                if (editTextText.getText().toString().length() < 15) {
                    isValid = false;

                }


                if (isValid) {

                    switch (messageType) {
                        case CONTACT_US: {
                            message.setTitle(radioButton3.getText().toString());
                            break;
                        }
                        case ORDER_APP: {
                            message.setTitle(radioButton2.getText().toString());
                            break;
                        }
                        case SUGGESTION: {
                            message.setTitle(radioButton1.getText().toString());
                            break;
                        }
                    }
                    message.setTitle(message.getTitle() + " " + editTextTitle.getText().toString());

                    if (Global.user == null)
                        message.setSenderUserID(20053);
                    else
                        message.setSenderUserID(Global.user.getUserId());

                    message.setReciverUserID(49);

                    if (message.getText() != null && message.getText().toString().contains(editTextText.getText().toString())){
                        new TubelessException().handleServerMessage(context,new TubelessException(TUBELESS_CONTENT_IS_COPIED));
                    }else {
                        message.setText(editTextText.getText().toString() + "\n" + message.getPhoneNumber());
                        sendMessage(message);
                    }
                }else {
                    new TubelessException().handleServerMessage(context,new TubelessException(TUBELESS_CHECK_INPUT_VALUES));
                }
            }
        });


        if (intent.hasExtra((Title))) {
            if (bundle.getString(Title).contains("محتوی نامناسب")){
                editTextTitle.setText(bundle.getString(Title));
                editTextTitle.setEnabled(false);
                radioButton1.setEnabled(false);
                radioButton2.setEnabled(false);
            }else {
                editTextTitle.setEnabled(true);
            }
        }
    }

    private void sendMessage(ContactUsRequest request) {

        Global.apiManagerTubeless.newContactUs(request, new TubelessRetrofitCallbackss(getContext(), ServerResponseBase.class) {
            @Override
            public void t_beforeSendRequest() {
                ((TubelessActivity)getContext()).progressDialog.show();
            }

            @Override
            public void t_afterGetResponse() {
                ((TubelessActivity)getContext()).progressDialog.hide();
            }

            @Override
            public void t_complite() {
            }

            @Override
            public void t_responseNull() {
                request.setText("");
                new TubelessException().handleServerMessage(getContext(),new TubelessException(TUBELESS_OPERATION_NOT_COMPLETE));
            }

            @Override
            public void t_retry(Call<Object> call) {
                request.setText("");
                new TubelessException().handleServerMessage(getContext(),new TubelessException(TUBELESS_TRY_AGAIN));
            }

            @Override
            public void t_onSuccess(Object response) {
                editTextText.setText("");
                new TubelessException().handleServerMessage(getContext(),new TubelessException(TUBELESS_OPERATION_COMPLETE));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                svScroll.fullScroll(ScrollView.FOCUS_UP);
            }
        },200);
    }

    @Override
    public SystemBarTintManager getSystemBarTint() {
        return null;
    }

    @Override
    public boolean hasTranslucentNavigation() {
        return false;
    }

    @Override
    public boolean hasTranslucentStatusBar() {
        return false;
    }

    @Override
    public BottomNavigation getBottomNavigation() {
        return null;
    }


    @Override
    public boolean hasAppBarLayout() {
        return false;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

}
