package com.jazouli.bluetoothapp

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_ENABLE_BT:Int=1
    private val REQUEST_CODE_DISCOVRABLE_BT:Int=1
    lateinit var bthAdapter:BluetoothAdapter

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init bluetooth adapter
        bthAdapter=BluetoothAdapter.getDefaultAdapter()

            //check if bluetooth is available or not
        if (bthAdapter ==null){
            bluetoothEtat.text="Bluetooth is not available"
        }else{
            bluetoothEtat.text="Bluetooth is available"
        }

        //change image depending on bluetoothEtat
        if (bluetoothEtat.isEnabled){
            //BTH ON
            bluetoothIco.setImageResource(R.drawable.ic_bth_on)
        }else{
            //BTH OFF
            bluetoothIco.setImageResource(R.drawable.ic_bth_off)
        }

        //turn on bth
        TurnOnbtn.setOnClickListener{
            if (bthAdapter.isEnabled){
                Toast.makeText(this,"Bluethooth is already on",Toast.LENGTH_LONG).show()
            }else{
                var intent=Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
            }
        }

        //turn off bth
        turnOffBtn.setOnClickListener{
            if (bthAdapter.isEnabled){
                Toast.makeText(this,"Bluethooth is already off",Toast.LENGTH_LONG).show()
            }else{
                bthAdapter.disable()
                bluetoothIco.setImageResource(R.drawable.ic_bth_off)
                Toast.makeText(this,"Bluethooth  turned off",Toast.LENGTH_LONG).show()


            }
    }
        //Diecovrable
        discovrableBtn.setOnClickListener{
            if(!bthAdapter.isDiscovering){
                Toast.makeText(this,"Making ur device discovrable",Toast.LENGTH_LONG).show()
                var intent=Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent,REQUEST_CODE_DISCOVRABLE_BT )

            }
            listPaired.setOnClickListener{
                if (bthAdapter.isEnabled){
                    pairedTv.text="Paired Devices"
                    val devices=bthAdapter.bondedDevices
                    for (device in devices){
                        val deviceName=device.name
                        pairedTv.append("\nDevice:$deviceName,$device")
                    }

                }else{
                    Toast.makeText(this,"turn on bth first ",Toast.LENGTH_LONG).show()
                }
            }
        }

     fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode){
            REQUEST_CODE_ENABLE_BT ->
                if (resultCode ==Activity.RESULT_OK){
                    bluetoothIco.setImageResource(R.drawable.ic_bth_on)
                    Toast.makeText(this,"Bluethooth is on",Toast.LENGTH_LONG).show()

                }else{
                    Toast.makeText(this,"Couldn't on bth",Toast.LENGTH_LONG).show()
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}}