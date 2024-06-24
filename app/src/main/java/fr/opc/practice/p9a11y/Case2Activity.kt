package fr.opc.practice.p9a11y

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import fr.opc.practice.p9a11y.databinding.ActivityCase2Binding

class Case2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCase2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCase2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var isFavourite = false
        setFavouriteButtonIcon(isFavourite)
        binding.favouriteButton.setOnClickListener {
            isFavourite = !isFavourite
            setFavouriteButtonIcon(isFavourite)
        }

        binding.addRecipeToBasket.setOnClickListener {
            Toast.makeText(this, getString(R.string.clic_recette), Toast.LENGTH_SHORT)
                .show()
        }

        binding.recipeCard.setOnClickListener {
            Toast.makeText(this, getString(R.string.recette_ajout_au_panier), Toast.LENGTH_SHORT)
                .show()
        }

        /**
         * TODO : Voir avec Denis => pas réussi (pas sur de comprendre l'attendu)
         * 2 - Il faut trois gestes pour parcourir une carte.
         * Nous pouvons optimiser la navigation grâce aux Accessibility Actions,
         * et faire que la carte soit parcourable en un seul geste.
         *
         * https://developer.android.com/reference/androidx/core/view/accessibility/AccessibilityNodeInfoCompat
         */

/*
        ViewCompat.addAccessibilityAction(
            binding.recipeCard,
            binding.productTitle.text.toString()
        ){ _, _ ->
            Log.d("Accessibilite","test")
            true // Retourne true pour indiquer que l'action a été gérée avec succès
        }
*/

        // Définir un AccessibilityDelegateCompat personnalisé pour votre vue
        ViewCompat.setAccessibilityDelegate(binding.recipeCard, object : AccessibilityDelegateCompat() {

            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {

                super.onInitializeAccessibilityNodeInfo(host, info)

                // Fonctionne
                val customAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    // TalkBack dis "Appuyer 2 fois pour ..."
                    AccessibilityNodeInfoCompat.ACTION_CLICK, "Action personnalisée par Jérémie"
                )
                info.addAction(customAction)

            }


            // Ecoute les actions qui ont lieu sur le cardView
            override fun performAccessibilityAction(cardView : View, action: Int, args: Bundle?): Boolean {

                when (action){
                    AccessibilityNodeInfoCompat.ACTION_ACCESSIBILITY_FOCUS -> {
                        cardView.requestFocus()
                        Log.d("performAccessibilityAction","ACTION_ACCESSIBILITY_FOCUS")
                        //return true
                    }

                    AccessibilityNodeInfoCompat.ACTION_CLICK -> {
                        Log.d("performAccessibilityAction","ACTION_CLICK")
                    }

                    else -> {
                        Log.d("performAccessibilityAction else ","$action")
                    }

                }

                return super.performAccessibilityAction(cardView, action, args)
            }

        })

    }

    private fun setFavouriteButtonIcon(isFavourite: Boolean) {

        // 3 - Selon l’état de sélection du bouton de favoris,
        // il faut que TalkBack annonce ”Ajouter aux favoris” ou “Retirer des favoris”.

        if (isFavourite) {
            binding.favouriteButton.setImageResource(R.drawable.ic_favourite_on)
            binding.favouriteButton.contentDescription = getString(R.string.cd_retirer_des_favoris) // 3
        } else {
            binding.favouriteButton.setImageResource(R.drawable.ic_favourite_off)
            binding.favouriteButton.contentDescription = getString(R.string.cd_ajouter_aux_favoris) // 3
        }
    }
}
