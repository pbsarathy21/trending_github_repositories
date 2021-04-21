package com.pbsarathy21.trendingrepos.utils

import java.io.IOException

class NoInternetException(msg: String) : IOException(msg)
class ApiException(msg: String) : IOException(msg)
class BadRequestException(msg: String) : IOException(msg)
class AuthenticationFailureException(msg: String) : IOException(msg)
class TooManyRequestsException(msg: String) : IOException(msg)
class UrlNotFoundException(msg: String) : IOException(msg)
class ApiServerException(msg: String) : IOException(msg)
class UnknownApiException(msg: String) : IOException(msg)
class ForbiddenResourceException(msg: String) : IOException(msg)
class NotSupportedOrNotAllowedException(msg: String): IOException(msg)
class ServiceUnavailableException(msg: String) : IOException(msg)
class ActivityIntentDataMissing(msg: String) : IOException(msg)