package com.benyq.sodaworld.base.extensions

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

fun fromTIRAMISU() = fromSpecificVersion(Build.VERSION_CODES.TIRAMISU)
fun beforeTIRAMISU() = beforeSpecificVersion(Build.VERSION_CODES.TIRAMISU)
fun fromSV2() = fromSpecificVersion(Build.VERSION_CODES.S_V2)
fun beforeSV2() = beforeSpecificVersion(Build.VERSION_CODES.S_V2)
fun fromS() = fromSpecificVersion(Build.VERSION_CODES.S)
fun beforeS() = beforeSpecificVersion(Build.VERSION_CODES.S)
fun fromR() = fromSpecificVersion(Build.VERSION_CODES.R)
fun beforeR() = beforeSpecificVersion(Build.VERSION_CODES.R)
fun fromQ() = fromSpecificVersion(Build.VERSION_CODES.Q)
fun beforeQ() = beforeSpecificVersion(Build.VERSION_CODES.Q)
fun fromM() = fromSpecificVersion(Build.VERSION_CODES.M)
fun beforeM() = beforeSpecificVersion(Build.VERSION_CODES.M)
fun fromN() = fromSpecificVersion(Build.VERSION_CODES.N)
fun beforeN() = beforeSpecificVersion(Build.VERSION_CODES.N)
fun fromN_MR1() = fromSpecificVersion(Build.VERSION_CODES.N_MR1)
fun beforeN_MR1() = beforeSpecificVersion(Build.VERSION_CODES.N_MR1)
fun fromO() = fromSpecificVersion(Build.VERSION_CODES.O)
fun beforeO() = beforeSpecificVersion(Build.VERSION_CODES.O)
fun fromP() = fromSpecificVersion(Build.VERSION_CODES.P)
fun beforeP() = beforeSpecificVersion(Build.VERSION_CODES.P)
@ChecksSdkIntAtLeast(parameter = 0)
fun fromSpecificVersion(version: Int): Boolean = Build.VERSION.SDK_INT >= version
fun beforeSpecificVersion(version: Int): Boolean = Build.VERSION.SDK_INT < version