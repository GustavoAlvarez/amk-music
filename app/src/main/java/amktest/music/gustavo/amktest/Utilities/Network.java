package amktest.music.gustavo.amktest.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class Network {

    private static final String ENDPOINT = "https://itunes.apple.com/search?term=";

    public static boolean isOnline(Context mContext) {
        ConnectivityManager conMgr;
        conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public Network(Context ctx, String term, String entity, Response.Listener<String> onPostsLoaded, Response.ErrorListener onPostsError) {

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(ENDPOINT);
        stringBuilder.append(term);
        stringBuilder.append("&entity=");
        stringBuilder.append(entity);
        stringBuilder.append("&limit=25");


        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        StringRequest request = new StringRequest(Request.Method.GET, stringBuilder.toString(), onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }

}
