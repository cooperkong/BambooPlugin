package network

import io.reactivex.*
import java.util.concurrent.ExecutionException
import javax.swing.SwingWorker

/**
 * Created by wenchaokong on 5/8/17.
 */
class AsyncTransformer<UpStream, DownStream> : CompletableTransformer, ObservableTransformer<UpStream, DownStream>{
    override fun apply(upstream: Observable<UpStream>?): ObservableSource<DownStream> {
        return Observable.create {
            object : SwingWorker<Any, Any>() {
                @Throws(Exception::class)
                override fun doInBackground() : UpStream{
                    return upstream!!.blockingFirst()
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
