package br.com.wirecard.plugin.strategy

import br.com.wirecard.base.business.strategy.DeviceIDStrategy
import br.com.wirecard.plugin.dao.IdentityDAO
import java.util.*

class DeviceIDStrategyImpl(private val dao: IdentityDAO) : DeviceIDStrategy {

    init {
        val uniqueId = dao.getUniqueId()
        if(uniqueId.isEmpty()) dao.saveUniqueId(UUID.randomUUID().toString())
    }

    override val deviceId: String
        get() = dao.getUniqueId()
}