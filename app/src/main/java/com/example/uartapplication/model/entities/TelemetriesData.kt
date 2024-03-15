package com.example.uartapplication.model.entities

import android.util.Log

/**
 * Questa classe definisce l'oggetto che conterrà tutti i dati telemetrici inclusi gli errori non decodificati.
 *
 * @property battery_voltage Tensione della batteria (V).
 * @property battery_current Corrente della batteria (A).
 * @property stateOfHealth Stato di salute della batteria in percentuale [0, 100]%.
 * @property stateOfCharge Stato di carica della batteria in percentuale [0, 100]%.
 * @property battery_remaining_energy Energia rimanente della batteria in Wh [0, 100000]Wh.
 * @property battery_remaining_capacity Capacità residua della batteria (Ah).
 * @property battery_charging_status Stato di carica della batteria, enumerazione ChargingStates.
 * @property average_cells_temperature Temperatura media delle celle della batteria (°C).
 * @property keyStatus Stato della chiave, enumerazione KeyStates.
 * @property velocityValue Velocità (Km/h).
 * @property motorTemperatureL Temperatura del motore sinistro [-20, 50]°C.
 * @property motorTemperatureR Temperatura del motore destro [-20, 50]°C.
 * @property currentGear Ingranaggio attuale ("P", "R", "N", "D").
 * @property error intero rappresentante i byte di errore.
 * @property warning intero rappresentante i byte di warning.
 * @property fault intero rappresentante i byte di fault.
 */
class TelemetriesData {

    var battery_voltage: Float? = (0.0).toFloat()

    var battery_current: Float? = (0.0).toFloat()

    var stateOfHealth: Int? = 0
        set(value) {
            if(value in 0..100) {
                field = value
            } else {
                Log.i("BATTERY", "ILLEGAL VALUE FOR: stateOfHealth")
            }
        }

    var stateOfCharge: Int? = 0
        set(value) {
            if(value in 0..100) {
                field = value
            } else {
                Log.i("BATTERY", "ILLEGAL VALUE FOR: stateOfCharge")
            }
        }

    var battery_remaining_energy: Float? = (0.0).toFloat()
        set(value) {
            if (value != null && value in 0.0..100000.0) {
                field = value
            } else {
                Log.i("BATTERY", "Invalid value for: battery_remaining_energy")
            }
        }

    var battery_remaining_capacity: Float? = (0.0).toFloat()

    var battery_charging_status: ChargingStates? = ChargingStates.NOT_SIGNIFICANT
        set(value) {
            if(value in ChargingStates.values()) {
                field = value
            } else {
                Log.i("BATTERY", "ILLEGAL VALUE FOR: battery_charging_status")
            }
        }

    var average_cells_temperature: Int? = 0

    var keyStatus: KeyStates? = KeyStates.OFF
        set(value) {
            if(value in KeyStates.values()) {
                field = value
            } else {
                Log.i("BATTERY", "ILLEGAL VALUE FOR: keyStatus")
            }
        }

    var velocityValue: Int? = 0

    var motorTemperatureL: Int? = 0
        set(value) {
            if (value in -20..50) {
                field = value
            } else {
                Log.i("MOTOR", "ILLEGAL VALUE FOR: motorTemperatureL")
            }
        }

    var motorTemperatureR: Int? = 0
        set(value) {
            if (value in -20..50) {
                field = value
            } else {
                Log.i("MOTOR", "ILLEGAL VALUE FOR: motorTemperatureR")
            }
        }

    var currentGear: String? = "P"
        set(value) {
            val validValues = setOf("P", "R", "N", "D")
            if (value in validValues) {
                field = value
            } else {
                Log.i("MOTOR", "ILLEGAL VALUE FOR: currentGear")
            }
        }

    var error: Int? = 0

    var warning: Int? = 0

    var fault: Int? = 0

    companion object {
        private var instance: TelemetriesData? = null

        /**
         * Ottiene un'istanza (Singleton) della classe TelemetriesData.
         *
         * @return Un'istanza della classe TelemetriesData.
         */
        @JvmStatic
        fun getInstance(): TelemetriesData {
            if (instance == null)
                instance = TelemetriesData()
            return instance as TelemetriesData
        }
    }

}

/**
 * Enumerazione degli stati di carica della batteria.
 *
 * @property description Descrizione dello stato di carica.
 */
enum class ChargingStates(val description: String) {
    NOT_SIGNIFICANT("Not significant"),
    CHARGING("Charging"),
    BALANCING("Balancing"),
    END_OF_CHARGE("End of charge")
}

/**
 * Enumerazione degli stati della chiave.
 *
 * @property description Descrizione dello stato della chiave.
 */
enum class KeyStates(val description: String) {
    ON("On"),
    OFF("Off")
}