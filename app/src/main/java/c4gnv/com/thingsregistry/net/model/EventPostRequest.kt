package c4gnv.com.thingsregistry.net.model

import java.io.Serializable

data class EventPostRequest(var serialNumber: String = "",
                            var clickType: String = "",
                            var rssiDb: Int = 0,
                            var batteryVoltage: Int = 0)
    : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1
    }
}
