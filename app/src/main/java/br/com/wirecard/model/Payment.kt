package br.com.wirecard.model

data class Payment(
    val fundingInstrument: FundingInstrument,
    val id: String,
    val installmentCount: Int
) {
    enum class Method {
        CREDIT_CARD, DEBIT_CARD, BOLETO, ONLINE_BANK_FINANCING, ONLINE_BANK_DEBIT, WALLET;
    }
}