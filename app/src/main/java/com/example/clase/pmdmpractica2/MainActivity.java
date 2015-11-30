package com.example.clase.pmdmpractica2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView ivFoto;
    ZoomControls zoomControls;
    String ruta;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivFoto = (ImageView)findViewById(R.id.imageView1);
        this.zoonMasMenos();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode ==
                RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
             uri = data.getData();
             ruta = uri.toString();
            if (uri != null) {
                ivFoto.setImageURI(uri);
            }
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
          String fileName="imagen";
                //String sd= Environment.getExternalStorageDirectory().getAbsolutePath();
                String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                File folder = new File(sd +"/fotografias");
                if(!folder.exists()){ //si la carpeta no existe la creamos
                    folder.mkdirs();
                }
                File image = new File(folder,fileName);
                FileOutputStream outStream;

                try {
                    outStream = new FileOutputStream(image);
                    ivFoto.getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 80, outStream);
                    outStream.flush();
                    outStream.close();
                    MediaStore.Images.Media.insertImage(getContentResolver(),image.getAbsolutePath(),image.getName(),image.getName());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //-----------------------------------------------------------
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /*----------Para abrir el Explorador de fotos---------------------*/
    public  static final int REQUEST_IMAGE_GET = 1;
    public void btAbrirFotosInternas(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //PARA ABRIR UNA PALICACION QUE USE LA IMG
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }
    /*-------------------------------------------------------------------*/
    //para la abrir la imagen principal
    public void imgFoto(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //PARA ABRIR UNA PALICACION QUE USE LA IMG
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }
    /*------------Envejecer la foto---------------------*/
    public void btBlancoNegro(View v){
        BitmapDrawable bmpDraw = (BitmapDrawable) ivFoto.getDrawable();
        Bitmap bitmap = bmpDraw.getBitmap();
        ivFoto.setImageBitmap(toEscalaDeGris(bitmap));
    }
    public void btRotarIzq(View v){
        BitmapDrawable bmpDraw = (BitmapDrawable) ivFoto.getDrawable();
        Bitmap bitmap = bmpDraw.getBitmap();
        // ivFoto.setImageBitmap(toEscalaDeGris(bitmap));
        ivFoto.setImageBitmap(rotarBitmap(bitmap, -90));
    }
    public void btRotarDer(View v){
        BitmapDrawable bmpDraw = (BitmapDrawable) ivFoto.getDrawable();
        Bitmap bitmap = bmpDraw.getBitmap();
        ivFoto.setImageBitmap(rotarBitmap(bitmap, 90));
    }
    public void btGris(View v){
        BitmapDrawable bmpDraw = (BitmapDrawable) ivFoto.getDrawable();
        Bitmap bitmap = bmpDraw.getBitmap();
        this.colorear(bitmap);
    }
    public void colorear(Bitmap bmpOriginal){

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
                bmp.setPixel(i, j, Color.argb(alpha, 0, green, blue));
            }
        }
        ivFoto.setImageBitmap(bmp);
    }
    /*-----------------------Metodos------------------------------------*/
    public static Bitmap toEscalaDeGris(Bitmap bmpOriginal) {
        Bitmap bmpGris = Bitmap.createBitmap(bmpOriginal.getWidth(),
                bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas lienzo = new Canvas(bmpGris);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(cmcf);
        lienzo.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGris;
    }
    //rotar la imagen
    public static Bitmap rotarBitmap(Bitmap bmpOriginal, float angulo) {
        Matrix matriz = new Matrix();
        matriz.postRotate(angulo);
        return Bitmap.createBitmap(bmpOriginal, 0, 0,
                bmpOriginal.getWidth(), bmpOriginal.getHeight(), matriz, true);
    }
    //abre la clase colores nos deja meter mas rojo azul verde..
    public void btAbrirColores(View v){
        Log.v("foto","abrir colores");
        Intent i = new Intent(this,Colores.class);
        Bundle bun = new Bundle();
        bun.putString("foto",ruta+"");
        System.out.println("luis" + ruta);
        i.putExtras(bun);
        startActivityForResult(i, 1);

    }
    public void btCamara(View v){
        Intent i = new Intent(this,Camara.class);
        startActivity(i);
    }
    public void zoonMasMenos(){
        zoomControls=(ZoomControls)findViewById(R.id.zoomControls);
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float x = ivFoto.getScaleX();
                float y = ivFoto.getScaleY();

                ivFoto.setScaleX(x + 1);
                ivFoto.setScaleY(y + 1);
            }
        });

        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {

                float x = ivFoto.getScaleX();
                float y = ivFoto.getScaleY();

                ivFoto.setScaleX(x - 1);
                ivFoto.setScaleY(y - 1);
            }
        });

    }
  

}
