package vn.asiantech.internship.ui.questions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestResultFragment extends Fragment {

    private static final String KEY_BOOL = "KEY_BOOL";
    private static final String KEY_INT = "KEY_INT";

    private boolean[] mBooleans;
    private int mRightCorrect;

    public static QuestResultFragment newInstance(boolean[] booleans, int rightCorrect) {
        QuestResultFragment fragment = new QuestResultFragment();
        Bundle args = new Bundle();
        args.putBooleanArray(KEY_BOOL, booleans);
        args.putInt(KEY_INT, rightCorrect);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBooleans = getArguments().getBooleanArray(KEY_BOOL);
            mRightCorrect = getArguments().getInt(KEY_INT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_result, container, false);
        TextView tv = (TextView) v.findViewById(R.id.tv);
        tv.setText(mRightCorrect +"  sss");
        return v;
    }

}
