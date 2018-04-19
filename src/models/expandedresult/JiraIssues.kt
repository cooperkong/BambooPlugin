package models.expandedresult

import com.google.gson.annotations.SerializedName

data class JiraIssues(@SerializedName("max-result")
                      val maxResult: Int = 0,
                      @SerializedName("size")
                      val size: Int = 0,
                      @SerializedName("start-index")
                      val startIndex: Int = 0)