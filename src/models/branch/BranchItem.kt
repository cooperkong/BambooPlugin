package models.branch

import com.google.gson.annotations.SerializedName

data class BranchItem(@SerializedName("shortKey")
                      val shortKey: String = "",
                      @SerializedName("link")
                      val link: Link,
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("description")
                      val description: String = "",
                      @SerializedName("workflowType")
                      val workflowType: String = "",
                      @SerializedName("shortName")
                      val shortName: String = "",
                      @SerializedName("latestCurrentlyActive")
                      val latestCurrentlyActive: LatestCurrentlyActive,
                      @SerializedName("enabled")
                      val enabled: Boolean = false,
                      @SerializedName("key")
                      val key: String = "")