package tigerstyle.social.com.banggiaxe.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

/**
 * Created by billymobile on 12/8/16.
 */

public class EffectsUtil {
    public static Bitmap applySnowFallingEffect(Bitmap source) {
        // get source image size
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        // create random object
        Random random = new Random();

        int R, G, B, index = 0, thresHold = 50;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // get RGB colors
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                // generate threshold
                thresHold = random.nextInt(255);
                if(R > thresHold && G > thresHold && B > thresHold) {
                    pixels[index] = Color.rgb(255, 255,255);
                }
            }
        }
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }
}
