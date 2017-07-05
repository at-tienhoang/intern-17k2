package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDataBase;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.main.NoteActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ducle on 20/06/2017.
 */

public class AddNoteFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CODE_GALLERY = 1;
    private ImageView mImageViewNote;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private ImageView mImgPicPhoto;
    private ImageView mImgSave;
    private Bitmap mBitmapImage;
    private NoteDataBase mNoteDataBase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_add_note, container, false);
        mNoteDataBase = new NoteDataBase(container.getContext());
        initViews(view);
        mImgPicPhoto.setOnClickListener(this);
        mImgSave.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mImageViewNote = (ImageView) view.findViewById(R.id.imgNote);
        mImgPicPhoto = (ImageView) view.findViewById(R.id.imgPicPhoto);
        mImgSave = (ImageView) view.findViewById(R.id.imgSave);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
        mEdtContent = (EditText) view.findViewById(R.id.edtContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPicPhoto:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_GALLERY);
                break;
            case R.id.imgSave:
                Note note = new Note();
                String date = getDate();
                note.setDate(date);
                if (mEdtTitle.getText().toString().equals("") || mEdtContent.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "vui long nhap day du", Toast.LENGTH_LONG).show();
                    break;
                }
                if (mBitmapImage != null) {
                    saveImageToSDCard(mBitmapImage, NoteActivity.FOLDER, getName(date) + ".png");
                    String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + NoteActivity.FOLDER + "/";
                    note.setUrlImage(fullPath + getName(date) + ".png");
                    Log.i("tag11", fullPath + getName(date) + ".png");
                }
                note.setTitle(mEdtTitle.getText().toString());
                note.setContent(mEdtContent.getText().toString());
                try {
                    mNoteDataBase.open();
                    mNoteDataBase.addNote(note);
                    mNoteDataBase.close();
                } catch (IOException e) {
                    Log.d("tag", "ERROR1");
                }
                getActivity().onBackPressed();
                //((OnReplaceFragmentListener) getActivity()).onReplaceFragmentMain();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GALLERY) {
                try {
                    mBitmapImage = getImageNote(data.getData());
                    mImageViewNote.setImageBitmap(mBitmapImage);
                } catch (Exception e) {
                    Log.i("tag111", "ERROR2");
                }
            }
        }
    }

    /**
     * get current time
     *
     * @return current time
     */
    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MMM dd HH:mm:ss", Locale.ENGLISH);
        String[] arr = simpleDateFormat.format(Calendar.getInstance().getTime()).split(" ");
        Log.i("tag11", simpleDateFormat.format(Calendar.getInstance().getTime()));
        return arr[0] + "\n" + arr[1] + " " + arr[2] + "\n" + arr[3];
    }

    /**
     * get name from time that maked note
     *
     * @param date time that maked note
     * @return
     */
    public String getName(String date) {
        return date.replace("\n", "").replace(" ", "").replace(":", "");
    }

    /**
     * save image to FOLDER in SDCard
     *
     * @param image  is image need save
     * @param folder is FOLDER that image saved
     * @param name   is name of image
     * @return
     */
    private static boolean saveImageToSDCard(Bitmap image, String folder, String name) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/";
        Log.i("tag11", name);
        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            OutputStream fOut;
            File file = new File(fullPath, name);
            if (!file.exists()) {
                file.createNewFile();
            }

            fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (IOException e) {
            Log.d("tag1111", "ERROR3");
            return false;
        }
    }

    /**
     * get image with smaller size
     *
     * @param imageFileUri is uri of image
     * @return
     */
    private Bitmap getImageNote(Uri imageFileUri) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int dw = size.x / 2;
        int dh = size.y / 2;
        Log.i("wh", dw + "   " + dh);
        Bitmap bmp;
        try {
            // Load up the image's dimensions not the image itself
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            //bmp = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);
            int heightRatio = (int) Math
                    .ceil(bmpFactoryOptions.outHeight / (float) dh / 10);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                    / (float) dw / 10);
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }
            bmpFactoryOptions.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(getActivity().getContentResolver()
                    .openInputStream(imageFileUri), null, bmpFactoryOptions);
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            Log.i("wh 3", width + "   " + height);
            return Bitmap.createScaledBitmap(bmp, width / 3, height / 3, true);
        } catch (FileNotFoundException e) {
            Log.v("ERROR4", e.toString());
            return null;
        }
    }
}
