package com.saldi.gittrending.data.model

data class ScanParsedItem(
    var color: Int,
    var criteria: List<CriteriaDto>,
    var id: Int,
    var name: String,
    var tag: String
)