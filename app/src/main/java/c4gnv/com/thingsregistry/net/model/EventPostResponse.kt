package c4gnv.com.thingsregistry.net.model

import java.io.Serializable

data class EventPostResponse(var count: String = "")
    : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1
    }
}
