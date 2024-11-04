package company.device.model.function;

import company.device.entity.Brand;
import company.device.entity.Device;
import company.device.model.DeviceCreateModel;
import company.device.model.UserCreateModel;
import company.user.entity.User;
import company.user.entity.UserRoles;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link DeviceCreateModel} to {@link Device}.
 */
public class ModelToUserFunction implements Function<UserCreateModel, User>, Serializable {

    @Override
    @SneakyThrows
    public User apply(UserCreateModel model) {
        return User.builder()
                .id(model.getId())
                .name(model.getName())
                .login(model.getLogin())
                .password(model.getPassword())
                .surname(model.getSurname())
                .email(model.getEmail())
                .birthDate(model.getBirthDate())
                .roles(List.of(UserRoles.USER))
                .build();
    }

}
