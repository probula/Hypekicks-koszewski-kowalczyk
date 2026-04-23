package com.example.hypekicks_koszewski_kowalczyk

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hypekicks_koszewski_kowalczyk.model.Sneaker
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore


class AdminPanelAcivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private var sneakersListener: ListenerRegistration? = null
    private lateinit var etId: EditText
    private lateinit var etBrand: EditText
    private lateinit var etModelName: EditText
    private lateinit var etReleaseYear: EditText
    private lateinit var etResellPrice: EditText
    private lateinit var etImageUrl: EditText
    private lateinit var btnAdd: Button

    private lateinit var listView: ListView
    private val listText = mutableListOf<String>()
    private lateinit var listAdapter: ArrayAdapter<String>
    private val sneakersInList = mutableListOf<Sneaker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        etId = findViewById(R.id.etId)
        etBrand = findViewById(R.id.etBrand)
        etModelName = findViewById(R.id.etModelName)
        etReleaseYear = findViewById(R.id.etReleaseYear)
        etResellPrice = findViewById(R.id.etResellPrice)
        etImageUrl = findViewById(R.id.etImageUrl)
        btnAdd = findViewById(R.id.btnAddToDb)

        listView = findViewById(R.id.lvSneakers)
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listText)
        listView.adapter = listAdapter

        btnAdd.setOnClickListener { addSneakerToFirestore() }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val sneaker = sneakersInList.getOrNull(position)
            val sneakerId = sneaker?.id?.trim().orEmpty()

            if (sneakerId.isBlank()) {
                Toast.makeText(this, "Brak id – nie można usunąć.", Toast.LENGTH_SHORT).show()
                return@setOnItemLongClickListener true
            }

            db.collection("sneakers")
                .document(sneakerId)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Usunięto z bazy.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { err ->
                    Toast.makeText(this, "Błąd usuwania: ${err.message}", Toast.LENGTH_SHORT).show()
                }

            true
        }
    }

    override fun onStart() {
        super.onStart()

        sneakersListener = db.collection("sneakers")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Toast.makeText(this, "Błąd pobierania: ${e.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val docs = snapshot?.documents.orEmpty()
                val sneakers = docs.mapNotNull { doc ->
                    val s = runCatching { doc.toObject(Sneaker::class.java) }.getOrNull()
                    if (s != null && s.id.isBlank()) s.id = doc.id
                    s
                }
                sneakersInList.clear()
                sneakersInList.addAll(sneakers)

                listText.clear()
                listText.addAll(
                    sneakers.map { s ->
                        "${s.brand} ${s.modelName} (${s.releaseYear}) | ${s.resellPrice} | id=${s.id} | ${s.imageUrl}"
                    }
                )
                listAdapter.notifyDataSetChanged()
            }
    }

    override fun onStop() {
        sneakersListener?.remove()
        sneakersListener = null
        super.onStop()
    }
    private fun addSneakerToFirestore(){
        val idInput =etId.text?.toString()?.trim().orEmpty()
        val brand = etBrand.text?.toString()?.trim().orEmpty()
        val modelName = etModelName.text?.toString()?.trim().orEmpty()
        val releaseYearStr = etReleaseYear.text?.toString()?.trim().orEmpty()
        val resellPriceStr = etResellPrice.text?.toString()?.trim().orEmpty()
        val imageUrl = etImageUrl.text?.toString()?.trim().orEmpty()

        if (brand.isBlank() || modelName.isBlank() || releaseYearStr.isBlank() || resellPriceStr.isBlank() || imageUrl.isBlank()) {
            Toast.makeText(this, "Uzupełnij wszystkie pola.", Toast.LENGTH_SHORT).show()
            return
        }

        val releaseYear = releaseYearStr.toLongOrNull()
        if (releaseYear == null) {
            Toast.makeText(this, "Niepoprawny rok wydania.", Toast.LENGTH_SHORT).show()
            return
        }

        val resellPrice = resellPriceStr.toLongOrNull()
        if (resellPrice == null) {
            Toast.makeText(this, "Niepoprawna cena.", Toast.LENGTH_SHORT).show()
            return
        }

        val col = db.collection("sneakers")
        val docRef = if (idInput.isNotBlank()) col.document(idInput) else col.document()

        val sneaker = Sneaker(
            id = docRef.id,
            brand = brand,
            modelName = modelName,
            releaseYear = releaseYear,
            resellPrice = resellPrice,
            imageUrl = imageUrl
        )

        docRef.set(sneaker)
            .addOnSuccessListener {
                Toast.makeText(this, "Dodano do bazy.", Toast.LENGTH_SHORT).show()
                etId.setText("")
                etBrand.setText("")
                etModelName.setText("")
                etReleaseYear.setText("")
                etResellPrice.setText("")
                etImageUrl.setText("")
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Błąd zapisu: ${err.message}", Toast.LENGTH_SHORT).show()
            }


    }
}