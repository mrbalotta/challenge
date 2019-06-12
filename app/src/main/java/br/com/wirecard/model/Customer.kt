package br.com.wirecard.model

data class Customer(
    val _links: Links,
    val addresses: List<Address>,
    val birthDate: String,
    val createdAt: String,
    val email: String,
    val fullname: String,
    val id: String,
    val ownId: String,
    val phone: Phone,
    val shippingAddress: ShippingAddress,
    val taxDocument: TaxDocument
)