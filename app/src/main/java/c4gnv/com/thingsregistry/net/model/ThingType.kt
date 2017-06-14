package c4gnv.com.thingsregistry.net.model

import java.io.Serializable

data class ThingType(var id: String = "",
                     var name: String = "",
                     var description: String = "",
                     var icon: String = "")
    : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1
    }

    override fun toString(): String {
        return this.name
    }
}
