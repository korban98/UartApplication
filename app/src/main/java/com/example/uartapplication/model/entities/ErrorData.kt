package com.example.uartapplication.model.entities

/**
 * Enumerazione delle possibili decodifiche dei segnali di errore.
 *
 * @property description Descrizione del segnale di errore.
 */
enum class ErrorData(val description: String) {
    OVER_VOLTAGE("Over Voltage"),
    UNDER_TEMPERATURE("Under Temperature"),
    OVER_TEMPERATURE("Over Temperature"),
    OVER_RESISTANCE("Over Resistance"),
    UNDER_RESISTANCE("Under Resistance"),
    OVER_TEMPERATURE_POWER_ZONE("Over Temperature Power Zone"),
    CURRENT_SENSOR("Current Sensor"),
    SPI_COMMUNICATION("SPI Communication"),
    HIGH_VOLTAGE_INCONSISTENCY("High Voltage Inconsistency"),
    RESERVED("Reserved"),
    POWER_DEVICES("Power Devices"),
    OVER_CURRENT("Over Current"),
    CAN_COMMUNICATION("CAN Communication"),
    OVER_TEMPERATURE_LOGIC_ZONE("Over Temperature Logic Zone"),
    UNDER_VOLTAGE("Under Voltage"),
    DISCHARGE_OVER_CURRENT("Discharge Over Current")
}