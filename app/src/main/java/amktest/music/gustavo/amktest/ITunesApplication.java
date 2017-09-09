package amktest.music.gustavo.amktest;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by gustavoalvarez on 08/09/17.
 */

public class ITunesApplication extends Application {

    private Realm realm;
    private static ITunesApplication instance;

    public static ITunesApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Realm.init(this);

        Realm.setDefaultConfiguration(getRealmConfig());

        realm = Realm.getDefaultInstance();

    }

    public RealmConfiguration getRealmConfig() {

        int newVersion = 1;
        return new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(newVersion)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public Realm getRealm() {
        return realm;
    }

}
