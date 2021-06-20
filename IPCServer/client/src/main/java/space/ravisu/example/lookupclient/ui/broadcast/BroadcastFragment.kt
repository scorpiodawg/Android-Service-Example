package space.ravisu.example.lookupclient.ui.broadcast

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import space.ravisu.example.lookupclient.DATA
import space.ravisu.example.lookupclient.PACKAGE_NAME
import space.ravisu.example.lookupclient.PID
import android.os.Process
import space.ravisu.example.lookupclient.databinding.FragmentBroadcastBinding
import java.util.*


class BroadcastFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentBroadcastBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBroadcastBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnConnect.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        sendBroadcast()
        showBroadcastTime()
    }

    private fun sendBroadcast(){
        val intent = Intent()
        intent.action = "space.ravisu.example.lookupservice"
        intent.putExtra(PACKAGE_NAME, context?.packageName)
        intent.putExtra(PID, Process.myPid().toString())
        intent.putExtra(DATA, binding.edtClientData.text.toString())
        intent.component = ComponentName("space.ravisu.example.lookupservice", "space.ravisu.example.lookupservice.IPCBroadcastReceiver")
        activity?.applicationContext?.sendBroadcast(intent)
    }

    private fun showBroadcastTime(){
        val cal = Calendar.getInstance()
        val time ="${cal.get(Calendar.HOUR)}:${cal.get(Calendar.MINUTE)}:${cal.get(Calendar.SECOND)}"
        binding.linearLayoutClientInfo.visibility = View.VISIBLE
        binding.txtDate.text = time
    }
}
