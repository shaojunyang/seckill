package org.seckill.enums;

/**
 * 定义 一个 枚举 类型
 */
public enum Color {
    //这些都是 一个 对象
    RED(10), GREEN, BLUE;

    private Color() {
        System.out.println("枚举的无参构造");
    }


    private int color;

    //枚举的构造方法必须是 私有化的
    private Color(int color) {
        this.color = color;
        System.out.println("枚举的有参构造");
    }

    public int getColor() {
        return color;
    }
}
