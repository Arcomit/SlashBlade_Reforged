package mod.slashblade.reforged.utils.tuple;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author: til
 * @Description: 二元组封装类，用于存储两个不同类型的值
 * @param <A> 第一个值的类型
 * @param <B> 第二个值的类型
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Tuple2<A, B> {
    private final A a;
    private final B b;

    /**
     * 创建二元组的静态工厂方法
     * @param a 第一个值
     * @param b 第二个值
     * @param <A> 第一个值的类型
     * @param <B> 第二个值的类型
     * @return 新的二元组实例
     */
    public static <A, B> Tuple2<A, B> of(A a, B b) {
        return new Tuple2<>(a, b);
    }
}
