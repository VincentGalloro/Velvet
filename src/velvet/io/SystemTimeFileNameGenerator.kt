package velvet.io

class SystemTimeFileNameGenerator(private val prefix: String,
                                  private val suffix: String) : FileNameGenerator {
    override val fName: String
        get() = "$prefix${System.currentTimeMillis()}$suffix"
}