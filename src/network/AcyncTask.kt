package network

import io.reactivex.*
import java.util.concurrent.ExecutionException
import javax.swing.SwingWorker

/**
 * Created by wenchaokong on 5/8/17.
 */
object AsyncTask {

    fun <T, V> toAsyncWorker(asyncTask : () -> T,
                             onFinish : (T) -> Unit,
                             onError : (Throwable) -> Unit = {throwable -> throwable.printStackTrace()}): SwingWorker<T, V> {
        return object : SwingWorker<T, V>() {
            @Throws(Exception::class)
            override fun doInBackground(): T {
                return asyncTask.invoke()
            }

            override fun done() {
                try {
                    onFinish.invoke(get())
                } catch (e: InterruptedException) {
                    onError(e)
                } catch (e: ExecutionException) {
                    onError(e)
                }
            }
        }
    }
}


object AsyncTranformer : CompletableTransformer{
    override fun apply(upstream: Completable?): CompletableSource {
        return Completable.create {
            object : SwingWorker<Any, Any>() {
                @Throws(Exception::class)
                override fun doInBackground() : Any{
                    return upstream!!.blockingAwait()
                }

                override fun done() {
                    try {
                        it.onComplete()
                    } catch (e: InterruptedException) {
                        it.onError(e)
                    } catch (e: ExecutionException) {
                        it.onError(e)
                    }
                }
            }.execute()
        }
    }
}
