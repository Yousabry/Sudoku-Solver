public class line{
    int id;
    int counter = 0;
    tile[] tiles = new tile[9];

    public line(int id){
        this.id = id;
    }

    public void addtile(tile mytile){
        tiles[counter ++] = mytile;
    }

    public int getId(){
        return this.id;
    }

    public tile[] getTiles(){
        return tiles;
    }

    public String toString(){
        String s = ("LINE      id: " + this.id + "         : ");
        for (int i = 0; i < 9; i++){
            s += (this.tiles[i].getVal() + " - ");
        }
        s += "\n";
        return s;
    }
}
