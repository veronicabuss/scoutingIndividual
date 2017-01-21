package org.strykeforce.scoutingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ScoutMainActivity extends AppCompatActivity {
    private int matchNum = 1;
    private int teamNum;
    private int[][] allTeamNums;
    private final int SCOUT_ID = 1; //red 1
    private String teamText = "";

    private EditText total_gears_input;
    private TextView matchView, teamView;
    private Button gen_btn, ok_code_btn, nextMat_btn, make_btn;
    private ImageView image;
    private String text2Qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_main);

        total_gears_input = (EditText) findViewById((R.id.total_gears));
        gen_btn = (Button) findViewById(R.id.gen_btn);
        ok_code_btn = (Button) findViewById((R.id.ok_button));
        nextMat_btn = (Button) findViewById((R.id.nextMatchButton));
        make_btn = (Button) findViewById((R.id.tempButton));
        image = (ImageView) findViewById(R.id.image);
        matchView = (TextView) findViewById(R.id.matchLabel);
        teamView = (TextView) findViewById(R.id.teamLabel);


        ok_code_btn.setVisibility(View.INVISIBLE);
        image.setVisibility(View.INVISIBLE);

        allTeamNums = getTeamNums();

        int count = 0, temp;
        matchNum = 1;
        teamNum = allTeamNums[matchNum-1][SCOUT_ID-1];
        teamView.setText("Team: " + Integer.toString(teamNum));

        nextMat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchNum++;
                teamNum = allTeamNums[matchNum-1][SCOUT_ID-1];
                matchView.setText("Match: " + Integer.toString(matchNum));
                teamView.setText("Team: " + Integer.toString(teamNum));
            }
        });

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ok_code_btn.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                text2Qr = "Scout ID: " + Integer.toString(SCOUT_ID) + "\nTeam: " + Integer.toString(teamNum) + "\nNum Gears: " + total_gears_input.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                } catch (WriterException e) {
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

    public int[][] getTeamNums()
    {
        try{
            InputStream stream = getAssets().open("teams_matches.txt");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            teamText = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Scanner scan = new Scanner(teamText);
        int numLines = countLines(teamText);
        int[][] allTeams = new int[numLines][6];
        for(int j=0; j<numLines; j++)
        {
            for(int k=0; k<6; k++)
            {
                allTeams[j][k] = scan.nextInt();
            }
        }
        return allTeams;
    }

    private static int countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }
}



