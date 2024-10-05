package com.example.mobile_development_lab_06

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CrimeListViewModel : ViewModel() {
//    val crimes = mutableListOf<Crime>()
//    init {
//        for (i in 0 until 100) {
//            val crime = Crime(
//                title = "Crime #$i",
//                isSolved = i % 2 == 0,
//            )
//            crimes += crime
//        }
//    }
//    private val crimeRepository =
//        CrimeRepository.get()
//    val crimeListLiveData = crimeRepository.getCrimes()
//    val crimeListLiveData = null

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

//    private val _crimeList = MutableLiveData<List<Crime>>()
//    val crimeListLiveData: LiveData<List<Crime>> get() = _crimeList
//
//    init {
//        val crimes = mutableListOf<Crime>()
//        for (i in 0 until 100) {
//            val crime = Crime(
//                title = "Crime #$i",
//                isSolved = i % 2 == 0,
//            )
//            crimes.add(crime)
//        }
//        _crimeList.value = crimes // Устанавливаем список преступлений
//    }

}