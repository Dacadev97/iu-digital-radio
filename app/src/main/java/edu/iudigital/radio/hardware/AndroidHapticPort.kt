package edu.iudigital.radio.hardware

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import edu.iudigital.radio.api.HapticPort

/**
 * Implementación de RF-05 — Motor de vibración físico.
 * Esta es la capa de HARDWARE.
 */
class AndroidHapticPort(private val context: Context) : HapticPort {

    override fun tick() {
        // 1. Obtenemos el servicio de vibración de Android
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        // 2. Ejecutamos un pulso corto (50ms). 
        // Nota: minSdk es 26 (Oreo), por lo que VibrationEffect siempre está disponible.
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}
