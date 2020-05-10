package velvet.io.files

import velvet.io.Reader
import velvet.io.Writer
import velvet.io.impl.ListIO
import java.nio.file.Files
import java.nio.file.Path

class RollingBackupBackupFileStore(private val backupPathGenerator: PathGenerator,
                                   private val backupIndexBridge: FileBridge,
                                   private val maxBackups: Int = 10): BackupFileStore{

    companion object{

        fun basicSetup(name: String, maxBackups: Int = 10) = RollingBackupBackupFileStore(
                SystemTimePathGenerator("$name backups/", ".dat"),
                SingleFileBridge(Path.of("$name backup index.dat")),
                maxBackups
        )
    }

    override val backups: MutableList<Path> = mutableListOf()

    private val backupIndexLoader = ListIO.createLoader(Reader.basic{ Path.of(it.readUTF()) })
    private val backupIndexWriter = ListIO.createWriter(Writer<Path>{ d, t -> d.writeUTF(t.toAbsolutePath().toString()) })

    init{
        try{
            backupIndexBridge.loadFromFile(backupIndexLoader, backups)
        }
        catch (_: Exception){
            System.err.println("Backup File Store could not load backups index")
        }
    }

    private fun removeBackup(backup: Path){
        backups.remove(backup)
        Files.delete(backup)

        backupIndexBridge.writeToFile(backupIndexWriter, backups)
    }

    override fun createBackup(primaryBridge: FileBridge) {
        if(backups.isNotEmpty() && backups.size >= maxBackups){ removeBackup(backups[0]) }

        val backup = backupPathGenerator.path
        backups.add(backup)
        primaryBridge.copyTo(backup)

        backupIndexBridge.writeToFile(backupIndexWriter, backups)
    }

    override fun restoreToBackup(primaryBridge: FileBridge, backup: Path) {
        primaryBridge.copyFrom(backup)
    }
}