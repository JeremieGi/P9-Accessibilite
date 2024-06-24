package fr.opc.practice.p9a11y

import android.content.Context
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.opc.practice.p9a11y.databinding.ActivityCase1Binding


class Case1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityCase1Binding

    private lateinit var accessibilityManager: AccessibilityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCase1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var quantity = 0

        accessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        binding.quantityText.text = "$quantity"
        binding.addButton.setOnClickListener {
            quantity++
            binding.quantityText.text = "$quantity"
            talkBackAnnounceQuantity()
        }

        binding.removeButton.setOnClickListener {
            if (quantity > 0) {
                quantity--
                binding.quantityText.text = "$quantity"
                talkBackAnnounceQuantity()
            } else {
                Toast.makeText(this, getString(R.string.impossible_d_avoir_une_quantit_n_gative), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // Ajout d'un AccessibilityEvent

    private fun talkBackAnnounceQuantity() {

        val quantity = binding.quantityText.text

        // Si talkBack est activé
        if (accessibilityManager.isEnabled()) {

            val sAnnouncement = getString(R.string.quantit_actuelle, quantity)

            // Etape 2 - Point 4
            // Le contructeur AccessibilityEvent est dispo qu'à prtir de l'APi 30
            // val event2 = AccessibilityEvent(AccessibilityEvent.TYPE_ANNOUNCEMENT)
            // J'utilise donc obtain qui est deprecated

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
