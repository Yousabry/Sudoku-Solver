public class tile{
    int value;   // Set to 0 if unknown
    boolean certain; //Wether or not the value assigned is certainly correct

    boolean[] poss = new boolean[9];  //When an unknown path is followed, current known poss is saved to tempposs
    boolean[] tempposs = new boolean[9];  //If path turns out false, poss will be restored to val of tempposs

    final int id;  // [0-80]
    final int row;  // [0-8]
    final int column;   // [0-8]

    public tile(int row, int column){
        this.id = (row * 9) + column;
        this.row = row;
        this.column = column;
        this.value = 0;
        this.certain = false;

        for (int i = 0; i < 9; i++){
            this.poss[i] = true;
            this.tempposs[i] = true;
        }
    }

    public void setVal(int newval){
        this.value = newval;
    }

    public void setVal(int newval, boolean certainty){
        this.value = newval;
        this.certain = certainty;
        for (int i = 0; i < 9; i++){
            this.poss[i] = false;
        }
    }

    public int getId(){
        return this.id;
    }

    public int getVal(){
        return this.value;
    }

    public int getRow() {  //0-8
        return this.row;
    }

    public int getcolumn(){ //0-8
        return this.column;
    }

    public boolean getPoss(int index){
        return this.poss[index];
    }

    public int getNumOfPoss(){
        int counter = 0;
        for (boolean b: this.poss){
            if (b){ counter ++; }
        }
        return counter;
    }

    public boolean getTempPoss(int index){
        return this.tempposs[index];
    }

    public void setPoss(int index, boolean chance){
        this.poss[index] = chance;
    }

    public void setTempPoss(int index, boolean chance){
        this.tempposs[index] = chance;
    }

    public String getPrettyPoss(){
        String s = "\nid: "+this.getId()+"   poss: ";
        for (int i = 0; i < 9; i++){
            if(this.getPoss(i)){
                s += (i+1)+", ";
            }
        }
        return s;
    }

    public String allPossibilities(){
        String s = "";
        for (int i = 0; i < 9; i++){
            if(this.getPoss(i)){
                s += (i+1)+",";
            }
        }
        return s;
    }

    public String toString(){
        return ""+this.value;
    }

}