package im.wangyan.soundtouchexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import net.surina.soundtouch.SoundTouch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private final String AUDIO_FILE_NAME = "test.wav";
    private final String AUDIO_CHANGED_FILE_NAME = "test_changed.wav";

    private final String MP3_FILE_NAME = "test.mp3";

    private MediaPlayer mediaPlayer;

    private SeekBar seekBar0, seekBar1, seekBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("xiaoxiaocainiao", "Sound Touch version: " + SoundTouch.getVersionString());

        copyAudioFile(AUDIO_FILE_NAME, audioFilePath());
        copyAudioFile(MP3_FILE_NAME, mp3FilePath());

        initSoundPlayer();

        Button playBtn = findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String xx = mp3FilePath();
                playSound(xx);
            }
        });


        Button changeSoundBtn = findViewById(R.id.change_sound_btn);
        changeSoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeSound();
                String xx = audioChangedFilePath();
                playSound(xx);
            }
        });

        seekBar0 = findViewById(R.id.sb0);
        seekBar1 = findViewById(R.id.sb1);
        seekBar2 = findViewById(R.id.sb2);
    }

    private void initSoundPlayer() {

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.d("xiaoxiaocainiao", "播放完毕.");
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

                Log.d("xiaoxiaocainiao", "播放错误: " + i + ", " + i1);
                return false;
            }
        });
    }


    private void playSound(String audioFilePath) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    private void changeSound() {
        SoundTouch soundTouch = new SoundTouch();

        // Sets new tempo control value. Normal tempo = 1.0, smaller values
        // represent slower tempo, larger faster tempo.
        // soundTouch.setTempo((float)1.5);

        float tempoValue = (float)(seekBar0.getProgress() / 10.0);
        soundTouch.setTempo(tempoValue);

        // Sets new pitch control value. Original pitch = 1.0, smaller values
        // represent lower pitches, larger values higher pitch.
        // soundTouch.setPitchSemiTones((float)1.5);

        float pitchValue = (float)(seekBar1.getProgress() / 10.0);
        soundTouch.setPitchSemiTones(pitchValue);

        // Sets new rate control value. Normal rate = 1.0, smaller values
        // represent slower rate, larger faster rates.
        // soundTouch.setSpeed((float)1.1);

        float speedValue = (float)(seekBar2.getProgress() / 10.0);
        soundTouch.setSpeed(speedValue);

        int result = soundTouch.processFile(audioFilePath(), audioChangedFilePath());
        Log.d("xiaoxiaocainiao", "变声参数. tempo: " + tempoValue + ", pitch: " + pitchValue + "speed: " + speedValue);
        Log.d("xiaoxiaocainiao", "变声结果(0是成功): " + result);
    }

    private String mp3FilePath() {
        String cacheFolder = getCacheDir().getPath();
        String savePath = cacheFolder + File.separatorChar + MP3_FILE_NAME;
        return  savePath;
    }

    private String audioFilePath() {
        String cacheFolder = getCacheDir().getPath();
        String savePath = cacheFolder + File.separatorChar + AUDIO_FILE_NAME;
        return  savePath;
    }

    private String audioChangedFilePath() {
        String cacheFolder = getCacheDir().getPath();
        String savePath = cacheFolder + File.separatorChar + AUDIO_CHANGED_FILE_NAME;
        return  savePath;
    }

    /**
     * 拷贝音频文件
     */
    private void copyAudioFile(String source_name, String target_path) {

        Log.d("xiaoxiaocainiao", "拷贝音频文件: " + source_name + ", " + target_path);

        AssetManager assetManager = getAssets();

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            inputStream = assetManager.open(source_name);

            File file = new File(target_path);
            fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = inputStream.read(buffer)) != -1) {
                // buffer字节
                fileOutputStream.write(buffer, 0, byteCount);
            }
            fileOutputStream.flush();// 刷新缓冲区
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
                if(fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
