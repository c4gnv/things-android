package c4gnv.com.thingsregistry.net.model

import java.io.Serializable
import java.util.*

data class Piece(var id: String = "",
                 var serialNumber: String = "",
                 var name: String = "",
                 var description: String = "",
                 var icon: String = "",
                 var url: String = "",
                 var token: String = "",
                 var normalEvent: EventPostRequest = EventPostRequest(),
                 var diagnosticEvent: EventPostRequest = EventPostRequest(),
                 var warningEvent: EventPostRequest = EventPostRequest(),
                 var faultEvent: EventPostRequest = EventPostRequest(),
                 var state: PieceState = Piece.PieceState.NORMAL)
    : Serializable {

    enum class PieceState {
        NORMAL,
        DIAGNOSTIC,
        WARNING,
        FAULT
    }

    companion object {
        private const val serialVersionUID: Long = 1
    }

    fun generateSerial() {
        if (serialNumber.isNullOrEmpty()) {
            this.serialNumber = UUID.randomUUID().toString()
        }
    }
}
