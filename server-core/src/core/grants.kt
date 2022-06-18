package sas.teknologi.core

class Granter(val grantType: String, val callback: () -> Unit)

fun granter(grantType: String, callback: () -> Unit) = Granter(grantType, callback)

interface GrantingCall {
	val callContext: CallContext
	val identityService: IdentityService
	val clientService: ClientService
	val tokenStore: TokenStore
	val converters: Converters
	val accessTokenResponder: AccessTokenResponder
}