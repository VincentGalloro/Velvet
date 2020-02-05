package velvet.io.files

import java.nio.file.Path

interface BackupFileStore {

    val backups: List<Path>

    fun createBackup(primaryBridge: FileBridge)
    fun restoreToBackup(primaryBridge: FileBridge, backup: Path)
}