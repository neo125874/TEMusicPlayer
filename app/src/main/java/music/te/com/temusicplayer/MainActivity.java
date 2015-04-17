package music.te.com.temusicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    TextView tv_status;
    Button btn_play, btn_pause, btn_stop;
    ToggleButton tb_loop;
    SeekBar seek_bar;

    MediaPlayer player;

    Handler seekHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInit();
        seekUpdation();
    }

    public void getInit()
    {
        tv_status = (TextView) findViewById(R.id.tv_status);
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        tb_loop = (ToggleButton) findViewById(R.id.tb_loop);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_status.setText("Playing...");
                player.start();
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player != null)
                    player.pause();
                tv_status.setText("Paused...");
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player != null)
                {
                    player.pause();
                    player.seekTo(0);
                }
                tv_status.setText("Stop...");
            }
        });
        tb_loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tb_loop.isChecked())
                {
                    if(player != null)
                    {
                        if(!player.isLooping())
                            player.setLooping(true);
                        else
                            player.setLooping(false);
                    }
                }
            }
        });

        player = MediaPlayer.create(this, R.raw.a2_1);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                player.release();
            }
        });

        seek_bar.setMax(player.getDuration());
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                {
                    player.seekTo(progress);
                    seek_bar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void seekUpdation()
    {
        seek_bar.setProgress(player.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    Runnable run = new Runnable()
    {
        @Override
        public void run()
        {
            seekUpdation();
        }
    };

    @Override
    protected void onDestroy() {
        if(player != null)
            player.release();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
