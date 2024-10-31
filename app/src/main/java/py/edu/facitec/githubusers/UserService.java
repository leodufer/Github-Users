package py.edu.facitec.githubusers;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface UserService {
    @GET("/users")
    void getUsers(Callback<List<User>> callback);
}
