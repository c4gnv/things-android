package c4gnv.com.thingsregistry.net.model

import java.io.Serializable
import java.util.*

data class Thing(var id: String = "",
                 var serialNumber: String = "",
                 var name: String = "",
                 var description: String = "",
                 var icon: String = "",
                 var typeId: String = "",
                 var pieceId: List<Int> = ArrayList<Int>(),
                 var pieces: MutableList<Piece> = ArrayList<Piece>())
    : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1
    }

    override fun toString(): String {
        return this.name
    }

    fun addPiece(piece: Piece) {
        this.pieces.add(piece)
    }

    fun generateSerial() {
        if (serialNumber.isNullOrEmpty()) {
            this.serialNumber = UUID.randomUUID().toString()
        }
    }
}
