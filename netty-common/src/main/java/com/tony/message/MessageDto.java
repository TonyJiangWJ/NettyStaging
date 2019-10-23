package com.tony.message;

import com.tony.constants.EnumNettyState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author jiangwenjie 2019/10/22
 */
@Data
@ToString
@EqualsAndHashCode
public class MessageDto implements Serializable {

    private String action;

    private int state = EnumNettyState.REQUEST.getState();

    private Serializable data;

    @SuppressWarnings("unchecked")
    public <T> T dataOfClazz(Class<T> clazz) {
        if (clazz.isInstance(data)) {
            return (T) data;
        } else {
            throw new IllegalArgumentException("data is not instance of class:" + clazz.getName());
        }
    }
}
