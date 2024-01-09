package ir.gonabad.taskmanagment.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.gonabad.taskmanagment.R
import ir.gonabad.taskmanagment.databinding.ActivityMainBinding
import ir.gonabad.taskmanagment.utils.BaseActivity
import ir.gonabad.taskmanagment.utils.UserInfoContainer

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_main_container) as NavHostFragment
        val navController = navHostFragment.navController

        if (UserInfoContainer.token == null){
            navController.setGraph(R.navigation.nav_auth)
        } else {
            navController.setGraph(R.navigation.nav_home)
        }
    }
}