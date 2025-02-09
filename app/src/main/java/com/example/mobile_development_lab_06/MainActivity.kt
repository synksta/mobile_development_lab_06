package com.example.mobile_development_lab_06

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_development_lab_06.databinding.ActivityMainBinding
import java.util.UUID

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(binding.fragmentContainer.id)?.let {
            // Фрагмент уже существует, ничего не делаем
        } ?: run {
            // Если фрагмент не найден, создаем новый
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(binding.fragmentContainer.id, fragment)
                .commit()
        }
    }

    override fun onCrimeSelected(crimeId: UUID)
    {
        Log.d(TAG, "MainActivity.onCrimeSelected: $crimeId")
        val fragment = CrimeFragment.newInstance(crimeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,
                fragment)
            .addToBackStack(null)
            .commit()
    }
}
