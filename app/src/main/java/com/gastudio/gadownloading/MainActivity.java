package com.gastudio.gadownloading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * author: GAStudio
 * qq:1935400187
 * 技术交流qq群:277582728
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] ids = new int[]{R.id.show_success, R.id.show_failed};
        final Intent[] intents = new Intent[]{
                new Intent(MainActivity.this, SuccessActivity.class),
                new Intent(MainActivity.this, FailedActivity.class)
        };

        int size = ids.length;
        for (int i = 0; i < size; i++) {
            final int intentIndex = i;
            findViewById(ids[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intents[intentIndex]);
                }
            });
        }
    }
}
