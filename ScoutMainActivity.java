package org.strykeforce.scoutingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ScoutMainActivity extends AppCompatActivity {
    private EditText text;
    private Button gen_btn, ok_code_btn;
    private ImageView image;
    private String text2Qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_main);
        text = (EditText) findViewById(R.id.text);
        gen_btn = (Button) findViewById(R.id.gen_btn);
        ok_code_btn = (Button) findViewById((R.id.ok_button));
        ok_code_btn.setVisibility(View.INVISIBLE);
        image = (ImageView) findViewById(R.id.image);
        image.setVisibility(View.INVISIBLE);
        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok_code_btn.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                text2Qr = text.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                }
                catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        ok_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok_code_btn.setVisibility(View.INVISIBLE);
                image.setVisibility((View.INVISIBLE));
            }
        });
    }
}
