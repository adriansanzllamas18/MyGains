package com.example.mygains

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyGainsApp @Inject constructor() : Application() {

}
