package ir.gonabad.core.helper

import ir.gonabad.core.domain.ErrorResponse


sealed class NetworkErrorType(override val message: String? = null, errorResponse: ErrorResponse?= null) : Throwable(message){
    data class UnAuthorized(override val message: String?= null) : NetworkErrorType(message)
    data class FieldsError(
        override val message: String?= null ,
        val errorResponse: ErrorResponse
        ) : NetworkErrorType(message , errorResponse)

    data class RateLimitError(
        override val message: String?= null ,
        val errorResponse: ErrorResponse
    ) : NetworkErrorType(message , errorResponse)

    data class Forbidden(override val message: String?= null) : NetworkErrorType(message)
    data class ResourceNotFound(override val message: String?= null) : NetworkErrorType(message)
    data class NotAllowed(override val message: String?= null) : NetworkErrorType(message)
    data class InternalServerError(override val message: String?= null) : NetworkErrorType(message)
    data class NetworkConnection(override val message: String?) : NetworkErrorType(message)
    data class BadRequest(override val message: String? = null , val errorResponse: ErrorResponse) : NetworkErrorType(message)
    data class Unknown(override val message: String? = null) : NetworkErrorType()
}
