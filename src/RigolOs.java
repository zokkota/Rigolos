import extensions.CSVFile;class RigolOs extends Program {
    //init des variables globales
    String[] elementOeil = new String[]{"sourcil", "cil", "paupiere","pupille"};
    String[] elementBouche = new String[]{"dent", "langue", "levre"};
    String[] elementMain = new String[]{"pouce" ,"annulaire", "index","majeur","auriculaire"};
    String[] elementPied = new String[]{"cheville", "orteil", "talon"};
    //Affichage
    void afficher(Boites a){
        String nomfichier= a.nom + ".csv";
        CSVFile fichier=loadCSV(nomfichier);
        for (int idxL=0;idxL<rowCount(fichier);idxL++){
            println();
            for(int idxC=0;idxC<columnCount(fichier,idxL);idxC++){
                print(getCell(fichier, idxL, idxC));
            }
        }

    }
    
    //initialisation des boite

    Boites creerBoites(String nomBoites, Boites[] objet){
        Boites b = new Boites ();
        b.nom = nomBoites;
        b.objets = new Boites[length(objet)];
        for(int idx = 0; idx < length(objet); idx ++){
            b.objets[idx] = objet[idx];
        }
        return b;
    }

    Boites creerBoiteVides(String nomBoites){
        Boites b = new Boites ();
        b.nom = nomBoites;
        return b;
    }

    Boites[] creerSousBoite(String[] element){
        Boites[] stockageBoite = new Boites[length(element)];
        for(int idx = 0; idx < length(element); idx ++){
            Boites b = creerBoiteVides(element[idx]);
            stockageBoite[idx] = b;
        }
        return stockageBoite;
    }

    Boites initTete(){
        Boites oeil = creerBoites("oeil", creerSousBoite(elementOeil));
        Boites oreille = creerBoiteVides("oreille");
        Boites bouche = creerBoites("bouche", creerSousBoite(elementBouche));
        Boites nez = creerBoiteVides("nez");
        Boites [] stockage= new Boites []{oeil,oreille,bouche,nez};
        Boites tete = creerBoites("tete",stockage);
        return tete;
    }
    
    Boites initJambes(){
        Boites pied = creerBoites("pied", creerSousBoite(elementPied));
        Boites genoux = creerBoiteVides("genoux");
        Boites mollets = creerBoiteVides("mollet");
        Boites tibia = creerBoiteVides("tibia");
        Boites[] stockage = new Boites[]{pied, genoux, mollets, tibia};
        Boites jambes = creerBoites("jambes", stockage);
        return jambes;
    }
    
    
    Boites initBras(){
        Boites biceps = creerBoiteVides("biceps");
        Boites triceps = creerBoiteVides("triceps");
        Boites coude = creerBoiteVides("coude");
        Boites mains = creerBoites("main",creerSousBoite(elementMain));
        Boites [] stockage= new Boites[]{biceps,triceps,coude,mains};
        Boites bras = creerBoites("bras",stockage);
        return bras;
    }

    Boites initTorse(){
        Boites teton = creerBoiteVides("teton");
        Boites pectoraux = creerBoiteVides("pectoraux");
        Boites nombril = creerBoiteVides("nombril");
        Boites abdominaux = creerBoiteVides("abdominaux");
        Boites epaules = creerBoiteVides("epaule");
        Boites [] stockage = new Boites[]{teton,pectoraux,nombril,abdominaux,epaules};
        Boites torse = creerBoites("torse",stockage);
        return torse;
    }
    Boites initCorps(){
        Boites bras= initBras();
        Boites jambes =initJambes();
        Boites torse =initTorse();
        Boites tete = initTete();
        Boites [] stockage = new Boites[]{tete, torse, bras, jambes};
        Boites corps= creerBoites("corps",stockage);
        return corps;
        }
    
    // affichage des elements à trouver

    void printObjet(Boites a){
        for(int idx=0;idx<length(a.objets);idx++){
            if(a.objets[idx].decouverte==true){
                println(idx + ": " + a.objets[idx].nom +  "  (trouvé)"); 
            }else{
                String rslt="";
                for(int idxc=0;idxc<length(a.objets[idx].nom);idxc++){
                    rslt = rslt + "*";
                }
                println(idx + " " + rslt);
            }
        }
    }
    String toString(Boites a){
        String rslt="";
        for(int idx=0;idx<length(a.objets);idx++){
            rslt=rslt + idx + ": "+ a.objets[idx].nom + " ";
        }
        return rslt;
    }

    void afficherIO(String nomfichier){
            CSVFile fichier=loadCSV(nomfichier + ".csv");
            for (int idxL=0;idxL<rowCount(fichier);idxL++){
                println();
                for(int idxC=0;idxC<columnCount(fichier,idxL);idxC++){
                    print(getCell(fichier, idxL, idxC));
                }
            }
    }

    boolean acceuil(){
       boolean rslt=false;
       while(!rslt){
       clearScreen();
       afficherIO("titre");
       String reponse = toUpperCase(readString());
       if (equals(reponse,"O")){
            rslt= true;
        } else if (equals(reponse,"N")){
            return false;
            }
        }
    return rslt;
    }

    Joueur initJoueur(){                   
        Joueur player = new Joueur();
        println("Comment vous appelez vous ?");
        player.nom=readString();
        clearScreen();
        delay(2000);
        println("Salut "+ " "+player.nom+" "+ " bienvenue sur le jeu Rigolos tu va découvrir mon corps AHAHAHA");
        delay(2000);
        println("Le but du jeu c'est de trouver toute les parties du corps et les nommer grace au bon numéros");
        delay(2000);
        println("prend ton temps et fait attention a l'orthographe des noms tout est au singulier ");
        delay(3000);
        return player;
    }
    //verification boites decouvertes

    boolean boitesdecouverte (Boites a){
        for (int idx=0;idx<length(a.objets);idx++){
            if (a.objets[idx].decouverte == false){
                return false;
            }
        }
        return true;
    }

    void deception(){
        println("tu abandonne ?");
        delay(1000);
        println("tu es faible");
        delay(1000);
        println("je suis déçu");
        delay(1000);
        println("mais heuresement il reste des personne courageuse qui ont relevé le defi");
        delay(2000);
        println("tu veux voir qui a eu le courage de jouer ? (o/n)");
        String reponse= toUpperCase(readString());
        if(equals(reponse,"O")){
            afficherIO("lstJoueur");
            delay(2000);
        }else{
            println("tu me déçois beaucoup");
            delay(500);
            afficherIO("bye");
        }
    }

       void sauvegarde(Joueur a , Boolean decouvert){
     CSVFile liste =loadCSV("lstJoueur.csv");
     String cont[][]= new String [rowCount(liste)+1][1];
     String rslt="";
     int idxl=0;
     int idxc=0;
     if (decouvert = false){
        rslt=" n'a pas decouvert tout mon beau corps";
     }else{
        rslt=" connait chaque recoin de mon corps";
     }
     for(int idx=0;idx< rowCount(liste);idx++){
        cont[idx][0]=getCell(liste,idx,0);
     }
        cont[rowCount(liste)][0]= a.nom+rslt;
        saveCSV(cont, "../ressources/lstJoueur.csv");
     }
    void jeu(Boites corps, boolean toutDecouvert){
        int message = 0;
        String idxObjets="";
        String idxObjetsZoom="";
        while(!toutDecouvert){
            toutDecouvert = false;
            afficher(corps);
            println(toString(corps));
            printObjet(corps);
            println("Entrez num partie corps : "+ "(pour quittez le jeu, veuillez taper le numéro 9 )");
            int idxcorps = readInt();
            if(idxcorps == 9){
                break;
            }
            boolean decouvert = false;
            clearScreen();
                while(!decouvert){
                    boolean nombre = false;
                    while(!nombre){
                        message = 0;
                        afficher(corps.objets[idxcorps]);
                        println();
                        printObjet(corps.objets[idxcorps]);
                        println("Entrez le num de la partie que vous voulez découvrir : (tapez " + 9 + " pour revenir aux squelettes )");
                        idxObjets= readString();
                        for(int i = 0; i<length(idxObjets);i++){
                            if(equals(idxObjets, "") || 47 > (int)charAt(idxObjets, i) || 58 < (int)charAt(idxObjets, i)){
                                if(message == 0){
                                    println("Veuillez rentré un nombre correct.");
                                    message = 1;
                                    delay(1000);
                                }
                            }   
                        }
                        if(message == 0){
                           if(stringToInt(idxObjets) == 9){
                                decouvert = true;
                                nombre = true;
                            } else {
                                if(stringToInt(idxObjets) > length(corps.objets[idxcorps].objets) - 1){
                                    println("veuillez entrez un nombre valide");
                                    delay(1000);
                                    clearScreen();
                                } else{
                                    nombre = true;
                                }
                            } 
                        }
                        
                    }
                    if(decouvert == true){
                            println("retour");
                            clearScreen();
                    } else {
                        println("A qu'elle partie correspond ce numéro ? : ");
                        String reponse = toLowerCase(readString());
                        if(equals(reponse,(corps.objets[idxcorps].objets[stringToInt(idxObjets)].nom)) && length(corps.objets[idxcorps].objets[stringToInt(idxObjets)].objets) == 1){
                            clearScreen();
                            println("Bonne réponse !!");
                            corps.objets[idxcorps].objets[stringToInt(idxObjets)].decouverte = true;
                        } else if(equals(reponse, corps.objets[idxcorps].objets[stringToInt(idxObjets)].nom) && length(corps.objets[idxcorps].objets[stringToInt(idxObjets)].objets) > 1){
                        boolean zoomDecouvert = false;
                        clearScreen();
                        while(!zoomDecouvert){
                            nombre = false;
                            while(!nombre){
                                message = 0;
                                afficher(corps.objets[idxcorps].objets[stringToInt(idxObjets)]);
                                println();
                                printObjet(corps.objets[idxcorps].objets[stringToInt(idxObjets)]);
                                println("Entrez le num de la partie que vous voulez découvrir : (tapez " + 9 + " pour revenir à " + corps.objets[idxcorps].nom +"");
                                idxObjetsZoom = readString();
                                for(int id = 0; id<length(idxObjetsZoom);id++){
                                    if(equals(idxObjetsZoom, "") || 47 > (int)charAt(idxObjetsZoom, id) || 58 < (int)charAt(idxObjetsZoom, id)){
                                        if(message == 0){
                                            println("Veuillez rentré un nombre correct.");
                                            message = 1;
                                            delay(1000);
                                        }
                                    } 
                                }
                                if(message ==0){
                                    if(stringToInt(idxObjetsZoom) == 9){
                                        zoomDecouvert = true;
                                        nombre = true;
                                    } else {
                                        if(stringToInt(idxObjetsZoom) > length(corps.objets[idxcorps].objets[stringToInt(idxObjets)].objets) - 1){
                                            println("veuillez entrez un nombre valide");
                                            delay(1000);
                                            clearScreen();
                                        } else{
                                            nombre = true;
                                        }
                                    }
                                }
                            }
                            if(zoomDecouvert == true){
                                println("retour");
                                clearScreen();
                            } else {
                                println("A qu'elle partie correspond ce numéro ? : ");
                                String reponseZoom = toLowerCase(readString());
                                if(equals(reponseZoom, corps.objets[idxcorps].objets[stringToInt(idxObjets)].objets[stringToInt(idxObjetsZoom)].nom)){
                                    clearScreen();
                                    println("Bonne réponse !!");
                                    corps.objets[idxcorps].objets[stringToInt(idxObjets)].objets[stringToInt(idxObjetsZoom)].decouverte = true;
                                } else {
                                    clearScreen();
                                    println("Vous vous êtes trompé, reesayer...");
                                }
                                zoomDecouvert = boitesdecouverte(corps.objets[idxcorps].objets[stringToInt(idxObjets)]);
                                if(zoomDecouvert == true){
                                    corps.objets[idxcorps].objets[stringToInt(idxObjets)].decouverte = true;
                            }
                            }
                        }
                        
                        } else {
                            clearScreen();
                            println("ce n'est pas la bonne réponse (vérifié l'orthographe)");
                            delay(1000);
                        }
                        decouvert = boitesdecouverte(corps.objets[idxcorps]);
                        if(decouvert == true){
                            corps.objets[idxcorps].decouverte = true;
                        } 
                    }
                }
        toutDecouvert = boitesdecouverte(corps);
        }
        if(toutDecouvert == true){
            corps.decouverte = true;
            println("Felicitation vous avez terminé le jeu au complet !!!");
            afficherIO("win");
        } else {
            clearScreen();
            println("au revoir, merci d'avoir joué :D");
        }
        
    }

    void algorithm(){
        if(acceuil() == true){
            Joueur player=initJoueur();
            Boites corps = initCorps();
            boolean toutDecouvert = false;
            jeu(corps, toutDecouvert);
            sauvegarde(player, toutDecouvert);
        } else {
            clearScreen();
            deception();
        }
    }


 }













