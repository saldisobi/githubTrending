package com.saldi.gittrending.data.model

sealed class ApiResponse<T> {

    class ApiSuccessResponse<T>(body: T) : ApiResponse<T>()

    class ApiErrorResponse<T>(errorCode: String) : ApiResponse<T>()

    class ApiLoading<T>() : ApiResponse<T>()

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