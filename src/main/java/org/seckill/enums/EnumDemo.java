package org.seckill.enums;


/**
 * 枚举
 *
 * @author yangshaojun
 * @create 2017-11-20 下午3:29
 **/

public class EnumDemo {
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;
    private static int color;

    //定义枚举类型
    public Color colorEnum;


    public void test1() {
        colorEnum = Color.RED;
        colorEnum = Color.GREEN;
        System.out.println(colorEnum);
        System.out.println(colorEnum.toString());
        System.out.println(colorEnum.name());
        //ordinal()方法 返回 枚举 的 序号
        System.out.println(colorEnum.ordinal());
        //values方法 获取 枚举中所有的值
        Color[] values = colorEnum.values();
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + "  ");
        }
    }


    public void test2() {
        System.out.println(Color.RED);
        System.out.println(colorEnum.RED);//RED
        System.out.println(colorEnum.RED.getColor());//10
    }
}
