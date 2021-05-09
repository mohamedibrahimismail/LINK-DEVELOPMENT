package  com.example.link.network.retrofit

import com.example.link.model.GenericResponse
import com.example.link.model.main.ArticlesModelItem
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Service is API call interface for retrofit
 * @author Mohamed Ibrahim
 */
interface Service {

    @GET("/v1/articles?source=the-next-web&apiKey=533af958594143758318137469b41ba9")
    fun getTheNextWeb(): Observable<GenericResponse<MutableList<ArticlesModelItem>>>

    @GET("/v1/articles?source=associated-press&apiKey=533af958594143758318137469b41ba9")
    fun getAssociatedPress(): Observable<GenericResponse<MutableList<ArticlesModelItem>>>



}