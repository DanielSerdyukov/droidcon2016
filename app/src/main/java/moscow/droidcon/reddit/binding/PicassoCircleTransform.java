package moscow.droidcon.reddit.binding;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.squareup.picasso.Transformation;

/**
 * @author Daniel Serdyukov
 */
class PicassoCircleTransform implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        final int size = Math.min(source.getWidth(), source.getHeight());
        final int width = (source.getWidth() - size) / 2;
        final int height = (source.getHeight() - size) / 2;
        final Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        final Paint paint = new Paint();
        final BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (width != 0 || height != 0) {
            final Matrix matrix = new Matrix();
            matrix.setTranslate(-width, -height);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);
        final float radius = size / 2f;
        canvas.drawCircle(radius, radius, radius, paint);
        source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }

}
