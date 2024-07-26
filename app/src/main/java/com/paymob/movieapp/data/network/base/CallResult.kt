package com.paymob.movieapp.data.network.base

import com.paymob.movieapp.data.network.error.getError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

suspend fun <T> getResultRestAPI(call: suspend () -> Response<T>): DataState<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null)
                return DataState.Success(body)
        }

        return DataState.Failed()

    } catch (e: Exception) {
        return DataState.Error(getError(e))
    }
}

suspend fun <M, T> getResultRestAPI(mapper: Mapper<T, M>, call: suspend () -> Response<T>): DataState<M> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null)
                return DataState.Success(mapper.map(body))
        }

        return DataState.Failed()

    } catch (e: Exception) {
        return DataState.Error(getError(e))
    }
}

suspend fun <T> getResultDao(call: suspend () -> T): DataState<T> {
    return try {
        val response = call()
        DataState.Success(response)

    } catch (e: Exception) {
        error(getError(e))
    }
}

suspend fun <M, T> getResultDao(mapper: Mapper<T, M>, call: suspend () -> T): DataState<M> {
    return try {
        val result = call()
        DataState.Success(mapper.map(result))
    } catch (e: Exception) {
        DataState.Failed()
    }
}


fun <T> flowStatus(call: suspend () -> DataState<T>): Flow<DataState<T>> =
    flow {
        emit(DataState.Loading())
        val responseStatus = call.invoke()
        emit(responseStatus)
    }