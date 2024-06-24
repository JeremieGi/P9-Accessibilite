package fr.opc.practice.p9a11y

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.opc.practice.p9a11y.databinding.ActivityCase3Binding

class Case3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityCase3Binding

    private lateinit var accessibilityManager: AccessibilityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCase3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.labelExplication.isVisible = false

        accessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
/*
        binding.pseudoEdit.doOnTextChanged { text, _, _, _ ->

            text?.length?.let { textLength ->

                // Au ,niveau design, il faut afficher l'anomalie dans un message explicite

                val bTextOk = textLength > 2

                if (bTextOk){
                    ColorStateList.valueOf(resources.getColor(R.color.green400, theme))
                    binding.labelExplication.isVisible = false
                }
                else{
                    ColorStateList.valueOf(resources.getColor(R.color.red400, theme))
                    binding.labelExplication.isVisible = true
                    talkBackAnnounceExplication()
                }


            }
        }
*/
        binding.validateButton.setOnClickListener{

            val bTextOk = binding.pseudoEdit.text.toString().length > 2

            if (bTextOk){
                ColorStateList.valueOf(resources.getColor(R.color.green400, theme))
                binding.labelExplication.isVisible = false
                talkBackAnnounceExplication(getString(R.string.pseudo_correct))
            }
            else{
                ColorStateList.valueOf(resources.getColor(R.color.red400, theme))
                binding.labelExplication.isVisible = true
                talkBackAnnounceExplication(binding.labelExplication.text.toString())
            }

        }

    }

    private fun talkBackAnnounceExplication(sAnnouncement : String) {

        // Si talkBack est activé
        if (accessibilityManager.isEnabled()) {

            val event = AccessibilityEvent.obtain().apply {
                eventType = AccessibilityEvent.TYPE_ANNOUNCEMENT
                className = javaClass.name
                packageName = packageName
                text.add(sAnnouncement)
            }
            accessibilityManager.sendAccessibilityEvent(event)
        }
    }
}
