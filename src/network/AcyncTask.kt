package network

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
