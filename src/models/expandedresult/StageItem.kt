package models.expandedresult

import com.google.gson.annotations.SerializedName

data class StageItem(@SerializedName("restartable")
                     val restartable: Boolean = false,
                     @SerializedName("description")
                     val description: String = "",
                     @SerializedName("collapsedByDefault")
                     val collapsedByDefault: Boolean = false,
                     @SerializedName("manual")
                     val manual: Boolean = false,
                     @SerializedName("displayClass")
                     val displayClass: String = "",
                     @SerializedName("expand")
                     val expand: String = "",
                     @SerializedName("displayMessage")
                     val displayMessage: String = "",
                     @SerializedName("runnable")
                     val runnable: Boolean = false,
                     @SerializedName("name")
                     val name: String = "",
                     @SerializedName("id")
                     val id: Int = 0,
                     @SerializedName("state")
                     val state: String = "",
                     @SerializedName("results")
                     val results: Results,
                     @SerializedName("lifeCycleState")
                     val lifeCycleState: String = "")