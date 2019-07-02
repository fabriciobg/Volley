package imc.unp.com.volleysample;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonVolley {

    private static SingletonVolley myInstancia;
    private RequestQueue myQueue;
    private static Context myContext;

    public SingletonVolley(Context context) {
        myContext = context;
        myQueue = getRequestFila();
    }

    public RequestQueue getRequestFila() {
        if(myQueue == null) {
            myQueue = Volley.newRequestQueue(myContext.getApplicationContext());
        }
        return myQueue;
    }

    public static synchronized SingletonVolley getInstance(Context context) {
        if(myInstancia == null) {
            myInstancia = new SingletonVolley(context);
        }
        return myInstancia;
    }

    public <T> void addReqFila(Request<T> req){
        getRequestFila().add(req);
    }
}
