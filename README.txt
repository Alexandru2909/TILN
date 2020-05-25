Instructiuni de utilizare: 
Vom avea nevoie de un dispozitiv android, Android Studio si Node.js instalate.
1. In command prompt/powershell/terminal vom scrie ipconfig pentru a afla adresa noastra din reteaua locala. 
Telefonul si calculatorul de pe care va rula serverul trebuie sa fie in aceeasi retea locala wifi.
2. In android studio, in clasa global, vom scrie ip-ul calculatorului la linia 4, in locul "private String ip ="http://[IP-UL DUMNEAVOASTRA]:3000";"
Default, ip-ul este 192.168.1.6; Daca ip-ul calculatorului este deja acesta,putem sari la pasul 4 cu apk-ul BookInside.apk din folderul TILN
3. Dupa ce am completat ip-ul local, in Android studio in bara de sus vom apasa Build > Build Bundle(s) / APK(s) > Build APK(s). Apk-ul va fi locat in "TILN\bookinside\app\build\outputs\apk\debug".
4. Vom instala pe telefon apk-ul 
5. Din terminal/command prompt vom naviga in folderul TILN/database.
6. Vom rula npm install
7. Vom rula node testmain.js
8. Acum serverul a pornit, aplicatia Android se va putea conecta la server si va functiona complet.
9. Un cont folosit pentru a naviga aplicatia este : user = "Andrei" si parola "parola";
10. Recomandam folosirea aplicatiei de harti inainte de a folosi aplicatia, pentru a avea o ultima locatie cat mai aproape de prezent.