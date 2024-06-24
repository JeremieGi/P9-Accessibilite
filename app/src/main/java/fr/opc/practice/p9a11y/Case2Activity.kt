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
 /*
        // Définir un AccessibilityDelegateCompat personnalisé pour votre vue
        ViewCompat.setAccessibilityDelegate(binding.recipeCard, object : AccessibilityDelegateCompat() {

            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {

                super.onInitializeAccessibilityNodeInfo(host, info)

                // Ajouter une action d'accessibilité personnalisée
                val customAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    // TalkBack dis "Appuer 2 fois pour ..."
                    AccessibilityNodeInfoCompat.ACTION_CLICK, "Action personnalisée"
                )
                info.addAction(customAction)

                val customAction2 = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    // TalkBack dis "Appuer 2 fois pour ..."
                    AccessibilityNodeInfoCompat.ACTION_ACCESSIBILITY_FOCUS, "Action personnalisée"
                )
                info.addAction(customAction2)

                // Ajouter une action d'accessibilité personnalisée pour ignorer les éléments internes du CardView
                val ignoreAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    AccessibilityNodeInfoCompat.ACTION_CLEAR_SELECTION, "Ignorer les éléments internes du CardView"
                )
                info.addAction(ignoreAction)

            }
   */

/*
            override fun performAccessibilityAction(host: View, action: Int, args: Bundle?): Boolean {
                if (action == R.id.custom_action_ignore_cardview) {
                    // Gérer l'action pour ignorer les éléments internes du CardView
                    return true // Retourner true pour indiquer que l'action a été gérée
                }
                return super.performAccessibilityAction(host, action, args)
            }

        })*/

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
