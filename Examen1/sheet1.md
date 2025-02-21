## 📝 CHEATSHEET - Programmation Android (Cours C44)

### **1️⃣ Création d’un projet Android**
- **Démarrage**: New Project → Empty Activity → Next
- **Package Name**: Minimum 2 parties séparées par un point (ex: `com.exemple.hello`)
- **API Level**: Min SDK = 26 (modifiable via `File > Project Structure > Modules > Default Config`)

### **2️⃣ Composantes d’une Interface**
- **Layouts** :
  - `ConstraintLayout` (par défaut) → nécessite des **contraintes** pour chaque widget
  - `LinearLayout` → disposition **verticale** ou **horizontale**
  - `TableLayout` → organisation en tableau avec `TableRow`
- **Widgets** :
  - `TextView` : Texte non modifiable
  - `EditText` : Champ de saisie utilisateur
  - `Button` : Bouton cliquable
  - `Switch` : Interrupteur à deux états
  - `ProgressBar` : Indicateur de progression

### **3️⃣ Propriétés Importantes**
- **Identifiant** : `android:id="@+id/mon_element"`
- **Texte** : `android:text="Mon texte"`
- **Couleur de fond** : `android:background` *(sauf pour les boutons, utiliser `background_tint`)*
- **Mise en page** :
  - `layout_width="match_parent"` → prend toute la largeur
  - `layout_height="wrap_content"` → adapte la hauteur au contenu
  - `layout_weight="1"` → partage l’espace disponible

### **4️⃣ Gestion des Événements**
📌 **3 étapes pour gérer un événement (Ex: clic sur un bouton)**
1. **Créer un écouteur** (`View.OnClickListener`)
2. **Associer l’écouteur à la source** (`button.setOnClickListener(monEcouteur)`)
3. **Définir la méthode de réaction** (`onClick(View v)`)

🔹 Exemples d'écouteurs :
```java
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Bouton cliqué!", Toast.LENGTH_SHORT).show();
    }
});
```
ou avec une **lambda** (plus court) :
```java
button.setOnClickListener(v -> Toast.makeText(this, "Bouton cliqué!", Toast.LENGTH_SHORT).show());
```

### **5️⃣ Manipulation des Données**
- **Récupérer une valeur d’un champ texte** :  
  ```java
  String input = editText.getText().toString();
  ```
- **Convertir les types** :
  ```java
  int nombre = Integer.parseInt(input);
  double prix = Double.parseDouble("9.99");
  String texte = String.valueOf(42);
  ```
- **Modifier une valeur d’un `TextView`** :  
  ```java
  textView.setText("Nouveau texte");
  ```

### **6️⃣ Exercices Types**
🔹 **Application de suivi d’eau**
- Interface avec `ImageView`, `ProgressBar` et `TextView`
- Chaque clic sur un élément ajoute une quantité d’eau :
  - **Verre** = 150ml
  - **Bouteille** = 330ml
  - **Bidon** = 1.5L
- Progression mise à jour avec `progressBar.setProgress(valeur)`

🔹 **Transfert Interac**
- Vérifier que l’utilisateur saisit un **compte valide** (`Cheque`, `Epargne`, `EpargnePlus`)
- Gestion des soldes et validation des champs

🔹 **Clavier numérique pour système de sécurité**
- **Afficher les chiffres tapés**
- **Comparer au code correct**
- **Changer la couleur de l’écran si le code est bon (vert) ou faux (rouge)**

---

💡 **Astuce** : Toujours utiliser `findViewById(R.id.nom_element)` pour référencer les éléments de l’interface.
