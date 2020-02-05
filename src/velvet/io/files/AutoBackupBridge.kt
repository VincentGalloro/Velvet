package velvet.io.files

import velvet.io.Loader
import velvet.io.Reader
import velvet.io.Writer
import java.nio.file.Path

class AutoBackupBridge(private val fileBridge: FileBridge,
                       val backupFileStore: BackupFileStore) : FileBridge {

    companion object{

        fun basicSetup(name: String, maxBackups: Int = 10) =
                AutoBackupBridge(SingleFileBridge(Path.of("$name.dat")),
                        RollingBackupBackupFileStore.basicSetup(name, maxBackups))
    }

    override fun <T> writeToFile(writer: Writer<T>, t: T) {
        fileBridge.writeToFile(writer, t)
        backupFileStore.createBackup(fileBridge)
    }
    override fun <T> readFromFile(reader: Reader<T>) = fileBridge.readFromFile(reader)
    override fun <T> loadFromFile(loader: Loader<T>, t: T) = fileBridge.loadFromFile(loader, t)

    override fun copyTo(target: Path) { fileBridge.copyTo(target) }
    override fun copyFrom(source: Path) { fileBridge.copyFrom(source) }
}