package velvet.io.files

import java.nio.file.Path

class AutoBackupBridge(private val fileBridge: FileBridge,
                       private val backupFileStore: BackupFileStore) :
        FileBridge by fileBridge, BackupFileStore by backupFileStore {

    companion object{

        fun basicSetup(name: String, maxBackups: Int = 10) =
                AutoBackupBridge(SingleFileBridge(Path.of("${name}.dat")),
                        RollingBackupBackupFileStore.basicSetup(name, maxBackups))
    }

    override fun writeToFile(writeable: VelvetWriteable) {
        fileBridge.writeToFile(writeable)
        backupFileStore.createBackup(fileBridge)
    }

    fun restoreToBackup(backup: Path) = restoreToBackup(this, backup)
}