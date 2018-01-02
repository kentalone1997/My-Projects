package uit.nhutvinh.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

import static uit.nhutvinh.photoapp.R.id.imgPic;

/**
 * Created by Vin Vin on 25/10/2017.
 */

public class TakePicture {
    private EffectView imgPic;

    public TakePicture(EffectView imgPic) {
        this.imgPic = imgPic;
    }

    public TakePicture() {
    }

    public EffectView getImgPic() {
        return imgPic;
    }

    public void setImgPic(EffectView imgPic) {
        this.imgPic = imgPic;
    }

    // Lấy file ảnh và scale ảnh phù hợp
    public void decodeUri(Context context, Uri uri) {
        ParcelFileDescriptor parcelFD = null;
        try {
            // Get Bitmap
            parcelFD = context.getContentResolver().openFileDescriptor(uri, "r");
            assert parcelFD != null;
            FileDescriptor imageSource = parcelFD.getFileDescriptor();

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(imageSource, null, o);

            // the new size we want to scale to
            final int REQUIRED_SIZE = 4096;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);
            imgPic.setOriginalBitmap(bitmap);

        // chinh lai image center view\
//        Drawable image = imgPic.getDrawable();
//        RectF rectfView = new RectF(0,0,imgPic.getWidth(),imgPic.getHeight());
//        RectF rectfImage = new RectF(0,0,image.getIntrinsicWidth(),image.getIntrinsicHeight());
//        Matrix matrix = new Matrix();
//        matrix.setRectToRect(rectfImage, rectfView, Matrix.ScaleToFit.CENTER);
//        imgPic.setImageMatrix(matrix);



        } catch (FileNotFoundException e) {
            Toast.makeText(context, "File ảnh ko khả dụng !, Vui lòng thủ lại", Toast.LENGTH_LONG).show();
        } finally {
            if (parcelFD != null)
                try {
                    parcelFD.close();
                } catch (IOException e) {
                    Toast.makeText(context, "File ảnh ko khả dụng !, Vui lòng thủ lại", Toast.LENGTH_LONG).show();
                    // ignored
                }
        }
    }
}
