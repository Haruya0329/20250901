package jp.ac.meijou.android.s241205111;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Optional;

import jp.ac.meijou.android.s241205111.databinding.ActivityMain5Binding;
import jp.ac.meijou.android.s241205111.databinding.ActivityMain6Binding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity6 extends AppCompatActivity {

    private ActivityMain6Binding binding;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private final Moshi moshi = new Moshi.Builder().build();

    private final JsonAdapter<Gist> gistJsonAdapter = moshi.adapter(Gist.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //getImage("https://placehold.jp/350x350.png");

        binding.button25.setOnClickListener(view -> {
            var text =binding.edittext3.getText().toString();
            var url = Uri.parse("https://placehold.jp/329fcd/000000/350x350.png")
                    .buildUpon()
                    .appendQueryParameter("text",text)
                    .build()
                    .toString();
            getImage(url);
        });


    }

    private void getImage(String url){

        var request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // InputStreamをBitmapに変換
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());

                // UIスレッド以外で更新するとクラッシュするので、UIスレッド上で実行させる
                runOnUiThread(() -> binding.imageView3.setImageBitmap(bitmap));
            }


        });

    }

}