package com.example.videoplayerapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.os.Handler
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    private var startTime: Double  = 0.0;
    private var finalTime: Double  = 0.0;

    private var myHandler: Handler = Handler()
    private var forwardTime: Int  = 5000;
    private var backwardTime: Int  = 5000;
    public var oneTimeOnly: Int  = 0;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView4.text = "Song.mp4"

        mediaPlayer = MediaPlayer.create(this, R.raw.song)
           seekBar.isClickable = false
           button2.isEnabled = true
           mediaPlayer?.start()

        fun updateSongTime(): Runnable  = Runnable() {

            fun run() {
                startTime = mediaPlayer?.currentPosition!!.toDouble()

                textView2.text = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.
                                    toMinutes(startTime.toLong())))

                seekBar.setProgress(startTime.toInt());
                myHandler.postDelayed(updateSongTime(), 100)
            }
        }

            button3.setOnClickListener{

                Toast.makeText(this, "Playing sound",
                    Toast.LENGTH_SHORT).show()

                mediaPlayer?.start()

                finalTime = mediaPlayer?.duration!!.toDouble()
                startTime = mediaPlayer?.currentPosition!!.toDouble()


                if (oneTimeOnly == 0) {
                    seekBar.max = finalTime.toInt()
                    oneTimeOnly = 1
                }

                textView3.text =
                    String.format(
                        "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(finalTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong())
                        )
                    )

                textView2.text =
                    String.format(
                        "%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()),
                        TimeUnit.MILLISECONDS.toSeconds(startTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())
                        )
                    )

                seekBar.progress = startTime.toInt()
                myHandler.postDelayed(updateSongTime(),100);
                button2.isEnabled = true
                button3.isEnabled = false

            }//END SETONCLICKLISTENER

            button2.setOnClickListener{
                Toast.makeText(this, "Pausing sound",Toast.LENGTH_SHORT).show();
                        mediaPlayer?.pause();
                button2.isEnabled = false
                button3.isEnabled = true

            }//END SETONCLICKLISTENER


            button.setOnClickListener{
                val temp = startTime.toInt()

                if((temp + forwardTime)<= finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer?.seekTo(startTime.toInt())
                    Toast.makeText(this,"You have Jumped forward 5 seconds",
                        Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Cannot jump forward 5 seconds",
                        Toast.LENGTH_SHORT).show();
                }
            }//END SETONCLICKLISTENER


            button4.setOnClickListener{

                val temp = startTime.toInt()

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer?.seekTo(startTime.toInt());
                    Toast.makeText(this,"You have Jumped backward 5 seconds",
                        Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Cannot jump backward 5 seconds",
                        Toast.LENGTH_SHORT).show();
                }

            }//END SETONCLICKLISTENER
    }// END ONCREATE
}// END ACTIVITY
