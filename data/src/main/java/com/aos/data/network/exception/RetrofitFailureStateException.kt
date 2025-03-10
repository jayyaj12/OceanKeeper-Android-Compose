package com.aos.data.network.exception

class RetrofitFailureStateException(error: String ?, val code: Int) : Exception(error) {
}