package sas.teknologi.uuid

import sas.teknologi.core.CodeToken
import sas.teknologi.core.CodeTokenConverter
import sas.teknologi.core.Identity
import java.time.Instant
import java.util.*

class UUIDCodeTokenConverter(private val codeTokenExpireInSeconds: Int = 300) : CodeTokenConverter {

	override fun convertToToken(
		identity: Identity,
		clientId: String,
		redirectUri: String,
		requestedScopes: Set<String>
	): CodeToken {
		return CodeToken(
			UUID.randomUUID().toString(),
			Instant.now().plusSeconds(codeTokenExpireInSeconds.toLong()),
			identity,
			clientId,
			redirectUri,
			requestedScopes
		)
	}
}