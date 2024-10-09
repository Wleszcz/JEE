package company.device.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import company.user.entity.User;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity for device owned by the user. Represents devices basic stats
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)

public class Device implements Serializable {

    /**
     * Unique id (primary key).
     */
    private UUID id;

    /**
     * Device's name.
     */
    private String name;

    /**
     * Device's price.
     */
    private Integer price;

    /**
     * Device's brand.
     */
    private Brand brand;

    /**
     * Owner of this device.
     */
    private User user;

    /**
     * Devices mass in g.
     */
    private Integer mass;

    /**
     * Devices type .
     */
    private DeviceType deviceType;


    /**
     * Devices Example image. Images in database are stored as blobs (binary large objects).
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] image;

}
