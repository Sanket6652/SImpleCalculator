package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputBinding
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    var lastnumri=false
    var stateerror=false
    var lastdot=false

    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun onClearClick(view: View){
          binding.queTV.text=""
        lastnumri=false
    }

    fun onallclearClick(view: View){
        binding.queTV.text=""
        binding.ansTV.text=""
        stateerror=false
        lastnumri=false
        binding.ansTV.visibility=View.GONE
    }
    fun onbackClick(view: View){

        binding.queTV.text=binding.queTV.text.toString().drop(1)

        try {
            val lastchar=binding.queTV.text.toString().first()

            if (lastchar.isDigit()){
                onEqual()
            }
        }catch(e:java.lang.Exception){
            binding.ansTV.text=""
            binding.ansTV.visibility=View.GONE
            Log.e("last char error",e.toString())
        }
    }
    fun onoperatorClick(view: View){
        if(!stateerror && lastnumri){
            binding.queTV.append((view as Button).text)
            lastdot=false
            lastnumri=false
            onEqual()
        }
    }

    fun onDigiClick(view: View){
        if(stateerror){
            binding.queTV.text=(view as Button).text
            stateerror=false
        }else{
            binding.queTV.append((view as Button).text)
        }
        lastnumri=true
        onEqual()
    }

     fun onEqualClick() {
         onEqual()
         binding.queTV.text=binding.ansTV.text.toString().drop(1)
    }

   fun onEqual() {
        if(lastnumri && !stateerror){
            val txt=binding.queTV.text.toString()
            expression=ExpressionBuilder(txt).build()
        }
        try {
            val result=expression.evaluate()
            binding.ansTV.visibility=View.VISIBLE
            binding.ansTV.text= "=$result"
        }catch (ex:java.lang.ArithmeticException){
            Log.e("evalute error",ex.toString())
            binding.ansTV.text="error"
            stateerror=true
            lastnumri=false
        }
    }

}