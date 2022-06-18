package sas.teknologi.core

import sas.teknologi.CallRouter
import sas.teknologi.`object`.CallRouterBuilder
import sas.teknologi.uuid.UUIDAccessTokenConverter
import sas.teknologi.uuid.UUIDCodeTokenConverter
import sas.teknologi.uuid.UUIDRefreshTokenConverter

data class Configuration(val callRouter: CallRouter)

object ConfigurationBuilder {
	open class Configuration {
		internal val callRouterConfiguration = CallRouterBuilder.Configuration()

		var authorizationEndpoint: String
			get() = callRouterConfiguration.authorizeEndpoint
			set(value) {
				callRouterConfiguration.authorizeEndpoint = value
			}

		var tokenEndpoint: String
			get() = callRouterConfiguration.tokenEndpoint
			set(value) {
				callRouterConfiguration.tokenEndpoint = value
			}

		var tokenInfoEndpoint: String
			get() = callRouterConfiguration.tokenInfoEndpoint
			set(value) {
				callRouterConfiguration.tokenInfoEndpoint = value
			}

		var tokenInfoCallback: (TokenInfo) -> Map<String, Any?>
			get() = callRouterConfiguration.tokenInfoCallback
			set(value) {
				callRouterConfiguration.tokenInfoCallback = value
			}

		var granters: List<GrantingCall.() -> Granter>
			get() = callRouterConfiguration.granters
			set(value) {
				callRouterConfiguration.granters = value
			}

		var identityService: IdentityService? = null
		var clientService: ClientService? = null
		var tokenStore: TokenStore? = null
		var accessTokenConverter: AccessTokenConverter = UUIDAccessTokenConverter()
		var refreshTokenConverter: RefreshTokenConverter = UUIDRefreshTokenConverter()
		var codeTokenConverter: CodeTokenConverter = UUIDCodeTokenConverter()
		var accessTokenResponder: AccessTokenResponder = DefaultAccessTokenResponder
	}

	fun build(configure: Configuration.() -> Unit, configuration: Configuration): sas.teknologi.core.Configuration {
		configure(configuration)
		val grantingCallFactory: (CallContext) -> GrantingCall = { callContext ->
			object : GrantingCall {
				override val callContext = callContext
				override val identityService = configuration.identityService!!
				override val clientService = configuration.clientService!!
				override val tokenStore = configuration.tokenStore!!
				override val converters = Converters(
					configuration.accessTokenConverter,
					configuration.refreshTokenConverter,
					configuration.codeTokenConverter
				)
				override val accessTokenResponder = configuration.accessTokenResponder
			}
		}
		return Configuration(CallRouterBuilder.build(configuration.callRouterConfiguration, grantingCallFactory))
	}

	fun build(configure: Configuration.() -> Unit): sas.teknologi.core.Configuration {
		val configuration = Configuration()
		return build(configure, configuration)
	}
}
