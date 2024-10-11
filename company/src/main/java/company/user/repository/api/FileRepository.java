package company.user.repository.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface FileRepository {
    void save(UUID id, InputStream file) throws IOException;

    void delete(UUID id);

    Optional<byte[]> read(UUID id);
}
