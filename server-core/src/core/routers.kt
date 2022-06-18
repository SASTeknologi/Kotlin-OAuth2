package sas.teknologi.core

interface RedirectRouter {
	fun route(callContext: CallContext, credentials: Credentials?): RedirectRouterResponse
}

data class RedirectRouterResponse(val successfulLogin: Boolean)