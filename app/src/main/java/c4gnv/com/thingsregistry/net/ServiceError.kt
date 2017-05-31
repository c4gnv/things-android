package c4gnv.com.thingsregistry.net

import java.io.IOException
import java.io.Serializable
import java.net.ConnectException
import java.net.UnknownHostException

import okhttp3.Request
import retrofit2.Call

class ServiceError(val statusCode: Int, val error: String, val message: String) : Serializable {

    enum class ErrorType {
        UNKNOWN,
        IOEXCEPTION,
        CONNECTION
    }

    companion object {

        private const val serialVersionUID: Long = 1

        fun from(call: Call<*>?, t: Throwable): ServiceError {

            val request = call?.request() as Request

            if (t is UnknownHostException || t is ConnectException) {
                return createError(request, ErrorType.CONNECTION)
            }

            if (t is IOException) {
                return createError(request, ErrorType.IOEXCEPTION)
            }

            return createError(request, ErrorType.UNKNOWN)
        }

        fun createError(request: Request, type: ErrorType): ServiceError {

            val statusCode: Int
            val error: String
            val message: String

            when (type) {
                ServiceError.ErrorType.IOEXCEPTION -> {
                    statusCode = -2
                    error = "IOException"
                    message = "Sorry, there was an IO exception."
                }
                ServiceError.ErrorType.CONNECTION -> {
                    statusCode = -3
                    error = "ConnectionError"
                    message = "Sorry, there was a connection error."
                }
                ServiceError.ErrorType.UNKNOWN -> {
                    statusCode = -1
                    error = "UnknownError"
                    message = "Sorry, something doesn't seem right."
                }
                else -> {
                    statusCode = -1
                    error = "UnknownError"
                    message = "Sorry, something doesn't seem right."
                }
            }

            return ServiceError(statusCode, error, message)
        }
    }
}
