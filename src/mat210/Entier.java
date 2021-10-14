package mat210;

/**
 * Fichier distribué dans le cadre du cours MAT210, session automne 2021, à l'ÉTS.
 *
 * Par Xavier Provençal.
 *
 * Modifications par les étudiant·e·s : 
 *  - TODO inscrivez vos noms ici.
 *  - TODO inscrivez vos noms ici.
 *  - TODO inscrivez vos noms ici.
 */



import java.util.ArrayList;
import java.lang.Math;


/**
 * Représente un entier positif (0 inclu), de taille arbitrairement grande.
 *
 * Pour être exact, la valeur maximum théorique est 10^Integer.MAX_VALUE - 1,
 * mais en pratique cette valeur est réduite par la taille de la pile (Java
 * heap space).
 *
 * Opérations supportées : addition, soustraction (avec résultat positif),
 * multiplication, puissance et modulo.
 */
public class Entier {

    //
    // DONNEES MEMBRES
    //

    /**
     * Tableau dans lequel sont stockées les décimales de l'entier. La case 0
     * contient le chiffre le moins significatif.
     */
    protected ArrayList<Integer> decimales;


    //
    // CONSTRUCTEURS
    // 

    /**
     * Constructeur vide, non accessible de l'extérieur de la classe.
     */
    private Entier() {
        this.decimales = new ArrayList<Integer>();
    }


    /**
     * Constructeur à partir d'un ``long``
     */
    public Entier(long x) {
        this.decimales = new ArrayList<Integer>();
        // x%10 donne la dernière décimale de x
        // x/10 enlève la dernière décimale de x
        do {
            this.decimales.add((int) x%10);
            x /= 10;
        } while (x > 0);
    }


    /** 
     * Constructeur de copie.
     */
    public Entier(Entier aCopier) {
        this.decimales = new ArrayList<Integer>(aCopier.decimales);
    }


    /**
     * Constructeur à partir d'une chaîne de caractères représentant un entier
     * écrit en base 10.
     */
    public Entier(String s) {
        this.decimales = new ArrayList<Integer>();
        int zero_index = Character.getNumericValue('0');
        for( int i=s.length()-1; i>=0; --i) {
            this.decimales.add(Character.getNumericValue(s.charAt(i) - zero_index));
        }
    }



    //
    // OPÉRATIONS ARITHMÉTIQUES
    // 

    /**
     * Additionne l'entier spécifié à l'entier.
     *
     * Cette fonction ne modifie pas l'entier actuel (this), ni celui spécifié en
     * paramètre. Un nouvel entier est retourné.
     */
    public Entier somme(Entier autre) {
        
        // Exercice 1
        //
        // Déboguer cette fonction !
        //
        Entier somme = new Entier();
        int decimale, retenue = 0;
        int lng = Math.max(this.longueur(), autre.longueur());
        for (int i=0; i<lng; ++i) {
            // On utilise le fait que .getDecimale(i) retourne 0 si (i >= this.longueur())
            decimale = this.getDecimale(i) + autre.getDecimale(i);   // <--- bug
            if (retenue!=0) {                      // <--- bug
                decimale+=(retenue);
            }
            retenue = decimale / 10;
            somme.decimales.add(decimale % 10);
        }
        if (retenue!=0) {                      // <--- bug
            somme.decimales.add(retenue);
        }
        return somme;
    }



    /**
     * Multiplie l'entier spécifié à l'entier.
     *
     * Cette fonction ne modifie pas l'entier actuel (this), ni celui spécifié en
     * paramètre. Un nouvel entier est retourné.
     */
    public Entier produit(Entier autre) {

        // Exercice 2.
        //
        // À compléter.
        // 
        Entier somme=new Entier();
        Entier produit,grand,petit;
        int decimale = 0;
        if(this.plusGrand(autre)){
            grand=this;
            petit=autre;
        }else{
            grand=autre;
            petit=this;
        }
        for (int i=0; i<petit.longueur(); ++i) {
            // On utilise le fait que .getDecimale(i) retourne 0 si (i >= this.longueur())
            decimale = petit.getDecimale(i);
            produit =grand.produit(decimale);
              
            somme=somme.somme(produit.ajouteZero(i));
            
        }
        
        
        return somme;
    }

    public Entier ajouteZero(int qteeZeros){
        if(qteeZeros<=0){return new Entier(this);}
        Entier produit = new Entier();
        for( int i=qteeZeros-1; i>=0; --i) {
            produit.decimales.add(0);
        }
        int decimale = 0;
        
        for (int i=0; i<this.longueur(); ++i) {
            decimale = this.getDecimale(i);   
            produit.decimales.add(decimale);
        }
        
        
        return produit;
    }
    public Entier produit(int chiffre) {

        // Exercice 2.
        //
        // À compléter.
        // 
        Entier produit=new Entier();
        int decimale, retenue = 0;
        
        for (int i=0; i<this.longueur(); ++i) {
            decimale = this.getDecimale(i) * chiffre;   
                if (retenue!=0) {                      
                    decimale+=(retenue);
                }
                retenue = decimale / 10;
                produit.decimales.add(decimale % 10);
        }
        if (retenue!=0) {                      
            produit.decimales.add(retenue);
        }
        
        
        return produit;
    }
    

    /**
     * Retourne le modulo l'entier par l'entier spécifié.
     *
     * Cette fonction ne modifie pas l'entier actuel (this), ni celui spécifié en
     * pramètre. Un nouvel entier est retourné.
     */
    public Entier puissance(Entier p) {

        // Exercice 3
        //
        // À compléter.
        //
    	Entier puissance = new Entier(1);
    	double total = 0;
    	for (int i=0; i<p.longueur(); ++i) {
    		total += p.getDecimale(i)*Math.pow(10, i);
        }
    	for (int i=0; i<total; ++i) {
    		puissance = puissance.produit(this);
    	}
    	return puissance;
    }


    /**
     * Retourne le modulo l'entier par l'entier spécifié.
     *
     * Pré-condition : l'entier spécifié (m) ne doit pas être 0.
     *
     * Cette fonction ne modifie pas l'entier actuel (this), ni celui spécifié en
     * paramètre. Un nouvel entier est retourné.
     */
    public Entier modulo(Entier m) {

        // Exercices 4 et 5.
        //
        // À compléter.
        //
    	Entier modulo = new Entier(this);
    	while(modulo.plusGrandOuEgal(m)) {
    		modulo = modulo.soustraire(m);
    	}
        return modulo;
    }

    /**
     * Retourne (this^p) mod m, soit la p-ième puissance de l'entier, modulo m.
     *
     * Cette fonction ne modifie pas l'entier actuel (this), ni ceux spécifiés
     * en paramètres. Un nouvel entier est retourné.
     */
    public Entier puissanceModulaire_exo_6(Entier p, Entier m) {
        
        // Exercices 6.
        //
        // À réécrire
        //
        return (this.puissance(p)).modulo(m);
    }

    /**
     * Retourne (this^p) mod m, soit la p-ième puissance de l'entier, modulo m.
     *
     * Cette fonction ne modifie pas l'entier actuel (this), ni ceux spécifiés
     * en paramètres. Un nouvel entier est retourné.
     *
     */
    public Entier puissanceModulaire_exo_7(Entier p, Entier m) {
        
        // Exercices 7.
        //
        // À réécrire
        //
        return (this.puissance(p)).modulo(m);
    }

    public Entier puissanceModulaire(Entier p, Entier m) {
        return this.puissanceModulaire_exo_6(p, m);
        //return this.puissanceModulaire_exo_7(p, m);
    }




////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
//// À partir d'ici, tout ce qui suit n'est pas pertinant dans le cadre ////
//// du cours MAT210.                                                   ////
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////


    
    /**
     * Retourne la moitié arrondie à l'entier inférieur.
     */
    public Entier divParDeux() {
        // On initialise un entier même lng avec des 0 partout.
        Entier m = new Entier();
        int n = this.longueur();
        for (int i=0; i<n; ++i) {
            m.decimales.add(0);
        }
        // Division par 2 comme on ferait à la main
        int reste = 0;
        for (int i=n-1; i>=0; --i) {
            int d = this.getDecimale(i);
            m.decimales.set(i, (reste*10+d)/2);
            reste = d%2;
        }
        // Il est possible que le nombre commence par un 0, dans ce cas, on le retire.
        m.retireZerosEnTete();
        return m;
    }

    
    /**
     * Retourne ``vrai`` si l'entier est 0, ``faux`` sinon.
     */
    public boolean estZero() {
        return longueur() == 1 && this.decimales.get(0) == 0;
    }


    /**
     * Retourne ``vrai`` si l'entier est pair, ``faux`` sinon.
     */
    public boolean estPair() {
        return this.getDecimale(0) % 2 == 0;
    }



    /**
     * Retire les zéros à gauche.
     *
     * Si l'écriture en base 10 du nombre commence par des zéros, ceux-ci sont retirés.
     * Ex : 00843 --> 843
     * Exception : le nombre 0 est représenté par 0.
     */
    protected void retireZerosEnTete() {
        int n = this.longueur();
        while (n>1 && this.decimales.get(n-1) == 0) {
            this.decimales.remove(n-1);
            --n;
        }
    }



    /**
     * Retourne le nombre de décimales de l'entier. Il s'agit du nombre de
     * chiffres nécessaire pour l'écrire en base 10.
     */
    public int longueur() {
        return this.decimales.size();
    }


    /**
     * Retourne la décimale à l'index spécifié. L'index 0 étant le chiffre le
     * moins significatif de l'entier. Si l'index demandé est supérieur au
     * nombre de décimales, retourne 0.
     */
    public int getDecimale(int k) {
        if (k < this.longueur()) {
            return this.decimales.get(k);
        } else {
            return 0;
        }
    }

    /**
     * Retourne la décimale la plus significative.
     */
    public int decimaleDeGauche() {
        return this.getDecimale(this.longueur()-1);
    }


    /**
     * Compare deux Entier.
     * 
     * Retourne :
     *  -1 si ``this`` < y
     *   0 si ``this`` == y
     *   1 si ``this`` > y
     */
    protected int compareTo(Entier y) {
        // On utilise le fait que la fonction ``decimale(i)`` retourne 0
        // lorsque ``i>=this.longueur()``
        int n = Math.max(this.longueur(), y.longueur());
        for (int i=n-1; i>=0; --i) {
            int a = this.getDecimale(i);
            int b = y.getDecimale(i);
            if (a<b) {
                return -1;
            } else if (b<a) {
                return 1;
            }
        }
        // Si on est rendu ici, c'est que les deux sont égaux.
        return 0;
    }


    /**
     * Retourne ``vrai`` si et seulement si les deux entiers sont égaux.
     */
    public boolean egal(Entier y) {
        return this.compareTo(y) == 0;
    }


    /**
     * Retourne ``vrai`` si et seulement si l'entier est plus petit ou égal à
     * celui spécifié.
     */
    public boolean plusPetitOuEgal(Entier y) {
        return compareTo(y) <= 0;
    }


    /**
     * Retourne ``vrai`` si et seulement si l'entier est strictement plus petit
     * que celui spécifié.
     */
    public boolean plusPetit(Entier y) {
        return compareTo(y) < 0;
    }


    /**
     * Retourne ``vrai`` si et seulement si l'entier est strictement plus grand
     * que celui spécifié.
     */
    public boolean plusGrand(Entier y) {
        return compareTo(y) > 0;
    }


    /**
     * Retourne ``vrai`` si et seulement si l'entier est plus grand ou égal à
     * celui spécifié.
     */
    public boolean plusGrandOuEgal(Entier y) {
        return compareTo(y) >= 0;
    }


    /**
     * Retourne un nouvel entier égal au complément à dix de l'entier.
     */
    protected Entier complementADix(int taille) {
        Entier c = new Entier();
        int retenue = 1;
        for (int i=0; i<taille; ++i) {
            int decimale = 9-this.getDecimale(i)+retenue;
            c.decimales.add(decimale%10);
            retenue = decimale/10;
        }
        if (retenue>0) {
            c.decimales.add(retenue);
        }
        return c;
    }


    /**
     * Retire la décimale la plus à gauche de l'écriture en base 10 de
     * l'entier.
     * (sert uniquement lors de la soustraction via le complément à 10)
     */
    protected void retireDecimaleDeGauche() {
        this.decimales.remove(this.longueur()-1);
    }


    /**
     * Soustrait l'entier spécifié à l'entier.
     *
     * Pré-condition : l'entier actuel (this) doit absolument être plus grand
     * que celui spécifié (y). (rappel : la classe Entier ne représente pas les
     * négatifs)
     *
     * Cette fonction ne modifie pas l'entier actuel (this), ni celui spécifié en
     * paramètre. Un nouvel entier est retourné.
     *
     */
    public Entier soustraire(Entier y) {
        if (this.plusPetit(y)) {
            return null;
        }
        // On additionne le complément à 10
        Entier diff = y.complementADix(this.longueur()).somme(this);
        // On retire la retenue
        diff.retireDecimaleDeGauche();
        // On retire les zéro inutiles en début 
        while (diff.decimaleDeGauche() == 0 && !diff.estZero()) {
            diff.retireDecimaleDeGauche();
        }
        return diff;
    }


    /**
     * Retourne une chaîne de caractère représentant la valeur de l'entier en
     * base 10. 
     *
     * Dans le cas où l'écriture de l'entier fait plus de 80 chiffres, seuls
     * les premières et les dernières décimales sont affichées. Utilisez la
     * fonction ``str`` pour obtenir toutes les décimales de l'entier.
     */

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = this.longueur();
        if (n > 80) {
            for (int i=0; i<30; ++i ) {
                sb.append(getDecimale(n-i-1));
            }
            sb.append("...(" + n + " décimales)...");
            for (int i=29; i>=0; --i ) {
                sb.append(getDecimale(i));
            }
            return sb.toString();
        } else {
            return str();
        }
    }


    /**
     * Retourne une chaîne de caractère représentant la valeur de l'entier en
     * base 10. 
     */
    public String str() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<longueur(); ++i) {
            sb.append(getDecimale(i));
        }
        return sb.reverse().toString();
    }
    
    
}
