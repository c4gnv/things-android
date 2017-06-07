package c4gnv.com.thingsregistry.net.model

import java.io.Serializable

class EventPostRequest : Serializable {

    var serialNumber: String? = null
    var clickType: String? = null
    var rssiDb: Int = 0
    var batteryVoltageMv: Int = 0

    companion object {
        private const val serialVersionUID: Long = 1
    }
}
