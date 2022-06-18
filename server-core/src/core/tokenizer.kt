package sas.teknologi.core

import java.time.Instant
import java.time.temporal.ChronoUnit

data class RefreshToken(
	val refreshToken: String,
	override val expireTime: Instant,
	val identity: Identity?,
	val clientId: String,
	val scopes: Set<String>
) : ExpirableToken

data class CodeToken(
	val codeToken: String,
	override val expireTime: Instant,
	val identity: Identity,
	val clientId: String,
	val redirectUri: String,
	val scopes: Set<String>
) : ExpirableToken

data class AccessToken(
	val accessToken: String,
	val tokenType: String,
	override val expireTime: Instant,
	val identity: Identity?,
	val clientId: String,
	val scopes: Set<String>,
	val refreshToken: RefreshToken?
) : ExpirableToken

interface TokenStore {

	fun storeAccessToken(accessToken: AccessToken)
	fun accessToken(token: String): AccessToken?
	fun revokeAccessToken(token: String)
	fun storeCodeToken(codeToken: CodeToken)
	fun codeToken(token: String): CodeToken?

	fun consumeCodeToken(token: String): CodeToken?
	fun storeRefreshToken(refreshToken: RefreshToken)
	fun refreshToken(token: String): RefreshToken?
	fun revokeRefreshToken(token: String)
}

interface ExpirableToken {

	val expireTime: Instant

	fun expiresIn(): Int = Instant.now().until(expireTime, ChronoUnit.SECONDS).toInt()

	fun expired(): Boolean = expiresIn() <= 0
}