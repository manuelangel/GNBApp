package com.barney.gnbapp.base

import android.app.Activity
import com.barney.gnbapp.GNBAppApplication

fun Activity.getGNBAppApplication(): GNBAppApplication = application as GNBAppApplication