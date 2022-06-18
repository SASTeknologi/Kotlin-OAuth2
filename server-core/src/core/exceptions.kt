package sas.teknologi.core

open class OauthException(val error: OauthError, val errorDescription: String? = null) : Exception()

open class InvalidGrantException(message: String? = null) : OauthException(OauthError.INVALID_GRANT, message)

class InvalidScopeException(val notAllowedScopes: Set<String>) : OauthException(OauthError.INVALID_SCOPE, "Scopes not allowed: $notAllowedScopes")

class InvalidRequestException(message: String) : OauthException(OauthError.INVALID_REQUEST, message)

class InvalidClientException : OauthException(OauthError.INVALID_CLIENT)

class InvalidIdentityException : InvalidGrantException()

class NoRoutesFoundException : Exception {
	constructor() : super()
	constructor(message: String?) : super(message)
	constructor(message: String?, cause: Throwable?) : super(message, cause)
	constructor(cause: Throwable?) : super(cause)
}

enum class OauthError(val errorName: String) {
	UNSUPPORTED_GRANT_TYPE("unsupported_grant_type"),
	UNAUTHORIZED_CLIENT("unauthorized_client"),
	INVALID_REQUEST("invalid_request"),
	INVALID_CLIENT("invalid_client"),
	INVALID_GRANT("invalid_grant"),
	INVALID_SCOPE("invalid_scope")
}

fun OauthException.toMap(): Map<String, String> = with(mutableMapOf("error" to error.errorName)) {
	if (errorDescription != null) { this["error_description"] = errorDescription }
	toMap()
}
