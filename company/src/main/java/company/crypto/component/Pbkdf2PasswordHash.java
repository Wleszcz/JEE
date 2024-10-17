package company.crypto.component;

import jakarta.enterprise.context.Dependent;
import lombok.SneakyThrows;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Components responsible for hashing password. This implementation uses Password-Based Key Derivation Function 2
 * (PBKDF2) with SHA256 hash algorithm used in Hash-based Message Authentication Code (HMAC).
 */
@Dependent
public class Pbkdf2PasswordHash {

    /**
     * Name of the algorithm.
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Salt length.
     */
    private static final int SALT_LENGTH = 16;

    /**
     * Hash length.
     */
    private static final int HASH_LENGTH = 32;

    /**
     * Number of iterations.
     */
    private static final int ITERATIONS = 10000;

    /**
     * @param password user's password
     * @return concatenation of salt and hashed password with salt.
     */
    @SneakyThrows
    public String generate(char[] password) {
        byte[] salt = generateSalt();
        KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_LENGTH * 8);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = factory.generateSecret(spec).getEncoded();

        // Concatenate salt and hash.
        byte[] saltPlusHash = new byte[SALT_LENGTH + HASH_LENGTH];
        System.arraycopy(salt, 0, saltPlusHash, 0, SALT_LENGTH);
        System.arraycopy(hash, 0, saltPlusHash, SALT_LENGTH, HASH_LENGTH);

        // Encode salt plus hash as Base64.
        return Base64.getEncoder().encodeToString(saltPlusHash);
    }

    /**
     * @param password       user's password
     * @param hashedPassword hashed password from database
     * @return true if user's password is correct
     */
    @SneakyThrows
    public boolean verify(char[] password, String hashedPassword) {
        byte[] saltPlusHash = Base64.getDecoder().decode(hashedPassword);
        byte[] salt = new byte[SALT_LENGTH];
        byte[] hash = new byte[HASH_LENGTH];

        // Separate salt and hash.
        System.arraycopy(saltPlusHash, 0, salt, 0, SALT_LENGTH);
        System.arraycopy(saltPlusHash, SALT_LENGTH, hash, 0, HASH_LENGTH);

        KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, HASH_LENGTH * 8);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] computedHash = factory.generateSecret(spec).getEncoded();

        // Compare the computed hash with the stored hash.
        return MessageDigest.isEqual(hash, computedHash);
    }

    /**
     * @return random salt
     */
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

}
