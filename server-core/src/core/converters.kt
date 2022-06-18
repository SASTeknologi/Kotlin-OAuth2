package sas.teknologi.core

data class Converters(
	val accessTokenConverter: AccessTokenConverter,
	val refreshTokenConverter: RefreshTokenConverter,
	val codeTokenConverter: CodeTokenConverter
)

interface AccessTokenConverter {
	fun convertToToken(
		identity: Identity?,
		clientId: String,
		requestedScopes: Set<String>,
		refreshToken: RefreshToken?
	): AccessToken
}

interface CodeTokenConverter {
	fun convertToToken(
		identity: Identity,
		clientId: String,
		redirectUri: String,
		requestedScopes: Set<String>
	): CodeToken
}

interface RefreshTokenConverter {
	fun convertToToken(refreshToken: RefreshToken): RefreshToken = convertToToken(
		refreshToken.identity,
		refreshToken.clientId,
		refreshToken.scopes
	)

	fun convertToToken(
		identity: Identity?,
		clientId: String,
		requestedScopes: Set<String>
	): RefreshToken
}