package com.ibroximjon.gym_rest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;



@ShellComponent
class UserCommand {

    private final String basePackagePath;

    public UserCommand() {
        // Asosiy Spring Boot application class'ini topish
        Package mainPackage = GymRestApplication.class.getPackage();
        if (mainPackage == null || mainPackage.getName().isBlank()) {
            throw new IllegalStateException("Loyihaning asosiy package'ini aniqlab bo‘lmadi.");
        }

        // Java folderidan keyingi package yo‘lini hosil qilish
        String packagePath = mainPackage.getName().replace(".", "/");
        this.basePackagePath = Paths.get("src", "main", "java", packagePath).toString();
    }

    @ShellMethod(key = "create package", value = "Creates a new package inside the project's base package")
    public String createPackage(@ShellOption String packageName) {
        // Foydalanuvchi kiritgan package nomini loyihaning root package ichiga joylashtirish
        String fullPackagePath = Paths.get(basePackagePath, packageName.replace(".", "/")).toString();
        File directory = new File(fullPackagePath);

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                return "Package '" + packageName + "' successfully created at " + fullPackagePath;
            } else {
                return "Failed to create package '" + packageName + "'.";
            }
        } else {
            return "Package '" + packageName + "' already exists.";
        }
    }


    @ShellMethod(key = "create model", value = "Creates a new model class inside the model package")
    public String createModel(@ShellOption String modelName) {
        // `model` package yo‘li
        String modelPackagePath = Paths.get(basePackagePath, "model").toString();
        File modelPackage = new File(modelPackagePath);
        if (!modelPackage.exists()) {
            modelPackage.mkdirs();
        }

        // Foydalanuvchi kiritgan class fayli
        String filePath = Paths.get(modelPackagePath, modelName + ".java").toString();
        File modelFile = new File(filePath);

        if (modelFile.exists()) {
            return "Model '" + modelName + "' already exists in model package.";
        }

        // Java entity class'ni yozish
        String packageName = GymRestApplication.class.getPackage().getName() + ".model";
        String classContent = """
            package %s;

            import jakarta.persistence.*;

            @Entity
            @Table(name = "%s")
            public class %s {
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;
                
                // TODO: Add more fields

                public %s() {}

                public Long getId() {
                    return id;
                }

                public void setId(Long id) {
                    this.id = id;
                }
            }
            """.formatted(packageName, modelName.toLowerCase(), modelName, modelName);

        try (FileWriter writer = new FileWriter(modelFile)) {
            writer.write(classContent);
            return "Model '" + modelName + "' successfully created at " + filePath;
        } catch (IOException e) {
            return "Failed to create model '" + modelName + "'. Error: " + e.getMessage();
        }
    }

    @ShellMethod(key = "create service", value = "Creates a new service class inside the model package")
    public String createService(@ShellOption String serviceName) {
        // `model` package yo‘li
        String modelPackagePath = Paths.get(basePackagePath, "service").toString();
        File modelPackage = new File(modelPackagePath);
        if (!modelPackage.exists()) {
            modelPackage.mkdirs();
        }

        // Foydalanuvchi kiritgan class fayli
        String filePath = Paths.get(modelPackagePath, serviceName + ".java").toString();
        File modelFile = new File(filePath);

        if (modelFile.exists()) {
            return "Model '" + serviceName + "' already exists in model package.";
        }

        // Java entity class'ni yozish
        String packageName = GymRestApplication.class.getPackage().getName() + ".service";
        String classContent = """
            package %s;

            import jakarta.persistence.*;
            import org.springframework.stereotype.Service;

            @Service
            public class %s {
                private UserRepository userRepository;
                
                public %s(UserRepository userRepository) {
                            this.userRepository = userRepository;
                        }

               
            }
            """.formatted(packageName, serviceName, serviceName, serviceName);

        try (FileWriter writer = new FileWriter(modelFile)) {
            writer.write(classContent);
            return "Service '" + serviceName + "' successfully created at " + filePath;
        } catch (IOException e) {
            return "Failed to create service '" + serviceName + "'. Error: " + e.getMessage();
        }
    }

    @ShellMethod(key = "create repository", value = "Creates a new repository interface inside the model package")
    public String createRepository(@ShellOption String repositoryName) {

        String modelPackagePath = Paths.get(basePackagePath, "repository").toString();
        File modelPackage = new File(modelPackagePath);
        if (!modelPackage.exists()) {
            modelPackage.mkdirs();
        }

        // Foydalanuvchi kiritgan class fayli
        String filePath = Paths.get(modelPackagePath, repositoryName + ".java").toString();
        File modelFile = new File(filePath);

        if (modelFile.exists()) {
            return "Repository '" + repositoryName + "' already exists in repository package.";
        }


        String packageName = GymRestApplication.class.getPackage().getName() + ".repository";
        String classContent = """
            package %s;

            import jakarta.persistence.*;
            import org.springframework.stereotype.Service;

            @Service
            public interface %s extends JpaRepository<User, Long>{
                private UserRepository userRepository;
            

              
            }
            """.formatted(packageName, repositoryName, repositoryName, repositoryName);

        try (FileWriter writer = new FileWriter(modelFile)) {
            writer.write(classContent);
            return "Repository '" + repositoryName + "' successfully created at " + filePath;
        } catch (IOException e) {
            return "Failed to create repository '" + repositoryName + "'. Error: " + e.getMessage();
        }
    }
}
