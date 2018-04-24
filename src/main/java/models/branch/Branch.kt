package models.branch

import com.google.gson.annotations.SerializedName

data class Branch(@SerializedName("expand")
                  val expand: String = "",
                  @SerializedName("link")
                  val link: Link,
                  @SerializedName("branches")
                  val branches: Branches)