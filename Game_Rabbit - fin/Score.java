
/**
 * D�crivez votre classe Score ici.
 *
 * @author (votre nom)
 * @version (un num�ro de version ou une date)
 */
public class Score
{
    // variables d'instance - remplacez l'exemple qui suit par le v�tre
  public int score;
  public  int level;
   int score_level_1;
   int score_level_2;
GCTileMapDemo class_p;
    /**
     * Constructeur d'objets de classe Score
     */
    public Score(GCTileMapDemo p)
    {
        // initialisation des variables d'instance
    score=0;
        level=1;
     score_level_1=6;
    score_level_2=10;
    class_p=p;
    }

    /**
     * Un exemple de m�thode - remplacez ce commentaire par le v�f4tre
     *
     * @param  y   un param�tre pour cette m�thode
     * @param  x   un autre param�tre
     * @return     la somme des deux param�tres
     */
  public void setscore(int x)
    {
        // Ins�rez votre code ici
        
        score=x;
    }
    public int getscore()
    {
        // Ins�rez votre code ici
        
        return score;
    }
    
     public int getlevel()
    {
        // Ins�rez votre code ici
        
        return level;
    }
    
    public void add_point()
    {
        // Ins�rez votre code ici
        score++;
        check_end();
    }
    
     public void change_level()
    {
        // Ins�rez votre code ici
        level++;
        check_end();
    }
     public void check_end()
    {
    if(level==1 && score==score_level_1)
    class_p.move_to_level2();
    if(level==2 && score==score_level_2)
    class_p.you_win();
    
    }
}
