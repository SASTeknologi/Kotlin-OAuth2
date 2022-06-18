package sas.teknologi.core

data class AuthorizationCodeRequest(
	override val clientId: String?,
	override val clientSecret: String?,
	val code: String?,
	val redirectUri: String?
) : ClientRequest {
	val grant_type = "authorization_code"
}

data class RefreshTokenRequest(
	override val clientId: String?,
	override val clientSecret: String?,
	val refreshToken: String?
) : ClientRequest {
	val grant_type = "refresh_token"
}

data class ClientCredentialsRequest(
	override val clientId: String?,
	override val clientSecret: String?,
	val scope: String?
) : ClientRequest {
	val grant_type = "client_credentials"
}

data class PasswordGrantRequest(
	override val clientId: String?,
	override val clientSecret: String?,
	val username: String?,
	val password: String?,
	val scope: String?
) : ClientRequest {
	val grant_type = "password"
}

interface CallContext {
	val path: String
	val method: String
	val headers: Map<String, String>
	val queryParameters: Map<String, String>
	val formParameters: Map<String, String>

	fun respondStatus(statusCode: Int)
	fun respondHeader(name: String, value: String)
	fun respondJson(content: Any)
	fun redirect(uri: String)
}

class RedirectAuthorizationCodeRequest(
	val clientId: String?,
	val redirectUri: String?,
	val username: String?,
	val password: String?,
	val scope: String?
)

class RedirectTokenRequest(
	val clientId: String?,
	val redirectUri: String?,
	val username: String?,
	val password: String?,
	val scope: String?
)

interface ClientRequest {
	val clientId: String?
	val clientSecret: String?
}

fun CallContext.headerCaseInsensitive(key: String) = headers
	.filter { it.key.equals(key, true) }.values.firstOrNull()