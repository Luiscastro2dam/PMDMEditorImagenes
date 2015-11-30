package com.example.clase.pmdmpractica2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Clase on 14/11/2015.
 */
public class Colores extends AppCompatActivity {
   // private ImageView ivFotoOriginal;
    private ImageView ivFotoPoner;
    private EditText etRojo,etVerde,etAzul;
    Uri uri;
    String ruta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colores);
       // ivFotoOriginal=(ImageView)findViewById(R.id.imageView1);
        ruta = getIntent().getExtras().getString("foto");
        uri = Uri.parse(ruta);
        ivFotoPoner=(ImageView)findViewById(R.id.imgColores);
        etAzul=(EditText)findViewById(R.id.etAzul);
        etRojo=(EditText)findViewById(R.id.etRojo);
        etVerde=(EditText)findViewById(R.id.etVerde);
        ivFotoPoner.setImageURI(uri);
    }
     public void btVerde(View v){
         BitmapDrawable bmpDraw = (BitmapDrawable) ivFotoPoner.getDrawable();
         Bitmap bitmap = bmpDraw.getBitmap();
         int verde=0;
         verde=Integer.parseInt(etVerde.getText().toString());
         this.colorearVerde(bitmap, verde);
     }
    public void btAzul(View v){
        BitmapDrawable bmpDraw = (BitmapDrawable) ivFotoPoner.getDrawable();
        Bitmap bitmap = bmpDraw.getBitmap();
        int azul=0;
        azul=Integer.parseInt(etAzul.getText().toString());
        this.colorearAzul(bitmap, azul);
    }
    public void colorearAzul(Bitmap bmpOriginal,int azul){
        Bitmap bmp = Bitmap.createBitmap(bmpOriginal.getWidth(),
                bmpOriginal.getHeight(), bmpOriginal.getConfig());
        int pixel, red, green, blue, alpha;
        for (int i = 0; i < bmpOriginal.getWidth(); i++) {
            for (int j = 0; j < bmpOriginal.getHeight(); j++) {
                pixel = bmpOriginal.getPixel(i, j);
                red = Color.red(pixel);
                green = Color.green(pixel);
                alpha = Color.alpha(pixel);

                bmp.setPixel(i, j, Color.argb(alpha,red, green, azul));
            }
        }
        ivFotoPoner.setImageBitmap(bmp);
    }
    public void colorearVerde(Bitmap bmpOriginal,int verde){
        Bitmap bmp = Bitmap.createBitmap(bmpOriginal.getWidth(),
                bmpOriginal.getHeight(), bmpOriginal.getConfig());
        int pixel, red, green, blue, alpha;
        for (int i = 0; i < bmpOriginal.getWidth(); i++) {
            for (int j = 0; j < bmpOriginal.getHeight(); j++) {
                pixel = bmpOriginal.getPixel(i, j);
                red = Color.red(pixel);
                blue = Color.blue(pixel);
                alpha = Color.alpha(pixel);

                bmp.setPixel(i, j, Color.argb(alpha,red, verde, blue));
            }
        }
        ivFotoPoner.setImageBitmap(bmp);
    }
    public void btcolores(View v){
        BitmapDrawable bmpDraw = (BitmapDrawable) ivFotoPoner.getDrawable();
        Bitmap bitmap = bmpDraw.getBitmap();
        int rojo=0;
        rojo=Integer.parseInt(etRojo.getText().toString());
        this.colorearRojo(bitmap, rojo);
    }
    public void colorearRojo(Bitmap bmpOriginal,int rojo){
        Bitmap bmp = Bitmap.createBitmap(bmpOriginal.getWidth(),
                bmpOriginal.getHeight(), bmpOriginal.getConfig());
        int pixel, red, green, blue, alpha;
        for (int i = 0; i < bmpOriginal.getWidth(); i++) {
            for (int j = 0; j < bmpOriginal.getHeight(); j++) {
                pixel = bmpOriginal.getPixel(i, j);
                green = Color.green(pixel);
                blue = Color.blue(pixel);
                alpha = Color.alpha(pixel);
                bmp.setPixel(i, j, Color.argb(alpha,rojo, green, blue));
            }
        }
        ivFotoPoner.setImageBitmap(bmp);
    }

    public void colorear(Bitmap bmpOriginal,int rojo,int verde,int azul){

        Bitmap bmp = Bitmap.createBitmap(bmpOriginal.getWidth(),
                bmpOriginal.getHeight(), bmpOriginal.getConfig());
        int pixel, red, green, blue, alpha;
        for (int i = 0; i < bmpOriginal.getWidth(); i++) {
            for (int j = 0; j < bmpOriginal.getHeight(); j++) {
                pixel = bmpOriginal.getPixel(i, j);
                red = Color.red(pixel);
                green = Color.green(pixel);
                blue = Color.blue(pixel);
                alpha = Color.alpha(pixel);

                    bmp.setPixel(i, j, Color.argb(alpha,rojo, green, blue));

            }
        }
        ivFotoPoner.setImageBitmap(bmp);
    }
    public static Bitmap bitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //------------menu principal--------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.guardar: {

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //-----------------------------------------------------------
}
