package com.example.russianroulette;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String[] arr1 = new String[] {"1", "2", "3", "4", "5", "6"};//will be used to compare with "bullets" int array
    ArrayList<String> barrel = new ArrayList<String>();
    ArrayList<String> barrel2 = new ArrayList<String>();
    int global;//for increasing for barrel2
    int bullets = 0;//is used to increase index of 'barrel' array
    int a;//used to temporarily store string value converted to integer for loading menu

    //set all guns to false, one will be set to true to change its skin
    boolean revolver1 = false;
    boolean revolver2 = false;
    boolean revolver3 = false;

    //create a method to call when switching between guns so the user can only choose skins for selected gun
    public void setFalse(){
        revolver1 = false;
        revolver2 = false;
        revolver3 = false;
    }


    String[] names = {"Background", "White", "Black", "Red",
                      "Green", "Yellow", "Orange", "Blue", "Pink", "Purple",
                      "Brown", "Gray"};//string array to hold words in background color spinner
    String[] guns = {"Revolver1", "Revolver2", "Revolver3"};
    int gun_images[] = {R.drawable.gun1, R.drawable.gun2,R.drawable.gun3};
    String[] skins = {"None", "Digital", "Blue Tiger", "Red Tiger", "Gold"};
    int gun_images2[] = {R.drawable.none, R.drawable.digital, R.drawable.bluetiger, R.drawable.redtiger, R.drawable.gold};


    ArrayAdapter<String> adapter;//variable to hold adapter array
    ArrayAdapter<String> adapter2;//variable to hold adapter for guns
    ArrayAdapter<String> adapter3;//variable to hold skins
    Spinner sp;//variable to hold spinner for background color
    Spinner sp2;//variable to hold spinner for guns
    Spinner sp4;//variable to hold spinner for skins

    TextView description;//variable to hold textView
    View screenView;//variable to hold the background layer
    ImageView image;
    Button button;//load button
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenView = findViewById(R.id.rView);
        sp = (Spinner) findViewById(R.id.spinner);//create spinner for background color
        description = (TextView) findViewById(R.id.textView);

        button = findViewById(R.id.button);//load button
        button2 = findViewById(R.id.button2);

        barrel.addAll(Arrays.asList(arr1));
        barrel2.addAll(Arrays.asList(arr1));

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
        sp.setAdapter(adapter);



        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        sp2 = (Spinner) findViewById(R.id.spinner2);//create spinner for guns
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), gun_images, guns);
        sp2.setAdapter(customAdapter);

        sp4 = (Spinner) findViewById(R.id.spinner4);//create spinner for skins
        CustomAdapter2 customAdapter2 = new CustomAdapter2(getApplicationContext(), gun_images2, skins);
        sp4.setAdapter(customAdapter2);

        //declare sound files to play later
        MediaPlayer click = MediaPlayer.create(MainActivity.this, R.raw.click);//click sound
        MediaPlayer shoot = MediaPlayer.create(MainActivity.this, R.raw.shoot);//shoot sound

        //declare alert
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setMessage("                    You Lose!");


        //the 'load' button
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialogue_spinner, null);
                mBuilder.setTitle("How many bullets?");
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner3);
                ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.load));
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter3);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Select number of bullets")) {
                            Toast.makeText(MainActivity.this,
                                    mSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT)
                                    .show();
                            a = Integer.parseInt(mSpinner.getSelectedItem().toString()) - 1;//convert number chosen to int to set value of 'global'
                            Log.d("Test", String.valueOf(a));
                            bullets = 0;
                            global = a;//give global the value of a which is the number of bullets selected
                            //determines how much checks are done, if you 'put in 2 bullets' set this to 2 if 3 then 3 and so on
                            //remember that the array starts from index 0 and ends at index 5
                        }

                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                Log.d("Global", String.valueOf(global));

                Collections.shuffle(barrel2);//randomize order of array list that is the same as barrel

                //print barrel2, this is the barrel that will be shuffled for randomness.
                //after shuffling the first index of both barrels will be compared when the 'shoot' button is clicked
                for (int i = 0; i < barrel2.size(); i++) {
                    Log.d("barrel2", barrel2.get(i));//print array to console
                }

                //print barrel
                for (int i = 0; i < barrel.size(); i++) {
                    Log.d("barrel1", barrel.get(i));//print array to console
                }

            }
        });

        //the 'shoot' button
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Global", String.valueOf(global));
                Log.d("Bullets", String.valueOf(bullets));
                switch (global) {
                    //one bullet case
                    case 0:
                        if (barrel.get(bullets) == barrel2.get(0)) {
                            Log.d("Case 0, 1 bullet GAME", "You Lose");
                            shoot.start();
                            alertDialog.show();
                            break;
                        } else {
                            Log.d("Case 0, 1 bullet GAME", "Click");
                            bullets++;
                            click.start();
                            break;
                        }
                        //two bullet case
                    case 1:
                        if (barrel.get(bullets) == barrel2.get(0) || barrel.get(bullets) == barrel2.get(1)) {
                            Log.d("Case 1, 2 bullet GAME", "You Lose");
                            shoot.start();
                            alertDialog.show();
                            break;
                        } else {
                            Log.d("Case 1, 2 bullet GAME", "Click");
                            bullets++;
                            click.start();
                            break;
                        }
                        //three bullet case
                    case 2:
                        if (barrel.get(bullets) == barrel2.get(0) || barrel.get(bullets) == barrel2.get(1) || barrel.get(bullets) == barrel2.get(2)) {
                            Log.d("Case 2, 3 bullet GAME", "You Lose");
                            shoot.start();
                            alertDialog.show();
                            break;
                        } else {
                            Log.d("Case 2, 3 bullet GAME", "Click");
                            bullets++;
                            click.start();
                            break;
                        }
                        //four bullet case
                    case 3:
                        if (barrel.get(bullets) == barrel2.get(0) || barrel.get(bullets) == barrel2.get(1) || barrel.get(bullets) == barrel2.get(2)
                                || barrel.get(bullets) == barrel2.get(3)) {
                            Log.d("Case 3, 4 bullet GAME", "You Lose");
                            shoot.start();
                            alertDialog.show();
                            break;
                        } else {
                            Log.d("Case 3, 4 bullet GAME", "Click");
                            bullets++;
                            click.start();
                            break;
                        }
                        //five bullet case
                    case 4:
                        if (barrel.get(bullets) == barrel2.get(0) || barrel.get(bullets) == barrel2.get(1) || barrel.get(bullets) == barrel2.get(2)
                                || barrel.get(bullets) == barrel2.get(3) || barrel.get(bullets) == barrel2.get(4)) {
                            Log.d("Case 4, 5 bullet GAME", "You Lose");
                            shoot.start();
                            alertDialog.show();
                            break;
                        } else {
                            Log.d("Case 4, 5 bullet GAME", "Click");
                            bullets++;
                            click.start();
                            break;
                        }
                        //six bullet case, should always be a loss
                    case 5:
                        Log.d("Case 5, 6 bullet GAME", "You Lose");
                        shoot.start();
                        alertDialog.show();
                        break;
                }

            }
        });


        //this is the spinner that allows the user to change background colors
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {//create switch statement with 11 cases
                    case 0://holds the first value of names array ("Background Color" in this case) all others will change the color as well, the second case holds second and so on
                        break;
                    case 1:
                        screenView.setBackgroundResource(R.color.white);//changes the background color to white
                        break;
                    case 2:
                        screenView.setBackgroundResource(R.color.black);
                        break;
                    case 3:
                        screenView.setBackgroundResource(R.color.red);
                        break;
                    case 4:
                        screenView.setBackgroundResource(R.color.green);
                        break;
                    case 5:
                        screenView.setBackgroundResource(R.color.yellow);
                        break;
                    case 6:
                        screenView.setBackgroundResource(R.color.orange);
                        break;
                    case 7:
                        screenView.setBackgroundResource(R.color.blue);
                        break;
                    case 8:
                        screenView.setBackgroundResource(R.color.pink);
                        break;
                    case 9:
                        screenView.setBackgroundResource(R.color.purple);
                        break;
                    case 10:
                        screenView.setBackgroundResource(R.color.brown);
                        break;
                    case 11:
                        screenView.setBackgroundResource(R.color.gray);
                        break;

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //this is the spinner that is used to select guns
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                image = (ImageView) findViewById(R.id.imageView1);//uses ImageView to get images

                switch (position) {
                    case 0:
                        setFalse();//make all booleans false and make selected gun true for applying skins
                        revolver1 = true;
                        Toast.makeText(getApplicationContext(), guns[position], Toast.LENGTH_LONG).show();//sets menu image to selected gun, utilizes CustomAdapter.java
                        image.setImageResource(R.drawable.gun1);//show gun image on screen
                        break;
                    case 1:
                        setFalse();
                        revolver2 = true;
                        Toast.makeText(getApplicationContext(), guns[position], Toast.LENGTH_LONG).show();
                        image.setImageResource(R.drawable.gun2);
                        break;
                    case 2:
                        setFalse();
                        revolver3 = true;
                        Toast.makeText(getApplicationContext(), guns[position], Toast.LENGTH_LONG).show();
                        image.setImageResource(R.drawable.gun3);
                        break;
                }
            }
            //when nothing is selected do nothing
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //this is the spinner that is used to select skins
        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
                image=(ImageView) findViewById(R.id.imageView1);//uses ImageView to get images

                //skins for revolver 1
                if (revolver1 == true) {
                    switch (position) {
                        case 0:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();////sets menu image to selected gun, utilizes CustomAdapter2.java
                            image.setImageResource(R.drawable.gun1);//show image on screen
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun1digital);//show image on screen
                            screenView.setBackgroundResource(R.color.gray);//set to color to skin theme (gray in this case)
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun1bluetiger);
                            screenView.setBackgroundResource(R.color.blue);
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun1redtiger);
                            screenView.setBackgroundResource(R.color.red);
                            break;
                        case 4:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun1gold);
                            screenView.setBackgroundResource(R.color.yellow);
                            break;
                    }
                }

                //skins for revolver 2
                if (revolver2 == true) {
                    switch (position) {
                        case 0:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();//utilizes CustomAdapter2.java
                            image.setImageResource(R.drawable.gun2);//show image on screen
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();//utilizes CustomAdapter2.java
                            image.setImageResource(R.drawable.gun2digital);//show image on screen
                            screenView.setBackgroundResource(R.color.gray);//set to color to skin theme (gray in this case)
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun2bluetiger);
                            screenView.setBackgroundResource(R.color.blue);
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun2redtiger);
                            screenView.setBackgroundResource(R.color.red);
                            break;
                        case 4:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun2gold);
                            screenView.setBackgroundResource(R.color.yellow);
                            break;
                    }
                }

                //skins for revolver 3
                if (revolver3 == true) {
                    switch (position) {
                        case 0:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();//utilizes CustomAdapter2.java
                            image.setImageResource(R.drawable.gun3);//show image on screen
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();//utilizes CustomAdapter2.java
                            image.setImageResource(R.drawable.gun3digital);//show image on screen
                            screenView.setBackgroundResource(R.color.gray);//set to color to skin theme (gray in this case)
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun3bluetiger);
                            screenView.setBackgroundResource(R.color.blue);
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun3redtiger);
                            screenView.setBackgroundResource(R.color.red);
                            break;
                        case 4:
                            Toast.makeText(getApplicationContext(), skins[position], Toast.LENGTH_LONG).show();
                            image.setImageResource(R.drawable.gun3gold);
                            screenView.setBackgroundResource(R.color.yellow);
                            break;
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }
}