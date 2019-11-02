package velvet.velements.container

import velvet.structs.Vector

data class ContainerLayout(val pos: Vector = Vector.ZERO,
                           val size: Vector = Vector.ZERO,
                           val origin: Vector = Vector.ZERO,
                           val angle: Double = 0.0)