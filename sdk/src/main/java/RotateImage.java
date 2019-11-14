//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.view.View;
//
//public  class RotateImage extends Activity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(new SampleView(this));
//    }
//
//
//    private static class SampleView extends View {
//
//        // CONSTRUCTOR
//        public SampleView(Context context) {
//            super(context);
//            setFocusable(true);
//
//        }
//        @Override
//        protected void onDraw(Canvas canvas) {
//            Paint paint = new Paint();
//
//            canvas.drawColor(Color.YELLOW);
//
//            // Bitmap b = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
//
//            //  you need to insert a image flower_blue into res/drawable folder
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.flower_blue);
//            Matrix mat = new Matrix();
//            mat.postRotate(90);
//            Bitmap bmpRotate = Bitmap.createBitmap(bmp, 0, 0,
//                    bmp.getWidth(), bmp.getHeight(),
//                    mat, true);
//            int h = bmp.getHeight();
//
//            canvas.drawBitmap(bmp, 10,10, paint);
//            canvas.drawBitmap(bmpRotate, 10,10 + h + 10, paint);
//
//        }
//
//    }
//}
