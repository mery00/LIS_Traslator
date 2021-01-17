package com.github.florent37.materialviewpager.sample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.florent37.materialviewpager.sample.fragment.RecyclerViewFragment;
import com.github.florent37.materialviewpager.sample.fragment.TranslationFragment;
import com.github.florent37.materialviewpager.sample.fragment.ViewChatFragment;
import com.github.florent37.materialviewpager.sample.fragment.ViewSelfLearningFragment;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import android.speech.RecognitionListener;


public class MainActivity extends AppCompatActivity{

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    private FlowingDrawer mDrawer;
    TextView textViewTranslation;

    //  private ActionBarDrawerToggle mDrawerToggle;


    public final static String MODULE_MAC = "00:13:EF:00:54:49";
    public final static int REQUEST_ENABLE_BT = 1;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    public BluetoothAdapter bta;
    public BluetoothSocket mmSocket;
    public BluetoothDevice mmDevice;
    public ConnectedThread btt = null;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private boolean mIslistening;
    // Button switchLight, switchRelay;
    String response;
    Fragment translationInstance;
    Fragment viewChatInstance;
    Fragment selfLearningInstance;
    // boolean lightflag = false;
    boolean relayFlag = true;
    public Handler mHandler;


    PyObject pyobj= null;
    PyObject pySelfLearningResult= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mix);
        setTitle("");
        response="";
        ButterKnife.bind(this);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        setupToolbar();
        setupMenu();
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        translationInstance = TranslationFragment.newInstance();
                        return translationInstance;
                    case 1:
                        viewChatInstance = ViewChatFragment.newInstance();
                        return viewChatInstance;
                    case 2:
                        return RecyclerViewFragment.newInstance();
                    case 3:
                        selfLearningInstance = ViewSelfLearningFragment.newInstance();
                        return selfLearningInstance;
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "Aiutami a tradurre";
                    case 1:
                        return "Cosa ci siamo detti?";
                    case 2:
                        return "Professionnel";
                    case 3:
                        return "Impara nuovi segni!";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.firstColor,
                                "https://nitratine.net/posts/my-desktop-backgrounds/hills-material-design-wallpaper.png");
                    //"http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.secondColor,
                                "https://1.bp.blogspot.com/-supV3PZd5J4/VTVdcDUtBdI/AAAAAAAAQVc/k2aiNhrdqmg/s1600/San%2BFrancisco%2B(Night).png");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.bluePrussia,
                                "https://cdn.wallpapersafari.com/45/51/CF1kQy.jpg");
                    //"http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.lastColor,
                                "https://i.pinimg.com/originals/d2/b8/fc/d2b8fc669367ac5eac78f9f2d4186913.png");
                    //"http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        final View logo = findViewById(R.id.logo_white);


        if (logo != null) {

            logo.setOnClickListener(new DoubleClickListener() {
                @Override
                public void onDoubleClick() {
                    mViewPager.notifyHeaderChanged();
                    boolean connesso = mmSocket.isConnected();
                    Toast.makeText(MainActivity.this, "Voglio mandare informazioni ad arduino, la connessione è "+ connesso, Toast.LENGTH_LONG).show();
                    if (connesso && btt != null) { //if we have connection to the bluetoothmodule
                        String sendtxt = "Testo da mandare ad arduino.";
                        btt.write(sendtxt.getBytes());
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Problemi collegamento bluetooth", Toast.LENGTH_LONG).show();
                    }


                }
                //*/
            });
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    int[] array = new int[] {28,100,67,66,40,0,0,1,1,0,59,224,104,25,96,64,63,31,0,0,1,1,0,58,225,102,39,96,66,65,25,0,0,0,0,0,70,219,133,0,19,6,9,0,0,0,0,0,0,27,136,58,0,3,0,0,0,0,0,0,0,0,30,134,4,31,44,48,32,6,0,0,0,0,0,-57,87,113,174,180,180,180,172,0,0,0,0,0,67,214,101,48,4,0,1,0,0,0,0,0,0,195,35,306,142,160,174,168,126,0,0,0,0,0,74,248,118,75,38,16,19,10,0,0,0,0,0,177,93,273};
                    PyObject obj = pyobj.callAttr("conv1D_model",array);
                    ((TranslationFragment)translationInstance).setTextViewTranslation(obj.toString());
                }
            });
        }
        bta = BluetoothAdapter.getDefaultAdapter();
        //if bluetooth is not enabled then create Intent for user to turn it on
        if(!bta.isEnabled()){
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
        }else{
            initiateBluetoothProcess();
        }

        if(!Python.isStarted()) {
            Python.start(new AndroidPlatform((this)));
        }
        Python py = Python.getInstance();
        //pyobj = py.getModule("rf_tesi");

        pyobj = py.getModule("sign_detection_CNN1D");
        pySelfLearningResult = py.getModule("self_learning_update");
        //     Toast.makeText(MainActivity.this, obj.toString(), Toast.LENGTH_LONG).show();

        //((TranslationFragment)translationInstance).setTextViewTranslation(obj.toString());

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());


        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);
    }

    public void sendBluetoothInformation(String text){


        boolean connesso = mmSocket.isConnected();
        if (connesso && btt != null) { //if we have connection to the bluetoothmodule
            String sendtxt = "Testo dal tasto inizia traduzione.";
            Toast.makeText(MainActivity.this, "Testo inviato: ("+text+")", Toast.LENGTH_LONG).show();
            btt.write(sendtxt.getBytes());
        }
        else {
            Toast.makeText(MainActivity.this, "Problemi collegamento bluetooth", Toast.LENGTH_LONG).show();
        }



/*

        boolean connesso = mmSocket.isConnected();
        if (connesso && btt != null) { //if we have connection to the bluetoothmodule
            //String sendtxt = text;
            Toast.makeText(MainActivity.this, "Testo inviato: ("+text+")", Toast.LENGTH_LONG).show();
            btt.write(text.getBytes());
        }
        else {
            Toast.makeText(MainActivity.this, "Problemi collegamento bluetooth", Toast.LENGTH_LONG).show();
        }


 */
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUEST_ENABLE_BT){
            initiateBluetoothProcess();
        }
    }


    public void initiateBluetoothProcess(){
        if(bta.isEnabled()){
            //attempt to connect to bluetooth module
            BluetoothSocket tmp = null;
            mmDevice = bta.getRemoteDevice(MODULE_MAC);
            //create socket
            try {
                tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
                mmSocket = tmp;
                mmSocket.connect();
                Toast.makeText(MainActivity.this, "Connected to: "+mmDevice.getName(), Toast.LENGTH_LONG).show();
                Log.i("[BLUETOOTH]","Connected to: "+mmDevice.getName());
            }catch(IOException e){
                try{mmSocket.close();}catch(IOException c){return;}
            }

            Log.i("[BLUETOOTH]", "Creating handler");
            mHandler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    //super.handleMessage(msg);
                    if(msg.what == ConnectedThread.RESPONSE_MESSAGE){
                        String txt = (String)msg.obj;
                        ((TranslationFragment)translationInstance).setTextViewTranslation(txt);


                    }
                }
            };

            Log.i("[BLUETOOTH]", "Creating and running Thread");
            btt = new ConnectedThread(mmSocket,mHandler);
            btt.start();
        }
    }




    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable hamburger_button = getResources().getDrawable(R.drawable.cloud_icon_wind);
        toolbar.setNavigationIcon(scaleImage(hamburger_button,(float)0.075));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });
    }

    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuListFragment mMenuFragment = (MenuListFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }

    public Drawable scaleImage (Drawable image, float scaleFactor) {
        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return image;
        }
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        int sizeX = Math.round(image.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(image.getIntrinsicHeight() * scaleFactor);
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);
        image = new BitmapDrawable(getResources(), bitmapResized);
        return image;
    }



    public void stopSelfLearning(){

    }
    public void startSelfLearning(){
        int[] array = new int[] {0,0,0,66,40,0,0,1,1,0,59,224,104,25,96,64,63,31,0,0,1,1,0,58,225,102,39,96,66,65,25,0,0,0,0,0,70,219,133,0,19,6,9,0,0,0,0,0,0,27,136,58,0,3,0,0,0,0,0,0,0,0,30,134,4,31,44,48,32,6,0,0,0,0,0,-57,87,113,174,180,180,180,172,0,0,0,0,0,67,214,101,48,4,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,79977979};
        int[] array2 = new int[] {4,5,6,66,40,0,0,1,1,0,59,224,104,25,96,64,63,31,0,0,1,1,0,58,225,102,39,96,66,65,25,0,0,0,0,0,70,219,133,0,19,6,9,0,0,0,0,0,0,27,136,58,0,3,0,0,0,0,0,0,0,0,30,134,4,31,44,48,32,6,0,0,0,0,0,-57,87,113,174,180,180,180,172,0,0,0,0,0,67,214,101,48,4,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,79977979};

        int[][] matrix = new int[][] {array,array2};
        String segno = "Clementino";
        PyObject self_learning_result = pySelfLearningResult.callAttr("model_self_learning",matrix,segno);
        //int[] array = new int[] {28,100,67,66,40,0,0,1,1,0,59,224,104,25,96,64,63,31,0,0,1,1,0,58,225,102,39,96,66,65,25,0,0,0,0,0,70,219,133,0,19,6,9,0,0,0,0,0,0,27,136,58,0,3,0,0,0,0,0,0,0,0,30,134,4,31,44,48,32,6,0,0,0,0,0,-57,87,113,174,180,180,180,172,0,0,0,0,0,67,214,101,48,4,0,1,0,0,0,0,0,0,195,35,306,142,160,174,168,126,0,0,0,0,0,74,248,118,75,38,16,19,10,0,0,0,0,0,177,93,273};
        //((TranslationFragment)translationInstance).setTextViewTranslation(self_learning_result.toString());
        Toast.makeText(MainActivity.this, "Autoapprendimento completato, inserito: "+self_learning_result.toString(), Toast.LENGTH_LONG).show();

    }







    protected class SpeechRecognitionListener implements RecognitionListener
    {

        @Override
        public void onBeginningOfSpeech()
        {
            //Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer)
        {

        }

        @Override
        public void onEndOfSpeech()
        {
            //Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error)
        {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

            //Log.d(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {

        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {

        }

        @Override
        public void onReadyForSpeech(Bundle params)
        {
            //Log.d(TAG, "onReadyForSpeech"); //$NON-NLS-1$
        }

        @Override
        public void onResults(Bundle results)
        {
            //Log.d(TAG, "onResults"); //$NON-NLS-1$
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            // matches are the return values of speech recognition engine
            // Use these values for whatever you wish to do
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
        }
    }




}
