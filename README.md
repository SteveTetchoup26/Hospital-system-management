# Hospital Management System

Application de bureau de gestion hospitalière, écrite en Java avec JavaFX et MySQL.
Elle propose trois portails : administrateur, médecin et patient.

## Fonctionnalités

| Portail | Ce qu'on peut y faire |
|---|---|
| **Admin** | Tableau de bord (compteurs, graphiques), gestion des médecins, des patients et des rendez-vous, encaissement des séjours, profil |
| **Médecin** | Tableau de bord, enregistrement de patients, gestion de ses rendez-vous, dossiers patients, profil |
| **Patient** | Suivi de son dossier et de ses rendez-vous, prise de rendez-vous, liste des médecins, profil |

## Prérequis

- **JDK 21** (Gradle le détecte automatiquement s'il est installé)
- **MySQL 8**
- Gradle n'a pas besoin d'être installé : le projet embarque le wrapper (`./gradlew`)

## Installation

### 1. Base de données

Dans le client MySQL, en tant que root :

```sql
CREATE DATABASE hospital CHARACTER SET utf8mb4;
CREATE USER 'steve'@'localhost' IDENTIFIED BY 'votre_mot_de_passe';
GRANT ALL PRIVILEGES ON hospital.* TO 'steve'@'localhost';
```

Puis créez les tables, et si vous le souhaitez chargez les données de démonstration :

```bash
mysql -usteve -p hospital < database/schema.sql
mysql -usteve -p hospital < database/seed.sql     # facultatif
```

> `seed.sql` **vide** les tables `doctor`, `patient`, `appointment` et `payment` avant de les remplir.
> Ne le lancez pas sur une base contenant de vraies données.

### 2. Identifiants de connexion

Les identifiants MySQL ne sont pas dans le code. Copiez `db.properties.example` vers :

- Linux / macOS : `~/.hospitalmanagement/db.properties`
- Windows : `C:\Users\<vous>\.hospitalmanagement\db.properties`

et renseignez-y `url`, `user` et `password`. Les photos de profil sont enregistrées dans ce même dossier.

### 3. Lancement

```bash
./gradlew run          # Linux / macOS
gradlew.bat run        # Windows
```

Dans IntelliJ : utilisez la configuration **HospitalManagement (Gradle run)**, ou la fenêtre Gradle → Tasks → application → run.

## Comptes

- **Admin** : créez un compte depuis le lien d'inscription de l'écran de connexion.
- **Données de démonstration** (`seed.sql`) :

| Portail | Identifiant | Mot de passe |
|---|---|---|
| Médecin | `DID-1` à `DID-50` | `doctor123` |
| Patient | `1` à `50` | `patient123` |

## Produire un exécutable

L'exécutable embarque son propre Java : la machine cible n'a besoin que de MySQL et du fichier `db.properties`.

`jpackage` ne produit que pour le système sur lequel il tourne : le `.exe` se construit sous Windows, le `.deb` sous Linux.

| Commande | Résultat | Emplacement |
|---|---|---|
| `./gradlew jpackageImage` | Dossier portable contenant le lanceur | `build/jpackage/HospitalManagement/` |
| `./gradlew jpackage` | Installateur (`.deb` sous Linux, `.exe` sous Windows) | `build/jpackage/` |

### Linux

```bash
./gradlew jpackage
sudo dpkg -i build/jpackage/hospitalmanagement_1.0.0_amd64.deb
/opt/hospitalmanagement/bin/HospitalManagement
```

Sans installation : `./gradlew jpackageImage` puis `build/jpackage/HospitalManagement/bin/HospitalManagement`.

### Windows

1. Installez un JDK 21 et [WiX Toolset 3](https://wixtoolset.org/) (requis par `jpackage` pour créer un `.exe`).
2. Lancez `gradlew.bat jpackage`.
3. L'installateur se trouve dans `build\jpackage\HospitalManagement-1.0.0.exe`.

Sans WiX : `gradlew.bat jpackageImage` produit `build\jpackage\HospitalManagement\HospitalManagement.exe`, utilisable sans installation.

L'icône de l'exécutable est `src/main/resources/logo.ico` (Windows) ou `logo.png` (Linux).

## Structure du projet

```
src/main/java/com/st/hospitalmanagement/
├── Main.java            point d'entrée
├── controllers/         contrôleurs des écrans JavaFX
├── database/            connexion MySQL, alertes, stockage des images
└── models/              objets affichés dans les tableaux
src/main/resources/com/st/hospitalmanagement/
├── fxml/                écrans
├── css/                 styles
└── fonts/               police des icônes
database/                schema.sql (tables) et seed.sql (données de démonstration)
```

## Technologies

Java 21 · JavaFX 21 · MySQL Connector/J 8 · Gluon Charm Glisten (icônes) · Gradle 9

## Limites connues

- Les mots de passe des comptes sont stockés en clair dans la base.
- Le bouton « Edit » du formulaire d'édition d'un rendez-vous (portail admin) n'est pas encore implémenté.
