package sas.teknologi.core

import sas.teknologi.`object`.ScopeParser

fun GrantingCall.authorize(passwordGrantRequest: PasswordGrantRequest): AccessToken {

	throwExceptionIfUnverifiedClient(passwordGrantRequest)

	if (passwordGrantRequest.username == null) {
		throw InvalidRequestException(INVALID_REQUEST_FIELD_MESSAGE.format("username"))
	}

	if (passwordGrantRequest.password == null) {
		throw InvalidRequestException(INVALID_REQUEST_FIELD_MESSAGE.format("password"))
	}

	val requestedClient = clientService.clientOf(passwordGrantRequest.clientId!!) ?: throw InvalidClientException()
	val authorizedGrantType = AuthorizedGrantType.PASSWORD

	if (!requestedClient.authorizedGrantTypes.contains(authorizedGrantType)) {
		throw InvalidGrantException("Authorize not allowed: '$authorizedGrantType'")
	}

	val requestedIdentity = identityService.identityOf(requestedClient, passwordGrantRequest.username)

	if (requestedIdentity == null || !identityService.validCredentials(
			requestedClient,
			requestedIdentity,
			passwordGrantRequest.password
		)
	) {
		throw InvalidIdentityException()
	}

	var requestedScopes = ScopeParser.parseScopes(passwordGrantRequest.scope).toSet()

	if (passwordGrantRequest.scope == null) {
		requestedScopes = requestedClient.clientScopes
	}

	validateScopes(requestedClient, requestedIdentity, requestedScopes)

	val accessToken = converters.accessTokenConverter.convertToToken(
		requestedIdentity,
		requestedClient.clientId,
		requestedScopes,
		converters.refreshTokenConverter.convertToToken(
			requestedIdentity,
			requestedClient.clientId,
			requestedScopes
		)
	)

	tokenStore.storeAccessToken(accessToken)
	return accessToken
}

fun GrantingCall.authorize(authorizationCodeRequest: AuthorizationCodeRequest): AccessToken {
	throwExceptionIfUnverifiedClient(authorizationCodeRequest)

	if (authorizationCodeRequest.code == null) {
		throw InvalidRequestException(INVALID_REQUEST_FIELD_MESSAGE.format("code"))
	}

	if (authorizationCodeRequest.redirectUri == null) {
		throw InvalidRequestException(INVALID_REQUEST_FIELD_MESSAGE.format("redirect_uri"))
	}

	val consumeCodeToken = tokenStore.consumeCodeToken(authorizationCodeRequest.code) ?: throw InvalidGrantException()


	if (consumeCodeToken.redirectUri != authorizationCodeRequest.redirectUri || consumeCodeToken.clientId != authorizationCodeRequest.clientId) {
		throw InvalidGrantException()
	}

	val accessToken = converters.accessTokenConverter.convertToToken(
		consumeCodeToken.identity,
		consumeCodeToken.clientId,
		consumeCodeToken.scopes,
		converters.refreshTokenConverter.convertToToken(
			consumeCodeToken.identity,
			consumeCodeToken.clientId,
			consumeCodeToken.scopes
		)
	)

	tokenStore.storeAccessToken(accessToken)

	return accessToken
}

fun GrantingCall.authorize(clientCredentialsRequest: ClientCredentialsRequest): AccessToken {
	throwExceptionIfUnverifiedClient(clientCredentialsRequest)

	val requestedClient = clientService.clientOf(clientCredentialsRequest.clientId!!) ?: throw InvalidClientException()

	val scopes = clientCredentialsRequest.scope
		?.let { ScopeParser.parseScopes(it).toSet() }
		?: requestedClient.clientScopes

	val accessToken = converters.accessTokenConverter.convertToToken(
		identity = null,
		clientId = clientCredentialsRequest.clientId,
		requestedScopes = scopes,
		refreshToken = converters.refreshTokenConverter.convertToToken(
			identity = null,
			clientId = clientCredentialsRequest.clientId,
			requestedScopes = scopes
		)
	)

	tokenStore.storeAccessToken(accessToken)
	return accessToken
}