
package uit.nhutvinh.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;


/**
 * Created by Vin Vin on 24/12/2017.
 */

public class RotatePicture {

    private EffectView imgPic;
    private BitmapDrawable originalBitmapDrawable;
    private Bitmap originalBitmap;
    private int originalImageWith;
    private int originalImageHeight;
    private Bitmap.Config originalImageConfig;

    public RotatePicture() {
    }

    public RotatePicture(EffectView imgPic) {
       setImgPic(imgPic);
    }

    public EffectView getImgPic() {
        return imgPic;
    }

    public void setImgPic(EffectView imgPic) {
        this.imgPic = imgPic;
        initCanvas();
    }

    public Bitmap getOriginalBitmap() {
        return originalBitmap;
    }

    public void setOriginalBitmap(Bitmap originalBitmap) {
        this.originalBitmap = originalBitmap;
        initCanvas();
    }

    public void initCanvas() {
        if (imgPic.getDrawable() != null) {
            originalBitmapDrawable = (BitmapDrawable) imgPic.getDrawable();
            originalBitmap = originalBitmapDrawable.getBitmap();
            originalImageHeight = originalBitmap.getHeight();
            originalImageWith = originalBitmap.getWidth();
            originalImageConfig = originalBitmap.getConfig();
        }
    }


    public Bitmap rotatePicture(float rotateDegree) {

        // Create a bitmap which has same width and height value of original bitmap.
        Bitmap rotateBitmap = Bitmap.createBitmap(originalImageWith, originalImageHeight, originalImageConfig);

        Canvas rotateCanvas = new Canvas(rotateBitmap);

        Matrix rotateMatrix = new Matrix();

        // Rotate around the center point of the original image.
        rotateMatrix.setRotate(rotateDegree, originalBitmap.getWidth() / 2, originalBitmap.getHeight() / 2);

        Paint paint = new Paint();
        rotateCanvas.drawBitmap(originalBitmap, rotateMatrix, paint);

        return rotateBitmap;
    }
}
