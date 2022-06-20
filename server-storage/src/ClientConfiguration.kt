package sas.teknologi.storage

data class ClientConfiguration(
	var clientId: String? = null,
	var clientSecret: String? = null,
	var scopes: Set<String> = setOf(),
	var redirectUris: Set<String> = setOf(),
	var authorizedGrantTypes: Set<String> = setOf()
)

data class IdentityConfiguration(
	var username: String? = null,
	var password: String? = null
)
