package dicoding.capstone.leukovision.ui.patients

data class ApiResponse(
    val status: String,
    val data: Data
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