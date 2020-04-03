import javafx.scene.paint.Color;

import java.util.List;

public class Tower extends Block {

    private int level;
    private double range;
    private boolean active;
    private int damage;
    private int hind;
    private int maxLevel;

    public Tower(Towers type, int x, int y) {
        super(x, y, type.getId() + 10, type.getDmg(), type.getColor(), 0);
        this.level = 1;
        this.range = type.getRange();
        this.active = false;
        this.damage = this.value;
        this.hind = type.getHind();
        this.maxLevel = type.getMaxLevel();
    }

    void lvlUp() {
        this.level += 1;
        this.range += 10;
        this.damage += 1;
    }

    public int getLevel() {
        return level;
    }

    boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    public int getPixelX() {
        return x;
    }

    public int getPixelY() {
        return y;
    }

    public double getRange() {
        return range;
    }

    public void shootLaser(List<Monster> monsters, boolean onlyAnimate) {
        for (Monster monster : monsters) {
            double distance = Math.hypot(this.x - monster.getX(), this.y - monster.getY());
            if (distance <= this.range) {
                if (!onlyAnimate) monster.setHp(monster.getHp() - this.damage);
                Game.getG().setStroke(getColor());
                Game.getG().setLineWidth(0.5);
                Game.getG().strokeLine(this.x, this.y, monster.getX(), monster.getY());
            }
        }
    }

    public Monster getClosestMonster(List<Monster> monsters) {
        Monster closestMonster = monsters.get(0);
        double closestDistance = Math.hypot(this.x - closestMonster.getX(), this.y - closestMonster.getY());
        for (Monster monster : monsters) {
            double distance = Math.hypot(this.x - monster.getX(), this.y - monster.getY());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestMonster = monster;
            }
        }
        if (closestDistance <= this.range) {
            return closestMonster;
        } else {
            return null;
        }
    }

    public void cannonNewMissile(Monster monster) {
        Projectile missile = new Projectile(this.x, this.y, this.damage, 500, 10, this.color);
        monster.addMissile(missile);
    }

    void drawRange() {

        Game.getG().setFill(new Color(0, 0, 0, 0.5));
        Game.getG().fillOval(this.x - this.range, this.y - this.range, this.range * 2, this.range * 2);

    }

    public int getDamage() {
        return damage;
    }

    public void sell() {
        Game.getMap().getTowers().remove(this);
        Block block = Game.getMap().getBlock(this.x, this.y);
        block.reconstruct(0, 0, Color.WHITE, 0);
        //Game.getMap().editMap_matrix(Game.pixelToIndex(this.getPixelX()), Game.pixelToIndex(this.getPixelY()), new Block(0, 0, new Color(1, 1, 1, 1), 0));
    }

    public int getHind() {
        return hind;
    }
}
