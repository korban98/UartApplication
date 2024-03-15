package com.example.uartapplication.model.utilities

import android.util.Log
import java.io.DataOutputStream
import java.io.IOException

object RootCmd {
    private const val TAG = "RootCmd"

    /**
     * Esegue il comando (Unix) senza prestare attenzione all'output del risultato
     */
    fun execRootCmdSilent(cmd: String): Int {
        var result = -1
        var dos: DataOutputStream? = null
        try {
            val p = Runtime.getRuntime().exec("su")
            dos = DataOutputStream(p.outputStream)
            Log.i(TAG, cmd)
            dos.writeBytes(
                """
                    $cmd
                    
                    """.trimIndent()
            )
            dos.flush()
            dos.writeBytes("exit\n")
            dos.flush()
            p.waitFor()
            result = p.exitValue()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (dos != null) {
                try {
                    dos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return result
    }
}