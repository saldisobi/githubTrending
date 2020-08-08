package com.saldi.gittrending.data.model

data class ScanParserItem(
    var color: String,
    var criteria: List<Criteria>,
    var id: Int,
    var name: String,
    var tag: String
)