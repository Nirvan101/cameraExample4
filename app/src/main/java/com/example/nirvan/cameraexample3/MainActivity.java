package com.example.nirvan.cameraexample3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity
{

    static int id1=1,id2=2;
    ImageView myImage;
    EditText textVar;
    int flag=0;             //flag==0 means place img below text, ==1 means below textVar


    private String pictureImagePath = "";
    Uri outputFileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button imgButton=(Button) findViewById(R.id.imgButton);


        View.OnClickListener imgButtonClickListener=new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = timeStamp + ".jpg";
                File storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
                File file = new File(pictureImagePath);
                outputFileUri = Uri.fromFile(file);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, 1);
            }
        };
        imgButton.setOnClickListener(imgButtonClickListener);


    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("TAG","CUSTOOOM");
        RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.relativeLayout);
        myImage=new ImageView(this);
        myImage.setId(id1++);
     //        textVar.setId(id2++);




        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            File imgFile = new File(pictureImagePath);
           if (imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
               // myImage = (ImageView) findViewById(R.id.imageViewTest);

                //this code ensures the imageView is placed below the editText
                RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                if(flag==0)
                   {
                       relativeParams.addRule(RelativeLayout.BELOW, R.id.text);
                       flag=1;
                   }
                else if(flag==1)
                    relativeParams.addRule(RelativeLayout.BELOW, textVar.getId());




                //myImage.setLayoutParams(relativeParams);
                relativeLayout.addView(myImage,relativeParams);
                //
                myImage.setImageBitmap(myBitmap);


                //place the new editText below the imgaeView just placed
                relativeParams = new RelativeLayout.LayoutParams
                        (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);                            //<--
                relativeParams.addRule(RelativeLayout.BELOW, myImage.getId());
                textVar=new EditText(this);
                textVar.setId(id2++);

                relativeLayout.addView(textVar,relativeParams);
                textVar.setText("NEW");




            }
        }//






    }



}
