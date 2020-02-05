package velvet.io.files

import java.nio.file.Path

class SystemTimePathGenerator(private val prefix: String,
                              private val suffix: String) : PathGenerator {
    override val path: Path
        get() = Path.of("$prefix${System.currentTimeMillis()}$suffix")
}