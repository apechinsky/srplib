package org.srplib.support;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Anton Pechinsky
 */
public class BeanBuilderTest {
    @Test
    public void testCreate() throws Exception {
        User.UserBuilder builder = BeanBuilder.create(new User(), User.UserBuilder.class);
        User user = builder
            .id("id")
            .name("name")
            .age(33)
            .build();

        Assert.assertThat(user.getId(), is("id"));
        Assert.assertThat(user.getName(), is("name"));
        Assert.assertThat(user.getAge(), is(33));
    }


    private static class User {

        private String id;

        private String name;

        private int age;

        public interface UserBuilder extends Builder<User> {
            UserBuilder id(String id);

            UserBuilder name(String name);

            UserBuilder age(int age);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
        }
    }
}
