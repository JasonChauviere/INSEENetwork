package com.chauviere.jason.postalnetwork.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chauviere.jason.postalnetwork.Model.NetworkSample;
import com.chauviere.jason.postalnetwork.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView mNomCommune, mDepartement, mNbLocaux;
    private TextView mNbLocauxTTE, mNbLocauxTT3, mNbLocauxTT8, mNbLocauxTT30, mNbLocauxTT100;
    private TextView mNbLocauxDSLE, mNbLocauxDSL3, mNbLocauxDSL8, mNbLocauxDSL30, mNbLocauxDSL100;
    private TextView mNbLocauxCABE, mNbLocauxCAB3, mNbLocauxCAB8, mNbLocauxCAB30, mNbLocauxCAB100;
    private TextView mNbLocauxFTTHE, mNbLocauxFTTH3, mNbLocauxFTTH8, mNbLocauxFTTH30, mNbLocauxFTTH100;
    private String mCode;
    private int i;
    private Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6, mButton7, mButton8, mButton9;
    private int find;
    private List<NetworkSample> mNetworkSamples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.activity_main_code_postal);
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mButton1 = findViewById(R.id.activity_main_T2_2015);
        mButton1.setEnabled(false);
        mButton2 = findViewById(R.id.activity_main_T3_2015);
        mButton2.setEnabled(false);
        mButton3 = findViewById(R.id.activity_main_T4_2015);
        mButton3.setEnabled(false);
        mButton4 = findViewById(R.id.activity_main_T1_2016);
        mButton4.setEnabled(false);
        mButton5 = findViewById(R.id.activity_main_T2_2016);
        mButton5.setEnabled(false);
        mButton6 = findViewById(R.id.activity_main_T3_2016);
        mButton6.setEnabled(false);
        mButton7 = findViewById(R.id.activity_main_T4_2016);
        mButton7.setEnabled(false);
        mButton8 = findViewById(R.id.activity_main_T1_2017);
        mButton8.setEnabled(false);
        mButton9 = findViewById(R.id.activity_main_T2_2017);
        mButton9.setEnabled(false);
        mNomCommune = findViewById(R.id.activity_main_nom_commune);
        mDepartement = findViewById(R.id.activity_main_departement);
        mNbLocaux = findViewById(R.id.activity_main_nb_locaux);
        mNbLocauxTTE = findViewById(R.id.activity_main_part_locaux_tt_eligibles);
        mNbLocauxTT3 = findViewById(R.id.activity_main_part_locaux_tt_3m);
        mNbLocauxTT8 = findViewById(R.id.activity_main_part_locaux_tt_8m);
        mNbLocauxTT30 = findViewById(R.id.activity_main_part_locaux_tt_30m);
        mNbLocauxTT100 = findViewById(R.id.activity_main_part_locaux_tt_100m);
        mNbLocauxFTTHE = findViewById(R.id.activity_main_part_locaux_ftth_eligibles);
        mNbLocauxFTTH3 = findViewById(R.id.activity_main_part_locaux_ftth_3m);
        mNbLocauxFTTH8 = findViewById(R.id.activity_main_part_locaux_ftth_8m);
        mNbLocauxFTTH30 = findViewById(R.id.activity_main_part_locaux_ftth_30m);
        mNbLocauxFTTH100 = findViewById(R.id.activity_main_part_locaux_ftth_100m);
        mNbLocauxDSLE = findViewById(R.id.activity_main_part_locaux_dsl_eligibles);
        mNbLocauxDSL3 = findViewById(R.id.activity_main_part_locaux_dsl_3m);
        mNbLocauxDSL8 = findViewById(R.id.activity_main_part_locaux_dsl_8m);
        mNbLocauxDSL30 = findViewById(R.id.activity_main_part_locaux_dsl_30m);
        mNbLocauxDSL100 = findViewById(R.id.activity_main_part_locaux_dsl_100m);
        mNbLocauxCABE = findViewById(R.id.activity_main_part_locaux_cab_eligibles);
        mNbLocauxCAB3 = findViewById(R.id.activity_main_part_locaux_cab_3m);
        mNbLocauxCAB8 = findViewById(R.id.activity_main_part_locaux_cab_8m);
        mNbLocauxCAB30 = findViewById(R.id.activity_main_part_locaux_cab_30m);
        mNbLocauxCAB100 = findViewById(R.id.activity_main_part_locaux_cab_100m);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mButton1.setEnabled(s.toString().length() == 5);
                mButton2.setEnabled(s.toString().length() == 5);
                mButton3.setEnabled(s.toString().length() == 5);
                mButton4.setEnabled(s.toString().length() == 5);
                mButton5.setEnabled(s.toString().length() == 5);
                mButton6.setEnabled(s.toString().length() == 5);
                mButton7.setEnabled(s.toString().length() == 5);
                mButton8.setEnabled(s.toString().length() == 5);
                mButton9.setEnabled(s.toString().length() == 5);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(1);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(2);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(3);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(4);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(5);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });

        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(6);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });

        mButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(7);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });

        mButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(8);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });

        mButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNetworkData(9);
                find = 0; i = 0;
                mCode = mEditText.getText().toString();

                do {
                    if (mCode.equals(mNetworkSamples.get(i).getCodeINSEE())){
                        find = 1;
                    } else {
                        i++;
                        if (mNetworkSamples.size() == i){
                            find = 2;
                        }
                    }
                } while (find == 0);

                if (find == 1) {
                    NetworkSample goodSample = mNetworkSamples.get(i);
                    mNomCommune.setText(goodSample.getNomCommune());
                    mDepartement.setText(goodSample.getDepartement());
                    mNbLocaux.setText(goodSample.getNbLocaux());
                    mNbLocauxTTE.setText(goodSample.getPartTTeligibles());
                    mNbLocauxTT3.setText(goodSample.getPartTT3M());
                    mNbLocauxTT8.setText(goodSample.getPartTT8M());
                    mNbLocauxTT30.setText(goodSample.getPartTT30M());
                    mNbLocauxTT100.setText(goodSample.getPartTT100M());
                    mNbLocauxDSLE.setText(goodSample.getPartDSLeligibles());
                    mNbLocauxDSL3.setText(goodSample.getPartDSL3M());
                    mNbLocauxDSL8.setText(goodSample.getPartDSL8M());
                    mNbLocauxDSL30.setText(goodSample.getPartDSL30M());
                    mNbLocauxDSL100.setText(goodSample.getPartDSL100M());
                    mNbLocauxCABE.setText(goodSample.getPartCABLEeligibles());
                    mNbLocauxCAB3.setText(goodSample.getPartCABLE3M());
                    mNbLocauxCAB8.setText(goodSample.getPartCABLE8M());
                    mNbLocauxCAB30.setText(goodSample.getPartCABLE30M());
                    mNbLocauxCAB100.setText(goodSample.getPartCABLE100M());
                    mNbLocauxFTTHE.setText(goodSample.getPartFTTHeligibles());
                    mNbLocauxFTTH3.setText(goodSample.getPartFTTH3M());
                    mNbLocauxFTTH8.setText(goodSample.getPartFTTH8M());
                    mNbLocauxFTTH30.setText(goodSample.getPartFTTH30M());
                    mNbLocauxFTTH100.setText(goodSample.getPartFTTH100M());
                }

                if (find == 2){
                    mNomCommune.setText("PAS DE COMMUNE VIA CE CODE");
                    mDepartement.setText("");
                    mNbLocaux.setText("");
                    mNbLocauxTTE.setText("");
                    mNbLocauxTT3.setText("");
                    mNbLocauxTT8.setText("");
                    mNbLocauxTT30.setText("");
                    mNbLocauxTT100.setText("");
                    mNbLocauxDSLE.setText("");
                    mNbLocauxDSL3.setText("");
                    mNbLocauxDSL8.setText("");
                    mNbLocauxDSL30.setText("");
                    mNbLocauxDSL100.setText("");
                    mNbLocauxCABE.setText("");
                    mNbLocauxCAB3.setText("");
                    mNbLocauxCAB8.setText("");
                    mNbLocauxCAB30.setText("");
                    mNbLocauxCAB100.setText("");
                    mNbLocauxFTTHE.setText("");
                    mNbLocauxFTTH3.setText("");
                    mNbLocauxFTTH8.setText("");
                    mNbLocauxFTTH30.setText("");
                    mNbLocauxFTTH100.setText("");
                }
            }
        });
    }

    // Based on info from: http://stackoverflow.com/a/19976110
    // Procédure qui va permettre la lecture des fichiers CSV
    private void readNetworkData(int id){
        InputStream is;
        switch (id)
        {
            case 1:
                is = getResources().openRawResource(R.raw.t22015);
                break;
            case 2:
                is = getResources().openRawResource(R.raw.t32015);
                break;
            case 3:
                is = getResources().openRawResource(R.raw.t42015);
                break;
            case 4:
                is = getResources().openRawResource(R.raw.t12016);
                break;
            case 5:
                is = getResources().openRawResource(R.raw.t22016);
                break;
            case 6:
                is = getResources().openRawResource(R.raw.t32016);
                break;
            case 7:
                is = getResources().openRawResource(R.raw.t42016);
                break;
            case 8:
                is = getResources().openRawResource(R.raw.t22017);
                break;
            case 9:
                is = getResources().openRawResource(R.raw.t22017);
                break;
            default:
                is = getResources().openRawResource(R.raw.t22017);
                break;
        }
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            mNetworkSamples.clear();
            while( (line = reader.readLine()) != null){
                // Split by ";"
                String[] tokens = line.split(";");

                // Read the data
                NetworkSample sample = new NetworkSample();
                sample.setCodeINSEE(tokens[0]);
                sample.setNomCommune(tokens[1]);
                sample.setDepartement(tokens[2]);
                sample.setNbLocaux(tokens[3]);
                sample.setPartTTeligibles(tokens[4]);
                sample.setPartTT3M(tokens[5]);
                sample.setPartTT8M(tokens[6]);
                sample.setPartTT30M(tokens[7]);
                sample.setPartTT100M(tokens[8]);
                sample.setPartDSLeligibles(tokens[9]);
                sample.setPartDSL3M(tokens[10]);
                sample.setPartDSL8M(tokens[11]);
                sample.setPartDSL30M(tokens[12]);
                sample.setPartDSL100M(tokens[13]);
                sample.setPartCABLEeligibles(tokens[14]);
                sample.setPartCABLE3M(tokens[15]);
                sample.setPartCABLE8M(tokens[16]);
                sample.setPartCABLE30M(tokens[17]);
                sample.setPartCABLE100M(tokens[18]);
                sample.setPartFTTHeligibles(tokens[19]);
                sample.setPartFTTH3M(tokens[20]);
                sample.setPartFTTH8M(tokens[21]);
                sample.setPartFTTH30M(tokens[22]);
                sample.setPartFTTH100M(tokens[23]);
                mNetworkSamples.add(sample);
            }
        } catch (IOException e){
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }
}
