package velvet.io

import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class RollingBackupFileStore(private val currentVersionFileName: String,
                             private val backupFileNameGenerator: FileNameGenerator,
                             private val maxFiles: Int = 10) {

    fun writeToFile(writer: Writeable) : Boolean{
        val backupFile = File(backupFileNameGenerator.fName)
        if(!backupFile.parentFile.exists() && !backupFile.parentFile.mkdirs()){
            System.err.println("Could not create folders for ${backupFile.path}")
            return false;
        }

        while(true){
            if((backupFile.parentFile?.listFiles()?.size ?: 0) < maxFiles){ break; }
            if(backupFile.parentFile?.listFiles()?.get(0)?.delete() != true){ break }
        }

        if(!DataOutputStream(FileOutputStream(backupFile)).use {
            try {
                writer.write(it)
            }
            catch(e: Exception) {
                System.err.println("Something went wrong when writing to ${backupFile.path}:")
                System.err.println(e)
                return@use false
            }
            return@use true
        }){ return false; }

        try {
            Files.copy(backupFile.toPath(),
                    Path.of(currentVersionFileName),
                    StandardCopyOption.REPLACE_EXISTING)
        }
        catch (e: Exception){
            System.err.println("Something went wrong while copying backup file to current file:")
            System.err.println(e)
            return false
        }

        return true
    }

    fun loadFromFile(loadable: Loadable) : Boolean{
        if(!File(currentVersionFileName).isFile){ return false }

        if(!DataInputStream(FileInputStream(currentVersionFileName)).use {
            try{
                loadable.load(it)
            }
            catch (e: Exception){
                System.err.println("Something went wrong when loading from $currentVersionFileName")
                System.err.println(e)
                return@use false
            }
            return@use true
        }){ return false; }
        return true
    }

    fun reloadToBackup(versionsBack: Int){
        File(backupFileNameGenerator.fName).parentFile?.listFiles()?.let {
            Files.copy(it[it.size-versionsBack].toPath(),
                    Path.of(currentVersionFileName),
                    StandardCopyOption.REPLACE_EXISTING)
        }
    }
}