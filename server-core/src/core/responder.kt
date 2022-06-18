package sas.teknologi.core

interface AccessTokenResponder {
	fun createResponse(accessToken: AccessToken): Map<String, Any?>
}

object DefaultAccessTokenResponder : AccessTokenResponder {
	override fun createResponse(accessToken: AccessToken): Map<String, Any?> = with(accessToken) {
		mapOf(
			"access_token" to this.accessToken,
			"token_type" to this.tokenType,
			"expires_in" to this.expiresIn(),
			"refresh_token" to this.refreshToken?.refreshToken
		)
	}
}