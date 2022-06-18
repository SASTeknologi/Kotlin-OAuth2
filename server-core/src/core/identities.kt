package sas.teknologi.core

data class Identity(val username: String, val metadata: Map<String, Any> = mapOf())

data class TokenInfo(val identity: Identity?, val client: Client, val scopes: Set<String>)

interface IdentityService : Authenticator, IdentityScopeVerifier {
	fun identityOf(forClient: Client, username: String): Identity?
}