package  com.example.link.network

import com.example.link.App
import com.example.link.model.GenericResponse
import com.example.link.model.main.ArticlesModelItem
import io.reactivex.Observable


/**
 * AppRepository is the holder and repo for all network call apis
 * @author Mohamed Ibrahim
 */
class AppRepository : DataManager {
    override fun getTheNextWeb(): Observable<GenericResponse<MutableList<ArticlesModelItem>>> {
        return App.getService.getTheNextWeb()
    }

    override fun getAssociatedPress(): Observable<GenericResponse<MutableList<ArticlesModelItem>>> {
        return App.getService.getAssociatedPress()
    }

}