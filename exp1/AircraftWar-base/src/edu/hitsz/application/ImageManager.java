package edu.hitsz.application;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.props.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage BACKGROUND_IMAGE_TWO;
    public static BufferedImage BACKGROUND_IMAGE_THREE;
    public static BufferedImage BACKGROUND_IMAGE_FOUR;
    public static BufferedImage BACKGROUND_IMAGE_FIVE;

    public static BufferedImage HERO_IMAGE;

    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;

    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;

    public static BufferedImage BLOOD_PROPS_IMAGE;
    public static BufferedImage BULLET_PROPS_IMAGE;
    public static BufferedImage BOMB_PROPS_IMAGE;
    static {
        try {

            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg_2.jpg"));
            BACKGROUND_IMAGE_TWO = ImageIO.read(new FileInputStream("src/images/bg2_2.jpg"));
            BACKGROUND_IMAGE_THREE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
            BACKGROUND_IMAGE_FOUR = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
            BACKGROUND_IMAGE_FIVE = ImageIO.read(new FileInputStream("src/images/bg5_2.jpg"));

            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero_3.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob_2.png"));

            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero_2.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy_2.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite_2.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss_2.png"));

            BLOOD_PROPS_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood_2.png"));
            BULLET_PROPS_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet_2.png"));
            BOMB_PROPS_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb_2.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BloodProps.class.getName(), BLOOD_PROPS_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BulletProps.class.getName(), BULLET_PROPS_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BombProps.class.getName(), BOMB_PROPS_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className) {
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj) {
        if (obj == null) {
            return null;
        }
        return get(obj.getClass().getName());
    }

}
