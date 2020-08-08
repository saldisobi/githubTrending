package com.saldi.gittrending.data.model

data class Variant(
    var type: String,
    var text: String? = null,
    var values: List<String>? = null,
    var default_value: String? = null,
    var max_value: Int = 0,
    var min_value: Int = 0,
    var parameter_name: String? = null,
    var study_type: String? = null
)