package repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
 * Listeleri dosyaya kaydetmek/yüklemek için Serialization kullanır.
 * - saveList: listeyi .ser dosyasına yazar
 * - loadListOrEmpty: dosyadan okur, yoksa boş liste döner
 */
public class SerializationStore {

    public <T> void saveList(Path path, List<T> list) {
        try {
            Files.createDirectories(AppPaths.DATA_DIR);

            try (ObjectOutputStream oos =
                         new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
                oos.writeObject(list);
            }

        } catch (IOException e) {
            throw new RuntimeException("Kaydetme hatası: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> loadListOrEmpty(Path path) {
        try {
            Files.createDirectories(AppPaths.DATA_DIR);

            if (!Files.exists(path)) return new ArrayList<>();

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(path.toFile()))) {
                Object obj = ois.readObject();
                return (List<T>) obj;
            }

        } catch (Exception e) {
            // Dosya bozuksa veya hata varsa boş listeye düş
            return new ArrayList<>();
        }
    }
}
