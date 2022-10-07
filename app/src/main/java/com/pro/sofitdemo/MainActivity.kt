package com.pro.sofitdemo

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pro.sofitdemo.databinding.ActivityMainBinding
import com.pro.sofitdemo.notificatin.NotificationAlarmBroadcast
import com.pro.sofitdemo.repository.Repository
import com.pro.sofitdemo.room.FavDb
import com.pro.sofitdemo.utils.MyPreferences
import com.pro.sofitdemo.viewmodel.DrinkViewModel
import com.pro.sofitdemo.views.MainViewModelFactory
import com.pro.sofitdemo.views.adapter.MainPagerAdapter
import java.util.*

class MainActivity : AppCompatActivity() {
    val pagerAdapter by lazy { MainPagerAdapter(supportFragmentManager, lifecycle) }
    lateinit var binding: ActivityMainBinding
    lateinit var drinkViewModel: DrinkViewModel
    lateinit var repository: Repository
     var pref:MyPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = Repository(FavDb.getDatabase(application).favDao())
        val viewModelFacctory = MainViewModelFactory(repository)
        drinkViewModel = ViewModelProvider(this,viewModelFacctory)[DrinkViewModel::class.java]
        pref = MyPreferences().getPrefInstance()?.getPrefInstance()
        initTabLayout()
        viewPageChanger()
        settingAlarm()
    }

    private fun initTabLayout() {
        binding.mainViewPager.adapter = pagerAdapter
        TabLayoutMediator(
            binding.mainTabs, binding.mainViewPager
        ) { tabLayout, position ->
            when (position) {
                0 -> {
                    tabLayout.text = "Home"
//                    tabLayout.setIcon(R.drawable.home_ic)
                }
                1 -> {
                    tabLayout.text = "Favorite"
//                    tabLayout.setIcon(R.drawable.fav_ic)
                }

            }
        }.attach()
        binding.mainTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (binding.mainTabs.selectedTabPosition) {
                    0 -> {}
                    1 -> {}
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                when (binding.mainTabs.selectedTabPosition) {
                    0 -> {}
                    1 -> {}
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                when (binding.mainTabs.selectedTabPosition) {
                    0 -> {}
                    1 -> {}
                }

            }
        })

    }
    private fun viewPageChanger() {
        binding.mainViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position){
                    0->{
                   drinkViewModel.homeUpdater.postValue(true)
                    }
                    1->{

                    }
                }
            }
        })
    }
    private fun settingAlarm() {
        try {
            val am = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val i = Intent(this, NotificationAlarmBroadcast::class.java)
            i.putExtra("alarmid", 2545);
            val pi = PendingIntent.getBroadcast(this, 2545, i, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            var calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, 0)
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Log.i("alaram", "${Calendar.getInstance().timeInMillis}<${calendar.timeInMillis}")
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)
        }
        catch (e: SecurityException) { }
        catch (e: Exception) { }
        catch (e: java.lang.Exception) { }


    }
}