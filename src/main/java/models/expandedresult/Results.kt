package models.expandedresult

import com.google.gson.annotations.SerializedName

data class Results(@SerializedName("max-result")
                   val maxResult: Int = 0,
                   @SerializedName("result")
                   val result: List<ResultItem>?,
                   @SerializedName("expand")
                   val expand: String = "",
                   @SerializedName("size")
                   val size: Int = 0,
                   @SerializedName("start-index")
                   val startIndex: Int = 0)