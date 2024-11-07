package py.edu.facitec.githubusers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(@NonNull Context context, List<User> users) {
        super(context, R.layout.item_user, R.id.textViewLogin, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        User user = getItem(position);
        ImageView avatar = view.findViewById(R.id.imageViewAvatar);
        TextView urlTextView = view.findViewById(R.id.textViewURL);

        Picasso.get().load(user.getAvatar_url()).into(avatar);

        urlTextView.setText(user.getUrl());

        return view;
    }
}
