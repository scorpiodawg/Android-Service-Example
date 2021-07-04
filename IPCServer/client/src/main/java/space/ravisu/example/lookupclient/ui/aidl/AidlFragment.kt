package space.ravisu.example.lookupclient.ui.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import space.ravisu.example.lookupclient.R
import space.ravisu.example.lookupclient.databinding.FragmentAidlBinding
import space.ravisu.example.lookupservice.*

class AidlFragment : Fragment(), ServiceConnection, View.OnClickListener {

    private var _binding: FragmentAidlBinding? = null
    private val binding get() = _binding!!
    private var remoteService: ILookupService? = null
    private var connected = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAidlBinding.inflate(inflater, container, false)
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
            connected = if (connected) {
                disconnectToRemoteService()
                binding.txtServerPid.text = ""
                binding.txtServerConnectionCount.text = ""
                binding.btnConnect.text = getString(R.string.connect)
                binding.linearLayoutClientInfo.visibility = View.INVISIBLE
                false
            } else {
                connectToRemoteService()
                binding.linearLayoutClientInfo.visibility = View.VISIBLE
                binding.btnConnect.text = getString(R.string.disconnect)
                true
            }
    }
    private fun connectToRemoteService() {
        val intent = Intent("aidlexample")
        val pack = ILookupService::class.java.`package`
        pack?.let {
            intent.setPackage(pack.name)
            activity?.applicationContext?.bindService(
                intent, this, Context.BIND_AUTO_CREATE
            )
        }
    }

    private fun disconnectToRemoteService() {
        if(connected){
            activity?.applicationContext?.unbindService(this)
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        // Gets an instance of the AIDL interface named IIPCExample,
        // which we can use to call on the service
        remoteService = ILookupService.Stub.asInterface(service)
        binding.txtServerPid.text = remoteService?.pid.toString()
        binding.txtServerConnectionCount.text = remoteService?.connectionCount.toString()
        binding.txtResults.text = remoteService?.lookup("test")?.result?.joinToString()
        connected = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Toast.makeText(context, "IPC server has disconnected unexpectedly", Toast.LENGTH_LONG).show()
        remoteService = null
        connected = false
    }
}
