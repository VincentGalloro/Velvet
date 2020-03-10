package velvet.io.files

import velvet.io.Writer
import java.nio.file.Path

class AutoBackupBridge(private val fileBridge: FileBridge,
                       private val backupFileStore: BackupFileStore) :
        FileBridge by fileBridge, BackupFileStore by backupFileStore {

    companion object{

        fun basicSetup(name: String, maxBackups: Int = 10) =
                AutoBackupBridge(ValidatingFileBridge(SingleFileBridge(Path.of("$name.dat"))),
                        RollingBackupBackupFileStore.basicSetup(name, maxBackups))
    }

    override fun <T> writeToFile(writer: Writer<T>, t: T) {
        fileBridge.writeToFile(writer, t)
        backupFileStore.createBackup(fileBridge)
    }

    fun restoreToBackup(backup: Path) = restoreToBackup(this, backup)
}