package network

import io.reactivex.Completable
import io.reactivex.Observable
import models.branch.Branch
import models.expandedresult.Result
import models.project.Project
import retrofit2.http.*

interface Api {

    @GET("project")
    fun getProjects() : Observable<Project>

    // ?expand=results[:9].result.stages.stage.results //show last 10 build's stages detail
    // includeAllStates will show inprogress builds
    @GET("result/{projectKey}")
    fun getResult(@Path("projectKey") projectKey : String = "MD",
                  @Query("expand") showStage : String = "results[:9].result.stages.stage.results",
                  @Query("includeAllStates") includeAllStates : Boolean = true ) : Observable<Result>


    @GET("plan/{shortKey}/branch")
    fun getProjectPlanBranches(@Path("shortKey") shortKey : String) : Observable<Branch>

    @POST("queue/{buildKey}")
    fun queueBuild(@Path("buildKey") buildKey : String) : Completable

    @DELETE("queue/{buildKey}")
    fun stopBuildsOrJobs(@Path("buildKey") buildKey : String) : Completable

}