package com.saldi.gittrending.data.model

sealed class ApiResponse<T>(
    val data: T? = null,
    val errorCode: String? = null
) {

    class ApiSuccessResponse<T>(data: T) : ApiResponse<T>(data)

    class ApiErrorResponse<T>(errorCode: String) : ApiResponse<T>(null, errorCode)

    class ApiLoading<T>(data: T? = null) : ApiResponse<T>(data)

    companion object {

        fun <T> loading() =
            ApiLoading<T>()

        fun <T> success(data: T) =
            ApiSuccessResponse(data)

        fun <T> error(message: String) =
            ApiErrorResponse<T>(
                message
            )

    }
}