package velvet.io

class NoMatchingVersionHandlerException(message: String): Exception(message)
class FailedToValidateException(): Exception("File could not be validated")