package in.ps;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

/**
 * Created by Ponsuyambu on 9/30/2015.
 */
public class VolleyHelper {
    private RequestQueue mRequestQueue;
    private Context mContext;
    private static final String TAG = "VolleyHelper";
    private static VolleyHelper instance;
    private VolleyHelper(Context context){
        this.mContext = context;
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }
    public static VolleyHelper getInstance(){
        return instance;
    }

    /**
     * Initialises VolleyHelper class
     * @param appContext
     */
    public static synchronized void init(Context appContext){
        if(instance == null)
            instance = new VolleyHelper(appContext);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(int requestId) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(requestId+"");
        }
    }

    public <T> void doPost(final int requestId,String url, Class<T> clazz,Map<String, String> headers,Map<String, String> params, final VolleyResponseReceiver responseReceiver){
        GsonRequest request = new GsonRequest(Request.Method.POST,url, clazz, headers,params, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                responseReceiver.onSuccess(requestId, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseReceiver.onFailure(requestId, error);
            }
        });
        addToRequestQueue(request, requestId +"");
    }
    public <T> void doPost(final int requestId,String url, Class<T> clazz,Map<String, String> params, final VolleyResponseReceiver responseReceiver) {
        doPost(requestId, url, clazz, null, params, responseReceiver);
    }
    public <T> void doPost(final int requestId,String url, Class<T> clazz, final VolleyResponseReceiver responseReceiver) {
        doPost(requestId,url,clazz,null,null,responseReceiver);
    }
    public <T> void doPost(final int requestId,String url,  final VolleyResponseReceiver responseReceiver) {
        doPost(requestId, url, null, null, null, responseReceiver);
    }

    public <T> void doGet(final int requestId,String url, Class<T> clazz,Map<String, String> headers,Map<String, String> params, final VolleyResponseReceiver responseReceiver){
        GsonRequest request = new GsonRequest(Request.Method.GET,url, clazz, headers,params, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                responseReceiver.onSuccess(requestId, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseReceiver.onFailure(requestId, error);
            }
        });
        addToRequestQueue(request, requestId + "");
    }

    public <T> void doGet(final int requestId,String url, Class<T> clazz,Map<String, String> params, final VolleyResponseReceiver responseReceiver) {
        doGet(requestId, url, clazz, null, params, responseReceiver);
    }
    public <T> void doGet(final int requestId,String url, Class<T> clazz, final VolleyResponseReceiver responseReceiver) {
        doGet(requestId, url, clazz, null, null, responseReceiver);
    }
    public <T> void doGet(final int requestId,String url,  final VolleyResponseReceiver responseReceiver) {
        doGet(requestId, url, null, null, null, responseReceiver);
    }
}
