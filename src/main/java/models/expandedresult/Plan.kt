package models.expandedresult

import com.google.gson.annotations.SerializedName

data class Plan(@SerializedName("shortKey")
                val shortKey: String = "",
                @SerializedName("link")
                val link: Link,
                @SerializedName("name")
                val name: String = "",
                @SerializedName("planKey")
                val planKey: PlanKey,
                @SerializedName("shortName")
                val shortName: String = "",
                @SerializedName("type")
                val type: String = "",
                @SerializedName("enabled")
                val enabled: Boolean = false,
                @SerializedName("key")
                val key: String = "",
                @SerializedName("master")
                val master: Master)