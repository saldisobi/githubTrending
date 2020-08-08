package com.saldi.gittrending.data.model

import com.google.gson.JsonObject

data class Criteria(
    var text: String,
    var type: String,
    var variable: JsonObject
)