package com.stxnext.ar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.stxnext.ar.R;
import com.stxnext.ar.util.Preferences;

/**
 * Created by Łukasz Ciupa on 19.01.2016.
 */
public class StartActivity extends Activity {

    public static final int TUTORIAL_REQUEST = 1;

    Button buttonLogo;
    Button buttonDinosaur;
    Button buttonCat;
    Button buttonTutorial;
    Button buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        buttonLogo = (Button) findViewById(R.id.stx_logo);
        buttonDinosaur = (Button) findViewById(R.id.dinosaur);
        buttonCat = (Button) findViewById(R.id.cat);
        buttonTutorial = (Button) findViewById(R.id.tutorial);
        buttonClose = (Button) findViewById(R.id.close);

        buttonLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // We can use also a UnitPlayerActivity. UnityPlayerNativeActivity has got
                // improved input latency.
                Intent intent = new Intent(StartActivity.this, UnityPlayerActivity.class);
                intent.putExtra(UnityPlayerActivity.AR_OBJECT_INTENT_TAG, UnityPlayerActivity.LOGO_REQUEST);
                startActivity(intent);
            }
        });

        buttonDinosaur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, UnityPlayerActivity.class);
                intent.putExtra(UnityPlayerActivity.AR_OBJECT_INTENT_TAG, UnityPlayerActivity.DINOSAUR_REQUEST);
                startActivity(intent);
            }
        });

        buttonCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, UnityPlayerActivity.class);
                intent.putExtra(UnityPlayerActivity.AR_OBJECT_INTENT_TAG, UnityPlayerActivity.CAT_REQUEST);
                startActivity(intent);
            }
        });

        buttonTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(StartActivity.this).setTutorialDone(false);
                runTutorial();
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(Preferences.getInstance(this).isTutorialDone()) {
            Log.d(this.getClass().getName(), "Tutorial is done");
            startUnityPlayer();
        } else {
            Log.d(this.getClass().getName(), "Tutorial is not done");
            runTutorial();
        }

    }

    private void runTutorial() {
        Intent tutorialIntent = new Intent(this, TutorialActivity.class);
        startActivityForResult(tutorialIntent, TUTORIAL_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case TUTORIAL_REQUEST:
                if (resultCode == TutorialActivity.RESULT_OK) {
                    Preferences.getInstance(this).setTutorialDone(true);
                }
                startUnityPlayer();
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startUnityPlayer() {
        startActivity(new Intent(StartActivity.this, UnityPlayerActivity.class));
        finish();
    }
}
