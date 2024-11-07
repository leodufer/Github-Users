package py.edu.facitec.githubusers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements Callback<List<User>> {

    ListView usersListView;
    TextView errorTextView;
    ProgressBar progressBar;

    Button reintentarButton;

    UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usersListView = findViewById(R.id.listViewUsers);
        errorTextView = findViewById(R.id.textViewError);
        progressBar = findViewById(R.id.progressbar);
        reintentarButton = findViewById(R.id.buttonReintentar);


        service = new RestAdapter.Builder()
                                    .setEndpoint("https://api.github.com")
                                    .build()
                                    .create(UserService.class);
        service.getUsers(this);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getAdapter().getItem(position);

                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

    }

    @Override
    public void success(List<User> users, Response response) {
        progressBar.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        usersListView.setVisibility(View.VISIBLE);
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,users);
        UserAdapter adapter = new UserAdapter(this, users);
        usersListView.setAdapter(adapter);
    }

    @Override
    public void failure(RetrofitError error) {
        progressBar.setVisibility(View.GONE);
        usersListView.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(error.getLocalizedMessage());
        reintentarButton.setVisibility(View.VISIBLE);
    }

    public void reintentar(View view) {
        errorTextView.setVisibility(View.GONE);
        reintentarButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        service.getUsers(this);
    }
}



