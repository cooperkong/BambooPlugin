package models.expandedresult

import com.google.gson.annotations.SerializedName

data class Result(@SerializedName("expand")
                  val expand: String = "",
                  @SerializedName("link")
                  val link: Link,
                  @SerializedName("results")
                  val results: Results)