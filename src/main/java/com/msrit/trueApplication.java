package com.msrit;

import com.msrit.Entity.Course;
import com.msrit.Entity.Student;
import com.msrit.Entity.Todo;
import com.msrit.Entity.User;
import com.msrit.dao.StudentDAO;
import com.msrit.dao.TodoDAO;
import com.msrit.dao.UserDAO;
import com.msrit.resources.StudentResource;
import com.msrit.resources.TodoResources;
import com.msrit.resources.UserResource;
import com.msrit.resources.greet;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

public class trueApplication extends Application<trueConfiguration> {

    public static void main(final String[] args) throws Exception {
        new trueApplication().run(args);
    }

    @Override
    public String getName() {
        return "trueApplication";
    }

    // Helps in setting up the connection without explicitly mentioning sessionFactory instance

    private final HibernateBundle<trueConfiguration> hibernate =
            new HibernateBundle<trueConfiguration>(Student.class, Course.class, User.class, Todo.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(trueConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(final Bootstrap<trueConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final trueConfiguration configuration, final Environment environment) {
        // Register resources, health checks, and more here.
        StudentDAO studentDao = new StudentDAO(hibernate.getSessionFactory());
        UserDAO userDao = new UserDAO(hibernate.getSessionFactory());
        TodoDAO todoDAO = new TodoDAO(hibernate.getSessionFactory());
        environment.jersey().register(new greet());
        environment.jersey().register(new StudentResource(studentDao));
        environment.jersey().register(new UserResource(userDao,todoDAO));
        environment.jersey().register(new TodoResources(todoDAO));
    }
}
