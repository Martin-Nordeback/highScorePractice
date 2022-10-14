package com.example.highscorepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
/* - Lägg till två knappar. - check (save + print knappar)
   - När man trycker på ena knappen ska score sparas till en fil - check
   - Och nollställas i appen
   - Trycker man på den andra knappen ska score läsas in från filen och skrivas in i score textView.
   */

    private Button randomNumberButton;
    private Button buttonSaveHighScore;
    private Button buttonPrintHighScore;
    private TextView outPutRandomNumber;
    private TextView highScoreViewField;
    private SharedPreferences sharedPreferences;
    File folderHighScore;

    int score;
    int highScore;
    int min = 0;
    int max = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("com.example.highscorepractice", MODE_PRIVATE);
        score = sharedPreferences.getInt("Score ", 0);

        randomNumberButton = findViewById(R.id.button);
        outPutRandomNumber = findViewById(R.id.textViewScore);
        highScoreViewField = findViewById(R.id.textView2);
        buttonSaveHighScore = findViewById(R.id.buttonSaveHighScore);
        buttonPrintHighScore = findViewById(R.id.buttonPrintHighScore);


        //highScore = getHighScore();
        //savedHighScore(0);

        randomNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                score += random.nextInt(max - min + 1) + min;
                outPutRandomNumber.setText(String.valueOf(score));

                /*if (score > highScore) {
                    savedHighScore(score);
                    getHighScore();
                }*/
            }
        });


        buttonSaveHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                folderHighScore = new File(MainActivity.this.getFilesDir(), "savedScore");
                if (!folderHighScore.exists()) {
                    folderHighScore.mkdir();
                }
                try {
                    File textFile = new File(folderHighScore, "highScore.txt");
                    FileWriter writer = new FileWriter(textFile);
                    writer.write(outPutRandomNumber.getText().toString());
                    writer.close();
                    Toast.makeText(MainActivity.this, "Your high score is saved", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed to save high score, try again!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        buttonPrintHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               File textFile = new File(folderHighScore, "highScore.txt");

                try {
                    Scanner in = new Scanner(textFile);
                    String scannerInput = in.nextLine();
                    highScoreViewField.setText(scannerInput);
                    Toast.makeText(MainActivity.this, "Saved high score visible", Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException errorCatch) {
                    errorCatch.printStackTrace();
                }

            }
        });
    }

    /*private int getHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.highscorepractice", MODE_PRIVATE);
        highScore = sharedPreferences.getInt("HIGH_SCORE", 0);
        highScoreViewField.setText("High score: " + highScore);
        return highScore;

    }

    private void savedHighScore(int x) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("HIGH_SCORE", x);
        editor.apply();
    }*/

}