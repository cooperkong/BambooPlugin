package models.expandedresult

import com.google.gson.annotations.SerializedName

data class ResultItem(@SerializedName("vcsRevisionKey")
                      val vcsRevisionKey: String = "",
                      @SerializedName("buildRelativeTime")
                      val buildRelativeTime: String = "",
                      @SerializedName("reasonSummary")
                      val reasonSummary: String = "",
                      @SerializedName("onceOff")
                      val onceOff: Boolean = false,
                      @SerializedName("restartable")
                      val restartable: Boolean = false,
                      @SerializedName("link")
                      val link: Link,
                      @SerializedName("planName")
                      val planName: String = "",
                      @SerializedName("buildCompletedTime")
                      val buildCompletedTime: String = "",
                      @SerializedName("buildNumber")
                      val buildNumber: Int = 0,
                      @SerializedName("buildState")
                      val buildState: String = "",
                      @SerializedName("jiraIssues")
                      val jiraIssues: JiraIssues,
                      @SerializedName("number")
                      val number: Int = 0,
                      @SerializedName("buildCompletedDate")
                      val buildCompletedDate: String = "",
                      @SerializedName("buildReason")
                      val buildReason: String = "",
                      @SerializedName("successfulTestCount")
                      val successfulTestCount: Int = 0,
                      @SerializedName("quarantinedTestCount")
                      val quarantinedTestCount: Int = 0,
                      @SerializedName("vcsRevisions")
                      val vcsRevisions: VcsRevisions,
                      @SerializedName("buildStartedTime")
                      val buildStartedTime: String = "",
                      @SerializedName("prettyBuildCompletedTime")
                      val prettyBuildCompletedTime: String = "",
                      @SerializedName("id")
                      val id: Int = 0,
                      @SerializedName("state")
                      val state: String = "",
                      @SerializedName("plan")
                      val plan: Plan,
                      @SerializedName("buildDuration")
                      val buildDuration: Int = 0,
                      @SerializedName("key")
                      val key: String = "",
                      @SerializedName("lifeCycleState")
                      val lifeCycleState: String = "",
                      @SerializedName("successful")
                      val successful: Boolean = false,
                      @SerializedName("artifacts")
                      val artifacts: Artifacts,
                      @SerializedName("planResultKey")
                      val planResultKey: PlanResultKey,
                      @SerializedName("continuable")
                      val continuable: Boolean = false,
                      @SerializedName("comments")
                      val comments: Comments,
                      @SerializedName("buildDurationInSeconds")
                      val buildDurationInSeconds: Int = 0,
                      @SerializedName("buildDurationDescription")
                      val buildDurationDescription: String = "",
                      @SerializedName("finished")
                      val finished: Boolean = false,
                      @SerializedName("buildTestSummary")
                      val buildTestSummary: String = "",
                      @SerializedName("master")
                      val master: Master,
                      @SerializedName("labels")
                      val labels: Labels,
                      @SerializedName("expand")
                      val expand: String = "",
                      @SerializedName("stages")
                      val stages: Stages,
                      @SerializedName("skippedTestCount")
                      val skippedTestCount: Int = 0,
                      @SerializedName("buildResultKey")
                      val buildResultKey: String = "",
                      @SerializedName("prettyBuildStartedTime")
                      val prettyBuildStartedTime: String = "",
                      @SerializedName("projectName")
                      val projectName: String = "",
                      @SerializedName("failedTestCount")
                      val failedTestCount: Int = 0,
                      @SerializedName("notRunYet")
                      val notRunYet: Boolean = false)