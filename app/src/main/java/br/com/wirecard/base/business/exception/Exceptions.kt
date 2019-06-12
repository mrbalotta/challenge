package br.com.wirecard.base.business.exception

import java.lang.RuntimeException

class AuthenticationException: RuntimeException()

class InternetConnectionException: RuntimeException()

class HttpException(val code: Int, message: String = ""): RuntimeException(message)