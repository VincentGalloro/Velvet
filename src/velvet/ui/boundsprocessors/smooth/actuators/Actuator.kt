package velvet.ui.boundsprocessors.smooth.actuators

interface Actuator<T> {

    operator fun invoke(current: T, target: T): T
}
