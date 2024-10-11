package company.user.repository.memory;

import company.user.repository.api.FileRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

/**
 * A repository implementation for storing and managing PNG files.
 * Files are stored in directories named after UUIDs, and the files themselves are named "image.png".
 */
public class SimpleFileRepository implements FileRepository {

    // Base directory where UUID folders and PNG files will be stored
    private final String baseDirectory;

    /**
     * Constructs a PngFileRepository that stores files under the given base directory.
     *
     * @param baseDirectory The directory where UUID-named folders will be created to store files.
     */
    public SimpleFileRepository(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Saves a PNG file to the repository under the given UUID.
     * Creates a directory named after the UUID if it doesn't exist, and stores the file as "image.png".
     *
     * @param id   The UUID used as the folder name where the file will be stored.
     * @param file The InputStream of the PNG file to be saved.
     * @throws RuntimeException If there is an error during file save.
     */
    @Override
    public void save(UUID id, InputStream file) throws IOException {
        Path directoryPath = Paths.get(baseDirectory, id.toString());
        try {

            // Create the directory if it doesn't exist
            Files.createDirectories(directoryPath);
            // Define the file path for the PNG file
            Path filePath = directoryPath.resolve("image.png");
            // Save the file to disk
            System.out.println("File saved: " + filePath.toAbsolutePath());
            try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                file.transferTo(outputStream);
            }
        } catch (IOException e) {
            throw new IOException("Error while saving the file", e);
        }
    }

    /**
     * Deletes the directory and the PNG file associated with the given UUID.
     *
     * @param id The UUID representing the directory to be deleted.
     * @throws RuntimeException If there is an error during deletion.
     */
    @Override
    public void delete(UUID id) {
        Path directoryPath = Paths.get(baseDirectory, id.toString());
        try {
            // Delete the directory and its contents
            if (Files.exists(directoryPath)) {
                Files.walk(directoryPath)
                        .sorted((a, b) -> b.compareTo(a)) // Sort from deepest files to folders
                        .map(Path::toFile)
                        .forEach(File::delete); // Delete each file
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while deleting the file", e);
        }
    }

    /**
     * Reads the PNG file associated with the given UUID.
     * Returns an InputStream to the file, or throws an exception if the file is not found.
     *
     * @param id The UUID representing the folder where the file is stored.
     * @return An InputStream for the file "image.png".
     * @throws RuntimeException If there is an error during file read or the file is not found.
     */
    @Override
    public Optional<byte[]> read(UUID id) {
        Path filePath = Paths.get(baseDirectory, id.toString(), "image.png");
        try {
            // Check if the file exists, then return the InputStream
            if (Files.exists(filePath)) {
                return Optional.of(Files.newInputStream(filePath).readAllBytes());
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the file", e);
        }
    }
}
