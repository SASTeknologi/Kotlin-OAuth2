package sas.teknologi.storage.client

import sas.teknologi.core.Client
import sas.teknologi.core.ClientService
import sas.teknologi.storage.ClientConfiguration

class InMemory : ClientService {

	private val clients = mutableListOf<ClientConfiguration>()

	override fun clientOf(clientId: String): Client? {
		return clients.filter { it.clientId == clientId }
			.map { client -> Client(client.clientId!!, client.scopes, client.redirectUris, client.authorizedGrantTypes) }
			.firstOrNull()
	}

	override fun validClient(client: Client, clientSecret: String): Boolean {
		return configuredClient(client.clientId)!!.clientSecret == clientSecret
	}

	private fun configuredClient(clientId: String) = clients.firstOrNull { it.clientId == clientId }

	fun client(inline: ClientConfiguration.() -> Unit): InMemory {
		val client = ClientConfiguration()
		inline(client)
		clients.add(client)
		return this
	}
}