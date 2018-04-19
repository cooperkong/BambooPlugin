package models.branch

import com.google.gson.annotations.SerializedName

data class Branches(@SerializedName("max-result")
                    val maxResult: Int = 0,
                    @SerializedName("expand")
                    val expand: String = "",
                    @SerializedName("size")
                    val size: Int = 0,
                    @SerializedName("start-index")
                    val startIndex: Int = 0,
                    @SerializedName("branch")
                    val branch: List<BranchItem>?)