import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
class Signal {
    var currentX: Float = 50f
    var width: Float = 0f
    var height: Float = 40f
    var yOffset: Float = 10f
    var color: Color = Color(0xFFD4AF37)  // Желтый по умолчанию

    constructor(startX: Float = 50f, width: Float = 20f, height: Float = 40f, yOffset: Float = 10f) {
        this.currentX = startX
        this.width = width
        this.height = height
        this.yOffset = yOffset
    }
}