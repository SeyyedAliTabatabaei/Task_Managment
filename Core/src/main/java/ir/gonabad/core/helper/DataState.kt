package ir.ghadamyaar.aminsoft.stepcounter.helper

import ir.gonabad.core.helper.NetworkErrorType

sealed class DataState<out T> {
    object Loading: DataState<Nothing>()
    data class Success<out T>(val value: T): DataState<T>()
    data class LocalError(val message: String? = null): DataState<Nothing>()
    data class NetworkError(val networkError: NetworkErrorType?): DataState<Nothing>()
    object NoInternet: DataState<Nothing>()
}
