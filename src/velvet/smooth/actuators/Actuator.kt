package velvet.smooth.actuators

interface Actuator<T> {

    fun step(current: T, target: T): T
}
