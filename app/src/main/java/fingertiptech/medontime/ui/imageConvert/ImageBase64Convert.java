package fingertiptech.medontime.ui.imageConvert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ImageBase64Convert {

    public ImageBase64Convert(){

    }

    /**
     * In database we store image as base64, this function is covert base 64 string
     * to Bitmap to show imageView
     * @param b64 is the image base 64 string
     * @return
     */
    public static Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    /**
     * This is we convert our image to code base 64 to store in database
     * @param bitmap ImageView
     * @return
     */
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
