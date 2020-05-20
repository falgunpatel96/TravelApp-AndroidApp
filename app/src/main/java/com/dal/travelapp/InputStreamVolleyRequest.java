package com.dal.travelapp;

import android.app.DownloadManager;
import android.net.Uri;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InputStreamVolleyRequest extends Request<byte[]> {

//    /**
//     * @param uri the HTTP or HTTPS URI to download.
//     */
//    public InputStreamVolleyRequest(Uri uri) {
//        super(uri);
//    }

    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;
    private JSONObject object;
    //create a static map for directly accessing headers
    public Map<String, String> responseHeaders;

    public InputStreamVolleyRequest(int method, String mUrl, JSONObject object, Response.Listener<byte[]> listener,
                                    Response.ErrorListener errorListener, HashMap<String, String> params) {
        // TODO Auto-generated constructor stub

        super(method, mUrl, errorListener);
        // this request would never use cache.
        setShouldCache(false);
        mListener = listener;
        mParams = params;
        this.object = object;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {

        Map<String, String> params =
                new HashMap<String, String>();
        // param1 is the name of the parameter and param its value
        try {
            params.put("email_id", object.getString("email_id"));
            params.put("booking_no", object.getString("booking_no"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }


    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }

}
