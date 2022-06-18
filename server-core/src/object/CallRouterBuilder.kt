package sas.teknologi.`object`

import sas.teknologi.*
import sas.teknologi.core.*

internal object CallRouterBuilder {
	class Configuration {
		var tokenEndpoint: String = "/oauth/token"
		var authorizeEndpoint: String = "/oauth/authorize"
		var tokenInfoEndpoint: String = "/oauth/tokeninfo"
		var granters: List<GrantingCall.() -> Granter> = listOf()
		var tokenInfoCallback: (TokenInfo) -> Map<String, Any?> = { tokenInfo ->
			mapOf("username" to tokenInfo.identity?.username, "scopes" to tokenInfo.scopes).filterValues { it != null }
		}
	}

	fun build(configuration: Configuration, grantingCallFactory: (CallContext) -> GrantingCall) = CallRouter(
		configuration.tokenEndpoint,
		configuration.authorizeEndpoint,
		configuration.tokenInfoEndpoint,
		configuration.tokenInfoCallback,
		listOf<GrantingCall.() -> Granter>(
			{ grantPassword() },
			{ grantAuthorizationCode() },
			{ grantClientCredentials() },
			{ grantRefreshToken() }
		) + configuration.granters,
		grantingCallFactory
	)
}