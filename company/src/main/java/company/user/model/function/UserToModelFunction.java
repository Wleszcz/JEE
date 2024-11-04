package company.user.model.function;

import company.user.entity.User;
import company.user.model.UserModel;

import java.io.Serializable;
import java.util.function.Function;

/**
 */
public class UserToModelFunction implements Function<User, UserModel>, Serializable {

    @Override
    public UserModel apply(User entity) {
        return UserModel.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .build();
    }

}
