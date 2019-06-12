package br.com.wirecard.feature.order.business.dto

import br.com.wirecard.base.business.dto.Links
import br.com.wirecard.base.business.dto.Summary
import br.com.wirecard.model.Order

data class OrderList(
    val _links: Links,
    val orders: List<Order>,
    val summary: Summary
)