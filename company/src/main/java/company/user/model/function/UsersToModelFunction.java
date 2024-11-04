package company.user.model.function;

import company.user.entity.User;
import company.user.model.UsersModel;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<User>} to {@link UsersModel}.
 */
public class UsersToModelFunction implements Function<List<User>, UsersModel> {

    @Override
    public UsersModel apply(List<User> entity) {
        return UsersModel.builder()
                .users(entity.stream()
                        .map(user -> UsersModel.User.builder()
                                .id(user.getId())
                                .login(user.getLogin())
                                .build())
                        .toList())
                .build();
    }

}
