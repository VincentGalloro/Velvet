package velvet.io.files

import velvet.io.Loader
import velvet.io.Writer
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class RollingBackupFileStore(private val currentVersionFileName: String,
                             private val backupFileNameGenerator: FileNameGenerator,
                             private val maxFiles: Int = 10) {

    fun <T, R> writeToFile(t: T, writer: Writer<T, R>): R?{
        val backupFile = File(backupFileNameGenerator.fName)
        if(!backupFile.parentFile.exists() && !backupFile.parentFile.mkdirs()){
            System.err.println("Could not create folders for ${backupFile.path}")
            return null
        }

        while(true){
            if((backupFile.parentFile?.listFiles()?.size ?: 0) < maxFiles){ break; }
            if(backupFile.parentFile?.listFiles()?.get(0)?.delete() != true){ break }
        }

        val r = DataOutputStream(FileOutputStream(backupFile)).use {
            try {
                writer.write(it, t)
            } catch (e: Exception) {
                System.err.println("Something went wrong when writing to ${backupFile.path}:")
                System.err.println(e)
                return null
            }
        }

        try {
            Files.copy(backupFile.toPath(),
                    Path.of(currentVersionFileName),
                    StandardCopyOption.REPLACE_EXISTING)
        }
        catch (e: Exception){
            System.err.println("Something went wrong while copying backup file to current file:")
            System.err.println(e)
            return null
        }

        return r
    }

    fun <T> loadFromFile(t: T, loader: Loader<T>){
        if(!File(currentVersionFileName).isFile){ return }

        DataInputStream(FileInputStream(currentVersionFileName)).use {
            try{
                loader.load(it, t)
            } catch (e: Exception){
                System.err.println("Something went wrong when loading from $currentVersionFileName")
                System.err.println(e)
            }
        }
    }

    fun reloadToBackup(versionsBack: Int){
        File(backupFileNameGenerator.fName).parentFile?.listFiles()?.let {
            Files.copy(it[it.size-versionsBack].toPath(),
                    Path.of(currentVersionFileName),
                    StandardCopyOption.REPLACE_EXISTING)
        }
    }
}