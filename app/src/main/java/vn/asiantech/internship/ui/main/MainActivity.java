package vn.asiantech.internship.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.ui.leftmenu.DrawerAdapter;

import static android.graphics.Bitmap.createBitmap;

/**
 * Used to display drawerLayout
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-12
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE_GALLERY = 1;
    private static final int REQUEST_CODE_IMAGE_CAMERA = 2;
    private static final String KEY_DATA = "data";

    private TextView mTvResult;
    private ImageView mImgMenu;
    private RecyclerView mRecyclerViewDrawer;
    private LinearLayout mLlContent;
    private DrawerLayout mDrawerLayout;

    private DrawerAdapter mAdapter;
    private List<DrawerItem> mDrawerItems;
    private int mPositionSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initDrawer();
        initAdapter();
    }

    private void initView() {
        mLlContent = (LinearLayout) findViewById(R.id.llContent);
        mTvResult = (TextView) findViewById(R.id.tvResult);
        mRecyclerViewDrawer = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        mImgMenu = (ImageView) findViewById(R.id.imgMenu);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.toolbar_title);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
    }

    private void initData() {
        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(new DrawerItem(getString(R.string.feed)));
        mDrawerItems.add(new DrawerItem(getString(R.string.activity)));
        mDrawerItems.add(new DrawerItem(getString(R.string.profile)));
        mDrawerItems.add(new DrawerItem(getString(R.string.friends)));
        mDrawerItems.add(new DrawerItem(getString(R.string.map)));
        mDrawerItems.add(new DrawerItem(getString(R.string.chat)));
        mDrawerItems.add(new DrawerItem(getString(R.string.setting)));
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewDrawer.setLayoutManager(layoutManager);
        mAdapter = new DrawerAdapter(this, mDrawerItems, new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mTvResult.setText(mDrawerItems.get(position).getName());
                mDrawerLayout.closeDrawers();
                if (mPositionSelected >= 0) {
                    mDrawerItems.get(mPositionSelected).setChecked(false);
                    mAdapter.notifyItemChanged(mPositionSelected + 1);
                }
                mPositionSelected = position;
                mDrawerItems.get(position).setChecked(true);
                mAdapter.notifyItemChanged(position + 1);
            }

            @Override
            public void onAvatarClick() {
                showChooseImageDialog();
            }
        });
        mRecyclerViewDrawer.setAdapter(mAdapter);
    }

    private void initDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLlContent.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }

        });
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    private void showChooseImageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle(R.string.dialog_title);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_item);
        arrayAdapter.add(getString(R.string.camera));
        arrayAdapter.add(getString(R.string.gallery));

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if (TextUtils.equals(strName, getString(R.string.camera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cropImage(intent);
                    startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    cropImage(intent);
                    startActivityForResult(intent, REQUEST_CODE_IMAGE_GALLERY);
                }
            }
        });
        builderSingle.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_IMAGE_GALLERY || requestCode == REQUEST_CODE_IMAGE_CAMERA) {
                getImage(data);
            }
        }
    }

    private void getImage(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get(KEY_DATA);
        ImageView imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        RoundedBitmapDrawable drawable = createRoundBorder(imageBitmap);
        imgAvatar.setImageDrawable(drawable);
    }

    private void cropImage(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 200);
        intent.putExtra("aspectY", 200);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
    }

    private RoundedBitmapDrawable createRoundBorder(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int borderWidth = 1;
        int radius = Math.min(width, height) / 2;
        int squareWidth = Math.min(width, height);
        int newSquare = Math.min(width, height) + borderWidth;

        Bitmap roundedBitmap = createBitmap(newSquare, newSquare, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        int x = borderWidth + squareWidth - width;
        int y = borderWidth + squareWidth - height;
        canvas.drawBitmap(bitmap, x, y, null);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth * 2);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getWidth() / 2, newSquare / 2, paint);
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), roundedBitmap);
        roundedDrawable.setCornerRadius(radius);
        roundedDrawable.setAntiAlias(true);
        return roundedDrawable;
    }
}
