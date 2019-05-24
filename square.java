public class square{
    int id;
    int counter = 0;
    tile[] tiles = new tile[9];

    public square(int id){
        this.id = id;
    }

    public tile[] getTiles(){
        return this.tiles;
    }

    public int getId(){
        return this.id;
    }

    public void addtile(tile mytile){
        tiles[counter] = mytile;
        counter ++;
    }

    public String toString(){
        String s = "SQUARE      id: " + this.id + "         : ";
        for (int i = 0; i < 9; i++){
            s += (this.tiles[i].getVal() + " - ");
        }
        s += "\n"; 
        return s;
    }
}