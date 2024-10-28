package company.device.model.function;

import company.device.entity.Brand;
import company.device.model.BrandModel;
import company.device.model.UserModel;
import company.device.model.UsersModel;
import company.user.entity.User;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Converts {@link Brand} to {@link BrandModel}.
 */
public class UserToModelFunction implements Function<User, UserModel>, Serializable {


    @Override
    public UserModel apply(User entity) {
        return UserModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
