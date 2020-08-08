package com.saldi.gittrending.data.model

data class CriteriaDto(
    var text: String,
    var type: String,
    var variable: List<Variant>,
    var textList: HashSet<String>?
)