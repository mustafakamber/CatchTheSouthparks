package com.mustafakamber.catchthesouthparks

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var score =0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable {}
    val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //ImageArray
        //Adding the characters
        imageArray.add(imageView)//kenny
        imageArray.add(imageView2)//kyle
        imageArray.add(imageView3)//liane
        imageArray.add(imageView4)//stan
        imageArray.add(imageView5)//cartman
        imageArray.add(imageView6)//towelie
        imageArray.add(imageView7)//tolkien
        imageArray.add(imageView8)//chef
        imageArray.add(imageView9)//bebe

        //Alert dialog to choose the difficulty
        val alertDif = AlertDialog.Builder(this@MainActivity)
        alertDif.setTitle("Choose your difficulty")
        alertDif.setPositiveButton("Hard"){dialog,which ->
            timer()
            hideImagesHard()
        }
        alertDif.setNegativeButton("Easy"){dialog,which ->
            timer()
            hideImagesEasy()
        }
        alertDif.show()
    }

    fun timer(){
        object : CountDownTimer(15500/* GameTime */,1000){
            override fun onTick(millisUntilFinished: Long){
                //Show the remaining seconds
                timeText.text = "Time : " +millisUntilFinished/1000
            }

            override fun onFinish() {
                timeText.text = "Time is Up"

                handler.removeCallbacks(runnable)

                //Make all images invisible
                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                //Alert dialog to show the score
                val alertScore = AlertDialog.Builder(this@MainActivity)
                alertScore.setTitle("Your Score : "+score)
                alertScore.setPositiveButton("Continue"){dialog,which ->

                    //Alert dialog to show the restart menu
                    val alertOver = AlertDialog.Builder(this@MainActivity)
                    alertOver.setTitle("Restart The Game ?")
                    alertOver.setMessage("")
                    alertOver.setNegativeButton("Yes") {dialog,which ->
                        val intent = intent
                        finish()
                        startActivity(intent)
                    }
                    alertOver.setPositiveButton("No"){dialog,which ->
                        Toast.makeText(this@MainActivity,"Game Over", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    alertOver.show()
                }
                alertScore.show()
            }

        }.start()


    }
    fun hideImagesEasy(){
        //Easy Mode
        runnable = object : Runnable{
            override fun run(){
                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,550)
            }
        }
        handler.post(runnable)
    }
    fun hideImagesHard(){
        //Hard Mode
        runnable = object : Runnable{
            override fun run(){
                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,275)
            }
        }
        handler.post(runnable)
    }
    fun increaseScore(view : View){
        score++
        scoreText.text = "Score: $score"
    }
}