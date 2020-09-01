import java.util.Random; 

public class border {

    int [][] field;
    public static void updateSensor(int p) {
        Random rand = new Random();
        for(int i=0;i<field.length;i++)
        {
            for(int j=0;j<field[i].length;j++)
            {

                int rand = rand.nextInt(100);
                if(rand>p)
                {
                    field[i][j] = 1;
                }
                else
                {
                    field[i][j] = 0;
                }
            }
        }
    }

}