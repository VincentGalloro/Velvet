package velvet.io.files

import java.io.DataInputStream
import java.io.DataOutputStream
import java.nio.file.Files
import java.nio.file.Path

class RollingBackupBackupFileStore(private val backupPathGenerator: PathGenerator,
                                   private val backupIndexBridge: FileBridge,
                                   private val maxBackups: Int = 10)
    : BackupFileStore, VelvetWriteable, VelvetLoadable{

    companion object{

        fun basicSetup(name: String, maxBackups: Int = 10) = RollingBackupBackupFileStore(
                SystemTimePathGenerator("${name}_backups/", ".dat"),
                SingleFileBridge(Path.of("${name}_backup_index.dat")),
                maxBackups
        )
    }

    override var backups: MutableList<Path> = mutableListOf()

    init{
        try{
            backupIndexBridge.loadFromFile(this)
        }
        catch (_: Exception){
            System.err.println("Backup File Store could not load backups index")
        }
    }

    private fun removeBackup(backup: Path){
        backups.remove(backup)
        Files.delete(backup)

        backupIndexBridge.writeToFile(this)
    }

    override fun createBackup(primaryBridge: FileBridge) {
        if(backups.isNotEmpty() && backups.size >= maxBackups){ removeBackup(backups[0]) }

        val backup = backupPathGenerator.path
        backups.add(backup)
        primaryBridge.copyTo(backup)

        backupIndexBridge.writeToFile(this)
    }

    override fun restoreToBackup(primaryBridge: FileBridge, backup: Path) {
        primaryBridge.copyFrom(backup)
    }

    override fun write(dOut: DataOutputStream) {
        VelvetIO.writeCollection(backups, dOut){ dOut.writeUTF(it.toAbsolutePath().toString()) }
    }

    override fun load(dIn: DataInputStream) {
        VelvetIO.loadCollection(backups, dIn){ Path.of(dIn.readUTF()) }
    }
}