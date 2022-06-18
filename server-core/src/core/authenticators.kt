package sas.teknologi.core

interface Authenticator {
	fun validCredentials(forClient: Client, identity: Identity, password: String): Boolean
}

interface IdentityScopeVerifier {
	fun allowedScopes(forClient: Client, identity: Identity, scopes: Set<String>): Set<String>
}

data class Credentials(val username: String?, val password: String?)