public class RedBallon implements Ballon () {
    private TextureRegion region;

    public RedBallon(Float distance) {
        this.distance = distance;
        this.speed = base_speed*1;
    }
    
    public void pop(Dart dart);
    
}