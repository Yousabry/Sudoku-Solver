public class line{
    int id;
    tile[] tiles = new tile[9];

    public line(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public tile[] getTiles(){
        return this.tiles;
    }

    public void addtile(tile mytile, int location){
        tiles[location] = mytile;
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
