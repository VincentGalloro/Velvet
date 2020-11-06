package velvet.io.files

import java.nio.file.Path

interface FileBridge {

    fun writeToFile(writeable: VelvetWriteable)
    fun loadFromFile(loadable: VelvetLoadable)

    fun copyTo(target: Path)
    fun copyFrom(source: Path)
}