package amktest.music.gustavo.amktest.Utilities;

import android.support.annotation.Nullable;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public interface Communication<G> {
    void onResponse(String callId, @Nullable G data);
}
