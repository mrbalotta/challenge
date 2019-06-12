package br.com.wirecard.model

data class Order(
    val _links: Links,
    val addresses: List<Address>,
    val amount: Amount,
    val checkoutPreferences: CheckoutPreferences,
    val createdAt: String,
    val customer: Customer,
    val events: List<Event>,
    val id: String,
    val items: List<Item>,
    val ownId: String,
    val payments: List<Payment>,
    val platform: String,
    val receivers: List<Receiver>,
    val status: String,
    val updatedAt: String
) {
    enum class Status {
        WAITING, NOT_PAID, PAID, REVERTED
    }
}