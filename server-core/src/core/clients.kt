package sas.teknologi.core

data class Client(
	val clientId: String,
	val clientScopes: Set<String>,
	val redirectUris: Set<String>,
	val authorizedGrantTypes: Set<String>
)

interface ClientService {
	fun clientOf(clientId: String): Client?
	fun validClient(client: Client, clientSecret: String): Boolean
}

object AuthorizedGrantType {
	const val IMPLICIT = "implicit"
	const val PASSWORD = "password"
	const val REFRESH_TOKEN = "refresh_token"
	const val AUTHORIZATION_CODE = "authorization_code"
	const val CLIENT_CREDENTIALS = "client_credentials"
}