package org.jemmy.fx;


import javafx.scene.Scene;
import org.jemmy.control.Wrap;
import org.jemmy.env.Environment;

/**
 *
 * @author shura
 */
class SceneWrapper implements org.jemmy.control.Wrapper {

    private Environment env;

    SceneWrapper(Environment env) {
        this.env = env;
    }

    public <T> Wrap<? extends T> wrap(Class<T> controlClass, T control) {
        if (controlClass.isAssignableFrom(Scene.class)) {
            return new SceneWrap(new Environment(env), (Scene) control);
        }
        return null;
    }
}
