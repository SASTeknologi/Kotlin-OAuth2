package sas.teknologi.storage.identity

import sas.teknologi.core.Client
import sas.teknologi.core.Identity
import sas.teknologi.core.IdentityService
import sas.teknologi.storage.IdentityConfiguration

class InMemory : IdentityService {

	private val identities = mutableListOf<IdentityConfiguration>()

	override fun allowedScopes(forClient: Client, identity: Identity, scopes: Set<String>) = scopes

	override fun validCredentials(forClient: Client, identity: Identity, password: String): Boolean =
		findConfiguration(identity.username)!!.password == password

	override fun identityOf(forClient: Client, username: String): Identity? {
		val findConfiguration = findConfiguration(username) ?: return null
		return Identity(findConfiguration.username!!)
	}

	private fun findConfiguration(username: String) = identities.firstOrNull { it.username == username }

	fun identity(inline: IdentityConfiguration.() -> Unit): InMemory {
		val client = IdentityConfiguration()
		inline(client)
		identities.add(client)
		return this
	}
}
