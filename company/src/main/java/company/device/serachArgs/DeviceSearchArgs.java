package company.device.serachArgs;

import company.device.entity.DeviceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceSearchArgs {
    private Integer mass;
    private Integer price;
    private DeviceType deviceType;
}
