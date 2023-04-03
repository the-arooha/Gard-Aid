package com.example.loginactivity.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.loginactivity.categories.bee.BeeActivity
import com.example.loginactivity.R
import com.example.loginactivity.categories.annual.AnnualActivity
import com.example.loginactivity.categories.butterfly.ButterflyActivity
import com.example.loginactivity.categories.climbers.ClimbersActivity
import com.example.loginactivity.categories.foliage.FoliageActivity
import com.example.loginactivity.categories.herb.HerbActivity
import com.example.loginactivity.categories.herbaltea.HerbalteaActivity
import com.example.loginactivity.categories.meditation.MeditationActivity
import com.example.loginactivity.categories.perennial.PerennialActivity
import com.example.loginactivity.categories.rock.RockActivity
import com.example.loginactivity.categories.seasonal.SeasonalActivity
import com.example.loginactivity.categories.succulents.SucculentsActivity
import com.example.loginactivity.categories.vegetable.VegetableActivity
import com.example.loginactivity.categories.water.WaterActivity

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        clickListener()
    }

    public fun clickListener(){
        var imageHerb= findViewById<ImageView>(R.id.ivHerb)
        var imageVegetable= findViewById<ImageView>(R.id.ivVegetable)
        var imageMeditation= findViewById<ImageView>(R.id.ivMeditation)
        var imageHerbaltea= findViewById<ImageView>(R.id.ivHerbtea)
        var imageFoliage= findViewById<ImageView>(R.id.ivFoliage)
        var imageSeasonal= findViewById<ImageView>(R.id.ivSeasonal)
        var imageBee= findViewById<ImageView>(R.id.ivBee)
        var imageClimbers= findViewById<ImageView>(R.id.ivClimbers)
        var imageRock= findViewById<ImageView>(R.id.ivRock)
        var imageWater= findViewById<ImageView>(R.id.ivWater)
        var imageAnnual= findViewById<ImageView>(R.id.ivAnnual)
        var imagePerenniel= findViewById<ImageView>(R.id.ivPerennials)
        var imageButterfly= findViewById<ImageView>(R.id.ivButterfly)
        var imageSucculents= findViewById<ImageView>(R.id.ivSucculents)

        imageHerb.setOnClickListener{
            openHerbActivity()
        }

        imageVegetable.setOnClickListener{
            openVegetableActivity()
        }
        imageMeditation.setOnClickListener{
            openMeditation()
        }
        imageHerbaltea.setOnClickListener{
            openHerbaltea()
        }
        imageFoliage.setOnClickListener{
            openFoliage()
        }
        imageSeasonal.setOnClickListener{
            openSeasonal()
        }
        imageBee.setOnClickListener{
            openBee()
        }
        imageClimbers.setOnClickListener{
            openClimbers()
        }
        imageRock.setOnClickListener{
            openRock()
        }
        imageWater.setOnClickListener{
            openWater()
        }
        imageAnnual.setOnClickListener{
            openAnnual()
        }
        imagePerenniel.setOnClickListener{
            openPerennial()
        }
        imageButterfly.setOnClickListener{
            openButterfly()
        }
        imageSucculents.setOnClickListener{
            openSucculents()
        }

    }

    public fun openHerbActivity(){
        startActivity(Intent(this@MainActivity4, HerbActivity::class.java))
    }

    public fun openVegetableActivity(){
        startActivity(Intent(this@MainActivity4, VegetableActivity::class.java))
    }
    public fun openMeditation(){
        startActivity(Intent(this@MainActivity4, MeditationActivity::class.java))
    }
    public fun openHerbaltea(){
        startActivity(Intent(this@MainActivity4, HerbalteaActivity::class.java))
    }
    public fun openFoliage(){
        startActivity(Intent(this@MainActivity4, FoliageActivity::class.java))
    }
    public fun openSeasonal(){
        startActivity(Intent(this@MainActivity4, SeasonalActivity::class.java))
    }
    public fun openBee(){
        startActivity(Intent(this@MainActivity4, BeeActivity::class.java))
    }
    public fun openClimbers(){
        startActivity(Intent(this@MainActivity4, ClimbersActivity::class.java))
    }
    public fun openRock(){
        startActivity(Intent(this@MainActivity4, RockActivity::class.java))
    }
    public fun openWater(){
        startActivity(Intent(this@MainActivity4, WaterActivity::class.java))
    }
    public fun openAnnual(){
        startActivity(Intent(this@MainActivity4, AnnualActivity::class.java))
    }
    public fun openPerennial(){
        startActivity(Intent(this@MainActivity4, PerennialActivity::class.java))
    }
    public fun openButterfly(){
        startActivity(Intent(this@MainActivity4, ButterflyActivity::class.java))
    }
    public fun openSucculents(){
        startActivity(Intent(this@MainActivity4, SucculentsActivity::class.java))
    }

}