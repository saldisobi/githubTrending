package com.saldi.gittrending.utils

import android.content.Context
import com.saldi.gittrending.data.model.ScanParsedItem
import com.saldi.gittrending.data.model.ScanParserItem


fun ScanParserItem.parseItem(context: Context) = ScanParsedItem(
    color = ScanUtils.getTagColor(color, context),
    id = id,
    criteria = ScanUtils.formatScanData(this),
    name = name,
    tag = tag
)