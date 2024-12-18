package dicoding.capstone.leukovision.ui.history

data class ApiResponse(
    val status: String,
    val data: Data
)

data class UploadResponse(
    val success: Boolean,
    val image_url: String // URL gambar yang dikembalikan server
)

data class Data(
    val patients: List<Patient>
)

data class Patient(
    val patient_id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val address: String,
    val created_at: String,
    val updated_at: String
)