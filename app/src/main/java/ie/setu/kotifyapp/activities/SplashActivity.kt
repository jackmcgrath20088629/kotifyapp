package ie.setu.kotifyapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ie.setu.kotifyapp.R

//reference: https://rex50.medium.com/amazing-animated-splash-screen-kotlin-and-lottie-how-to-b98504005abf


class SplashActivity : AppCompatActivity() {

    companion object {

        //NOTE: It is recommended to keep the time less than 3000 ms
        // but as this is just going to be demo so I'm using 6000 ms
        // to show the full animation.
        const val ANIMATION_TIME: Long = 6000 //Change time according to your animation.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Use Handler to delay the start of next Activity
        // and show animation in the mean while
        Handler(this.mainLooper).postDelayed({

            //This block will be executed after ANIMATION_TIME milliseconds.

            //After ANIMATION_TIME we will start the MainActivity
            startActivity(Intent(this, SignInActivity::class.java))

            //To remove this activity from back stack so that
            // this activity will not show when user closes MainActivity
            finish()

        }, ANIMATION_TIME)

    }

}