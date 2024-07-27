package com.paymob.movieapp.data.network.error

import android.content.Context
import android.database.StaleDataException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.google.gson.JsonSyntaxException
import com.paymob.movieapp.R
import com.paymob.movieapp.data.network.NoConnectivityException
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun getError(throwable: Throwable): ErrorEntity {
    return when (throwable) {
        is NoConnectivityException, is UnknownHostException -> ErrorEntity.ApiError.InternetConnection

        is TimeoutException, is SocketTimeoutException -> ErrorEntity.ApiError.TimeOutNetwork

        is JsonSyntaxException -> ErrorEntity.ApiError.BadResponseNetwork

        is IOException -> ErrorEntity.ApiError.Network

        is HttpException -> {
            when (throwable.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.ApiError.NotFound(
                    throwable.message()
                )

                HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ApiError.ServiceUnavailable

                else -> ErrorEntity.ApiError.Unknown(throwable.message())
            }
        }

        is SQLiteConstraintException -> ErrorEntity.DatabaseError.Unknown
        is SQLiteException -> ErrorEntity.DatabaseError.Unknown
        is StaleDataException -> ErrorEntity.DatabaseError.Unknown

        else -> ErrorEntity.ApiError.Unknown()
    }
}

fun Context.handleError(errorEntity: ErrorEntity?): String {
    return when (errorEntity) {
        is ErrorEntity.ApiError.InternetConnection -> {
            getString(R.string.no_internet_connection)
        }

        is ErrorEntity.ApiError.Network -> {
            getString(R.string.no_internet_connection)
        }

        is ErrorEntity.ApiError.TimeOutNetwork -> {
            getString(R.string.timeout_exceeded)
        }
        else -> {
            getString(R.string.error_occurred)
        }
    }
}