package uit.nhutvinh.photoapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.concurrent.CopyOnWriteArrayList;

import uit.nhutvinh.model.EffectView;
import uit.nhutvinh.model.RotatePicture;
import uit.nhutvinh.model.TakePicture;

/**
 * Created by Vin Vin on 06/12/2017.
 */

public class EffectActivity extends AppCompatActivity {
    private static final int SELECT_PHOTO = 100;
    boolean enabledGrid = true;
    private float currRotateDegree = 0;

    EffectView imgPic;
    ImageView imgGrid;
    boolean CropClick=false;

    Toolbar toolbar;

    BottomNavigationView bottomNavigationView;

    RotatePicture rotatePicture;
    TakePicture takePicture;
    Uri imageUri;
    Intent CropIntent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.effect_activity);

        addConTrols();
        addEvents();


    }

    private void addEvents() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if(item.getItemId()==R.id.addPic)
                {
                    takePicture();

                }else  if(item.getItemId()==R.id.cropPic)
                {
                    CropImage();

                }else if(item.getItemId()==R.id.gridPic)
                {

                    enabledGrid = !enabledGrid;
                    if(enabledGrid){
                        imgGrid.setVisibility(View.VISIBLE);
                        item.setIcon(R.drawable.ic_grid_off);
                    }
                    else{
                        imgGrid.setVisibility(View.INVISIBLE);
                        item.setIcon(R.drawable.ic_grid_on);
                    }

                    return true;
                }else if(item.getItemId()==R.id.drawPic)
                {
                    drawPicture();
                    return true;
                }else if(item.getItemId()==R.id.rotatePic)
                {

                    currRotateDegree += 90;
                    rotatePicture(currRotateDegree);
                    return true;
                }


                return false;
            }
        });

    }

    private void addConTrols() {
        imgPic = (EffectView) findViewById(R.id.imgPic);
        imgGrid = (ImageView) findViewById(R.id.imgGrid);

        rotatePicture = new RotatePicture(imgPic);
        takePicture = new TakePicture(imgPic);


        // kiem tra co gui uri anh tu mainactivity
        if (getIntent().getData() != null) {
            imageUri = getIntent().getData();
            takePicture.decodeUri(this, imageUri);
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode)
        {
            case SELECT_PHOTO: {
                if (resultCode == RESULT_OK && null != imageReturnedIntent)
                {

                    takePicture.decodeUri(this, imageReturnedIntent.getData());

                    rotatePicture.initCanvas();

                    // Test Draw
//                    if(imgPic.getDrawable()!=null)
//                    {
//                        originalBitmapDrawable = (BitmapDrawable) imgPic.getDrawable();
//                        originalBitmap = originalBitmapDrawable.getBitmap();
//                        originalImageHeight = originalBitmap.getHeight();
//                        originalImageWith = originalBitmap.getWidth();
//                        originalImageConfig = originalBitmap.getConfig();
//                    }
                }

            }
        }
        //Xet dk tra anh ve sau khi crop
        if(requestCode == 2 && resultCode == RESULT_OK && imageReturnedIntent!=null )
        {
                Bundle bundle = imageReturnedIntent.getExtras();
                assert bundle != null;
                Bitmap bitmap = bundle.getParcelable("data");
                imgPic.setImageBitmap(bitmap);
        }

    }

    // lay anh tu bo suu tap
    public void takePicture() {
        imgPic.setEnableDraw(false);
        imgPic.setEnableZoomDrag(true);

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public void rotatePicture(float rotateDegree) {

        imgPic.setEnableDraw(false);
        imgPic.setEnableZoomDrag(true);
        if(imgPic.getOriginalBitmap()!=null)
        rotatePicture.rotateImage(rotateDegree);

    }

    public  void drawPicture(){
        imgPic.setEnableDraw(true);
        imgPic.setEnableZoomDrag(false);

    }

    public void CropImage() {

        Intent CropIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //CropIntent = new Intent("com.android.camera.action.CROP");


        CropIntent.putExtra("crop", "true");
        CropIntent.putExtra("aspectX", 1);
        CropIntent.putExtra("aspectY", 1);
        CropIntent.putExtra("outputX", 200);
        CropIntent.putExtra("outputY", 200);
        CropIntent.putExtra("return-data", true);
        startActivityForResult(CropIntent, 2);
    }


}
