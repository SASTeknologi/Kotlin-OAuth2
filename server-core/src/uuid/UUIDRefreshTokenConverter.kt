package sas.teknologi.uuid

import sas.teknologi.core.Identity
import sas.teknologi.core.RefreshToken
import sas.teknologi.core.RefreshTokenConverter
import java.time.Instant
import java.util.*

class UUIDRefreshTokenConverter(private val refreshTokenExpireInSeconds: Int = 86400) : RefreshTokenConverter {

	override fun convertToToken(identity: Identity?, clientId: String, requestedScopes: Set<String>): RefreshToken {
		return RefreshToken(
			UUID.randomUUID().toString(),
			Instant.now().plusSeconds(refreshTokenExpireInSeconds.toLong()),
			identity,
			clientId,
			requestedScopes
		)
	}
}