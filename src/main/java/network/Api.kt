package network

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import models.branch.Branch
import models.expandedresult.Result
import models.project.Project
import retrofit2.http.*

interface Api {

    // just to test the https connection
    @GET("userlogin.action")
    fun testConnection() : Completable

    // login call should try to retrive a list of projects (limit to max result 25)
    @GET("project")
    fun login() : Single<Project>

    // ?expand=results[:9].result.stages.stage.results //show last 10 build's stages detail
    // includeAllStates will show builds that are in progress
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