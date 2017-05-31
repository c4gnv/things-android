package c4gnv.com.thingsregistry.net.model

import java.io.Serializable
import java.util.UUID

class Piece : Serializable {

    enum class PieceState {
        NORMAL,
        DIAGNOSTIC,
        WARNING,
        FAULT
    }

    var id: String? = null
    var serialNumber: String? = null
    var name: String? = null
    var description: String? = null
    var icon: String? = null
    var url: String? = null
    var token: String? = null
    var normalEvent: EventPostRequest? = null
    var diagnosticEvent: EventPostRequest? = null
    var warningEvent: EventPostRequest? = null
    var faultEvent: EventPostRequest? = null
    var state: PieceState? = null

    fun generateSerial() {
        if (serialNumber.isNullOrEmpty()) {
            this.serialNumber = UUID.randomUUID().toString()
        }
    }

    companion object {
        private const val serialVersionUID: Long = 1
    }
}
