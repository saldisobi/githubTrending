package com.saldi.gittrending.ui.detail

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.saldi.gittrending.data.model.CriteriaDto
import com.saldi.gittrending.data.model.Variant
import com.saldi.gittrending.databinding.ItemScanBinding
import com.saldi.gittrending.utils.ScanUtils


class CriteriaAdapter(private val criteriaList: List<CriteriaDto>) :
    RecyclerView.Adapter<CriteriaAdapter.ScanListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanListViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ItemScanBinding =
            ItemScanBinding.inflate(layoutInflater, parent, false)
        return ScanListViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return criteriaList.size
    }

    override fun onBindViewHolder(holder: ScanListViewHolder, position: Int) {
        var trendingListItem = criteriaList[position];
        holder.bind(trendingListItem);
    }


    inner class ScanListViewHolder(@param:NonNull var itemTrendingBinding: ItemScanBinding) :
        RecyclerView.ViewHolder(itemTrendingBinding.root) {

        fun bind(criteriaDto: CriteriaDto) {

            var replacedList = HashMap<String, Int>()
            var replacedListVariable = HashMap<String, Variant>()
            if (criteriaDto.type == ScanUtils.PLAIN_TEXT) {
                itemTrendingBinding.name.text = criteriaDto.text
            } else {

                var criteriaText = criteriaDto.text
                var counter = 0;
                criteriaDto.textList?.forEach {
                    if (criteriaDto.variable[counter].type == ScanUtils.INDICATOR) {
                        var index = criteriaText.indexOf(it)
                        criteriaText = criteriaText.replace(
                            it,
                            criteriaDto.variable[counter].default_value.toString()
                        )
                        replacedList.put(
                            criteriaDto.variable[counter].default_value.toString(),
                            index
                        )

                        replacedListVariable.put(
                            criteriaDto.variable[counter].default_value.toString(),
                            criteriaDto.variable[counter]
                        )
                    } else {
                        var index = criteriaText.indexOf(it)
                        criteriaText = criteriaText.replace(
                            it,
                            criteriaDto.variable[counter].values?.get(0).toString()
                        )
                        replacedList.put(
                            criteriaDto.variable[counter].values?.get(0).toString(),
                            index
                        )

                        replacedListVariable.put(
                            criteriaDto.variable[counter].values?.get(0).toString(),
                            criteriaDto.variable[counter]
                        )
                    }
                    counter++
                }


                var spannableStringNew = SpannableString(criteriaText)


                counter = 0;
                for ((key, value) in replacedList) {


                    val clickableSpan: ClickableSpan = object : ClickableSpan() {
                        override fun onClick(textView: View) {
                            if (replacedListVariable[key]?.type == ScanUtils.INDICATOR) {
                                Log.v(
                                    TAG,
                                    "this is of indicator type with max = ${replacedListVariable[key]?.max_value}  min = ${replacedListVariable[key]?.min_value}"
                                )
                            } else {
                                Log.v(
                                    TAG,
                                    "this is of value type with values starting from  ${replacedListVariable[key]?.values?.get(
                                        0
                                    ).toString()}"
                                )
                            }
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = false
                        }
                    }

                    spannableStringNew.setSpan(
                        clickableSpan,
                        value,
                        value + key.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    counter++
                }



                itemTrendingBinding.name.text = spannableStringNew
                itemTrendingBinding.name.movementMethod = LinkMovementMethod.getInstance()
                itemTrendingBinding.name.highlightColor = Color.TRANSPARENT

            }
        }
    }

    companion object {
        private const val TAG = "CriteriaAdapter"

    }
}
