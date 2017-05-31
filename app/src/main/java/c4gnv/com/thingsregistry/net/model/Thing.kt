package c4gnv.com.thingsregistry.net.model

import java.io.Serializable
import java.util.ArrayList
import java.util.UUID

class Thing : Serializable {

    var id: String? = null
    var serialNumber: String? = null
    var name: String? = null
    var description: String? = null
    var icon: String? = null
    var typeId: String? = null
    var pieceId: List<Int>? = null
    private var pieces: MutableList<Piece>? = null

    override fun toString(): String {
        return this.name as String
    }

    fun getPieces(): List<Piece> {
        return pieces as List<Piece>
    }

    fun setPieces(pieces: MutableList<Piece>) {
        this.pieces = pieces
    }

    fun addPiece(piece: Piece) {
        if (this.pieces == null) {
            this.pieces = ArrayList<Piece>()
        }

        this.pieces!!.add(piece)
    }

    fun generateSerial() {
        if (serialNumber.isNullOrEmpty()) {
            this.serialNumber = UUID.randomUUID().toString()
        }
    }

    companion object {
        private const val serialVersionUID: Long = 1
    }
}
