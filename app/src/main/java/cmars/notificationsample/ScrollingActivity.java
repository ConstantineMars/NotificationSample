package cmars.notificationsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.tool.DataBinder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Locale;

import cmars.notificationsample.databinding.ActivityScrollingBinding;

import static cmars.notificationsample.Const.ACTION_NOTIFICATION_POSTED;
import static cmars.notificationsample.Const.EXTRA_TITLE;

public class ScrollingActivity extends AppCompatActivity {

    public ObservableField<String> text = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityScrollingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling);
        binding.setActivity(this);

//        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setText(getIntent());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(ACTION_NOTIFICATION_POSTED));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_NOTIFICATION_POSTED)) {
                text.set(intent.getStringExtra(EXTRA_TITLE));
            }
        }
    };

    @Override
    protected void onPause() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);

        super.onPause();
    }

    void setText(Intent intent){
        if(intent==null){
            text.set("no intent");
            return;
        }

        if (intent.getAction().equals(MainActivity.ACTION)) {
            text.set(String.format(Locale.getDefault(), "%d",intent.getIntExtra(MainActivity.EXTRA, 0)));
        } else {
            text.set("wrong action");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setText(intent);

        super.onNewIntent(intent);
    }
}
