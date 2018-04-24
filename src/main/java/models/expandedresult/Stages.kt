package models.expandedresult

import com.google.gson.annotations.SerializedName

data class Stages(@SerializedName("max-result")
                  val maxResult: Int = 0,
                  @SerializedName("expand")
                  val expand: String = "",
                  @SerializedName("size")
                  val size: Int = 0,
                  @SerializedName("stage")
                  val stage: List<StageItem>?,
                  @SerializedName("start-index")
                  val startIndex: Int = 0)