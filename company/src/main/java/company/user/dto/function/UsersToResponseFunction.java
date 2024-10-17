package company.user.dto.function;

import company.user.dto.GetUsersResponse;
import company.user.entity.User;
import jakarta.enterprise.context.Dependent;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<User>} to {@link GetUsersResponse}.
 */
@Dependent
public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(List<User> users) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .login(user.getLogin())
                                .build())
                        .toList())
                .build();
    }

}
