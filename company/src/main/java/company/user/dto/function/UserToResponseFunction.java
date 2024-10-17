package company.user.dto.function;

import company.user.dto.GetUserResponse;
import company.user.entity.User;
import jakarta.enterprise.context.Dependent;

import java.util.function.Function;

/**
 * Converts {@link User} to {@link GetUserResponse}.
 */
@Dependent
public class UserToResponseFunction implements Function<User, GetUserResponse> {

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }

}
