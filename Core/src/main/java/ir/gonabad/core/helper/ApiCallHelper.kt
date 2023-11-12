package ir.gonabad.core.helper

import com.google.gson.Gson
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ir.ghadamyaar.aminsoft.stepcounter.helper.DataState
import ir.gonabad.core.domain.ErrorResponse
import retrofit2.HttpException
import java.io.IOException

class ApiCallHelper {
    companion object {
        private const val BAD_REQUEST = 400
        private const val UNAUTHORIZED = 401
        private const val FORBIDDEN = 403
        private const val NOT_FOUND = 404
        private const val NOT_ALLOWED = 405
        private const val FIELDS_ERROR = 422
        private const val INTERNAL_SERVER_ERROR = 500
        private const val RATELIMIT_ERROR = 429


        suspend fun <T> safeApiCall(apiCall: suspend () -> T): Flow<DataState<T>> = flow {
            emit(
                try {
                    val a = apiCall.invoke()
                    DataState.Success(a)
                }
                catch (throwable : Throwable) {
                    when (throwable) {
                        is HttpException -> DataState.NetworkError(extractHttpExceptions(throwable))
                        is IOException -> DataState.NetworkError(
                            NetworkErrorType.NetworkConnection(
                                throwable.message
                            )
                        )
                        is TimeoutCancellationException -> DataState.NetworkError(
                            NetworkErrorType.NetworkConnection(
                                throwable.message
                            )
                        )
                        else -> DataState.NetworkError(NetworkErrorType.Unknown(if (!throwable.message.isNullOrEmpty()) throwable.message else null))
                    }
                }
            )
        }

        private fun extractHttpExceptions(ex: HttpException): NetworkErrorType {
            return try {
                when (ex.code()) {
                    UNAUTHORIZED ->
                        NetworkErrorType.UnAuthorized(message = ex.message)

                    FIELDS_ERROR ->
                        NetworkErrorType.FieldsError(errorResponse = extractErrorMessage(ex))

                    FORBIDDEN ->
                        NetworkErrorType.Forbidden(message = ex.message())

                    NOT_ALLOWED ->
                        NetworkErrorType.NotAllowed(message = ex.message)

                    INTERNAL_SERVER_ERROR ->
                        NetworkErrorType.InternalServerError(message = ex.message)

                    BAD_REQUEST ->
                        NetworkErrorType.BadRequest(errorResponse = extractErrorMessage(ex))

                    NOT_FOUND ->
                        NetworkErrorType.ResourceNotFound(message = ex.message)

                    RATELIMIT_ERROR ->
                        NetworkErrorType.RateLimitError(errorResponse = extractErrorMessage(ex))

                    else -> NetworkErrorType.Unknown(message = ex.message)

                }
            } catch (exception: Exception) {
                NetworkErrorType.Unknown()
            }
        }

        private fun extractErrorMessage(ex: HttpException): ErrorResponse {
            return if (ex.response()?.errorBody()!=null) Gson().fromJson(ex.response()?.errorBody()?.string().toString() , ErrorResponse::class.java)
                else ErrorResponse("ERROR_BODY_IS_NULL" , ex.message.toString() , null , null )
        }
    }
}