package c4gnv.com.thingsregistry.net.model

import java.io.Serializable

class ThingType : Serializable {

    var id: String? = null
    var name: String? = null
    var description: String? = null
    var icon: String? = null

    override fun toString(): String {
        return name as String
    }

    companion object {
        private const val serialVersionUID: Long = 1
    }
}
