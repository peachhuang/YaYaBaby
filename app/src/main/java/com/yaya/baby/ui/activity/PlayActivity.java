package com.yaya.baby.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.necer.calendar.EmuiCalendar;
import com.yaya.baby.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity {
    @BindView(R.id.emuiCalendar)
    EmuiCalendar nCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
    }
}
