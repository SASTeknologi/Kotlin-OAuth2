package sas.teknologi.uuid

import sas.teknologi.core.AccessToken
import sas.teknologi.core.AccessTokenConverter
import sas.teknologi.core.Identity
import sas.teknologi.core.RefreshToken
import java.time.Instant
import java.util.*

class UUIDAccessTokenConverter(private val accessTokenExpireInSeconds: Int = 3600) : AccessTokenConverter {

	override fun convertToToken(
		identity: Identity?,
		clientId: String,
		requestedScopes: Set<String>,
		refreshToken: RefreshToken?
	): AccessToken {
		return AccessToken(
			UUID.randomUUID().toString(),
			"bearer",
			Instant.now().plusSeconds(accessTokenExpireInSeconds.toLong()),
			identity,
			clientId,
			requestedScopes,
			refreshToken
		)
	}
}