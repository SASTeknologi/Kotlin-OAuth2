package sas.teknologi.core

import java.util.*

object CallContextBasicAuthenticator {
	fun handleAuthentication(context: CallContext, router: RedirectRouter) = with(BasicAuthenticator(context)) {
		router.route(context, this.extractCredentials()).also { response ->
			if (!response.successfulLogin)
				openAuthenticationDialog()
		}
	}
}

object BasicAuth {
	fun parseCredentials(authorization: String): Credentials {
		var username: String? = null
		var password: String? = null

		if (authorization.startsWith("basic ", true)) {
			val basicAuthorizationString = String(Base64.getDecoder().decode(authorization.substring(6)))

			with(basicAuthorizationString.split(":")) {
				if (this.size == 2) {
					username = this[0]
					password = this[1]
				}
			}
		}

		return Credentials(username, password)
	}
}

open class BasicAuthenticator(protected val context: CallContext) {
	fun extractCredentials() = BasicAuth.parseCredentials(
		context.headerCaseInsensitive("authorization") ?: ""
	)

	fun openAuthenticationDialog() {
		context.respondHeader("WWW-Authenticate", "Basic realm=\"${context.queryParameters["client_id"]}\"")
	}
}