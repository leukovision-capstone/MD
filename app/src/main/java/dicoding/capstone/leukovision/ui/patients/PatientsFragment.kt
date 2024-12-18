//package dicoding.capstone.leukovision.ui.patients
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import dicoding.capstone.leukovision.PatientAdapter
//import dicoding.capstone.leukovision.R
//import dicoding.capstone.leukovision.network.RetrofitInstance
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class PatientsFragment : Fragment() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var patientAdapter: PatientAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding = inflater.inflate(R.layout.fragment_patients, container, false)
////        val binding = inflater.inflate(R.layout.fragment)
//
//        recyclerView = binding.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//
//        fetchPatients()
//
//        return binding
//    }
//
//    private fun fetchPatients() {
//        RetrofitInstance.apiService.getPatients().enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                if (response.isSuccessful) {
//                    val patients = response.body()?.data?.patients ?: emptyList()
//                    patientAdapter = PatientAdapter(patients)
//                    recyclerView.adapter = patientAdapter
//                }
//            }
//
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                // Handle failure
//            }
//        })
//    }
//}