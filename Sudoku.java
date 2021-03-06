public class Sudoku{
    int id;
    String name;
    String oldComp;
    tile[] alltiles = new tile[81];
    line[] alllines = new line[18];
    square[] allsquares = new square[9];

    public Sudoku(int id){
        this("Unnamed puzzle", id, "");
    }

    public Sudoku(String name, int id, String setup){
        this.name = name;
        this.id = id;

        //Creating 81 tiles with value 0
        int counter = 0;

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                alltiles[counter ++] = new tile(i,j);
            }
        }

        //Adding tiles to list of lines
        counter = 0;
        line myline;

        //rows (lines 0-8)
        for (int i = 0; i < 9; i++){
            myline = new line(counter);

            for (int j = 0; j < 9; j ++){
                myline.addtile(alltiles[(i*9)+j],j);
            }

            alllines[counter] = myline;
            counter ++;
        }

        //columns (lines 9-17)
        for (int i = 0; i < 9; i++){
            myline = new line(counter);

            for (int j = 0; j < 9; j ++){
                myline.addtile(alltiles[(j*9)+i],j);
            }

            alllines[counter ++] = myline;
        }

        //Squares
        for (int i = 0; i < 9; i++){
            allsquares[i] = new square(i);
        }

        for (int rowsection = 0; rowsection < 3; rowsection++){
            for (int colsection = 0; colsection < 3; colsection++){
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection)], 0);
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection) + 1], 1);
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection) + 2], 2);
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection) + 9], 3);
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection) + 10], 4);
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection) + 11], 5);
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection) + 18], 6);
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection) + 19], 7);
                allsquares[(rowsection*3) + colsection].addtile(alltiles[(27*rowsection)+(3*colsection) + 20], 8);
            }
        }

        //If setup is provided, change value of tiles
        String[] values = setup.split(",");
        int location;
        int tempval;
        for (String val: values){
            if (val.length() == 2){
                location = Character.getNumericValue(val.charAt(0));
            }else{
                location = Integer.parseInt(val.substring(0,2));
            }
            
            tempval = Character.getNumericValue(val.charAt(val.length()-1));

            alltiles[location].setVal(tempval, true);
        }
    }
    public boolean isValid(){
        int[] tempchecker = {0,0,0,0,0,0,0,0,0};
        //Check lines for repeated value
        for (line l: alllines){
            for (tile t: l.getTiles()){
                if(t.getVal() != 0){
                    tempchecker[t.getVal()-1] ++;
                }
            }

            for (int c = 0; c < 9; c++){
                if (tempchecker[c] > 1){
                    //System.out.println("Problem in line (id: "+l.getId()+"): "+l.toString());
                    return false;
                }
                tempchecker[c] = 0;
            }
        }

        //Check Squares for repeated value
        for (square s: allsquares){
            for (tile t: s.getTiles()){
                if(t.getVal() != 0){
                    tempchecker[t.getVal()-1] ++;
                }
            }

            for (int c = 0; c < 9; c++){
                if (tempchecker[c] > 1){
                    //System.out.println("Problem in square (id: "+s.getId()+"): "+s.toString());
                    return false;
                }
                tempchecker[c] = 0;
            }
        }

        //Check if each individual tile has min 1 poss solution
        for (tile t: alltiles){
            if (t.getNumOfPoss() == 0 && t.getVal() == 0){
                //System.out.println("There are no possible answers for tile: "+ t.getId());
                return false;
            }
        }
        return true;
    }
    public boolean solved(){
        for(tile t: alltiles){
            if(t.getVal() == 0){
                return false;
            }
        }
        return true;
    }
    public tile getTile(int id){
        return alltiles[id];
    }
    public void updatePoss(){
        int row;
        int col;
        int square;

        for (int i = 0; i < 81; i++){
            if (alltiles[i].getVal() != 0){
                continue;
            }

            row = alltiles[i].getRow();
            col = alltiles[i].getcolumn();
            square = ((row/3)*3)+(col/3);

            //Check rows
            for (tile adjtile: alllines[row].getTiles()){
                if (adjtile.getVal() != 0){
                    alltiles[i].setPoss(adjtile.getVal() - 1, false);
                }
            }
            //Check columns
            for (tile adjtile: alllines[col + 9].getTiles()){
                if (adjtile.getVal() != 0){
                    alltiles[i].setPoss(adjtile.getVal() - 1, false);
                }
            }
            //Check Squares
            //(*3 then /3 using floor division)
            for (tile adjtile: allsquares[square].getTiles()){
                if (adjtile.getVal() != 0){
                    alltiles[i].setPoss(adjtile.getVal() - 1, false);
                }
            }
        }
    }
    public void s1(boolean certainty){
        //If tile only has one possibility
        for(int i = 0; i < 81; i++){
            if (alltiles[i].getVal() != 0){
                continue;
            }

            if (alltiles[i].getNumOfPoss() == 1){
                for (int j = 0; j < 9; j++){
                    if (alltiles[i].getPoss(j)){
                        alltiles[i].setVal(j+1, certainty);
                        this.updatePoss();
                        break;
                    }
                }
            }
        }
    }
    public void s2(boolean certainty){
        //Check if number is only possible in one position (in line)
        int[] rep = {0,0,0,0,0,0,0,0,0};

        for (int line = 0; line < 18; line++){
            for(tile t: alllines[line].getTiles()){
                for(int i = 0; i < 9; i++){
                    if (t.getPoss(i)){
                        rep[i]++;
                    }
                }
            }
            for(int i = 0; i < 9; i++){
                if (rep[i] == 1){
                    for (int t = 0; t < 9; t++){
                        if (alllines[line].getTiles()[t].getPoss(i)){
                            alllines[line].getTiles()[t].setVal(i+1, certainty);
                            this.updatePoss();
                            break;
                        }
                    }
                }
            }
            //reset rep (counter)
            for(int i = 0; i < 9; i++){
                rep[i] = 0;
            }
        }
    }
    public void s3(boolean certainty){
        int[] rep = {0,0,0,0,0,0,0,0,0};
        //Check if number is only possible in one position (in square)
        for (int square = 0; square < 9; square++){
            for(tile t: allsquares[square].getTiles()){
                for(int i = 0; i < 9; i++){
                    if (t.getPoss(i)){
                        rep[i]++;
                    }
                }
            }
            for(int i = 0; i < 9; i++){
                if (rep[i] == 1){
                    for (int t = 0; t < 9; t++){
                        if (allsquares[square].getTiles()[t].getPoss(i)){
                            allsquares[square].getTiles()[t].setVal(i+1, certainty);
                            this.updatePoss();
                            break;
                        }
                    }
                }
            }
            //reset rep (counter)
            for(int i = 0; i < 9; i++){
                rep[i] = 0;
            }
        }
    }
    public void save(){
        for (int i = 0; i < 81; i++){
            //Error with saving lists as each other so must copy manually
            for (int p = 0; p < 9; p++){
                this.alltiles[i].setTempPoss(p, this.alltiles[i].getPoss(p));
            }
        }
    }
    public void reset(){
        for (int i = 0; i < 81; i++){
            //Restore previous possibilities
            for (int p = 0; p < 9; p++){
                this.alltiles[i].setPoss(p, this.alltiles[i].getTempPoss(p));
            }
            //If value was changed through uncertain guesses, reset values to unknown
            if (!this.alltiles[i].certain){
                this.alltiles[i].setVal(0);
            }
        }
    }
    public void solver(){
        String s = "";
        while (! s.equals(this.longCompressed())){
            s = this.longCompressed();
            while((!this.solved()) && this.isValid()){
                //Run solving algorithms with no guessing
                oldComp = this.compress();
                this.s1(true);
                this.s2(true);
                this.s3(true);
    
                if (oldComp.equals(this.compress())){
                    //System.out.println("Not making progress... ");
                    break;
                }
            }
    
            if (this.solved()){
                System.out.println(this.toString());
                //System.out.println("Solved!!!");
                break;
            }
    
            else if (!this.isValid()){
                System.out.println("There is a mistake in the entries...\n\nEND OF PROGRAM");
                break;
            }
    
            else{
                //Enter trial stage
                System.out.println("Starting trial stage... ");
                for (int i = 0; i < 81; i ++){
                    if (this.alltiles[i].getVal() == 0){
                        for (int p = 0; p < 9; p++){
                            if (this.alltiles[i].getPoss(p)){
                                //System.out.println("Trying out value: "+(p+1)+" for tile: "+i);
                                //Saving tempposs as current state of poss
                                this.save();
                                //A tile (i) has a possibility (p+1)
                                //Try the value and see what happens
                                this.alltiles[i].setVal((p+1), false);
                                this.updatePoss();
    
                                while((!this.solved()) && this.isValid()){
                                    //Run solving algorithms with no guessing
                                    oldComp = this.compress();
                                    this.s1(false);
                                    this.s2(false);
                                    this.s3(false);
                        
                                    if (oldComp.equals(this.compress())){
                                        //System.out.println("Not making progress (no info gained)...");
                                        //Set value of poss to tempposs
                                        this.reset();
                                        break;
                                    }
                                }
                        
                                if (this.solved()){
                                    //System.out.println("Solved!!!");
                                    break;
                                }
                        
                                if (!this.isValid()){
                                    //This guess results in a contradiction, therefore cannot be the answer
                                    //System.out.println("Eliminated 1 possibility...");
                                    //Set value of poss to tempposs and add new information
                                    this.reset();
                                    this.alltiles[i].setPoss(p, false);
                                }
                            }
                        }
                    }
                }
                if (! this.solved()){
                    this.solver();
                }
            }
        } 
    }
    public String compress(){
        //tile id followed by value, seperated by commas
        //Used to compare state of puzzle (NOT including possibilities)
        String s = "";
        for (tile t:this.alltiles){
            if (t.getVal() != 0){
                s += "" + t.getId() + t.getVal() + ",";
            }
        }
        return s;
    }
    public String longCompressed(){
        //Every tile represented, either value or possibilities, seperated by a comma
        //Used to compare state of puzzle (including possibilities)
        String s = "";
        for (tile t:this.alltiles){
            if (t.getVal() != 0){
                s += "" + t.getId() + t.getVal() + ",";
            }
            else{
                s += t.allPossibilities();
            }
        }
        return s;
    }
    public String toString(){
        String s = "-------------------------\n";
        for (int i = 0; i < 9 ; i++){
            s += "| " + this.getTile((i*9) + 0) + " " + this.getTile((i*9) + 1) + " " + this.getTile((i*9) + 2);
            s += " | " + this.getTile((i*9) + 3) + " " + this.getTile((i*9) + 4) + " " + this.getTile((i*9) + 5);
            s += " | " + this.getTile((i*9) + 6) + " " + this.getTile((i*9) + 7) + " " + this.getTile((i*9) + 8) + " |\n";
            if ((i+1) % 3 == 0){
                s += "-------------------------\n";
            }
        }
        return s;
    }
    public static void main(String[] args){
        //String setup =  "25,36,43,71,84,155,173,192,211,256,285,314,351,362,373,401,435,447,451,498,523,554,595,617,639,652,725,737,766,773,781"; //Medium puzzle solved
        String setup =  "26,34,57,65,139,232,258,269,271,312,333,354,365,401,446,452,477,494,538,546,551,579,676,742,753,775,787"; //Hard puzzle solved
        //String setup =  "02,25,84,197,239,248,251,167,288,302,316,345,398,413,466,499,504,522,551,564,579,616,642,728,781,802"; //Evil puzzle solved
        //String setup =  "37,78,99,115,164,223,241,252,303,337,373,384,399,415,422,436,478,502,551,562,588,646,698,711,734,777"; //Evil puzzle solved
                        
        Sudoku mySudoku = new Sudoku("Trial Sudoku", 2, setup);

        System.out.println(mySudoku.toString());
        mySudoku.updatePoss();        

        mySudoku.solver();

    }
}