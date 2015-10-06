package in.ps;

import com.android.volley.VolleyError;

/**
 * Created by mobility on 30/09/15.
 */
public interface VolleyResponseReceiver {
    void onSuccess(int requestId, Object response);
    void onFailure(int requestId, VolleyError error);
}
