package company.device.model.function;

import company.device.entity.Brand;
import company.device.model.BrandsModel;
import company.device.model.UsersModel;
import company.user.entity.User;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<Brand>} to {@link BrandsModel}.
 */
public class UsersToModelFunction implements Function<List<User>, UsersModel> {

    @Override
    public UsersModel apply(List<User> entity) {
        return UsersModel.builder()
                .users(entity.stream()
                        .map(user -> UsersModel.User.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .build())
                        .toList())
                .build();
    }

}
