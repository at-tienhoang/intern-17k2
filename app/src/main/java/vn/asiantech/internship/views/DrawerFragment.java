package vn.asiantech.internship.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.MainActivity;
import vn.asiantech.internship.R;
import vn.asiantech.internship.DrawerAdapter;
import vn.asiantech.internship.RecyclerItemClickListener;

/**
 * DrawerFragment create on 12/06 by Thien Nguyen
 */
public class DrawerFragment extends Fragment {

    private String[] mListDrawer;
    private RecyclerView mRecyclerView;
    private DrawerAdapter mDrawerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rvDrawer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListDrawer = getResources().getStringArray(R.array.listDrawer);
        mDrawerAdapter = new DrawerAdapter(getContext(), mListDrawer);
        mRecyclerView.setAdapter(mDrawerAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onItemMenuSelect(position);
                mDrawerAdapter.setPositionSelected(position);
                MainActivity.closeDrawer();
            }
        }));

        return v;
    }

    private void onItemMenuSelect(int position) {
        MainActivity.setMainText(getResources().getStringArray(R.array.listDrawer)[position]);
    }
}
