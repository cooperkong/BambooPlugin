package models.expandedresult

import com.google.gson.annotations.SerializedName

data class PlanResultKey(@SerializedName("entityKey")
                         val entityKey: EntityKey,
                         @SerializedName("resultNumber")
                         val resultNumber: Int = 0,
                         @SerializedName("key")
                         val key: String = "")