package im.wangyan.soundtouchexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import net.surina.soundtouch.SoundTouch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("xiaoxiaocainiao", "Sound Touch version: " + SoundTouch.getVersionString());
    }
}
