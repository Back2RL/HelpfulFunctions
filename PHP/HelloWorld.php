<?php
// Kommentar
# auch Kommentar
/*
Block Kommentar
*/

// Variablen Deklaration
$name = "Leo etc.";
echo "Mein name ist $name<br>";

// Variablen kombinieren/concat
$name = "Nils ";
$name .= "Reimers";
echo $name."<br>";
// 2. Beispiel
$farbe = "rotes";
$text = "Wir haben ein ".$farbe." Haus<br>";
echo $text;

// Datentypen
$integer = 15; //Eine Integer Variable
$string = "Ganz viel Text"; //Ein String
$float = 15.5; //Eine Zahl mit einem Komma
$bool = true; // ein boolean

// rechnen
$zahl1 = 10;
$zahl2 = 5;
echo "Zahl1: $zahl1 <br />";
echo "Zahl2: $zahl2 <br />";
$ergebnis = $zahl1 + $zahl2;
echo "Ergebnis: $ergebnis<br>";

$zahl1 = 10;
$zahl2 = 5;
echo $zahl1 + $zahl2; //addieren
echo "<br />";
echo $zahl1 - $zahl2; //subtrahieren
echo "<br />";
echo $zahl1 * $zahl2; //multiplizieren
echo "<br />";
echo $zahl1 / $zahl2; //dividieren
echo "<br />";
echo pow($zahl1,$zahl2); //Zahl1 hoch Zahl2 (10^5)
echo "<br />";
echo sqrt(64); // Wurzel von 64
echo "<br />";
echo 2*$zahl1 + 5*$zahl2 - 3; //Berechnet 2*10 + 5*5 - 3
echo "<br />";

// Inkrement/Dekrement
$erhoehen = 1;
$erhoehen++;
echo $erhoehen;
$senken = 2;
$senken--;
echo $senken;
$zahl = 1;
$zahl += 10;
echo $zahl;

// float/double
$zahl1 = 2.5;
$zahl2 = 5.5;
$ergebnis = $zahl1 * $zahl2;
echo "Ergebnis: $ergebnis";

// Get/Post : aufruf mit HelloWorld.php?vorname=Max&nachname=Meier
$vorname = $_GET['vorname'];
$nachname = $_GET['nachname'];
echo "Hallo $vorname $nachname (übergeben via URL) <br />";

$vorname = $_POST["vorname"];
$nachname = $_POST["nachname"];
echo "Hallo $vorname $nachname (received from seite1.php)<br />";

// post/get kombiniert
$vorname = $_POST["vorname"];
$nachname = $_POST["nachname"];
$wochentag = $_GET["wochentag"];

$user = $_POST["user"];
 
if($user!="")
   {
   echo "Herzlich Willkommen $user";
   }
else
   {
   echo "Das Feld User wurde nicht ausgefüllt";
   }

echo "Hallo $vorname $nachname. Treffen wir uns am $wochentag?<br>";

// if
$user = "Nils";

if($user == "Nils") 
   {
   echo "Hallo Nils<br>";
   }
   $user = "Klaus";

if($user == "Nils") 
   {
   echo "Hallo Nils<br>";
   } 
else 
   {
   echo "Du bist nicht Nils!<br>";
   }
   
 // Passwörter
 $passwort = $_POST["passwort"];
 
if($passwort=="geheim")
   {
   echo "Herzlich Willkommen im internen Bereich<br>";
   }
else
   {
   echo "Das Passwort ist leider falsch<br>";
   }
   
 // Vergleichsoperatoren
 // === identisch (wert und Datentyp)
 // !== Identisch (Datentyp != oder Wert !=)
 $integer = 1;
$string = "1";

echo "Ergebnis von ==: ";
var_dump($integer == $string);

echo "<br /> Ergebnis von ===: ";
var_dump($integer === $string);

// Anwendungsbeispiel mit notwendigkeit des ===,
// da index 0 als false interpretiert werden würde
$text = "Dies ist ein Text";
$suchwort = "Dies";

$position = strpos($text, $suchwort);

if($position === false) {
   echo "Dein Suchwort wurde im Text nicht gefunden";
} else {
   echo "Dein Suchwort wurde an Position $position gefunden";
}

// logische Operatoren AND/OR
$gehalt = 25000;
$vermoegen = 10000;
$erbschaft = 0;

if($gehalt > 10000 OR $vermoegen > 50000 OR $erbschaft > 1000000) {
   echo "Wow, du bist reich!";
} else if($gehalt < 1000 AND $vermoegen < 10000 AND $erbschaft == 0) {
   echo "Kleines Gehalt, wenig Vermögen, keine Erbschaft, schade!";
} else {
  echo "Keine der Bedingungen war erfüllt";
}
// weiteres Besipiel
$username = $_POST["username"];
$passwort = $_POST["passwort"];

if($username=="Nils" AND $passwort=="php-einfach")
   {
   echo "Zugriff erlaubt";
   }
else
   {
   echo "Zugriff fehlgeschlagen";
   }

// Beispiel für einfache Passwort geschützte Seite
echo "<br>  Einfache Passwort Seite: <br>";
$username = $_POST["username"];
$passwort = $_POST["passwort"];
 
$pass = sha1($passwort);
echo  "<br> Sha1 = ".$pass;

$pass = md5($passwort);
echo "<br> MD5 = ".$pass;  
   
   if($username == "Leo" AND $pass=="e22a63fb76874c99488435f26b117e37")
   {
   echo "<br>Herzlich Willkommen";
   }
else
   {
   echo "<br>Login Fehlgeschlagen";
   }
   
$email = $_POST["email"];
$kommentar = $_POST["kommentar"];
 
if($email == "" OR $kommentar == "")
   {
   echo "Bitte füllen Sie alle Felder aus";
   }
else
   {
   echo "Ihr Eintrag wurde gespeichert";
   }
   
   // Seiten in einer php Datei
   $seite = $_GET["seite"];
 
if(empty($seite))
   {
   $seite="index";
   }
 
if($seite=="index")
   {
   echo "Indexseite";
   echo "<a href=\"?seite=start\">Zur Startseite</a>";
   }
 
if($seite=="start")
   {
   echo "Startseite";
   echo "<a href=\"?seite=index\">Zur Indexseite</a>";
   }
   
   // do while wie in Java
   // Zufallszahlen
   $zufall = rand(0, 30);
while($zufall > 10 AND $zufall < 20) {
   $zufall = rand(0, 30);
}

echo "Unsere Zufallszahl: $zufall";

// Arrays

$wochentage = array("Sonntag","Montag","Dienstag",
"Mittwoch","Donnerstag","Freitag","Samstag");
echo $wochentage[1];

// assoziative Arrays
$wochentage = array(
"so" => "Sonntag",
"mo" => "Montag",
"di" => "Dienstag",
"mi" => "Mittwoch",
"do" => "Donnerstag",
"fr" => "Freitag",
"sa" => "Samstag");

echo $wochentage["mo"];   
   
// werte zu einem Array hinzufügen
$mitarbeiter = array();
$mitarbeiter[] = "Bob";
$mitarbeiter[] = "Peter";
$mitarbeiter[] = "Lisa";

echo $mitarbeiter[0];
   
 // Multidimensionale Arrays
 
$mitarbeiter = array(
  array("Klaus", "Zabel"),
  array("Arnie", "Meier"),
  array("Willi", "Brand")
);

//Daten ausgeben
echo "Vorname: ".$mitarbeiter[0][0];
echo " Nachname: ".$mitarbeiter[0][1];
   
  // Kombi
  $mitarbeiter = array();
$mitarbeiter[] = array("Vorname"=>"Klaus",
                       "Nachname"=>"Zabel");

$mitarbeiter[] = array("Vorname"=>"Arnie",
                       "Nachname"=>"Meier");

$mitarbeiter[] = array("Vorname"=>"Willi",
                       "Nachname"=>"Brand");

//Daten ausgeben
echo "Vorname: ".$mitarbeiter[0]["Vorname"];
echo " Nachname: ".$mitarbeiter[0]["Nachname"];
   
   
 // 3-Dim Arrays und foreach
 $mitarbeiter = array();
$mitarbeiter["Klaus"]["Vorname"] = "Klaus";
$mitarbeiter["Klaus"]["Nachname"] = "Zabel";
$mitarbeiter["Klaus"]["Kinder"][] = "Klaus-Junior";
$mitarbeiter["Klaus"]["Kinder"][] = "Kind2";

//Daten ausgeben
echo "Vorname: ".$mitarbeiter["Klaus"]["Vorname"];
echo " Nachname: ".$mitarbeiter["Klaus"]["Nachname"];
echo "<br> Er hat ";
echo count($mitarbeiter["Klaus"]["Kinder"])." Kinder";

//Ausgabe von Kind1:
//$mitarbeiter["Klaus"]["Kinder"][0];

echo "<br> Kinder: <br>";
foreach($mitarbeiter["Klaus"]["Kinder"] AS $name) {
   echo $name."<br>";
}
  
// in Arrays suche
$mitarbeiter = array("Bob","Peter","Lisa");
$name = "Bob";
if(in_array($name,$mitarbeiter)) {
   echo "Der Name $name ist in dem Array enthalten";
}

// in assoziative Arrays zusätzliche Suche
$mitarbeiter = array("Bob" => "Baumeister", "Klaus" => "Muster");
$key = "Bob";

if(array_key_exists($key, $mitarbeiter)) {
  echo "Das Element $key hat den Wert: ".$mitarbeiter[$key];
} else {
  echo "Das Array hat keinen Schlüssel $key";
}

// Länge eines Arrays
// count($array)
$namen = array("Klaus", "Anna", "Dieter");

echo "<br> Durchlaufen des Arrays mittels for-Schleife: ";
for($i=0; $i<count($namen); $i++) {
  echo $namen[$i].", ";
}

echo "<br> Durchlaufen des Arrays mittels foreach-Schleife: ";
foreach($namen AS $name) {
  echo $name.", ";
}

// Arrays sortieren
$namen = array("Klaus", "Dieter", "Anna", "Melissa");

sort($namen);
foreach($namen AS $name) {
  echo "$name, ";
}

/*
    array_key_exists($key, $array) - Überprüft, ob der Schlüssel $key im $array exisitert.
    count($array) - Gibt die Anzahl der Elemente im Array zurück.
    in_array($suchwert, $array) - Überprüft, ob der Wert $suchwert im $array exisitert.
    sort($array) - Sortiert ein Array aufsteigend, vom kleinsten zum größten Wert (A -> Z).
    rsort($array) - Sortiert ein Array absteigend, vom größten zum kleinsten Wert (Z -> A).
    shuffle($array) - Mischt zufällig die Elemente des Arrays.
*/

// Foreach mehrDim
$unternehmen = array(
"Vertrieb" => array("Klaus", "Lisa", "Lea"),
"Produktion" => array("Peter", "Max")
);

foreach($unternehmen AS $abteilung => $mitarbeiter_der_abteilung) {
  echo "Mitarbeiter der Abteilung: $abteilung <br>";
  foreach($mitarbeiter_der_abteilung AS $mitarbeiter) {
    echo "$mitarbeiter <br>";
  }
  echo "<br>";
}   
   
// Dateien öffen und lesen
$zitate = file_get_contents('zitate.txt');
echo nl2br($zitate); 
for($i=0;$i < count($zitate); $i++){
   echo $i.": ".$zitate[$i]."<br><br>";
}  

// schreiben
$zeile = "Per GET wurde der Name $name übergeben \r\n";
file_put_contents("beispiel.txt", $zeile);
echo "beispiel.txt wurde überschrieben";
   
$counter = file_get_contents("counter.txt"); //Einlesen des Wertes
$counter++; //Wert um 1 erhöhen
file_put_contents("counter.txt", $counter); //Abspeichern des Wertes
echo "Diese Datei wurde bereits $counter mal aufgerufen";
  
// an Datei anhängen
$zeile = "Per GET wurde der Name $name übergeben \r\n";
file_put_contents("beispiel.txt", $zeile, FILE_APPEND);
echo "beispiel.txt aktualisiert";
  
 // Beispiel Registrierung
$name = $_POST['name'];
$email = $_POST['email'];
$passwort = $_POST['passwort'];
$user_info = array($email, $passwort, $name);
 
if(!empty($name) AND !empty($email) and !empty($passwort)) {
   $eintrag = implode(";", $user_info)."\r\n";
   file_put_contents("users.txt", $eintrag, FILE_APPEND);
   echo "$email wurde erfolgreich registriert";
} else {
   echo "Bitte alle Felder ausfüllen";
}

//Beispiel Login
$email = $_POST["email"];
$passwort = $_POST["passwort"];
 
$users = file("users.txt");
foreach($users AS $line)  {
   $user_info = explode(";", $line);
   if($user_info[0] == $email AND $user_info[1] == $passwort) {
       echo "Hallo ".$user_info[2];
   }
}
   
   
   
   echo "<br>";
echo "Hello \"World\"<br>";
echo "<b>Hello World</b><br>";
echo date("d.m.Y H:i:s");
echo "<br>";
echo "Mittels echo können Daten ausgegeben werden<br>";
	phpinfo();
?>