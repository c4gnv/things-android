package c4gnv.com.thingsregistry.net

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ServiceCallback<T> : Callback<T> {

    private var statusCode: Int = 0

    abstract fun onSuccess(response: T)

    abstract fun onFail(error: ServiceError)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        this.statusCode = response.code()

        if (response.isSuccessful) {
            onSuccess(response.body() as T)
        } else {
            try {
                val errorJson = response.errorBody()!!.string()
                handleConnectionError(call, statusCode, errorJson)
            } catch (e: Exception) {
                handleException(call, e)
            }

        }
    }

    private fun handleConnectionError(call: Call<T>, responseCode: Int, errorJson: String) {
        onFail(ServiceError.createError(call.request(), ServiceError.ErrorType.CONNECTION))
    }

    private fun handleException(call: Call<T>, e: Exception) {
        onFail(ServiceError.createError(call.request(), ServiceError.ErrorType.UNKNOWN))
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFail(ServiceError.from(call, t))
    }
}
