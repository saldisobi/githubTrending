package com.saldi.gittrending.utils

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import com.saldi.gittrending.R
import com.saldi.gittrending.data.model.CriteriaDto
import com.saldi.gittrending.data.model.ScanParserItem
import com.saldi.gittrending.data.model.Variant

object ScanUtils {

    private const val COLOR_RED = "red"

    private const val COLOR_GREEN = "green"

     const val INDICATOR = "indicator"

    const val PLAIN_TEXT = "plain_text"

    private const val _TYPE = "type"

    private const val VALUE = "value"

    private const val VALUES = "values"

    private const val STUDY_TYPE = "study_type"

    private const val PARAMETER_NAME = "parameter_name"

    private const val MIN_VALUE = "min_value"

    private const val MAX_VALUE = "max_value"

    private const val DEFAULT_VALUE = "default_value"


    fun formatScanData(item: ScanParserItem): List<CriteriaDto> {


        var criteriaList = ArrayList<CriteriaDto>()



        for (i in item.criteria.indices) {

            var crieteriaText = item.criteria[i].text

            var type = item.criteria[i].type

            var variantList = ArrayList<Variant>()

            var textList: HashSet<String>? = null

            if (item.criteria[i].type == PLAIN_TEXT) {
                variantList.add(
                    Variant(
                        PLAIN_TEXT,
                        item.criteria[i].text,
                        null,
                        "",
                        0,
                        0,
                        null,
                        null
                    )
                )
            } else {

                var variableList = parseVariables(item.criteria[i].text)

                textList = variableList

                val list: MutableList<String> = ArrayList()

                var variablesJson = item.criteria[i].variable

                variableList.forEach {


                    var variablesJson = variablesJson.getAsJsonObject(it)

                    if (variablesJson.get(_TYPE).asString == VALUE) {

                        for (i in 0 until variablesJson.get(VALUES).asJsonArray.size()) {
                            list.add(variablesJson.get(VALUES).asJsonArray[i].toString())
                        }
                        variantList.add(
                            Variant(
                                variablesJson.get(_TYPE).asString,
                                null,
                                list,
                                "",
                                0,
                                0,
                                null,
                                null
                            )
                        )
                    } else {
                        Log.v("saldi111", variablesJson.get(DEFAULT_VALUE).toString())
                        var tempVariant = Variant(
                            variablesJson.get(_TYPE).asString,
                            null,
                            null,
                            variablesJson.get(DEFAULT_VALUE).toString(),
                            variablesJson.get(
                                MAX_VALUE
                            ).asInt,
                            variablesJson.get(
                                MIN_VALUE
                            ).asInt,
                            variablesJson.get(
                                PARAMETER_NAME
                            ).asString,
                            variablesJson.get(
                                STUDY_TYPE
                            ).asString
                        )

                        variantList.add(
                            tempVariant
                        )
                    }

                }
            }




            criteriaList.add(CriteriaDto(crieteriaText, type, variantList, textList))
        }
        return criteriaList
    }

    fun getTagColor(color: String, context: Context): Int {
        return when (color) {
            COLOR_GREEN -> ContextCompat.getColor(context, R.color.greenText)
            COLOR_RED -> ContextCompat.getColor(context, R.color.colorSalmon)
            else -> {
                Color.GRAY
            }
        }
    }


    private fun parseVariables(s: String): HashSet<String> {
        var textVariableMap = HashSet<String>()

        var currentWord = StringBuilder()

        var isVariable = false;

        var i = 0

        while (i < s.length) {
            if ((s[i] == ' ' && currentWord.isNotEmpty())) {
                if (isVariable)
                    textVariableMap.add(currentWord.toString())
                isVariable = false;
                currentWord.clear()
            } else if (s[i] == '$') {
                isVariable = true;
                currentWord.append(s[i])
            } else {
                currentWord.append(s[i])
            }
            i++

            if (i == s.length && isVariable) {
                textVariableMap.add(currentWord.toString())
            }
        }
        return textVariableMap
    }


}
