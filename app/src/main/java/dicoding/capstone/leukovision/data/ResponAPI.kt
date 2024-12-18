package dicoding.capstone.leukovision.data

data class AnalyzeResponse(
    val data: AnalyzeData
)

data class AnalyzeData(
    val predicted_class: String,
    val confidence: Float
)

data class DetectResponse(
    val detected_objects: List<DetectedObject>
)

data class DetectedObject(
    val class_name: String,
    val confidence: Float,
    val bounding_box: BoundingBox
)

data class BoundingBox(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
)