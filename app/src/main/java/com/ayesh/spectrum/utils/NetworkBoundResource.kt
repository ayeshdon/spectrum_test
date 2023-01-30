package com.ayesh.spectrum.utils

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
   crossinline query: () -> Flow<ResultType>,
   crossinline fetch: suspend () -> RequestType,
   crossinline saveFetchResult: suspend (RequestType) -> Unit,
   crossinline  shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val result = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (thr: Throwable) {
            query().map { Resource.Error(thr.message!!, it) }
        }

    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(result)
}