# Cheatsheet pour MainActivity.java dans Android Studio

## Importants Concepts et Explications

### 1. **Composants de l'UI**
- `Spinner spinnerNomCompte;` → Liste déroulante pour choisir un compte.
- `TextView champSolde;` → Affiche le solde du compte sélectionné.
- `Button boutonEnvoyer;` → Bouton pour envoyer un transfert.
- `EditText champSoldeDeTransaction;` → Saisir le montant du transfert.
- `EditText getCompteExterne;` → Saisir le compte de destination.

### 2. **Hiérarchie des Superclasses des Composants UI**

| Composant Android | Superclasse Directe | Superclasse Principale |
|-------------------|--------------------|------------------------|
| `TextView`       | `View`             | `Object`               |
| `EditText`       | `TextView`         | `View`                 |
| `Button`         | `TextView`         | `View`                 |
| `Spinner`        | `AbsSpinner`       | `View`                 |
| `AdapterView`    | `ViewGroup`        | `View`                 |

- `TextView` est la classe de base pour l'affichage du texte.
- `EditText` hérite de `TextView`, ce qui lui permet d'accepter une saisie utilisateur.
- `Button` hérite aussi de `TextView`, ce qui signifie qu'un bouton est un `TextView` interactif.
- `Spinner` hérite de `AbsSpinner`, qui est une sous-classe de `AdapterView`.

### 3. **Gestion des Comptes**
- `Hashtable<String, Compte> ht;` → Stocke les comptes avec leur ID.
- `Vector<String> choix;` → Liste des noms de comptes pour le Spinner.

### 4. **Formatage et Valeurs Initiales**
- `DecimalFormat dl = new DecimalFormat("0.00$");` → Formatage des montants.
- `double soldeFictif = 0.0;` → Valeur de solde par défaut.

## Cycle de Vie et Initialisation

### **`onCreate()` : Initialisation de l'activité**
1. **Récupération des vues**
   - `findViewById(R.id.<id>)` pour associer les composants XML aux variables.
2. **Remplissage du `Hashtable` avec les comptes**
   ```java
   ht.put("c1", new Compte("Epargne", 25345));
   ht.put("c2", new Compte("Cheque", 4560));
   ht.put("c3", new Compte("Epargne Plus", 50));
   ```
3. **Récupération des noms des comptes dans un `Vector`**
   ```java
   ht.entrySet().stream().forEach(e -> choix.add(e.getValue().getNom()));
   ```
4. **Création et assignation de l'adaptateur pour le `Spinner`**
   ```java
   ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, choix);
   spinnerNomCompte.setAdapter(adapter);
   ```
5. **Association des écouteurs**
   ```java
   Ecouteur ec = new Ecouteur();
   boutonEnvoyer.setOnClickListener(ec);
   spinnerNomCompte.setOnItemSelectedListener(ec);
   ```

## Classe interne `Ecouteur`
### **Gère les événements liés aux composants UI**

### **1. Bouton Envoyer (`onClick`)**
- Vérifie si l'entrée contient un `@` (simule une vérification de validité de compte).
- Vérifie si le solde est suffisant et effectue le transfert.
- Met à jour `champSolde` avec le nouveau solde ou affiche "Manque de fond".

```java
@Override
public void onClick(View source) {
    String CompteVers = getCompteExterne.getText().toString().toUpperCase().trim();
    if (CompteVers.contains("@")) {
        double transfert = Double.parseDouble(champSoldeDeTransaction.getText().toString());
        if (temp.transfer(transfert)) {
            champSolde.setText(dl.format(temp.getSolde()));
        }
    } else {
        getCompteExterne.setText("Manque de fond");
        champSolde.setText("0");
    }
}
```

### **2. Sélection d'un compte (`onItemSelected`)**
- Récupère le compte sélectionné.
- Met à jour `champSolde` avec le solde correspondant.

```java
@Override
public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String str_compte_temp = choix.get(position);
    temp = ht.entrySet().stream()
        .filter(e -> e.getValue().getNom().equals(str_compte_temp))
        .map(Map.Entry::getValue)
        .findAny()
        .get();
    champSolde.setText(dl.format(temp.getSolde()));
}
```

## Classe `Compte`
### **Gestion des Comptes et des Transferts**

```java
public class Compte {
    private String nom;
    private double solde;

    public Compte(String nom, double solde) {
        this.nom = nom;
        this.solde = solde;
    }

    public String getNom() {
        return nom;
    }

    public double getSolde() {
        return solde;
    }

    public boolean transfer(double montant) {
        if(this.solde >= montant) {
            this.solde -= montant;
            return true;
        } else {
            return false;
        }
    }
}
```

## Résumé
- **Gestion d'un Spinner** avec une liste de comptes.
- **Transfert d'argent** avec vérification du solde.
- **Mise à jour dynamique** des soldes.
- **Utilisation d'un `Hashtable`** pour stocker les comptes et récupérer les données efficacement.
- **Gestion des transactions via la classe `Compte`** avec une méthode `transfer()` qui vérifie le solde.
- **Hiérarchie des composants UI** permettant de mieux comprendre les relations entre `TextView`, `EditText`, `Button`, et `Spinner`.

Ce document servira de référence rapide pour comprendre et manipuler `MainActivity.java` efficacement lors de votre examen.

