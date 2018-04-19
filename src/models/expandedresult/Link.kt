package models.expandedresult

import com.google.gson.annotations.SerializedName

data class Link(@SerializedName("rel")
                val rel: String = "",
                @SerializedName("href")
                val href: String = "")