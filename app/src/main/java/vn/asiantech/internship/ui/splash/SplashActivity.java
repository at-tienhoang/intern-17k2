package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.MainActivity;
import vn.asiantech.internship.ui.mockito.LoginActivity;

/**
 * Activity for Splash
 * Created by huypham on 15/06/2017.
 */
public class SplashActivity extends AppCompatActivity {

    private Button mBtnEx6;
    private Button mBtnMockito;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initSplashView();
        onClickButton();
    }

    private void initSplashView() {
        mBtnEx6 = (Button) findViewById(R.id.btnEx6);
        mBtnMockito = (Button) findViewById(R.id.btnMockito);
    }

    private void onClickButton() {
        mBtnEx6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEx6 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intentEx6);
            }
        });
        mBtnMockito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMockito = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intentMockito);
            }
        });
    }
}
