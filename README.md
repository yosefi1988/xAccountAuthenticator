# a library from persia 💖
creator sajjad yosefi 09123678522

1-
```project level
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

2-app level

```
implementation 'com.github.yosefi1988:xAccountAuthenticator:1.3.0'
```

3-
```
            <Button
                android:id="@+id/btn"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="8"
                android:onClick="onClick"
                android:text="btn"  />
```

4-
```
    public static final String ACCOUNT_TYPE = "ir.sajjadyosefi.android";
    private static int LOGIN_REQUEST_CODE = 101 ;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Bundle bundle = new Bundle();
                Intent intent = SignInActivity.getIntent(getContext(),bundle);
                intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, AccountGeneral.ACCOUNT_TYPE);
                intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
                intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
                //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
                bundle.putParcelable(AccountManager.KEY_INTENT, intent);
                this.startActivityForResult(intent, LOGIN_REQUEST_CODE);
                break;
        }
    }
```

5-
```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                    if (data.hasExtra(PARAM_USER)){
                        Gson gson = new Gson();
                        User user = gson.fromJson(data.getExtras().getString(PARAM_USER),User.class);
                        if(savedToDataBase(user)){
                            if (Global.user != null && Global.user.isAdmin()) {
                                firstFragmentsAdapter.notifyList();
                                updatedrawableMenuItems();
                            }
                        }
                        Toast.makeText(getContext(),getContext().getString(R.string.welcome) ,Toast.LENGTH_LONG).show();
                    }
            }
        }
   }
```


6- User Model:
```
private long userId;
	private String userName;
	private String email;
	private String mobileNumber;
	private String userImage;
	private String profileImage;
	public long balanse;
```
_________________________________________________________________________________

--payment
1- pay method 1
    //payment = charge by method 
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        bundle.putInt("amount", 1000);
        bundle.putString("metaData", "sajjad 1000");
        Intent intent = PaymentActivity.getIntent(this, bundle); 
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        startActivityForResult(intent, WALLETCHARGE_REQUEST_CODE);

2- pay method 2
        Bundle bundle = new Bundle();
        bundle.putInt("type" , 1);
        Intent intent = SignInActi
        intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        startActivityForResult(intent, 1000);


3- get pay result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent x;
        if (requestCode == 661) {
            if (PaymentActivity.isPaymentSuccess()) {
                x = PaymentActivity.getPaymentIntent();

                Toast.makeText(context,"pay success" ,Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(context,"pay not ok" ,Toast.LENGTH_LONG).show();
            }

            PaymentActivity.PaymentDone();
        }
    }