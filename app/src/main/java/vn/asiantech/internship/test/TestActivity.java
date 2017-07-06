package vn.asiantech.internship.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display  viewPager that contain question fragments.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
public class TestActivity extends AppCompatActivity {
    private static final String TAG = "error";
    private TextView mTvPrev;
    private TextView mTvNext;
    private TextView mTvQuestionTitle;
    private ViewPager mQuestionViewPager;
    private QuestionViewPagerAdapter mAdapter;
    private List<Test> mTestsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTvQuestionTitle = (TextView) findViewById(R.id.tvQuestionTitle);
        mTvPrev = (TextView) findViewById(R.id.tvPrev);
        mTvNext = (TextView) findViewById(R.id.tvNext);
        mQuestionViewPager = (ViewPager) findViewById(R.id.testViewPager);
        List<Test> tests = new ArrayList<>();
        tests.addAll(getTests());
        Collections.shuffle(tests);
        mTestsList = new ArrayList<>();
        for (int i = 0; i < tests.size() / 2; i++) {
            mTestsList.add(tests.get(i));
        }
        mAdapter = new QuestionViewPagerAdapter(getSupportFragmentManager(), mTestsList);
        mQuestionViewPager.setAdapter(mAdapter);

        mQuestionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateButton(position);
                mTvQuestionTitle.setText(getString(R.string.textView_question) + " " + (position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTvPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionViewPager.setCurrentItem(mQuestionViewPager.getCurrentItem() - 1, true);
            }
        });

        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.equals(mTvNext.getText().toString(), "Result")) {
                    showDialog();
                } else {
                    mQuestionViewPager.setCurrentItem(mQuestionViewPager.getCurrentItem() + 1, true);
                }
            }
        });
    }

    private void updateButton(int position) {
        mTvPrev.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        mTvNext.setText(position == mAdapter.getCount() - 1 ? "Result" : "Next");
    }

    private void showDialog() {
        android.app.FragmentManager fm = this.getFragmentManager();
        ResultDialog resultDialog = ResultDialog.newInstance(mTestsList);
        resultDialog.show(fm, null);
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream inputStream = getAssets().open("question.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
        return json;
    }

    private List<Test> getTests() {
        List<Test> questions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("test");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = (JSONObject) jsonArray.get(i);
                List<String> answers = new ArrayList<>();
                answers.add(jb.getString("answer_a"));
                answers.add(jb.getString("answer_b"));
                answers.add(jb.getString("answer_c"));
                answers.add(jb.getString("answer_d"));
                Collections.shuffle(answers);
                questions.add(new Test(jb.getString("question"), answers.get(0), answers.get(1),
                        answers.get(2), answers.get(3), jb.getString("answer_right")));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        return questions;
    }
}
