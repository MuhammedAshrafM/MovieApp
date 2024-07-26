package com.paymob.movieapp.data.network.error


sealed class ErrorEntity(val errorMessage: String = "") {

    sealed class ApiError(private val error: String): ErrorEntity(error) {
        object InternetConnection : ErrorEntity()
        object Network : ErrorEntity()
        object TimeOutNetwork : ErrorEntity()
        object BadResponseNetwork : ErrorEntity()

        data class NotFound(val error: String) : ErrorEntity(error)

        object ServiceUnavailable : ErrorEntity()

        data class Unknown(val error: String = "") : ErrorEntity(error)

    }

    sealed class DatabaseError(private val error: String = ""): ErrorEntity(error) {
        object Unknown: ErrorEntity()
    }

}