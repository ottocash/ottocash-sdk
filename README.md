# ottocash-sdk

[![](https://jitpack.io/v/ottocash/ottocash-sdk.svg)](https://jitpack.io/#ottocash/ottocash-sdk)

Ottocash-SDK


## Installation guide Version PROD:

**1. Add the JitPack repository to your build file**
```allprojects
{
    repositories {
      ...
   	  maven { url 'https://jitpack.io' }
    }
}
```


**2. Add the dependency**
```
dependencies {
		 implementation 'com.github.acan12:coconut:2.3.0-rc.4'
		 implementation 'com.github.ottocash:ottocash-sdk:1.0.83'

	}
```


**3. Add Application project class extend BaseApp**
```java
    public class App extends BaseApp {
        private static Context context;

            @Override
            public void onCreate() {
                super.onCreate();
                context = getApplicationContext();
                setupBuilder(DaggerAppComponent.builder(), this);
                setupDefaultFont("fonts/Barlow-Regular.ttf");

            }

            public static AppComponent getAppComponent() {
                if (context == null) return null;
                return getComponent();
            }

        ...
    }
```


**4. Add Application class into `AndroidManifest.java`**
```java
   ...
   <application
           android:name=".<AppClassName>"
           android:allowBackup="true"
           android:icon="@mipmap/ic_launcher"
           android:label="@string/app_name"
           android:supportsRtl="true"
           android:theme="@style/AppTheme"
           tools:replace="android:name">
  ...

```


**5.  Add MainActivity project extend SdkActivity**
```java

   public class MainActivity extends SdkActivity {

       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);
           ButterKnife.bind(this);

       }
    ...
   }


```


**6.  Call this function for goto OttoCash Dashboard**
```java
   ...
   onCallOttoCashDashboard(context);
   //OttoCash.onCallOttoCashDashboard(context);
   ...

```

**7.  Call this function for goto TopUp Screen**
```java
   ...
   OttoCash.goTopUpOttoCash(this);
   ...

```


**8.  Call this function for goto OttoCash Payment**
```java
   ...
   //For accessing ottocash payment, application need to call OttoCash.onCallPayment() and follow the function arguments.

   OttoCash.onCallPayment(context, String phoneNumber, int amount, int fee, String productName,
                          String billerId, String productCode, String partnerCode);



   //After success payment, Ottocash sdk will return a PaymentData. Catch the data by handling the onActivityResult(), code example shown below:
   @Override
       protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           if (resultCode == RESULT_OK && requestCode == OttoCash.REQ_OTTOCASH_PAYMENT) {

               if (data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA) != null) {
                   PaymentData paymentData = data.getParcelableExtra(OttoCash.OTTOCASH_PAYMENT_DATA);

                   Toast.makeText(this, paymentData.getReferenceNumber(), Toast.LENGTH_LONG).show();
               }
           }
       }
   ...

```

**9.  Call this function for Get Balance OttoCash**
```java
   ...
   String balanceOttoCash = OttoCash.getBalance(this);
   ...

```


**10.  Call this function for LogOut**
```java
   ...
   OttoCash.onLogoutOttoCash(this);
   ...

```






