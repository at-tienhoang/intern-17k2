package vn.asiantech.internship.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.fragments.FirstFragment;
import vn.asiantech.internship.ui.fragments.SecondFragment;
import vn.asiantech.internship.models.MyData;


/**
 * PassDataActivity
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/13/2017
 */
public class PassDataActivity extends AppCompatActivity {
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_day7_ex2);

        Button btnPassData = (Button) findViewById(R.id.btnPassData);
        btnPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondFragment.setText(mFirstFragment.getText());
            }
        });

        mFirstFragment = new FirstFragment();
        mSecondFragment = new SecondFragment();
        MyData myData = new MyData(new OnClickListener() {
            @Override
            public void onClick(TextView tv) {
                tv.setText(mFirstFragment.getText());
            }
        });
        Bundle bundle = new Bundle();
        bundle.putSerializable("Data", myData);
        mSecondFragment.setArguments(bundle);
        addFragment(mFirstFragment, R.id.frgContent1, false);
        addFragment(mSecondFragment, R.id.frgContent2, false);
    }

    public void addFragment(Fragment fragment, int idContaint, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(idContaint, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.commit();
    }

    /**
     * This interface used to handle OnclickItem of Fragment
     */
    public interface OnClickListener {
        void onClick(TextView tv);
    }
}
