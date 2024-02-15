package com.mauto.myapplication.home.model

import com.google.gson.annotations.SerializedName

data class CustResponse (

	@SerializedName("message"                   ) var message                 : String?           = null,
	@SerializedName("id"                        ) var id                      : String?           = null,
	@SerializedName("lead_id"                   ) var leadId                  : String?           = null,
	@SerializedName("future_contract_reference" ) var futureContractReference : String?           = null,
	@SerializedName("name"                      ) var name                    : String?           = null,
	@SerializedName("surname"                   ) var surname                 : String?           = null,
	@SerializedName("selected_model"            ) var selectedModel           : String?           = null,
	@SerializedName("model_list"                ) var modelList               : ArrayList<String> = arrayListOf(),
	@SerializedName("payment_amount"            ) var paymentAmount           : ArrayList<String> = arrayListOf(),
	@SerializedName("total_amount"              ) var totalAmount             : Int?              = null,
	@SerializedName("currency_code"             ) var currencyCode            : String?           = null,
	@SerializedName("status"                    ) var status                  : String?           = null,
	@SerializedName("do_payment"                ) var doPayment               : String?           = null,
	@SerializedName("do_cancel"                 ) var doCancel                : String?           = null,
	@SerializedName("type"                   ) var type                 : String?           = null,
	@SerializedName("transactions"              ) var transactions            : ArrayList<DataTransaction> = arrayListOf()
)